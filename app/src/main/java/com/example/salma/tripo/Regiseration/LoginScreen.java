package com.example.salma.tripo.Regiseration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.MainWindow.TripActivity;
import com.example.salma.tripo.R;
import com.example.salma.tripo.SplashScreen;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

public class LoginScreen extends AppCompatActivity {
    public static  final String PREEFS_NAME = "myPrefsFile";
    LoginButton loginButton ;
    CallbackManager callbackManager;
    private static final String EMAIL = "email";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        TextView reg = (TextView )findViewById(R.id.reg);
        final EditText email = (EditText)findViewById(R.id.email_txt);
        final EditText password = (EditText)findViewById(R.id.password_txt);
        FacebookSdk.getApplicationContext();


        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(EMAIL));
        callbackManager = CallbackManager.Factory.create();
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(LoginScreen.this , "logged in "  , Toast.LENGTH_SHORT).show();
                SharedPreferences sp = getSharedPreferences(PREEFS_NAME,0);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("loggedIn" , "true");
                editor.apply();

                SharedPreferences sp2 = getSharedPreferences(PREEFS_NAME,MODE_PRIVATE);
                String isLoggedIn = sp2.getString("loggedIn" , "false");

                Toast.makeText(LoginScreen.this , "  isLoggedin " + isLoggedIn , Toast.LENGTH_SHORT).show();


                Intent intent = new Intent(LoginScreen.this, TripActivity.class);
                startActivity(intent);
            }

            @Override
            public void onCancel() {
                // App code
                Toast.makeText(LoginScreen.this , "not logged "  , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException exception) {
                // App code

                Toast.makeText(LoginScreen.this , " error in login"  , Toast.LENGTH_SHORT).show();
            }
        });


        CardView logbtn =(CardView) findViewById(R.id.MyBtn);
        logbtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(!email.getText().toString().equals("") && !password.getText().toString().equals(""))
            {
                DBAdapter dbAdapter = new DBAdapter(LoginScreen.this);
                boolean userExists = dbAdapter.getUserByEmailAndPassword(email.getText().toString().trim() , password.getText().toString().trim());
//                Toast.makeText(LoginScreen.this , "the email is " + userExists , Toast.LENGTH_SHORT).show();

                if(userExists) {
                    Toast.makeText(LoginScreen.this, "ya Welcome", Toast.LENGTH_SHORT).show();
                    SharedPreferences sp = getSharedPreferences(PREEFS_NAME,0);
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString("loggedIn" , "true");
                    editor.apply();

                    SharedPreferences sp2 = getSharedPreferences(PREEFS_NAME,MODE_PRIVATE);
                    String isLoggedIn = sp2.getString("loggedIn" , "false");

                    Toast.makeText(LoginScreen.this , "  isLoggedin " + isLoggedIn , Toast.LENGTH_SHORT).show();


                    Intent intent = new Intent(LoginScreen.this, TripActivity.class);
                    startActivity(intent);
                }
                else
                    Toast.makeText(LoginScreen.this , "no such user with this data , if you haven't an account please register " , Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(LoginScreen.this, "all fields required", Toast.LENGTH_SHORT).show();

            }

        }
    });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


     reg.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(LoginScreen.this, SignupScreen.class);
            startActivity(intent);
        }
     });
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
