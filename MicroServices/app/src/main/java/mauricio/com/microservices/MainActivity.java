package mauricio.com.microservices;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity implements Handler.Callback {

    private Messenger messenger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Handler handler = new Handler(this);
        messenger = new Messenger(handler);
    }

    @Override
    public boolean handleMessage(Message message) {

        ((TextView) findViewById(R.id.result)).append((CharSequence) message.obj);

        return true;
    }

    public void onClick(View v) {

        try {

            ((TextView) findViewById(R.id.result)).append("\nclicked "+System.currentTimeMillis()+"\n");

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cmd",v.getTag());
            jsonObject.put("result","");

            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),MainService.class);
            intent.putExtra("messenger",messenger);
            intent.putExtra("request",jsonObject.toString());
            startService(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
