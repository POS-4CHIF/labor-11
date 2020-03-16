package at.michaelkoenig.labor_11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity implements SetCorrectNumber {

    private static final String MSG_KEY = "msg";

    private int level;
    private Handler msgHandler;
    private TextView txtNumber;
    private TextView txtCode;
    private int input;
    private int currCorrectNumber;
    private SharedPreferences prefs;

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt("LEVEL", level);
        editor.commit();
    }

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

        startNextLevel();

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
        // while not all digits entered and all numbers have been shown
        if ((input == 0 || Integer.toString(input).length() < level) && Integer.toString(currCorrectNumber).length() == level) {
            System.out.println("asdf");
            input = input * 10 + number;
            System.out.println(number);
            txtCode.setText(txtCode.getText() + "x");
        }
    }

    public void onBtnOkClick(View view) {
        // if all digits have been generated
        if (Integer.toString(currCorrectNumber).length() == level) {
            if (input == currCorrectNumber) {
                level++;
                System.out.println("correct input");
            } else {
                level = 1;
                System.out.println("incorrect input");
            }

            currCorrectNumber = 0;
            input = 0;
            txtCode.setText(R.string.code);

            startNextLevel();

        }

    }

    private void startNextLevel() {
        if (level % 2 == 1) // Strategy 1
            new RandomNumberMessageThread(MSG_KEY, msgHandler, level).start();
        else // Strategy 2
            new RandomNumberAsyncTask(MSG_KEY, msgHandler, level, txtNumber, this).execute();
    }

    public void onResetButtonClick(View view) {
        // If all digits have been shown
        if (currCorrectNumber == 0 || Integer.toString(currCorrectNumber).length() == level) {
            level = 1;
            txtNumber.setText("");
            txtCode.setText(R.string.code);
            input = 0;
            currCorrectNumber = 0;
            startNextLevel();
        }
    }

    @Override
    public void setCorrectNumber(int correctNumber) {
        this.currCorrectNumber = correctNumber;
    }


}

interface SetCorrectNumber {
    void setCorrectNumber(int correctNumber);
}
