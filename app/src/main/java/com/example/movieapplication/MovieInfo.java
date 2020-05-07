package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;

public class MovieInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_info);
        final TextView titleView = (TextView) findViewById(R.id.Title);
        final TextView ratedView = (TextView) findViewById(R.id.Rated);
        final TextView directorView = (TextView) findViewById(R.id.Director);
        final TextView genreView = (TextView) findViewById(R.id.Genre);
        final TextView plotView = (TextView) findViewById(R.id.Plot);
        final ImageView posterView = (ImageView) findViewById(R.id.imageView2);
        final TextView actorsView = (TextView) findViewById(R.id.Actors);
        final Button backToHome = (Button)findViewById(R.id.button2);
        final ToggleButton favorite = (ToggleButton)findViewById(R.id.toggleButton);

        backToHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intToHome=new Intent(MovieInfo.this,HomeActivity.class);
                startActivity(intToHome);
            }
        });
//gets movie title from home screen and turns it into a url
        Intent in = getIntent();
        String movie = in.getStringExtra("input").replaceAll(" ","+");
        String apiKey="&apikey=97d1039f";
        final String url= "https://www.omdbapi.com/?t="+movie+apiKey+"&plot=full";
// Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject movie = new JSONObject(response);
                            String result = movie.getString("Response");
                            if(result.equals("True")){
                                //Retrieves these values from the api request
                                String title = movie.getString("Title");
                                String rated = movie.getString("Rated");
                                String director = movie.getString("Director");
                                String genre = movie.getString("Genre");
                                String plot = movie.getString("Plot");
                                String actors = movie.getString("Actors");
                                String poster = movie.getString("Poster");
                                titleView.setText("Title: "+title);
                                ratedView.setText("Rating: "+rated);
                                directorView.setText("Director: "+director);
                                genreView.setText("Genre: "+genre);
                                plotView.setText("Plot: "+plot);
                                actorsView.setText("Actors: "+actors);
                                new DownLoadImageTask(posterView).execute(poster);
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(MovieInfo.this,"Error! Please Try Again",Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    //used to download and show the poster
    private class DownLoadImageTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

        public DownLoadImageTask(ImageView imageView){
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String...urls){
            String urlOfImage = urls[0];
            Bitmap logo = null;
            try{
                InputStream is = new URL(urlOfImage).openStream();
                logo = BitmapFactory.decodeStream(is);
            }catch(Exception e){ // Catch the download exception
                e.printStackTrace();
            }
            return logo;
        }
        protected void onPostExecute(Bitmap result){
            imageView.setImageBitmap(result);
        }
    }
}




