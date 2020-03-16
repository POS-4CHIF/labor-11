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
        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(1000);
                Message msg = new Message();
                Bundle b = new Bundle();
                b.putInt(msgKey, rand.nextInt(10) + 1);
                msg.setData(b);
                msgHandler.sendMessage(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
