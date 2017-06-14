package com.mikeschen.www.hangboardrepeaters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ConverterActivity extends AppCompatActivity implements View.OnClickListener{
    @BindView(R.id.gradeConverterTextView) TextView mGradeConverterTextView;
    @BindView(R.id.huecoButton) Button mHuecoButton;
    @BindView(R.id.fontButton) Button mFontButton;
    @BindView(R.id.ydsButton) Button mYdsButton;
    @BindView(R.id.frenchButton) Button mFrenchButton;
    @BindView(R.id.uiaaButton) Button mUiaaButton;

    final String[] huecoMenu = {"VB", "V0", "V0+", "V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8", "V9", "V10", "V11", "V12", "V13", "V14", "V15", "V16", "V17"};
    final String[] hueco = {"VB", "VB", "VB", "VB", "VB", "VB", "VB", "VB", "VB", "VB", "V0", "V0+", "V1", "V2", "V2", "V3", "V4", "V4", "V5", "V5", "V6", "V7", "V8", "V8", "V9", "V10", "V11", "V12", "V13", "V14", "V15", "V16", "V17"};
    final String[] fontMenu = {"1", "1+", "2", "2+", "3", "3+", "4", "4+", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+", "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b", "8b+", "8c", "8c+", "9a"};
    final String[] font = {"1", "1", "1", "1", "1", "1+", "2", "2+", "3", "3+", "4", "4+", "5", "5+", "6a", "6a+", "6b", "6b+", "6c", "6c+", "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b", "8b+", "8c", "8c+", "9a"};
    final String[] yds = {"5.1", "5.2", "5.3", "5.4", "5.5", "5.6", "5.7", "5.8", "5.9", "5.10a", "5.10b", "5.10c", "5.10d", "5.11a", "5.11b", "5.11c", "5.11d", "5.12a", "5.12b", "5.12c", "5.12d", "5.13a", "5.13b", "5.13c", "5.13d", "5.14a", "5.14b", "5.14c", "5.14d", "5.15a", "5.15b", "5.15c", "5.15d"};
    final String[] french = {"2", "2+", "3", "3+", "4", "4+", "5a", "5b", "5c", "6a", "6a+", "6b", "6b+", "6c", "6c/6c+", "6c+", "7a", "7a+", "7b", "7b+", "7c", "7c+", "8a", "8a+", "8b", "8b+", "8c", "8c+", "9a", "9a+", "9b", "9b+", "9c"};
    final String[] uiaa = {"iii-", "iii", "iii+", "iv-", "iv", "iv+/v-", "v-/v", "v+/vi-", "vi-/vi", "vi/vi+", "vii-", "vii-/vii", "vii/vii+", "vii+", "viii-", "viii", "viii/viii+", "viii+", "ix-", "ix-/ix", "ix/ix+", "ix+", "x-", "x-/x", "x/x+", "x+", "xi-", "xi", "xi+", "xi+/xii-", "xii-/xii", "xii", "xii+"};
    ArrayList<Integer> universalGrades = new ArrayList<Integer>();
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_converter);
        ButterKnife.bind(this);
        mContext =  this;

        mHuecoButton.setOnClickListener(this);
        mFontButton.setOnClickListener(this);
        mYdsButton.setOnClickListener(this);
        mFrenchButton.setOnClickListener(this);
        mUiaaButton.setOnClickListener(this);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mGradeConverterTextView.setTypeface(custom_font);
        mHuecoButton.setTypeface(custom_font);
        mFontButton.setTypeface(custom_font);
        mYdsButton.setTypeface(custom_font);
        mFrenchButton.setTypeface(custom_font);
        mUiaaButton.setTypeface(custom_font);
    }

    private void gradeFinder(String grade, String[] gradeArray) {
        for (int i = 0; i < gradeArray.length; i++) {
            if (grade == gradeArray[i]) {
                universalGrades.add(i);
            }
        }
    }

    private void animate(Button button) {
        button.setScaleX(0.96f);
        button.setScaleY(0.96f);
        button.animate().scaleX(1).scaleY(1).start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case(R.id.huecoButton) :
                animate(mHuecoButton);
                GradeConverter(huecoMenu);
                break;
            case(R.id.fontButton) :
                animate(mFontButton);
                GradeConverter(fontMenu);
                break;
            case(R.id.ydsButton) :
                animate(mYdsButton);
                GradeConverter(yds);
                break;
            case(R.id.frenchButton) :
                animate(mFrenchButton);
                GradeConverter(french);
                break;
            case(R.id.uiaaButton) :
                animate(mFrenchButton);
                GradeConverter(uiaa);
                break;
        }
    }

    private void GradeConverter(final String[] gradeMenu) {
        RelativeLayout linearLayout = new RelativeLayout(mContext);
        final NumberPicker mGradeNumberPicker = new NumberPicker(mContext);
        mGradeNumberPicker.setMaxValue(gradeMenu.length - 1);
        mGradeNumberPicker.setMinValue(0);
        mGradeNumberPicker.setDisplayedValues(gradeMenu);
        mGradeNumberPicker.setWrapSelectorWheel(true);
//      mGradeNumberPicker.setValue();
        mGradeNumberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                universalGrades.clear();
                if (gradeMenu == huecoMenu) {
                    mHuecoButton.setText("Hueco: " + huecoMenu[newVal]);
                    gradeFinder(huecoMenu[newVal], hueco);
                    if (universalGrades.size() == 1) {
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)]);
                    } else {
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)] + " - " + yds[universalGrades.get(universalGrades.size() - 1)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)] + " - " + font[universalGrades.get(universalGrades.size() - 1)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)] + " - " + french[universalGrades.get(universalGrades.size() - 1)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)] + " - " + uiaa[universalGrades.get(universalGrades.size() - 1)]);
                    }
                } else if (gradeMenu == fontMenu) {
                    mFontButton.setText("Font: " + fontMenu[newVal]);
                    gradeFinder(fontMenu[newVal], font);
                    if (universalGrades.size() == 1) {
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)]);
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)]);
                    } else {
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)] + " - " + yds[universalGrades.get(universalGrades.size() - 1)]);
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)] + " - " + hueco[universalGrades.get(universalGrades.size() - 1)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)] + " - " + french[universalGrades.get(universalGrades.size() - 1)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)] + " - " + uiaa[universalGrades.get(universalGrades.size() - 1)]);
                    }
                } else if (gradeMenu == yds) {
                    mYdsButton.setText("YDS: " + yds[newVal]);
                    gradeFinder(yds[newVal], yds);
                    if (universalGrades.size() == 1) {
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)]);
                    } else {
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)] + " - " + hueco[universalGrades.get(universalGrades.size() - 1)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)] + " - " + font[universalGrades.get(universalGrades.size() - 1)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)] + " - " + french[universalGrades.get(universalGrades.size() - 1)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)] + " - " + uiaa[universalGrades.get(universalGrades.size() - 1)]);
                    }
                } else if (gradeMenu == uiaa) {
                    mUiaaButton.setText("UIAA: " + uiaa[newVal]);
                    gradeFinder(uiaa[newVal], uiaa);
                    if (universalGrades.size() == 1) {
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)]);
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)]);
                    } else {
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)] + " - " + hueco[universalGrades.get(universalGrades.size() - 1)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)] + " - " + font[universalGrades.get(universalGrades.size() - 1)]);
                        mFrenchButton.setText("French: " + french[universalGrades.get(0)] + " - " + french[universalGrades.get(universalGrades.size() - 1)]);
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)]);
                    }
                } else {
                    mFrenchButton.setText("French: " + french[newVal]);
                    gradeFinder(french[newVal], french);
                    if (universalGrades.size() == 1) {
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)]);
                        mHuecoButton.setText("Hueco: " + hueco[universalGrades.get(0)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)]);
                    } else {
                        mYdsButton.setText("Yds: " + yds[universalGrades.get(0)] + " - " + yds[universalGrades.get(universalGrades.size() - 1)]);
                        mFontButton.setText("Font: " + font[universalGrades.get(0)] + " - " + font[universalGrades.get(universalGrades.size() - 1)]);
                        mHuecoButton.setText("French: " + hueco[universalGrades.get(0)] + " - " + hueco[universalGrades.get(universalGrades.size() - 1)]);
                        mUiaaButton.setText("UIAA: " + uiaa[universalGrades.get(0)] + " - " + uiaa[universalGrades.get(universalGrades.size() - 1)]);
                    }
                }
            }
        });

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(gradeMenu.length - 1, gradeMenu.length - 1);
        RelativeLayout.LayoutParams numPicerParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

        linearLayout.setLayoutParams(params);
        linearLayout.addView(mGradeNumberPicker,numPicerParams);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setTitle("Select Grade");
        alertDialogBuilder.setView(linearLayout);
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {}
                        })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
