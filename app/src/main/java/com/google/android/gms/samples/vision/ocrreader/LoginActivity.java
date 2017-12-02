package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button Loginbutton;
    private EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Loginbutton=(Button)findViewById(R.id.loginbutton);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
            Loginbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!username.getText().toString().equals("rajasthan") && !password.getText().toString().equals("1234567"))
                    {
                        Toast.makeText(getApplicationContext(),"Wrong username or password",Toast.LENGTH_LONG).show();
                    }
                    else {
                        startActivity(new Intent(getApplicationContext(), Firstmenu.class));
                    }
                }
            });
    }
}
