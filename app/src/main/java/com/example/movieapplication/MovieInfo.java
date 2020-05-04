package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONObject;

public class MovieInfo extends AppCompatActivity {
    Button backToHome,next,prev;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        backToHome = findViewById(R.id.button2);
        backToHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intToHome=new Intent(MovieInfo.this,HomeActivity.class);
                startActivity(intToHome);
            }
        });

        Intent in = getIntent();
        String userInput = in.getStringExtra("input");
        String movie = userInput.replaceAll(" ","+");
        String apiKey="&apikey=97d1039f";
        final String url= "http://www.omdbapi.com/?t="+movie+apiKey;

        final TextView textView = (TextView) findViewById(R.id.textView2);
        if(userInput.isEmpty()){

        }
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        // Request a string response from the provided URL.
        StringRequest request = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText("Response is: "+ response.substring(1,500));

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                textView.setText("That didn't work!");
            }
        });
        // Add the request to the RequestQueue.
        queue.add(request);
    }
}
