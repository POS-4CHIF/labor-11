package at.michaelkoenig.labor_11;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

import java.util.Random;

public class RandomNumberAsyncTask extends AsyncTask<Void, Integer, Integer> {
    private String msgKey;
    private Handler msgHandler;
    private int count;
    private TextView txtNumber;
    private SetCorrectNumber callback;

    public RandomNumberAsyncTask(String msgKey, Handler msgHandler, int count, TextView txtNumber, SetCorrectNumber callback) {
        this.msgKey = msgKey;
        this.msgHandler = msgHandler;
        this.count = count;
        this.txtNumber = txtNumber;
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(Void... voids) {
        Random rand = new Random();
        int result = 0;

        // wait longer before first message
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < count * 2; i++) {
            try {
                // Sleep less when generating more numbers
                Thread.sleep(1000 / count);
                Message msg = new Message();
                Bundle b = new Bundle();

                // every second msg should be a blank
                if (i % 2 == 0) {
                    int number = rand.nextInt(9) + 1;
                    result = result * 10 + number;
                    publishProgress(number);
                } else {
                    publishProgress(Integer.MIN_VALUE);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        int currCorrectNumber = 0;
        for(int number : values) {
            if (number == Integer.MIN_VALUE) {
                txtNumber.setText("");
            } else {
                currCorrectNumber = currCorrectNumber * 10 + number;
                txtNumber.setText(Integer.toString(number));
            }
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        callback.setCorrectNumber(integer);
    }
}
