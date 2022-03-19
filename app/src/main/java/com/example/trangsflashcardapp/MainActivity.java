package com.example.trangsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView question = findViewById(R.id.flashcard_question);
        TextView answer = findViewById(R.id.flashcard_answer);
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

        TextView answer1 = findViewById(R.id.flashcard_answer1);
        TextView answer2 = findViewById(R.id.flashcard_answer2);
        TextView answer3 = findViewById(R.id.flashcard_answer3);


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

                if (isShowingAnswers) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_5);
                    answer1.setVisibility(View.INVISIBLE);
                    answer2.setVisibility(View.INVISIBLE);
                    answer3.setVisibility(View.INVISIBLE);
                    isShowingAnswers = false;
                } else {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_8);
                    answer1.setVisibility(View.VISIBLE);
                    answer2.setVisibility(View.VISIBLE);
                    answer3.setVisibility(View.VISIBLE);
                    isShowingAnswers = true;
                }
            }
        });


        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                String question = ((TextView) findViewById(R.id.flashcard_question)).getText().toString();
                String answerRight = ((TextView) findViewById(R.id.flashcard_answer1)).getText().toString();
                String answerWrong1 = ((TextView) findViewById(R.id.flashcard_answer2)).getText().toString();
                String answerWrong2 = ((TextView) findViewById(R.id.flashcard_answer3)).getText().toString();
                intent.putExtra("question", question);
                intent.putExtra("answerRight", answerRight);
                intent.putExtra("answerWrong1", answerWrong1);
                intent.putExtra("answerWrong2", answerWrong2);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 || resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String question = data.getExtras().getString("question"); // 'question' needs to match the key we used when we put the string in the Intent
            String answerRight = data.getExtras().getString("answerRight");
            String answerWrong1 = data.getExtras().getString("answerWrong1");
            String answerWrong2 = data.getExtras().getString("answerWrong2");

            TextView answer1 = findViewById(R.id.flashcard_answer1);
            TextView answer2 = findViewById(R.id.flashcard_answer2);
            TextView answer3 = findViewById(R.id.flashcard_answer3);

            ((TextView) findViewById(R.id.flashcard_question)).setText(question);
            ((TextView) findViewById(R.id.flashcard_answer)).setText(answerRight);
            answer1.setText(answerRight);
            answer2.setText(answerWrong1);
            answer3.setText(answerWrong2);
            Snackbar.make(findViewById(R.id.flashcard_question),
                    "Card successfully created",
                    Snackbar.LENGTH_SHORT)
                    .show();


            answer1.setBackgroundColor(getResources().getColor(R.color.mint));
            answer2.setBackgroundColor(getResources().getColor(R.color.mint));
            answer3.setBackgroundColor(getResources().getColor(R.color.mint));
        }
    }
}