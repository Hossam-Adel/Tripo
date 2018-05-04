package com.example.salma.tripo.Regiseration;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;

import com.example.salma.tripo.R;

public class LoginChoose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_choose);

        CardView mybtn = (CardView)findViewById(R.id.MyBtn);
        CardView mybtn2 = (CardView)findViewById(R.id.MyBtn2);

        mybtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginChoose.this, SignupScreen.class);
                startActivity(intent);


            }
        });

        mybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(LoginChoose.this, LoginScreen.class);
                startActivity(intent);


            }
        });
    }
}
