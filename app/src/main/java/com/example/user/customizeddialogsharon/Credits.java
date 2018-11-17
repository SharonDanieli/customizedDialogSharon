package com.example.user.customizeddialogsharon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Credits extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_credits);
    }

    public void backToMain(View view) {
        Intent t = new Intent(this, MainActivity.class);
        startActivity(t);
        finish();
    }
}
