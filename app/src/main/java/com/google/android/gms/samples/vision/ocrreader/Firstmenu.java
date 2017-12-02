package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Firstmenu extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private ListView listview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firstmenu);
        listview = (ListView) findViewById(R.id.listview);
        sharedPreferences=getSharedPreferences("com.example.rmitra",MODE_PRIVATE);
        String[] values = new String[] { "Bhamashah Application","Driving License","Bank Account form","Aadhaar Card","PAN card"
           ,"Passport","Rashion card","Voter Id card","Scholarship Form"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.list_first,R.id.chooseheading, values);

        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=listview.getItemAtPosition(i).toString();
                sharedPreferences.edit().putString("first",selected).apply();
                startActivity(new Intent(getApplicationContext(),Secondmenu.class));
            }
        });
    }
}
