package at.michaelkoenig.labor_11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String MSG_KEY = "msg";

    private int level = 1;
    private Handler msgHandler;
    private TextView txtNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNumber = findViewById(R.id.txtNumber);

        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                int number = b.getInt(MSG_KEY);
                txtNumber.setText(Integer.toString(number));
                System.out.println(txtNumber.getText().toString());
            }
        };

        new RandomNumberMessageThread(MSG_KEY, msgHandler, level).start();



    }
}
