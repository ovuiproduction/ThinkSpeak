package com.example.thinkspeak;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private final String url = "https://api.thingspeak.com/channels/<Your_CHANNEL_ID>/feeds.json";
    private  final String appId = "<YOUR_API_KEY";
    Button btnGetInfo;
    TextView textViewDisplayResult;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGetInfo = findViewById(R.id.btnGetInfo);
        textViewDisplayResult = findViewById(R.id.textViewDisplayResult);


        btnGetInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getInfo();
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void getInfo() {
        String tempUrl = "";
        int value = 2;  //no of data points we want
        //tempurl
            tempUrl = url+"?api_key="+appId+"&results"+value;
            StringRequest stringRequest = new StringRequest(Request.Method.POST,tempUrl,new Response.Listener<String>(){
                @Override
                public void onResponse(String response){
                    String output = "";
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("feeds");
                        JSONObject jsonObjectFeeds = jsonArray.getJSONObject(0);
                        String description = jsonObjectFeeds.getString("field1");

                        output +=description;
                        textViewDisplayResult.setText(output);
                    }catch(JSONException e) {
                        e.printStackTrace();
                    }
                }
            },new Response.ErrorListener() {

                @Override
                public  void onErrorResponse(VolleyError error){
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            requestQueue.add(stringRequest);
    }
}
