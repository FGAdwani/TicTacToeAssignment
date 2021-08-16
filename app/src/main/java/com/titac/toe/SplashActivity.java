package com.titac.toe;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class SplashActivity extends AppCompatActivity {
Button gameSTart;
Button gameQuit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setUI();
    }

    private void setUI() {
        gameSTart = findViewById(R.id.startButton);
        gameSTart.setOnClickListener(
                view -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
        );
        gameQuit = findViewById(R.id.quitButton);
        gameQuit.setOnClickListener(
                view -> {
                    AlertDialog.Builder a= new AlertDialog.Builder(this);
                    a.setMessage("Are you sure you want to quit?");
                    a.setPositiveButton("Yes",(dialog, i) -> {
                        dialog.dismiss();
                        finish();
                    });
                    a.setNegativeButton("No",(dialog, i) -> {
                        dialog.dismiss();
                    });
                    a.show();
                }
        );
    }
}