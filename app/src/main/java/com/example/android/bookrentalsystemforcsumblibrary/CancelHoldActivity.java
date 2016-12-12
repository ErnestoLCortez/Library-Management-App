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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LibraryUser;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.TransactionLogActivity;

public class CancelHoldActivity extends AppCompatActivity {

    private TextView userField;
    private TextView passwordField;
    private AlertDialog errorDialog;
    private int errorCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userField = ((TextView)findViewById(R.id.user_field));
        passwordField = ((TextView)findViewById(R.id.password_field));
        errorDialog = buildDialogBox();
        errorCounter = 0;
    }

    private AlertDialog buildDialogBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Too Many Failed Attempts!").setTitle("ERROR");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });
        return builder.create();
    }

    private void loginFailed(){
        errorCounter++;
        if(errorCounter >= 2){
            errorDialog.show();
        }
    }

    public void signIn(View view){
        SystemDataBase db = new SystemDataBase(this);
        LibraryUser user = db.getUser(userField.getText().toString(), passwordField.getText().toString());

        if(user == null){
            Toast.makeText(getBaseContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            loginFailed();
            return;
        }

        startActivity(new Intent(view.getContext(), HoldLogActivity.class)
                .putExtra(Intent.EXTRA_USER, userField.getText().toString()));
        finish();
    }

}
