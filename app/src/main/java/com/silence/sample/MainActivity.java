package com.silence.sample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.silence.countdownview.formatter.DefaultDateFormatter;
import com.silence.countdownview.formatter.SpannableTimeFormatter;
import com.silence.countdownview.view.CountdownTextView;

public class MainActivity extends AppCompatActivity {

    private CountdownTextView timer1;

    private CountdownTextView timer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer1 = (CountdownTextView) findViewById(R.id.countdown1);

        timer2 = (CountdownTextView) findViewById(R.id.countdown2);

        timer1.setCountdownDeadineTime(System.currentTimeMillis() + 100000)
                .start();

        timer2.setDataFormater(new SpannableTimeFormatter.Builder().build())
                .setCountdownDeadineTime(System.currentTimeMillis() + 1000 * 60 * 60 * 12)
                .start();
    }


}
