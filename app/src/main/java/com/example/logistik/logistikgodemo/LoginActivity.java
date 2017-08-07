package com.example.logistik.logistikgodemo;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    public void onMenuClick(View view){
        Context currentContext = this;
        Intent activity_login = new Intent(currentContext, MenuActivity.class);
        startActivity(activity_login);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}
