package com.mikeschen.www.hangboardrepeaters;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource;
import com.mikeschen.www.hangboardrepeaters.Databases.MySQLiteHelper;
import com.mikeschen.www.hangboardrepeaters.Models.Days;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogActivity extends ListActivity implements View.OnClickListener {
    private DaysDataSource datasource;
    public Context mContext;
    @BindView(R.id.completedTextView) TextView mCompletedTextView;
    @BindView(R.id.deleteButton) Button mDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        mContext = this;
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "Bebas.ttf");
        mCompletedTextView.setTypeface(custom_font);
        mDeleteButton.setTypeface(custom_font);


        mDeleteButton.setOnClickListener(this);
        datasource = new DaysDataSource(this);
        datasource.open();
        ListView lv = getListView();
        List<Days> values = datasource.getAllLogs();
        ArrayAdapter<Days> adapter = new ArrayAdapter<>(this, R.layout.white_text, values);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void animate() {
        mDeleteButton.setScaleX(0.96f);
        mDeleteButton.setScaleY(0.96f);
        mDeleteButton.animate().scaleX(1).scaleY(1).start();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case(R.id.deleteButton) :
                animate();
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Workouts");
                builder.setMessage("Are you sure you want to delete all your workouts?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        datasource.deleteAllLogs();
                        datasource.close();
                        refresh();
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.show();
                break;
        }
    }

    public void refresh() {
        Intent intent = new Intent(LogActivity.this,LogActivity.class);
        startActivity(intent);
        finish();
    }
}
