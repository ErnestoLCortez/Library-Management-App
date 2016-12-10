package com.example.android.bookrentalsystemforcsumblibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.SystemDataBase;
import com.example.android.bookrentalsystemforcsumblibrary.transactionloganddatabase.TransactionLogActivity;

public class ManageSystemActivity extends AppCompatActivity {
    private TextView userField;
    private TextView passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_system);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userField = ((TextView)findViewById(R.id.user_field));
        passwordField = ((TextView)findViewById(R.id.password_field));
    }



    public void signIn(View view){
        SystemDataBase db = new SystemDataBase(this);
        LibraryUser user = db.getUser(userField.getText().toString(), passwordField.getText().toString());

        if(user == null || user.getIsAdmin() != 1){
            Toast.makeText(getBaseContext(), "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
            return;
        }

        startActivity(new Intent(view.getContext(), TransactionLogActivity.class));


    }
}
