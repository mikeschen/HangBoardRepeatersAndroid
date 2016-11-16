package com.mikeschen.www.hangboardrepeaters;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConverterActivity extends AppCompatActivity {
    @BindView(R.id.huecoTextView) TextView mHuecoTextView;
    @BindView(R.id.gradeNumberPicker) NumberPicker mGradeNumberPicker;
    @BindView(R.id.ydsTextView) TextView mYdsTextView;

    final String[] huecoMenu = {"VB", "V0", "V0+", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12", "V13", "V14", "V15", "V16", "V17"};
    final String[] hueco = {"VB", "VB", "VB", "VB", "VB", "VB", "VB", "VB", "VB", "VB", "V0", "V0+", "V1", "V2", "V2", "V3", "V4", "V4", "V5", "V5", "V6", "V7", "V8", "V8", "V9", "V10", "V11", "V12", "V13", "V14", "V15", "V16", "V17"};
    final String[] yds = {"5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10a", "5.10b", "5.10c", "5.10d", "5.11a", "5.11b", "5.11c", "5.11d", "5.12a", "5.12b", "5.12c", "5.12d", "5.13a", "5.13b", "5.13c", "5.13d", "5.14a", "5.14b", "5.14c", "5.14d", "5.15a", "5.15b", "5.15c", "5.15d"};
    ArrayList<Integer> universalGrades = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ButterKnife.bind(this);

        mGradeNumberPicker.setMinValue(0);
        mGradeNumberPicker.setMaxValue(huecoMenu.length-1);

        mGradeNumberPicker.setDisplayedValues(huecoMenu);
        mGradeNumberPicker.setWrapSelectorWheel(true);

        mGradeNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal){
                universalGrades.clear();
                mHuecoTextView.setText("Hueco: " + huecoMenu[newVal]);
                gradeConverter(huecoMenu[newVal]);
                Log.d("heres the log!!", "" + universalGrades);
                mYdsTextView.setText("Yds: " + yds[universalGrades.get(0)] + "-" + yds[universalGrades.get(universalGrades.size() - 1)]);
            }
        });
    }

    private void gradeConverter(String grade) {
        for (int i = 0; i < hueco.length; i++) {
            if (grade == hueco[i]) {
                universalGrades.add(i);
            }
        }
    }
}
