package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Secondmenu extends AppCompatActivity {


    private SharedPreferences sharedPreferences;
    private ListView listview;
    private String string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secondmenu);
        listview = (ListView) findViewById(R.id.listview1);
        sharedPreferences=getSharedPreferences("com.example.rmitra",MODE_PRIVATE);
        string=sharedPreferences.getString("first","");
        String[] values = new String[] { "Bhamashah Application","Driving License","Bank Account form","Aadhaar Card","PAN card"
                ,"Passport","Rashion card","Voter Id card","Scholarship Form"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,
                R.layout.layout_second,R.id.chooseheadings, values);

        listview.setAdapter(adapter1);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String selected=listview.getItemAtPosition(i).toString();
                if(string.equals(selected)) {
                    Toast.makeText(getApplicationContext(),"Both document cannot be same.Please choose different one",Toast.LENGTH_LONG).show();
                }
                else {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            }
        });
    }
}
