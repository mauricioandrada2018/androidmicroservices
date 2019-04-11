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
public class Service1 extends IntentService {

    public Service1() {
        super("");
    }

    public Service1(String name) {
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

            String request = intent.getStringExtra("request");
            Messenger messenger = intent.getParcelableExtra("messenger");

            try {
                JSONObject jsonObject = new JSONObject(request);

                String myRequest = "ASERV1";

                //Here the service decides if it will handle the command or cascade it to another service
                if (jsonObject.getString("cmd").equals(myRequest)) {

                    String result = jsonObject.getString("result");
                    result += "\nHandled by Service1 "+System.currentTimeMillis();

                    Message message = new Message();
                    message.obj = result;

                    //sending the response back to the Activity
                    messenger.send(message);

                } else {

                    //Not this service's request - cascade to both "left" and "right" services
                    intent.setClass(getApplicationContext(), Service3.class);
                    startService(intent);
                    intent.setClass(getApplicationContext(), Service4.class);
                    startService(intent);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
