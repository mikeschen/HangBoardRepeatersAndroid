package com.mikeschen.www.hangboardrepeaters;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateLogActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.logButton) Button mLogButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_log);
        ButterKnife.bind(this);
        
        mLogButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.logButton):
                Intent intent1 = new Intent(this, LogActivity.class);
                this.startActivity(intent1);
        }
    }

}
