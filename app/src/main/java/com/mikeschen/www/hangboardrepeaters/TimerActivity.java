package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.hangTextView) TextView mHangTextView;
    @BindView(R.id.pauseTextView) TextView mPauseTextView;
    @BindView(R.id.restTextView) TextView mRestTextView;
    @BindView(R.id.roundTextView) TextView mRoundTextView;
    @BindView(R.id.setTextView) TextView mSetsTextView;
    @BindView(R.id.hangText) TextView mHangText;
    @BindView(R.id.pauseText) TextView mPauseText;
    @BindView(R.id.restText) TextView mRestText;
    @BindView(R.id.setsText) TextView mSetsText;
    @BindView(R.id.roundsText) TextView mRoundsText;
    @BindView(R.id.startButton) Button mStartButton;
    @BindView(R.id.soundButton) ImageButton mSoundButton;

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
    int roundCounter = 2;
    boolean newWorkoutSwitch = true;
    boolean flipState = true;
    boolean sound = false;

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
                mRoundsText.setText(roundCounter + "/" + rounds);
                roundCounter++;
            }
            if(i == rounds * 2 - 1) {
                roundCounter = 1;
                mRoundsText.setText(roundCounter + "/" + rounds);
                currentTimer = rest ;
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
                mStartButton.setText("stop");
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
        mStartButton.setOnClickListener(this);
        mSoundButton.setOnClickListener(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mHangTextView.setTypeface(custom_font);
        mPauseTextView.setTypeface(custom_font);
        mRestTextView.setTypeface(custom_font);
        mRoundTextView.setTypeface(custom_font);
        mSetsTextView.setTypeface(custom_font);
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
        mRoundsText.setText("1/" + rounds);
        mSetsText.setText("1/" + sets);
        mStartButton.setTypeface(custom_font);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.startButton):
                if (newWorkoutSwitch) {
                    new CountDownTimer(3000, 900) {
                        public void onTick(long millisUntilFinished) {
                            mStartButton.setText("Get Ready!  " + millisUntilFinished / 1000);
                        }
                        public void onFinish() {
                            startTime = System.currentTimeMillis();
                            timerHandler.postDelayed(timerRunnable, 0);
                            mStartButton.setText("stop");
                            newWorkoutSwitch = false;
                        }
                    }.start();
                }
                if (!newWorkoutSwitch) {
                    if (mStartButton.getText().equals("stop")) {
                        timerHandler.removeCallbacks(timerRunnable);
                        mStartButton.setText("start");
                    } else {
                        startTime = System.currentTimeMillis();
                        timerHandler.postDelayed(timerRunnable, 0);
                        mStartButton.setText("stop");
                    }
                }
                break;
            case (R.id.soundButton):
                if (sound) {
                    sound = false;
                    mSoundButton.setImageResource(R.drawable.ic_volume_off_white_24dp);
                } else {
                    sound = true;
                    mSoundButton.setImageResource(R.drawable.ic_volume_up_white_24dp);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_instruction:
                Intent intent = new Intent(this, InstructionActivity.class);
                this.startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

