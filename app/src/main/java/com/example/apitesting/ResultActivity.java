package com.example.apitesting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    TextView textView;
    TextView textView2;
    TextView textView3;
    TextView textView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        textView=findViewById(R.id.textView);
        textView2=findViewById(R.id.textView2);
        textView3=findViewById(R.id.textView3);
        textView4=findViewById(R.id.textView4);

        ArrayList<String> QsnList=getIntent().getStringArrayListExtra("Qsn");
        textView2.setText(String.valueOf(QsnList));

        ArrayList<String> AnsList=getIntent().getStringArrayListExtra("Ans");
        textView4.setText(String.valueOf(AnsList));

    }
}