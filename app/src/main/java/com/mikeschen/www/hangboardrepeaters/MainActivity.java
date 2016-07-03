package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.hangTimeTextView) TextView mHangTimeTextView;
    @BindView(R.id.pauseTimeTextView) TextView mPauseTimeTextView;
    @BindView(R.id.restTimeTextView) TextView mRestTimeTextView;
    @BindView(R.id.roundNumberTextView) TextView mRoundNumberTextView;
    @BindView(R.id.setsTimeTextView) TextView mSetsTimeTextView;
    @BindView(R.id.hangEditText) EditText mHangEditText;
    @BindView(R.id.pauseEditText) EditText mPauseEditText;
    @BindView(R.id.roundsEditText) EditText mRoundsEditText;
    @BindView(R.id.restEditText) EditText mRestEditText;
    @BindView(R.id.setsEditText) EditText mSetsEditText;
    @BindView(R.id.startButton) Button mStartButton;
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
        mSharedPreferencesEditor = mSharedPreferences.edit();
        mStartButton.setOnClickListener(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mHangTimeTextView.setTypeface(custom_font);
        mPauseTimeTextView.setTypeface(custom_font);
        mRestTimeTextView.setTypeface(custom_font);
        mRoundNumberTextView.setTypeface(custom_font);
        mSetsTimeTextView.setTypeface(custom_font);
        mStartButton.setTypeface(custom_font);
        String hang = mSharedPreferences.getString(Constants.KEY_USER_HANG, null);
        String pause = mSharedPreferences.getString(Constants.KEY_USER_PAUSE, null);
        String rounds = mSharedPreferences.getString(Constants.KEY_USER_ROUNDS, null);
        String rest = mSharedPreferences.getString(Constants.KEY_USER_REST, null);
        String sets = mSharedPreferences.getString(Constants.KEY_USER_SETS, null);
        if (hang != null) {
            mHangEditText.setText(hang);
        }
        if (pause != null) {
            mPauseEditText.setText(pause);
        }
        if (rounds != null) {
            mRoundsEditText.setText(rounds);
        }
        if (rest != null) {
            mRestEditText.setText(rest);
        }
        if (sets != null) {
            mSetsEditText.setText(sets);
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

    @Override
    public void onClick(View v) {
        try {
            int hang = Integer.parseInt(mHangEditText.getText().toString());
            mSharedPreferencesEditor.putString(Constants.KEY_USER_HANG, mHangEditText.getText().toString()).apply();
            int pause = Integer.parseInt(mPauseEditText.getText().toString());
            mSharedPreferencesEditor.putString(Constants.KEY_USER_PAUSE, mPauseEditText.getText().toString()).apply();
            int rounds = Integer.parseInt(mRoundsEditText.getText().toString());
            mSharedPreferencesEditor.putString(Constants.KEY_USER_ROUNDS, mRoundsEditText.getText().toString()).apply();
            int rest = Integer.parseInt(mRestEditText.getText().toString());
            mSharedPreferencesEditor.putString(Constants.KEY_USER_REST, mRestEditText.getText().toString()).apply();
            int sets = Integer.parseInt(mSetsEditText.getText().toString());
            mSharedPreferencesEditor.putString(Constants.KEY_USER_SETS, mSetsEditText.getText().toString()).apply();
            Intent intent = new Intent(MainActivity.this, TimerActivity.class);
            intent.putExtra("hang", hang);
            intent.putExtra("pause", pause);
            intent.putExtra("rounds", rounds);
            intent.putExtra("rest", rest);
            intent.putExtra("sets", sets);
            startActivity(intent);
        } catch(NumberFormatException e) {
            mHangEditText.setHint("Set Sec.");
            mPauseEditText.setHint("Set Sec.");
            mRoundsEditText.setHint("Set Num.");
            mRestEditText.setHint("Set Sec.");
            mSetsEditText.setHint("Set Sets");
        }
    }
}
