package com.sourcey.materiallogindemo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sourcey.materiallogindemo.R;

public class Profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView textView = (TextView)findViewById(R.id.textView2);
        String email;
        if(LoginActivity.email_id.contains(".")){
            email = LoginActivity.email_id;
        }
        else{
            email = SignupActivity.email_id;
        }
        textView.setText(" You have been signed in as " + email + " ");
    }

}
