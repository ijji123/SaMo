package com.example.samo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button Composer, AudioBook;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Composer = (Button) findViewById(R.id.btn_Composer);
        AudioBook = (Button) findViewById(R.id.btn_AudioBook);

        AudioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAudiobook();
            }
        });

        Composer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComposer();
            }
        });
    }

    public void openAudiobook()
    {
        Intent intent = new Intent(this,AudioBook.class);
        startActivity(intent);
    }

    public void openComposer()
    {
        Intent intent = new Intent(this,Composer.class);
        startActivity(intent);
    }

}