package com.example.android.bookrentalsystemforcsumblibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LogConverter;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.DetailActivity;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase;

import static com.example.android.bookrentalsystemforcsumblibrary.R.id.container;

public class HoldLogActivity extends AppCompatActivity {
    private ArrayAdapter<String> logAdapter;
    AlertDialog confirmationDialog;
    String holdToCancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hold_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initalizeListView();
        confirmationDialog = buildConfirmBox();
    }

    private void initalizeListView(){
        SystemDataBase db = new SystemDataBase(this);
        Intent intent = getIntent();

        logAdapter =
                new ArrayAdapter<>(
                        this, // The current context (this activity)
                        R.layout.list_item_log, // The name of the layout ID.
                        R.id.list_item_log_textview, // The ID of the textview to populate.
                        db.getHoldLogs(intent.getStringExtra(Intent.EXTRA_USER)));



        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = (ListView) findViewById(R.id.listview_holds);
        listView.setAdapter(logAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String log = logAdapter.getItem(position);
                confirmCancel(log);
            }
        });
    }

    private AlertDialog buildConfirmBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createCancelLog();
                initalizeListView();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });
        return builder.create();
    }

    private void confirmCancel(String log){
        holdToCancel = log;
        confirmationDialog.setMessage("Are you sure you want to cancel the following reservation?\n" + log);
        confirmationDialog.show();
    }

    private void createCancelLog(){
        String holdID = holdToCancel.split("\\t")[0];
        SystemDataBase db = new SystemDataBase(this);
        LogConverter log = new LogConverter(db.getLog(holdID), Integer.parseInt(holdID));
        db.insertLog(log);

    }

}
