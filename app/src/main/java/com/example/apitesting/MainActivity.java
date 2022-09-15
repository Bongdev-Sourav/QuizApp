package com.example.apitesting;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    CardView cardView;
    TextView ansquestions;
    TextView slas;
    TextView totalquestions;
    TextView anstext;
    ProgressBar progressBar;
    View view;
    TextView question;
    RadioGroup radioGroup;
    RadioButton option1;
    RadioButton option2;
    RadioButton option3;
    RadioButton option4;
    Button button2;//NextButton
    int count=1;
    int current_progress=10;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cardView=findViewById(R.id.cardView);
        ansquestions=findViewById(R.id.ansquestions);
        slas=findViewById(R.id.slas);
        totalquestions=findViewById(R.id.totalquestions);
        anstext=findViewById(R.id.anstext);
        progressBar=findViewById(R.id.progressbar);
        view=findViewById(R.id.view);
        question=findViewById(R.id.question);
        radioGroup=findViewById(R.id.radiogrp);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        button2=findViewById(R.id.button2);
        ArrayList<String> questions=new ArrayList<String>();
        ArrayList<String> answers=new ArrayList<>();






        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://test-api-88736-default-rtdb.firebaseio.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface apiInterface=retrofit.create(ApiInterface.class);

        Call<Response> call=apiInterface.getPost();

    call.enqueue(new Callback<Response>() {
        @Override
        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

            if (!response.isSuccessful()){
                Toast.makeText(MainActivity.this, "for"+response.code(), Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                Response rsp=response.body();
                List<Data> newrsp=rsp.getData("");

                //set questions
                 question.setText(newrsp.get(0).getQuiz().getQuiz_title());

                 //set options
                option1.setText(newrsp.get(0).getQuiz().getQuiz_options().get(0).getOption_name());
                option2.setText(newrsp.get(0).getQuiz().getQuiz_options().get(1).getOption_name());
                option3.setText(newrsp.get(0).getQuiz().getQuiz_options().get(2).getOption_name());
                option4.setVisibility(View.INVISIBLE);



                questions.add(question.getText().toString());
                

              //Next Button works
               button2.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       
                       if (option1.isChecked()||option2.isChecked()||option3.isChecked()||option4.isChecked()){
                           //add checked ans in arraylist
                           if (option1.isChecked()){
                               answers.add(option1.getText().toString());
                               Toast.makeText(MainActivity.this, "option 1 select", Toast.LENGTH_SHORT).show();
                           }
                           else if (option2.isChecked()){
                               answers.add(option2.getText().toString());
                               Toast.makeText(MainActivity.this, "option 2 select", Toast.LENGTH_SHORT).show();

                           }
                           else if (option3.isChecked()){
                               answers.add(option3.getText().toString());
                               Toast.makeText(MainActivity.this, "option 3 select", Toast.LENGTH_SHORT).show();

                           }
                           else if (option4.isChecked()){
                               answers.add(option4.getText().toString());
                               Toast.makeText(MainActivity.this, "option 4 select", Toast.LENGTH_SHORT).show();

                           }

                           QuestionSet(newrsp,count,question);
                           questions.add(question.getText().toString());
                           OptionSet(newrsp,count);
                           //progressbar
                           current_progress+=10;
                           progressBar.setProgress(current_progress);


                           if (count<newrsp.size()-1){
                               count++;
                           }
                           else {
                               button2.setText("Submit");
                               button2.setOnClickListener(new View.OnClickListener() {
                                   @Override
                                   public void onClick(View view) {
                                       if (option1.isChecked()){
                                           answers.add(option1.getText().toString());
                                           Toast.makeText(MainActivity.this, "option 1 select", Toast.LENGTH_SHORT).show();
                                       }
                                       else if (option2.isChecked()){
                                           answers.add(option2.getText().toString());
                                           Toast.makeText(MainActivity.this, "option 2 select", Toast.LENGTH_SHORT).show();

                                       }
                                       else if (option3.isChecked()){
                                           answers.add(option3.getText().toString());
                                           Toast.makeText(MainActivity.this, "option 3 select", Toast.LENGTH_SHORT).show();

                                       }
                                       else if (option4.isChecked()){
                                           answers.add(option4.getText().toString());
                                           Toast.makeText(MainActivity.this, "option 4 select", Toast.LENGTH_SHORT).show();

                                       }
                                       Intent intent=new Intent(MainActivity.this,ResultActivity.class);
                                       intent.putStringArrayListExtra("Ans",answers);
                                       intent.putStringArrayListExtra("Qsn",questions);
                                       startActivity(intent);
                                       finish();


                                   }
                               });
                           }
                       }
                       else {
                           Toast.makeText(MainActivity.this, "first select any option", Toast.LENGTH_SHORT).show();
                       }
                       
                   }
               });













            }
        }

        @Override
        public void onFailure(Call<Response> call, Throwable t) {
            Toast.makeText(MainActivity.this, "dek"+t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

        }
    });






    }

    private void OptionSet(List<Data> newrsp,int count) {
        int nq=newrsp.get(count).getQuiz().getQuiz_options().size();
        switch (nq){
            case 2: option1.setText(newrsp.get(count).getQuiz().getQuiz_options().get(0).getOption_name().toString());
                option2.setText(newrsp.get(count).getQuiz().getQuiz_options().get(1).getOption_name().toString());
                option3.setVisibility(View.INVISIBLE);
                option4.setVisibility(View.INVISIBLE);
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(false);

                break;

            case 3:
                option1.setText(newrsp.get(count).getQuiz().getQuiz_options().get(0).getOption_name().toString());
                option2.setText(newrsp.get(count).getQuiz().getQuiz_options().get(1).getOption_name().toString());
                option3.setText(newrsp.get(count).getQuiz().getQuiz_options().get(2).getOption_name().toString());
                option3.setVisibility(View.VISIBLE);
                option4.setVisibility(View.INVISIBLE);
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(false);

                break;

            case 4:
                option1.setText(newrsp.get(count).getQuiz().getQuiz_options().get(0).getOption_name().toString());
                option2.setText(newrsp.get(count).getQuiz().getQuiz_options().get(1).getOption_name().toString());
                option3.setText(newrsp.get(count).getQuiz().getQuiz_options().get(2).getOption_name().toString());
                option4.setText(newrsp.get(count).getQuiz().getQuiz_options().get(3).getOption_name().toString());
                option3.setVisibility(View.VISIBLE);
                option4.setVisibility(View.VISIBLE);
                option1.setChecked(false);
                option2.setChecked(false);
                option3.setChecked(false);
                option4.setChecked(false);


        }





    }

    private void QuestionSet(List<Data> newrsp, int count, TextView question) {
        question.setText(newrsp.get(count).getQuiz().getQuiz_title());
        ansquestions.setText(String.valueOf(count+1));
//        Toast.makeText(this, "Button pressed"+count, Toast.LENGTH_SHORT).show();
//        if (count>6){
//            Toast.makeText(this, "All Questions Are done", Toast.LENGTH_SHORT).show();
//        }

    }
}