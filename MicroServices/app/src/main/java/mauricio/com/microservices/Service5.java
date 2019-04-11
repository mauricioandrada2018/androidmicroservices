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

public class Service5 extends IntentService {

    public Service5() {
        super("");
    }

    public Service5(String name) {
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
                String myRequest = "QSERV5";
                Message message = new Message();
                message.obj = jsonObject.get("result");

                if (jsonObject.getString("cmd").equals(myRequest)) {

                    message.obj += "\nHandled by Service5 "+System.currentTimeMillis();

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
