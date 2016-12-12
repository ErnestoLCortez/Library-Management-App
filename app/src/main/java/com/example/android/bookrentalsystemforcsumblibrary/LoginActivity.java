package com.example.android.bookrentalsystemforcsumblibrary;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LibraryBook;
import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LibraryUser;
import com.example.android.bookrentalsystemforcsumblibrary.helperobjects.LogConverter;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.TransactionLogActivity;

import java.text.NumberFormat;
import java.util.Date;

import static android.R.attr.author;

public class LoginActivity extends AppCompatActivity {
    private TextView userField;
    private TextView passwordField;
    private AlertDialog errorDialog;
    private AlertDialog confirmationDialog;
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
        confirmationDialog = buildConfirmBox();
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

    private AlertDialog buildConfirmBox(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                createHold();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                //
            }
        });
        return builder.create();
    }

    private void updateStringValues(String bookInfo, String userName, String pickupDate, String returnDate){

        String confirmationMessage = "Is this following information correct?\n" +
                "User Name:\t" + userName +
                "\nPickup Date:\t" + pickupDate +
                "\nReturn Date:\t" + returnDate + "\n";

        confirmationDialog.setMessage(confirmationMessage);
    }

    private void loginFailed(){
        errorCounter++;
        if(errorCounter >= 2){
            errorDialog.show();
        }
    }

    private void createHold(){

    }

    public void signIn(View view){
        SystemDataBase db = new SystemDataBase(this);
        LibraryUser user = db.getUser(userField.getText().toString(), passwordField.getText().toString());

        if(user == null){
            Toast.makeText(getBaseContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            loginFailed();
            return;
        }

        Toast.makeText(getBaseContext(), "Login Information Valid", Toast.LENGTH_SHORT).show();

        Intent intent = getIntent();
        String bookInfo = "";
        String pickupDate = "";
        String returnDate = "";
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            bookInfo = intent.getStringExtra(intent.EXTRA_TEXT);
            pickupDate = sdfDate.format((Date)intent.getSerializableExtra("PICKUPDATE"));
            returnDate = sdfDate.format((Date)intent.getSerializableExtra("RETURNDATE"));
        }

        LibraryBook book = new LibraryBook(bookInfo);
        updateStringValues(bookInfo, user.getUserName(), pickupDate, returnDate);
        confirmationDialog.show();
    }

}
