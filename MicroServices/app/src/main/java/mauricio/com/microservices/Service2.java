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
 * Micro-service in the "Q" branch
 */
public class Service2 extends IntentService {


    public Service2(String name) {
        super(name);

    }

    public Service2() {
        super("");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Messenger messenger;

        if (intent != null) {
            messenger = intent.getParcelableExtra("messenger");

            String request = intent.getStringExtra("request");

            try {
                JSONObject jsonObject = new JSONObject(request);
                String myRequest = "QSERV2";
                //The logic here is a little different; this service knows it will need help from Service6 so it sends the partially handled command to it
                if (jsonObject.getString("cmd").equals(myRequest)) {

                    String result = jsonObject.getString("result");
                    result = result + "\nHandled by Service2 "+System.currentTimeMillis();
                    jsonObject.put("result", result);
                    intent.putExtra("request", jsonObject.toString());

                    intent.setClass(getApplicationContext(), Service6.class);
                    startService(intent);


                } else {

                    //This is the normal case where the service doesn;t handle the command and send it down the pipe
                    intent.setClass(getApplicationContext(), Service5.class);
                    startService(intent);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
