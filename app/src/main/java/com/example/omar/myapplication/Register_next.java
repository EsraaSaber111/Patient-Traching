package com.example.omar.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.omar.myapplication.Constants;
import com.example.omar.myapplication.Remote.IGoogleAPIService;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import okhttp3.MultipartBody;

public class Register_next extends AppCompatActivity  {

    //Declaring views
    private TextView Type,btnskip;
    private ImageView imageView;

EditText username,adress,phonenumper;
    //Image request code
    private int PICK_IMAGE_REQUEST = 1;
    IGoogleAPIService mServise;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;
CheckBox check1,check2,check3,check4;
    //Bitmap to get image from gallery
    private Bitmap bitmap;
ProgressDialog progressDialog;
    //Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_next);
        username = findViewById(R.id.name_next);
        Type = findViewById(R.id.name_next);
        progressDialog=new ProgressDialog(this);
        adress = findViewById(R.id.address_next);
        phonenumper = findViewById(R.id.Phine_next);
        check1 = findViewById(R.id.RB1);
        check2 = findViewById(R.id.RB2);
        check3 = findViewById(R.id.RB4);
        check4 = findViewById(R.id.RB5);
        username.setText(sharedprefmanger.getInstance(Register_next.this).getUsername());
        if (sharedprefmanger.getInstance(Register_next.this).getGroupId() == 1) {
            adress.setHint("Adress Clinck");
            phonenumper.setHint("Phone Clinck");
            check1.setText("Pressure");
            check2.setText("Heart");
            check3.setText("Pressure");
            check4.setText("Others");
            Type.setText("Spicelist");

        } else if (sharedprefmanger.getInstance(Register_next.this).getGroupId() == 2) {

            adress.setHint("Adress");
            phonenumper.setHint("Phone Family");


        } else if (sharedprefmanger.getInstance(Register_next.this).getGroupId() == 3) {

            Type.setText("Experiance");

            check1.setText("Pressure");
            check2.setText("Heart");
            check3.setText("Pressure");
            check4.setText("Others");
        }
        //Requesting storage permission
        requestStoragePermission();

        //Initializing views
        btnskip = (Button) findViewById(R.id.buttonskip);
     //  buttonUpload = findViewById(R.id.txt);
   //    buttonUpload.setEnabled(false);
        imageView = (ImageView) findViewById(R.id.image);
        String imge=Constants.ROOTURL+"upload/"+sharedprefmanger.getInstance(Register_next.this).getImage();
        Toast.makeText(Register_next.this, imge, Toast.LENGTH_SHORT).show();

        Picasso.with(this)
                .load(imge)
                .into(imageView);
        btnskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();

            }
        });
        //Setting clicklistener
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();

            }
        });

    }


    /*
     * This is the method responsible for image upload
     * We need the full image path and the name for the image in this method
     * */
    public void uploadMultipart() {
        //getting name for the image

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part adress,phonenumper
            new MultipartUploadRequest(this, uploadId, Constants.ubdateImage)
                    .addFileToUpload(path, "image_path") //Adding file
                    .addParameter("user_id", String.valueOf(sharedprefmanger.getInstance(this).getId()))
                    //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                uploadMultipart();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }


    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }


    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void registerUser() {

        final String Name=username.getText().toString().trim();
        final String Phone=phonenumper.getText().toString().trim();
        final String address=adress.getText().toString().trim();
       // final String Gender = ((RadioButton) findViewById(rgender.getCheckedRadioButtonId())).getText().toString();
        progressDialog.setMessage("Registering user...");
        progressDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_UBDATE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                        //    Toast.makeText(Register.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            if (jsonObject.getBoolean("success")) {
                                Toast.makeText(Register_next.this, "Done", Toast.LENGTH_SHORT).show();
                                sharedprefmanger.getInstance(Register_next.this)
                                        .userIlogin(
                                                jsonObject.getInt("id"),
                                                jsonObject.getString("name"),
                                                jsonObject.getString("email"),
                                                jsonObject.getString("image"),
                                                jsonObject.getInt("group_id")
                                        );
                            }
                            else
                                {
                                    Toast.makeText(Register_next.this, "Not Save", Toast.LENGTH_SHORT).show();

                                }
                                if (sharedprefmanger.getInstance(Register_next.this).getGroupId() == 1) {

                                    Intent intent = new Intent(Register_next.this, Home_doctor.class);
                                    startActivity(intent);
                                    finish();

                                } else if (sharedprefmanger.getInstance(Register_next.this).getGroupId() == 2) {

                                    Intent intent = new Intent(Register_next.this, Home_patient.class);
                                    startActivity(intent);
                                    finish();
                                } else if (sharedprefmanger.getInstance(Register_next.this).getGroupId() == 3) {


                                    Intent intent = new Intent(Register_next.this, Home_pramdic.class);
                                    startActivity(intent);
                                    finish();
                                }



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(Register_next.this, error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",String.valueOf(sharedprefmanger.getInstance(Register_next.this).getId()));
                params.put("name",Name);
                params.put("phonefam",Phone);
                params.put("address",address);
                params.put("experience","");
                params.put("specification","");
                params.put("diseases","");
                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);


    }


}