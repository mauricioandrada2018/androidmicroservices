package mauricio.com.microservices;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the Service used by all the Activities to send request to the micro-services.
 * In this example the request/response are simple JSON strings but they can be any Parcelable object (e.g. a Bundle)
 * The request name will start with either 'A" for Action or 'Q' for Query; Action requests can be used by the appropriate Service to do something like store data or send something over the network.
 * Request commands will be used to return some data back to the Activity.
 * In our binary tree we'll put services that handle A requests on the "left branch" and Q requests on the "right branch" to reduce the number of Services triggered for each request.
 *
 * The MainService is only "aware" of 2 Services, the main entries for Action and Query requests; then each service is responsible for launching their own children as needed.
 *
 *
 */
public class MainService extends IntentService {

    public MainService() {
        super("");
    }

    public MainService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null) {

            /**
             * This is the Messenger that will be used by the micro-services to send responses back to the Activity
             */
            Messenger messenger = intent.getParcelableExtra("messenger");
            String request = intent.getStringExtra("request");

            try {

                JSONObject jsonObject = new JSONObject(request);
                Intent next = new Intent();
                next.putExtra("request", request);
                //the Messenger is a Parcelable so it can be sent to the Services in an Intent
                next.putExtra("messenger", messenger);

                //Here we make a decision about which "branch" of the tree will get the request
                if (jsonObject.getString("cmd").startsWith("A")) {

                    next.setClass(getApplicationContext(), Service1.class);

                } else {

                    next.setClass(getApplicationContext(), Service2.class);
                }

                startService(next);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
