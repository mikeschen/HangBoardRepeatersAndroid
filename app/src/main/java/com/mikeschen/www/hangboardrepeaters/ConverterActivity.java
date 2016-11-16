package com.mikeschen.www.hangboardrepeaters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.NumberPicker;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConverterActivity extends AppCompatActivity {
    @BindView(R.id.gradeTextView) TextView mGradeTextView;
    @BindView(R.id.gradeNumberPicker) NumberPicker mGradeNumberPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ButterKnife.bind(this);

        final String[] huecoMenu = {"vb", "v0", "v0+", "v1", "v2", "v2", "v3", "v4", "v4", "v5", "v5", "v6", "v7", "v8", "v8", "v9", "v10", "v11", "v12", "v13", "v14", "v15", "v16", "v17"};
        final String[] hueco = {"vb", "vb", "vb", "vb", "vb", "vb", "vb", "vb", "vb", "vb", "v0", "v0+", "v1", "v2", "v2", "v3", "v4", "v4", "v5", "v5", "v6", "v7", "v8", "v8", "v9", "v10", "v11", "v12", "v13", "v14", "v15", "v16", "v17"};

        mGradeNumberPicker.setMinValue(0);
        mGradeNumberPicker.setMaxValue(huecoMenu.length-1);

        mGradeNumberPicker.setDisplayedValues(huecoMenu);
        mGradeNumberPicker.setWrapSelectorWheel(true);

        mGradeNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                mGradeTextView.setText("Selected value : " + huecoMenu[newVal]);
            }
        });
    }
}
