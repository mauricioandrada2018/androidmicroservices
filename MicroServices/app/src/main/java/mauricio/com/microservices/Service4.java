package mauricio.com.microservices;

import android.app.IntentService;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Micro-service in the "A" branch
 */
public class Service4 extends IntentService {

    public Service4() {
        super("");
    }

    public Service4(String name) {
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


            Messenger messenger = intent.getParcelableExtra("messenger");
            String request = intent.getStringExtra("request");

            try {
                JSONObject jsonObject = new JSONObject(request);
                String myRequest = "ASERV4";
                Message message = new Message();
                message.obj = jsonObject.get("result");

                //In case the parent Service only partially handled the request, this service completes it
                if (jsonObject.getString("cmd").equals(myRequest)) {

                    message.obj += "\nHandled by Service4 "+System.currentTimeMillis();

                }

                //This is the last Service down the "right" branch; send the accumulated response to the Activity
                messenger.send(message);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
