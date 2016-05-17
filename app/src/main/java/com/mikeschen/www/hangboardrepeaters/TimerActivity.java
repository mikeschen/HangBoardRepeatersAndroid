package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity {
    @BindView(R.id.hangTextView) TextView mHangTextView;
    @BindView(R.id.pauseTextView) TextView mPauseTextView;
    @BindView(R.id.restTextView) TextView mRestTextView;
    @BindView(R.id.setTextView) TextView mSetsTextView;
    @BindView(R.id.hangText) TextView mHangText;
    @BindView(R.id.pauseText) TextView mPauseText;
    @BindView(R.id.restText) TextView mRestText;
    @BindView(R.id.setsText) TextView mSetsText;
    @BindView(R.id.startButton) Button b;

    TextView timerText;
    TextView timerTextView;
    long startTime = 0;
    int hang;
    int pause;
    int rounds;
    int rest;
    int sets;
    int currentTimer;
    int i = 0;
    int counter = 2;
    boolean flipState = true;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
        long millis = System.currentTimeMillis() - startTime;
        int seconds = (int) (millis / 1000);
            int secondsDisplay = (int) (millis / 1000);
        int minutes = secondsDisplay / 60;
        secondsDisplay = seconds % 60;
            if (seconds == currentTimer) {
                timerTextView.setText(String.format("%d:%02d", 0, 0));
                timerText.animate()
                    .alpha(0.3f)
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(500);
                timerTextView.animate()
                    .alpha(0.3f)
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(500);
                timerHandler.removeCallbacks(timerRunnable);
                timerHandler.postDelayed(this, 500);
                startTime = System.currentTimeMillis();
                i++;
                if (flipState) {
                    currentTimer = pause;
                    timerText = mPauseTextView;
                    timerTextView = mPauseText;
                    flipState = false;
                } else {
                    currentTimer = hang;
                    timerText = mHangTextView;
                    timerTextView = mHangText;
                    flipState = true;
                }
                if(i == rounds * 2 - 1) {
                    currentTimer = rest ;
                    Log.d("time", currentTimer + "");
                    timerTextView = mRestText;
                    timerText = mRestTextView;
                    i = -1;
                    mSetsText.setText(counter + "/" + sets);
                    counter++;
                    flipState = false;
                }
                if(counter == sets + 2) {
                    timerHandler.removeCallbacks(timerRunnable);
                    mSetsText.setText("DONE");
                    b.setText("stop");
                }
            } else {
                timerText.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f);
                timerTextView.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f);
                timerTextView.setText(String.format("%d:%02d", minutes, secondsDisplay));
                timerHandler.postDelayed(this, 500);
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_timer);
        ButterKnife.bind(this);

        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mHangTextView.setTypeface(custom_font);
        mPauseTextView.setTypeface(custom_font);
        mRestTextView.setTypeface(custom_font);
        mHangTextView.animate()
                .alpha(0.3f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
        mPauseTextView.animate()
                .alpha(0.3f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
        mRestTextView.animate()
                .alpha(0.3f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
        mHangText.animate()
                .alpha(0.3f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
        mPauseText.animate()
                .alpha(0.3f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
        mRestText.animate()
                .alpha(0.3f)
                .scaleX(0.9f)
                .scaleY(0.9f)
                .setDuration(500);
        Intent intent = getIntent();
        hang = intent.getIntExtra("hang", 0);
        pause = intent.getIntExtra("pause", 0);
        rounds = intent.getIntExtra("rounds", 0);
        rest = intent.getIntExtra("rest", 0);
        sets = intent.getIntExtra("sets", 0);
        timerText = (TextView) findViewById(R.id.hangTextView);
        timerTextView = (TextView) findViewById(R.id.hangText);
        currentTimer = hang;
        mSetsText.setText("1/" + sets);
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

