package at.michaelkoenig.labor_11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String MSG_KEY = "msg";

    private int level;
    private Handler msgHandler;
    private TextView txtNumber;
    private TextView txtCode;
    private int input;
    private int currCorrectNumber;
    private SharedPreferences prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences("at.michaelkoenig.labor_11", MODE_PRIVATE);
        level = prefs.getInt("LEVEL", 1);


        txtNumber = findViewById(R.id.txtNumber);
        txtCode = findViewById(R.id.txtCode);

        msgHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Bundle b = msg.getData();
                int number = b.getInt(MSG_KEY);

                System.out.println(number);

                if (number == Integer.MIN_VALUE) {
                    txtNumber.setText("");
                } else {
                    currCorrectNumber = currCorrectNumber * 10 + number;
                    txtNumber.setText(Integer.toString(number));
                }

            }
        };

        new RandomNumberMessageThread(MSG_KEY, msgHandler, level).start();

        findViewById(R.id.btn1).setOnClickListener(l -> registerInput(1));
        findViewById(R.id.btn2).setOnClickListener(l -> registerInput(2));
        findViewById(R.id.btn3).setOnClickListener(l -> registerInput(3));
        findViewById(R.id.btn4).setOnClickListener(l -> registerInput(4));
        findViewById(R.id.btn5).setOnClickListener(l -> registerInput(5));
        findViewById(R.id.btn6).setOnClickListener(l -> registerInput(6));
        findViewById(R.id.btn7).setOnClickListener(l -> registerInput(7));
        findViewById(R.id.btn8).setOnClickListener(l -> registerInput(8));
        findViewById(R.id.btn9).setOnClickListener(l -> registerInput(9));

    }

    public void registerInput(int number) {
        // while not all digits entered
        System.out.println("fads" + Integer.toString(input).length() + " " + level);
        if (input == 0 || Integer.toString(input).length() < level) {
            System.out.println("asdf");
            input = input * 10 + number;
            System.out.println(number);
            txtCode.setText(txtCode.getText() + "x");
        }
    }

    public void onBtnOkClick(View view) {
        // if all digits have been generated
        if (Integer.toString(input).length() == level) {
            if (input == currCorrectNumber) {
                level++;
                System.out.println("correct input");
            } else {
                level = 1;
                System.out.println("incorrect input");
            }

            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt("LEVEL", level);
            editor.commit();

            currCorrectNumber = 0;
            input = 0;
            txtCode.setText("");

            new RandomNumberMessageThread(MSG_KEY, msgHandler, level).start();
        }

    }

}
