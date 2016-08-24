package com.mikeschen.www.hangboardrepeaters;

import android.app.ListActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource;
import com.mikeschen.www.hangboardrepeaters.Models.Days;

import java.util.List;

import butterknife.BindView;

public class LogActivity extends ListActivity {
    private DaysDataSource datasource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        datasource = new DaysDataSource(this);
        datasource.open();
        ListView lv = getListView();
        List<Days> values = datasource.getAllLogs();
        ArrayAdapter<Days> adapter = new ArrayAdapter<Days>(this,
                R.layout.white_text, values);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
