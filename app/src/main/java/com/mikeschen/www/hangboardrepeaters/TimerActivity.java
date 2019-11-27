package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.os.Build;

import com.mikeschen.www.hangboardrepeaters.Presenters.TimerActivityPresenter;
import com.mikeschen.www.hangboardrepeaters.Views.TimerActivityView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TimerActivity extends AppCompatActivity implements TimerActivityView, View.OnClickListener {
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
    boolean soundSwitch = true;
    int buttonchimeId;
    int pausechimeId;
    int restwarningId;
    int endAlarmId;

    SoundPool ourSounds;

    TimerActivityPresenter presenter;

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {

        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;
            int seconds = (int) (millis / 1000);
            int countdownDisplay = currentTimer - seconds;
            int minutes = countdownDisplay / 60;
            int secondsDisplay = countdownDisplay % 60;
            if (seconds == currentTimer) {
                timerTextView.setText(String.format("%d:%02d", 0, 0));
                fade(timerText);
                fade(timerTextView);
                timerHandler.removeCallbacks(timerRunnable);
                timerHandler.postDelayed(this, 500);
                startTime = System.currentTimeMillis();
                i++;
                if (soundSwitch) {
                    if(i == rounds * 2 - 1) {
                        ourSounds.play(restwarningId, 0.9f, 0.9f, 1, 0, 1);
                    } else if(flipState) {
                        ourSounds.play(pausechimeId, 0.9f, 0.9f, 1, 0, 1);
                    } else {
                        ourSounds.play(buttonchimeId, 0.9f, 0.9f, 1, 0, 1);
                    }
                }
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
                    currentTimer = rest;
                    timerTextView = mRestText;
                    timerText = mRestTextView;
                    i = -1;
                    mSetsText.setText(counter + "/" + sets);
                    counter++;
                    flipState = false;
                }
                if(counter == sets + 2) {
                    timerHandler.removeCallbacks(timerRunnable);
                    if(soundSwitch) {
                        ourSounds.play(endAlarmId, 0.7f, 0.7f, 1, 0, 1);
                    }
                    mSetsText.setText(getString(R.string.done));
                    fade(mRoundTextView);
                    fade(mRoundsText);
                    mStartButton.setText(getString(R.string.logworkout));
                }
            } else {
                timerText.animate()
                    .alpha(1f)
                    .scaleX(1.1f)
                    .scaleY(1.1f);
                timerTextView.animate()
                    .alpha(1f)
                    .scaleX(1.1f)
                    .scaleY(1.1f);
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

        presenter = new TimerActivityPresenter(this);

        mStartButton.setOnClickListener(this);
        mSoundButton.setOnClickListener(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "BebasNeue.ttf");
        mHangTextView.setTypeface(custom_font);
        mPauseTextView.setTypeface(custom_font);
        mRestTextView.setTypeface(custom_font);
        mRoundTextView.setTypeface(custom_font);
        mSetsTextView.setTypeface(custom_font);
        fade(mHangTextView);
        fade(mPauseTextView);
        fade(mRestTextView);
        fade(mHangText);
        fade(mPauseText);
        fade(mRestText);
        Intent intent = getIntent();
        hang = intent.getIntExtra("hang", 0);
        pause = intent.getIntExtra("pause", 0);
        rounds = intent.getIntExtra("rounds", 0);
        rest = intent.getIntExtra("rest", 0);
        sets = intent.getIntExtra("sets", 0);
        timerText = findViewById(R.id.hangTextView);
        timerTextView = findViewById(R.id.hangText);
        currentTimer = hang;
        mRoundsText.setText("1/" + rounds);
        mSetsText.setText("1/" + sets);
        mStartButton.setTypeface(custom_font);
        initializeSoundPool();
    }

    private void initializeSoundPool() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AudioAttributes audioAttributes = new AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .build();

            ourSounds = new SoundPool.Builder()
                    .setMaxStreams(4)
                    .setAudioAttributes(audioAttributes)
                    .build();

            buttonchimeId = ourSounds.load(this, R.raw.electricchime, 1);
            pausechimeId = ourSounds.load(this, R.raw.pausechime, 1);
            restwarningId = ourSounds.load(this, R.raw.buttonchime, 1);
            endAlarmId = ourSounds.load(this, R.raw.endalarm, 1);
        } else {
            ourSounds = new SoundPool(3, AudioManager.STREAM_MUSIC, 1);
            buttonchimeId = ourSounds.load(this, R.raw.electricchime, 1);
            pausechimeId = ourSounds.load(this, R.raw.pausechime, 1);
            restwarningId = ourSounds.load(this, R.raw.buttonchime, 1);
            endAlarmId = ourSounds.load(this, R.raw.endalarm, 1);
        }
    }

    private void animateButton() {
        mStartButton.setScaleX(0.96f);
        mStartButton.setScaleY(0.96f);
        mStartButton.animate().scaleX(1).scaleY(1).start();
    }

    private void fade(TextView fadeText) {
        fadeText.animate()
                .alpha(0.3f)
                .scaleX(0.8f)
                .scaleY(0.8f)
                .setDuration(500);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.startButton):
                if(mStartButton.getText().equals(getString(R.string.logworkout))) {
                    Intent intent1 = new Intent(this, CreateLogActivity.class);
                    intent1.putExtra("hang", hang);
                    intent1.putExtra("pause", pause);
                    intent1.putExtra("rounds", rounds);
                    intent1.putExtra("rest", rest);
                    intent1.putExtra("sets", sets);
                    this.startActivity(intent1);
                } else {
                    animateButton();
                    presenter.startRunnableButtonClicked();
                }
                break;
            case (R.id.soundButton):
                if (soundSwitch) {
                    soundSwitch = false;
                    mSoundButton.setImageResource(R.drawable.ic_volume_off_white_36dp);
                } else {
                    soundSwitch = true;
                    mSoundButton.setImageResource(R.drawable.ic_volume_up_white_36dp);
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
                Intent instructionIntent = new Intent(this, InstructionActivity.class);
                this.startActivity(instructionIntent);
                break;
            case R.id.action_log:
                Intent logIntent = new Intent(this, LogActivity.class);
                this.startActivity(logIntent);
                break;
            case R.id.action_converter:
                Intent converterIntent = new Intent(this, ConverterActivity.class);
                this.startActivity(converterIntent);
                break;
            case R.id.action_tick:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://grypped.netlify.com"));
                startActivity(browserIntent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        if (timerRunnable != null)
            timerHandler.removeCallbacks(timerRunnable);
        super.onDestroy();
        ourSounds.release();
    }

    @Override
    public void startRunnable() {
        if (newWorkoutSwitch) {
            new CountDownTimer(5000, 1000) {
                public void onTick(long millisUntilFinished) {
                    mStartButton.setText(getString(R.string.getready) + " " + millisUntilFinished / 1000);
                }
                public void onFinish() {
                    if(soundSwitch) {
                        ourSounds.play(buttonchimeId, 0.9f, 0.9f, 1, 0, 1);
                    }
                    startTime = System.currentTimeMillis();
                    timerHandler.postDelayed(timerRunnable, 0);
                    mStartButton.setText(getString(R.string.stop));
                    newWorkoutSwitch = false;
                }
            }.start();
        }
        if (!newWorkoutSwitch) {
            if (mStartButton.getText().equals(getString(R.string.stop))) {
                timerHandler.removeCallbacks(timerRunnable);
                mStartButton.setText(getString(R.string.start));
            } else {
                startTime = System.currentTimeMillis();
                timerHandler.postDelayed(timerRunnable, 0);
                mStartButton.setText(getString(R.string.stop));
            }
        }
    }
}

