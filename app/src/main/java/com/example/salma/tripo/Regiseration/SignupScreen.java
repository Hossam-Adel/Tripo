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
import android.widget.Toast;

import com.example.salma.tripo.Database.DBAdapter;
import com.example.salma.tripo.Model.User;
import com.example.salma.tripo.R;

import java.util.regex.Pattern;

public class SignupScreen extends AppCompatActivity {
    public static  final String PREEFS_NAME = "myPrefsFile";
    Pattern emailPattern = Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$");
//    Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\\\S+$).{4,}$");



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_screen);
        final EditText email_sign = (EditText) findViewById(R.id.email_txt_sign);
        final EditText password_sign = (EditText) findViewById(R.id.password_txt_sign);
        final EditText confirm_sign = (EditText) findViewById(R.id.confirm_txt_sign);

        CardView register_btn = (CardView)findViewById(R.id.MyBtn);
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences.Editor editor = getSharedPreferences(PREEFS_NAME,0).edit();

                if(!email_sign.getText().toString().equals("")&& !password_sign.getText().toString().equals("")&& !confirm_sign.getText().toString().equals("")  ){
                    //if email has invalid regex
                    if(!emailPattern.matcher(email_sign.getText().toString().trim()).matches())
                        Toast.makeText(getApplicationContext(),"invalid email , it must look like : salma@gmail.com",Toast.LENGTH_LONG).show();
                    // if password has invalid regex
                     else if(password_sign.getText().toString().trim().length() < 4)
                         Toast.makeText(getApplicationContext(),"invalid password , it must contains : at least uppercase letter and lowercase and special character and number and at least 4 characters",Toast.LENGTH_LONG).show();

//                    else if(!passwordPattern.matcher(password_sign.getText().toString().trim()).matches())
//                        Toast.makeText(getApplicationContext(),"invalid password , it must contains : at least uppercase letter and lowercase and special character and number and at least 4 characters",Toast.LENGTH_LONG).show();
                    //if password didn't match the confirm field
                    else if(!password_sign.getText().toString().equals(confirm_sign.getText().toString()))
                       Toast.makeText(getApplicationContext(),"password and confirm fields didn't match",Toast.LENGTH_LONG).show();
                    else
                    {

                        DBAdapter dbAdapter = new DBAdapter(SignupScreen.this);
                        User user = new User(email_sign.getText().toString().trim() , password_sign.getText().toString().trim());
                        boolean userSaved = dbAdapter.saveNewUser(user);

                        if(userSaved)
                        {
                            Toast.makeText(SignupScreen.this , "your information registered successfully" , Toast.LENGTH_SHORT).show();
                            editor.putString("email" , email_sign.getText().toString().trim());
                            editor.putString("password" , password_sign.getText().toString());
                            editor.putString("loggedIn" , "true");
                            Intent intent = new Intent(SignupScreen.this,LoginScreen.class);
                            startActivity(intent);
                        }
                        else
                            Toast.makeText(SignupScreen.this , "This email already exists , please try another one" , Toast.LENGTH_LONG).show();
                    }

                } else {
                    // if email is empty only
                    if(email_sign.getText().toString().equals("") && !password_sign.getText().toString().equals("")&& !confirm_sign.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(),"please enter email",Toast.LENGTH_LONG).show();
                    // if password or confirm is emppty
                    else if((password_sign.getText().toString().equals("") && !email_sign.getText().toString().equals("") && !confirm_sign.getText().toString().equals("")) ||
                            (!password_sign.getText().toString().equals("") && !email_sign.getText().toString().equals("") && confirm_sign.getText().toString().equals("")))
                        Toast.makeText(getApplicationContext(),"password and confirm field didn't match",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(getApplicationContext(),"all fields required",Toast.LENGTH_LONG).show();
                }
            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==android.R.id.home)
            this.finish();

        return super.onOptionsItemSelected(item);
    }
}
