package banana.duo.Client;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText ipEditText;
    private EditText portEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ipEditText = findViewById(R.id.ipEditText);
        portEditText = findViewById(R.id.portEditText);
    }

    public void onConnect(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);
        intent.putExtra("ip", ipEditText.getText().toString());
        intent.putExtra("port", Integer.valueOf(portEditText.getText().toString()));
        System.out.println(ipEditText.getText().toString() + ":" + portEditText.getText().toString());
        startActivity(intent);
    }
}