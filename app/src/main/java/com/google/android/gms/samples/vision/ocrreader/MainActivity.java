package com.google.android.gms.samples.vision.ocrreader;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.CommonStatusCodes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private CompoundButton autoFocus;
    private CompoundButton useFlash;
    private TextView statusMessage;
    private ListView textValue;
    private SharedPreferences sharedPreferences;
    private Integer integer=0;

    private static final int RC_OCR_CAPTURE = 9003;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusMessage = (TextView)findViewById(R.id.status_message);
        textValue = (ListView)findViewById(R.id.text_value);

        sharedPreferences=getSharedPreferences("com.example.rmitra",MODE_PRIVATE);

        autoFocus = (CompoundButton) findViewById(R.id.auto_focus);
        useFlash = (CompoundButton) findViewById(R.id.use_flash);

        findViewById(R.id.read_text).setOnClickListener(this);
        findViewById(R.id.proceed).setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.read_text) {
            Intent intent = new Intent(this, OcrCaptureActivity.class);
            intent.putExtra(OcrCaptureActivity.AutoFocus, autoFocus.isChecked());
            intent.putExtra(OcrCaptureActivity.UseFlash, useFlash.isChecked());

            startActivityForResult(intent, RC_OCR_CAPTURE);
        }
        else if(v.getId()==R.id.proceed)
        {
            Log.i("demo","clicked");
            startActivity(new Intent(getApplicationContext(),form.class));
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RC_OCR_CAPTURE) {
            if (resultCode == CommonStatusCodes.SUCCESS) {
                if (data != null) {
                    String text = data.getStringExtra(OcrCaptureActivity.TextBlockObject);
                    statusMessage.setText(R.string.ocr_success);
                    List<String> list=new ArrayList<>(Arrays.asList(text.split(" ")));
                    ArrayAdapter<String> stringArrayAdapter=new ArrayAdapter<String>(this,R.layout.listocr,R.id.ocr,list);
                    textValue.setAdapter(stringArrayAdapter);
                    textValue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            Toast.makeText(getApplicationContext(),"Saved",Toast.LENGTH_SHORT).show();
                            if(integer==0) {
                                String name = textValue.getItemAtPosition(i).toString();
                                sharedPreferences.edit().putString("name",name).apply();
                                integer=integer+1;
                            }
                            else if(integer==1) {
                                String father = textValue.getItemAtPosition(i).toString();
                                sharedPreferences.edit().putString("father",father).apply();
                                integer=integer+1;
                            }
                            else if(integer==2) {
                                String DOB = textValue.getItemAtPosition(i).toString();
                                sharedPreferences.edit().putString("DOB", DOB).apply();
                            }
                        }
                    });

                    Log.d(TAG, "Text read: " + text);
                } else {
                    statusMessage.setText(R.string.ocr_failure);
                    Log.d(TAG, "No Text captured, intent data is null");
                }
            } else {
                statusMessage.setText(String.format(getString(R.string.ocr_error),
                        CommonStatusCodes.getStatusCodeString(resultCode)));
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}
