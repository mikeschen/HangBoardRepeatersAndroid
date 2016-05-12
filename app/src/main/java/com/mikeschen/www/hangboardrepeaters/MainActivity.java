package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.hangEditText) EditText mHangEditText;
    @BindView(R.id.pauseEditText) EditText mPauseEditText;
    @BindView(R.id.restEditText) EditText mRestEditText;
    @BindView(R.id.setsEditText) EditText mSetsEditText;
    @BindView(R.id.startButton) Button mStartButton;
    private SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        mStartButton.setOnClickListener(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mStartButton.setTypeface(custom_font);
        String hang = mSharedPreferences.getString(Constants.KEY_USER_HANG, null);
        String pause = mSharedPreferences.getString(Constants.KEY_USER_PAUSE, null);
        String rest = mSharedPreferences.getString(Constants.KEY_USER_REST, null);
        String sets = mSharedPreferences.getString(Constants.KEY_USER_SETS, null);
        if (hang != null) {
            mHangEditText.setText(hang);
        }
        if (hang != null) {
            mPauseEditText.setText(pause);
        }
        if (hang != null) {
            mRestEditText.setText(rest);
        }
        if (hang != null) {
            mSetsEditText.setText(sets);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, TimerActivity.class);
        startActivity(intent);
    }
}
