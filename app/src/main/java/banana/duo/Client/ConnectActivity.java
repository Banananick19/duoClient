package banana.duo.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;

import banana.duo.Common.Message;
import banana.duo.Common.MessageType;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class ConnectActivity extends AppCompatActivity {
    private String ip;
    private int port;
    private Client client;
    private TextView infoTextView;
    private ConnectTask connectTask;
    private Context context;
    private Resources resources;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        context = getApplicationContext();
        resources = context.getResources();
        Intent callerActivity = getIntent();
        this.ip = callerActivity.getStringExtra("ip");
        this.port = callerActivity.getIntExtra("port", R.string.defaultPort);
        this.infoTextView = findViewById(R.id.infoTextView);
        System.out.println(ip + ":" + port);
        connectTask = new ConnectTask();
        connectTask.execute(ip, String.valueOf(port));
        JoystickView joystick = (JoystickView) findViewById(R.id.joyStickView);
        joystick.setOnMoveListener(new JoystickView.OnMoveListener() {
            @Override
            public void onMove(int angle, int strength) {
                System.out.println(angle + "-" + strength);
                System.out.println(Math.cos(Math.toRadians(angle)) + "+" + Math.sin(Math.toRadians(angle)));
                int x = (int) (Math.cos(Math.toRadians(angle)) * strength) / 10;
                int y = - (int )  (Math.sin(Math.toRadians(angle)) * strength) / 10;
                SendTask sendTask = new SendTask();
                sendTask.execute(new Message(MessageType.MouseMove, x + "|" + y).toString());
            }
        });
    }

    class SendTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... strings) {
            try {
                client.sendMessage(Message.parseString(strings[0]));
                System.out.println("Sended");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

    }

    class ConnectTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                client = new Client(strings[0], Integer.parseInt(strings[1]));
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean hasConnection) {
            super.onPostExecute(hasConnection);
            String info = "";
            if (hasConnection) {
                info += (String) resources.getString(R.string.connectionMessage);
                System.out.println("connect");
            } else {
                info += (String) resources.getString(R.string.brokenConnectionMessage);
                System.out.println("no connect");
            }
            infoTextView.setText(info);
        }
    }
}