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

        Composer = (Button) findViewById(R.id.btn_Composer);            // Invokering af de to buttons
        AudioBook = (Button) findViewById(R.id.btn_AudioBook);

        // Specifik listener til lyt på AudioBook, kalder åben lydbog metode
        AudioBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAudiobook();
            }
        });

        // Specifik listener til lyt på Composer, kalder åben komponistspil metode
        Composer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openComposer();
            }
        });
    }

    public void openAudiobook()   // Åbner lydbog
    {
        Intent intent = new Intent(this,AudioBook.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);   // Udfør arbejde uden nogen transition
    }

    public void openComposer()    // Åbner komponistspil
    {
        Intent intent = new Intent(this,Composer.class);
        startActivity(intent);
        this.overridePendingTransition(0, 0);  // Udfør arbejde uden nogen transition
    }

}