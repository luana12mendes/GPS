package com.example.luana.gps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
        private Button btDesp, btRenda, btListar;
        private EditText edtRenda;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            Button btDesp = (Button) findViewById(R.id.latlong);
            btDesp.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                    startActivity(intent); } });

            Button btListar = (Button) findViewById(R.id.End);
            btListar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, Main3Activity.class);
                    startActivity(intent);}
            });


        }

    }
