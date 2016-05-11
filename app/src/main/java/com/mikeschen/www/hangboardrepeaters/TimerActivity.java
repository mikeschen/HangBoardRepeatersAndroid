package com.mikeschen.www.hangboardrepeaters;

import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity {
    @BindView(R.id.hangTextView) TextView mHangTextView;
    @BindView(R.id.pauseTextView) TextView mPauseTextView;
    @BindView(R.id.restTextView) TextView mRestTextView;
    @BindView(R.id.hangText) TextView mHangText;
    @BindView(R.id.pauseText) TextView mPauseText;
    @BindView(R.id.restText) TextView mRestText;
    @BindView(R.id.startButton) Button b;

    TextView timerTextView;
    TextView pauseText;
    long startTime = 0;
    int hang = 2;
    int currentTimer = hang;
    int pause = 5;
    int rest = 10;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            if (seconds == currentTimer) {
                Log.d("thats 10", seconds + "time");
                timerTextView.setText(String.format("%d:%02d", 0, 0));
                timerTextView.animate()
                        .alpha(0.3f)
                        .scaleX(0.9f)
                        .scaleY(0.9f)
                        .setDuration(500);
                timerTextView = mPauseText;
                mPauseText = mRestText;
                timerHandler.removeCallbacks(timerRunnable);
                timerHandler.postDelayed(this, 500);
                startTime = System.currentTimeMillis();
                currentTimer = pause;
                pause = rest;

            } else {
                timerTextView.animate()
                        .alpha(1f)
                        .scaleX(1f)
                        .scaleY(1f);
                timerTextView.setText(String.format("%d:%02d", minutes, seconds));
                timerHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mHangTextView.setTypeface(custom_font);
        mPauseTextView.setTypeface(custom_font);
        mRestTextView.setTypeface(custom_font);
        timerTextView = (TextView) findViewById(R.id.hangText);
        b.setTypeface(custom_font);
        b.setText("start");
        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                if (b.getText().equals("stop")) {
                    timerHandler.removeCallbacks(timerRunnable);
                    b.setText("start");
                } else {
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    b.setText("stop");
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        timerHandler.removeCallbacks(timerRunnable);
        Button b = (Button)findViewById(R.id.startButton);
        b.setText("start");
    }
}

