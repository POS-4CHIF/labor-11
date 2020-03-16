package at.michaelkoenig.labor_11;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import java.util.Random;

public class RandomNumberMessageThread extends Thread {
    private String msgKey;
    private Handler msgHandler;
    private int count;

    public RandomNumberMessageThread(String msgKey, Handler msgHandler, int count) {
        this.msgKey = msgKey;
        this.msgHandler = msgHandler;
        this.count = count;
    }

    @Override

    public void run() {
        Random rand = new Random();
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
                if (i % 2 == 0)
                    b.putInt(msgKey, rand.nextInt(9) + 1);
                else
                    b.putInt(msgKey, Integer.MIN_VALUE);
                msg.setData(b);
                msgHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
