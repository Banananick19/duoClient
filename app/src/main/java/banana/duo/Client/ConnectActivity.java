package banana.duo.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

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