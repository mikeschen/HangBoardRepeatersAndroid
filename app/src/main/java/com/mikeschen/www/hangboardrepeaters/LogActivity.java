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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;

import com.mikeschen.www.hangboardrepeaters.DataSources.DaysDataSource;
import com.mikeschen.www.hangboardrepeaters.Models.Days;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LogActivity extends ListActivity {
    private DaysDataSource datasource;
    public Context mContext;
    int recordCount;
    @BindView(R.id.completedTextView) TextView mCompletedTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);
        ButterKnife.bind(this);
        mContext = this;
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "BebasNeue.ttf");
        mCompletedTextView.setTypeface(custom_font);
        datasource = new DaysDataSource(this);
        datasource.open();
        recordCount = datasource.count();
        mCompletedTextView.setText(getString(R.string.completedworkouts, "", recordCount));
        final ListView lv = getListView();
        List<Days> values = datasource.getAllLogs();
        ArrayAdapter<Days> adapter = new ArrayAdapter<>(this, R.layout.white_text, values);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
            public void onItemClick(AdapterView<?> l, View v, int position, long id) {
                deleteOneButton(lv, position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void deleteOneButton(final ListView lv, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.deleteworkout));
        builder.setMessage(getString(R.string.workoutpopup));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                @SuppressWarnings("unchecked")
                ArrayAdapter<Days> adapter = (ArrayAdapter<Days>) lv.getAdapter();
                if (lv.getAdapter().getCount() > 0) {
                    Days log = (Days) lv.getAdapter().getItem(position);
                    datasource.deleteLog(log);
                    adapter.remove(log);
                    recordCount = datasource.count();
                    mCompletedTextView.setText(getString(R.string.completedworkouts, "", recordCount));
                }
                adapter.notifyDataSetChanged();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void deleteButton(View target) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(getString(R.string.deleteworkouts));
        builder.setMessage(getString(R.string.workoutspopup));
        builder.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                datasource.deleteAllLogs();
                datasource.close();
                refresh();
            }
        });

        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.show();
    }

    public void homeButton(View target) {
        startActivity(new Intent(LogActivity.this, MainActivity.class));
    }

    public void refresh() {
        Intent intent = new Intent(LogActivity.this,LogActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        datasource.close();
        super.onDestroy();
    }
}
