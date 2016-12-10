package com.example.android.bookrentalsystemforcsumblibrary;

import android.content.DialogInterface;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import android.database.SQLException;

import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase;

import java.util.Date;

public class AccountCreationActivity extends AppCompatActivity implements TextView.OnEditorActionListener {

    int regexErrorCounter;
    int duplicateErrorCounter;
    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_creation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ((EditText)findViewById(R.id.user_field)).setOnEditorActionListener(this);
        ((EditText)findViewById(R.id.password_field)).setOnEditorActionListener(this);

        regexErrorCounter = 0;
        duplicateErrorCounter =0;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Too Many Failed Attempts!").setTitle("ERROR");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        dialog = builder.create();

    }

    private boolean checkRegEx(String text){
        String regExp = "^(?=.*\\d)(?=(.*([a-z]|[A-Z]){2}))(?=.*\\W).{5,20}$";

        return text.matches(regExp);
    }

    private boolean checkUserInput(View v){

        String text = ((TextView)v).getText().toString();
        String content = v.getContentDescription().toString();
        if(!checkRegEx(text)){
            Toast.makeText(getBaseContext(), content + " Has Incorrect Format!", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private LibraryUser createUserObject(){
        String userName = ((TextView)findViewById(R.id.user_field)).getText().toString();
        String userPassword = ((TextView)findViewById(R.id.password_field)).getText().toString();
        return new LibraryUser(userName, userPassword, 0);
    }

    private String getCurrentTime(){
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
        Date now = new Date();
        return sdfDate.format(now);
    }

    private void creationError(){
        if(duplicateErrorCounter >=2 || regexErrorCounter >= 2){
            dialog.show();
        }
    }

    public void createUser(View v){
        if(!checkUserInput(findViewById(R.id.user_field)) || !checkUserInput(findViewById(R.id.password_field))){
            regexErrorCounter++;
            creationError();
            return;
        }

        SystemDataBase db = new SystemDataBase(this);
        LibraryUser user = createUserObject();
        try {
            db.insertUser(user);
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "User Name Already Exists!", Toast.LENGTH_SHORT).show();
            duplicateErrorCounter++;
            creationError();
            return;
        }

        Toast.makeText(getBaseContext(), "User Name Created Successfully!", Toast.LENGTH_SHORT).show();
        LogConverter log = new LogConverter("New Account", user.getUserName(), getCurrentTime());
        db.insertLog(log);
        finish();

    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        String text = ((EditText)v).getText().toString();
        if (actionId == EditorInfo.IME_ACTION_DONE ||
                actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
            checkUserInput(v);
        }

        return false;
    }
}
