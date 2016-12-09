package com.example.android.bookrentalsystemforcsumblibrary;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.create_account).setOnClickListener(this);
        findViewById(R.id.place_hold).setOnClickListener(this);
        findViewById(R.id.cancel_hold).setOnClickListener(this);
        findViewById(R.id.manage_system).setOnClickListener(this);
    }

    @Override
    public void onClick(View view){

        Class nextActivity = MainActivity.class;
        switch (view.getId()) {
            case R.id.create_account:
                nextActivity = AccountCreationActivity.class;
                break;
            case R.id.place_hold:
                nextActivity = PlaceHoldActivity.class;
                break;
            case R.id.cancel_hold:
                nextActivity = CancelHoldActivity.class;
                break;
            case R.id.manage_system:
                nextActivity = ManageSystemActivity.class;
                break;
        }

        startActivity(new Intent(view.getContext(), nextActivity));
    }
}
