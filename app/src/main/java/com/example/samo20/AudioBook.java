package com.example.samo20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AudioBook extends AppCompatActivity {

    Button TBB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_book);

        TBB = (Button) findViewById(R.id.Btn_TBB);
        //close = (Button) findViewById(R.id.close);

        TBB.setOnClickListener(clickListener);
        //close.setOnClickListener(closeClickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            openTreBukkeBruse();
        }
    };

    public void openTreBukkeBruse(){
        Intent intent = new Intent(this,TreBukkeBruse.class);
        startActivity(intent);
    }


}