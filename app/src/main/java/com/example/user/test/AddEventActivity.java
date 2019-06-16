package com.example.user.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddEventActivity extends AppCompatActivity {
    public static final String TAG = "SAVE";
    ImageView imageView;
    String base64;
    EditText asunto,descripcion;
    RequestQueue request;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    Context context ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        imageView = findViewById(R.id.preview_event);
        asunto = findViewById(R.id.asunto);
        descripcion = findViewById(R.id.descripcion);
        context = this;
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            Toast.makeText(this,"Foto cargada exitosamente !!",Toast.LENGTH_LONG).show();
            imageView.setImageBitmap(imageBitmap);
            base64 = convert(imageBitmap);
        }
    }

    public static String convert(Bitmap bitmap)
    {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);

        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    public void sendRequest(View view){
        Date date = new Date();
        String url = "https://0d0e0d34.ngrok.io/registrar";
        Map<String, Object> ob= new HashMap<>();
        ob.put("fotos",base64);
        ob.put("asunto",asunto.getText().toString());
        ob.put("descri",descripcion.getText().toString());
        ob.put("fecha",date.toString());
        ob.put("ide_sitio","0");
        request = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest o = new JsonObjectRequest
                (Request.Method.POST, url, new JSONObject(ob), new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(AddEventActivity.this,"Solicitud Enviada !!!!",Toast.LENGTH_SHORT).show();
                        finish();
                        Intent intent = new Intent(context,MainActivity.class);
                        context.startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(AddEventActivity.this,"Error Volley : " + error.toString(),Toast.LENGTH_SHORT).show();
                    }
                });
        o.setRetryPolicy(new DefaultRetryPolicy(30000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        o.setTag(TAG);
        request.add(o);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(request != null){
            request.cancelAll(TAG);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(request != null){
            request.cancelAll(TAG);
        }
    }

}
