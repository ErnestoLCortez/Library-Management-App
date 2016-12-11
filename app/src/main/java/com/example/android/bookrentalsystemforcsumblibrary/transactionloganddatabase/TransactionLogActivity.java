package com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.bookrentalsystemforcsumblibrary.R;

public class TransactionLogActivity extends AppCompatActivity {

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_log);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new TransactionLogFragment())
                    .commit();
        }
        dialog = buildDialogBox();
    }

    private AlertDialog buildDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Create New Book?").setTitle("Admin Prompt");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                startActivity(new Intent(getBaseContext(), NewBook.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        return builder.create();
    }

    public void promptNewBook(View view){
        dialog.show();
    }

}
