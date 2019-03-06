package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.preference.PreferenceManager;
import android.widget.EditText;
import android.widget.RadioButton;

import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateLogActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.sizeEditText) EditText mSizeEditText;
    @BindView(R.id.weightEditText) EditText mWeightEditText;
    @BindView(R.id.logButton) Button mLogButton;
    @BindView(R.id.lbsButton) RadioButton mLbsButton;
    @BindView(R.id.kgButton) RadioButton mKgButton;

    int hang;
    int pause;
    int rounds;
    int rest;
    int sets;
    private DaysDataSource datasource;
    String weightUnit = "lbs";
    String size;
    String weight;
    String sizeLog = "";
    String weightLog = "";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mSharedPreferencesEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_log);
        ButterKnife.bind(this);
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(CreateLogActivity.this);
        mSharedPreferencesEditor = mSharedPreferences.edit();
        Intent intent = getIntent();
        hang = intent.getIntExtra("hang", 0);
        pause = intent.getIntExtra("pause", 0);
        rounds = intent.getIntExtra("rounds", 0);
        rest = intent.getIntExtra("rest", 0);
        sets = intent.getIntExtra("sets", 0);
        size = mSharedPreferences.getString(Constants.KEY_USER_SIZE, null);
        weight = mSharedPreferences.getString(Constants.KEY_USER_WEIGHT, null);
        if (size != null) {
            mSizeEditText.setText(size);
        }
        if (weight != null) {
            mWeightEditText.setText(weight);
        }
        mLogButton.setOnClickListener(this);
        mLbsButton.setOnClickListener(this);
        mKgButton.setOnClickListener(this);
        loadRadioButtons();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.lbsButton):
                weightUnit = "lbs";
                saveRadioButtons();
                break;
            case (R.id.kgButton):
                weightUnit = "kg";
                saveRadioButtons();
                break;
            case (R.id.logButton):
                datasource = new DaysDataSource(CreateLogActivity.this);
                datasource.open();
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - hh:mm a -");
                Date date = new Date();
                String formattedDate = dateFormat.format(date);
                String getSizeEditText = mSizeEditText.getText().toString();
                mSharedPreferencesEditor.putString(Constants.KEY_USER_SIZE, mSizeEditText.getText().toString()).apply();
                String getWeightEditText = mWeightEditText.getText().toString();
                mSharedPreferencesEditor.putString(Constants.KEY_USER_WEIGHT, mWeightEditText.getText().toString()).apply();
                if (!getSizeEditText.isEmpty()) {
                    sizeLog = " Hold Size: " + getSizeEditText + "mm,";
                }
                if (!getWeightEditText.isEmpty()) {
                    weightLog = " Weight: " + mWeightEditText.getText().toString() + weightUnit + ",";
                }
                String workOutStats = " Hang: " + hang + ", Pause: " + pause + ", Rounds: " + rounds + ", Rest: " + rest + ", Sets: " + sets;
                String logs = formattedDate + sizeLog + weightLog + workOutStats;
                datasource.createLog(logs);
                datasource.close();
                Intent intent1 = new Intent(this, LogActivity.class);
                this.startActivity(intent1);
                break;
        }
    }

    public void saveRadioButtons(){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean("Lbs", mLbsButton.isChecked());
        editor.putBoolean("Kg", mKgButton.isChecked());
        editor.apply();
    }

    public void loadRadioButtons(){
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mLbsButton.setChecked(mSharedPreferences.getBoolean("Lbs", false));
        mKgButton.setChecked(mSharedPreferences.getBoolean("Kg", false));
    }
}
