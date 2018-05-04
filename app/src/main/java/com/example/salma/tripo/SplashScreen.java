package com.example.salma.tripo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.salma.tripo.MainWindow.TripActivity;
import com.example.salma.tripo.Regiseration.LoginChoose;

public class SplashScreen extends AppCompatActivity {
private final int SPLASH_SCREEN=500;
    public static  final String PREEFS_NAME = "myPrefsFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences sp = getSharedPreferences(PREEFS_NAME,MODE_PRIVATE);
                String isLoggedIn = sp.getString("loggedIn" , "false");

               // Toast.makeText(SplashScreen.this , "the isLogged in " + isLoggedIn , Toast.LENGTH_SHORT).show();

                if(isLoggedIn.equals("false"))
                {
                    Intent intent = new Intent(SplashScreen.this, LoginChoose.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();


                }else
                {
                    Toast.makeText(SplashScreen.this , "ya welcome " , Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SplashScreen.this, TripActivity.class);
                    startActivity(intent);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }


            }
        },SPLASH_SCREEN);

  }
}
