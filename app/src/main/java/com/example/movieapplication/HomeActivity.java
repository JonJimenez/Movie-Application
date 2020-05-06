package com.example.movieapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {
    Button buttonLogOut,buttonSearch;
    EditText userInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        buttonLogOut=findViewById(R.id.logout);
        buttonSearch=findViewById(R.id.search);
        userInput=findViewById(R.id.editText3);
        //Sets a listener if user taps on Logout button
        buttonLogOut.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FirebaseAuth.getInstance().signOut();
                Intent intToMain = new Intent(HomeActivity.this,MainActivity.class);
                startActivity(intToMain);
            }
        });
        //Sets a listener if user taps on search then sends them to movie info activity
        buttonSearch.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intToMovieInfo = new Intent(HomeActivity.this,MovieInfo.class);
                intToMovieInfo.putExtra("input",userInput.getText().toString());
                startActivity(intToMovieInfo);
            }
        });

    }
}
