package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateLogActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.logButton) Button mLogButton;

    int hang;
    int pause;
    int rounds;
    int rest;
    int sets;
    private DaysDataSource datasource;
    private static final String TAG = "MyActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_log);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        hang = intent.getIntExtra("hang", 0);
        pause = intent.getIntExtra("pause", 0);
        rounds = intent.getIntExtra("rounds", 0);
        rest = intent.getIntExtra("rest", 0);
        sets = intent.getIntExtra("sets", 0);
        Log.d(TAG, "hang: @@@@@@@@@@@@@@@@@@" + hang);
        mLogButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.logButton):
                datasource = new DaysDataSource(CreateLogActivity.this);
                datasource.open();
                DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy - hh:mm a");
                Date date = new Date();
                String formattedDate = dateFormat.format(date);
                String workOutStats = " - Hang: " + hang + ", Pause: " + pause + ", Rounds: " + rounds + ", Rest: " + rest + ", Sets: " + sets;
                String logs = formattedDate + workOutStats;
                datasource.createLog(logs);
                datasource.close();
                Intent intent1 = new Intent(this, LogActivity.class);
                this.startActivity(intent1);
        }
    }

}
