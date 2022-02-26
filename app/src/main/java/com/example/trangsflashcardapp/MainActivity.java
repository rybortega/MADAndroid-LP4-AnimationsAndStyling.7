package com.example.trangsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = (TextView)findViewById(R.id.flashcard_question);
        TextView answer = (TextView)findViewById(R.id.flashcard_answer);
        question.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                question.setVisibility(View.INVISIBLE);
                answer.setVisibility(View.VISIBLE);
            }
        });
        answer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer.setVisibility(View.INVISIBLE);
                question.setVisibility(View.VISIBLE);
            }
        });

        TextView answer1 = (TextView)findViewById(R.id.flashcard_answer1);
        TextView answer2 = (TextView)findViewById(R.id.flashcard_answer2);
        TextView answer3 = (TextView)findViewById(R.id.flashcard_answer3);


        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setBackgroundColor(getResources().getColor(R.color.red));
                answer3.setBackgroundColor(getResources().getColor(R.color.teal_700));
            }
        });

        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer2.setBackgroundColor(getResources().getColor(R.color.red));
                answer3.setBackgroundColor(getResources().getColor(R.color.teal_700));
            }
        });

        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer3.setBackgroundColor(getResources().getColor(R.color.teal_700));
            }
        });

        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1.setBackgroundColor(getResources().getColor(R.color.mint));
                answer2.setBackgroundColor(getResources().getColor(R.color.mint));
                answer3.setBackgroundColor(getResources().getColor(R.color.mint));
            }
        });

        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            boolean isShowingAnswers = true;
            @Override
            public void onClick(View view) {

                if(isShowingAnswers) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_5);
                    answer1.setVisibility(View.INVISIBLE);
                    answer2.setVisibility(View.INVISIBLE);
                    answer3.setVisibility(View.INVISIBLE);
                    isShowingAnswers = false;
                }
                else{
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_8);
                    answer1.setVisibility(View.VISIBLE);
                    answer2.setVisibility(View.VISIBLE);
                    answer3.setVisibility(View.VISIBLE);
                    isShowingAnswers = true;
                }
            }
        });





    }
}