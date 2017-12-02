package com.google.android.gms.samples.vision.ocrreader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class form extends AppCompatActivity {

    private EditText name;
    private EditText father;
    private EditText dob;
    private EditText address;
    private ImageView image;
    private JSONArray jsonArray;
    private Button submit;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private String output;
    private String nametext,fathertext,DOB;
    private Integer REQUEST_CAMERA = 101, SELECT_FILE = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        name = (EditText) findViewById(R.id.name);
        father = (EditText) findViewById(R.id.father);
        dob = (EditText) findViewById(R.id.dob);
        jsonArray=new JSONArray();
        address = (EditText) findViewById(R.id.address);
        submit = (Button) findViewById(R.id.submit);
        image = (ImageView) findViewById(R.id.image);
        dialog=new ProgressDialog(form.this);
        sharedPreferences=getSharedPreferences("com.example.rmitra",MODE_PRIVATE);
        nametext=sharedPreferences.getString("name","");
        fathertext=sharedPreferences.getString("father","");
        DOB=sharedPreferences.getString("DOB","");
        name.setText(nametext);
        father.setText(fathertext);
        dob.setText(DOB);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectimage();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("checked","0");
                JSONArray temp;
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("name", nametext);
                    jsonObject.put("fathername", fathertext);
                    jsonObject.put("DOB", DOB);
                    jsonArray.put(jsonObject);
                    File file=new File(Environment.getExternalStorageDirectory()+File.separator+"app","forms");
                    File file1=new File(file,"details");

                    FileOutputStream fileOutputStream=new FileOutputStream(file1);
                    fileOutputStream.write(jsonArray.toString().getBytes());
                    fileOutputStream.close();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    Log.i("checked","ERROR"+e.getMessage());
                }
            }
        });
    }

    public void selectimage() {
        final CharSequence[] items = {"Camera", "Gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(form.this);
        builder.setTitle("Upload profile picture");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {
                    if (Build.VERSION.SDK_INT > 22 && ActivityCompat.checkSelfPermission(form.this, Manifest.permission.CAMERA) != PermissionChecker.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(form.this, new String[]{android.Manifest.permission.CAMERA}, REQUEST_CAMERA);
                    } else {
                        openCamera();
                    }
                } else if (items[i].equals("Gallery")) {
                    if (Build.VERSION.SDK_INT > 22 && ActivityCompat.checkSelfPermission(form.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PermissionChecker.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(form.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, SELECT_FILE);
                    } else {
                        gallerys();
                    }
                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();
    }

    private void gallerys() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent.createChooser(intent, "Select image"), SELECT_FILE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);
    }

    @Override
    public void onActivityResult(int requestcode, int resultcode, Intent data) {
        super.onActivityResult(requestcode, resultcode, data);
        if (requestcode == REQUEST_CAMERA) {
            if (resultcode == Activity.RESULT_OK) {
                Bitmap bitmap1 = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(bitmap1);
            }
        } else if (requestcode == SELECT_FILE) {
            if (resultcode == Activity.RESULT_OK) {
                gallerys();
                Uri imagedata = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagedata);
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
