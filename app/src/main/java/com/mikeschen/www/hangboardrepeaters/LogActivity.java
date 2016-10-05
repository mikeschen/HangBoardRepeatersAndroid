package com.mikeschen.www.hangboardrepeaters;

import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource;
import com.mikeschen.www.hangboardrepeaters.Databases.MySQLiteHelper;
import com.mikeschen.www.hangboardrepeaters.Models.Days;

import java.util.List;

public class LogActivity extends ListActivity implements View.OnClickListener {
    private DaysDataSource datasource;
    public Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
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

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case(R.id.deleteButton) :
                Log.d("clicked", "clicked");
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Delete Data");
                builder.setMessage("Are you sure you want to delete your history?");
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
        }
    }
    public void refresh() {
        Intent intent = new Intent(LogActivity.this,LogActivity.class);
        startActivity(intent);
        finish();
    }
}
