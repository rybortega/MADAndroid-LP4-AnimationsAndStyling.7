package com.example.trangsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddCardActivity.this, MainActivity.class);
                AddCardActivity.this.startActivity(intent);
                finish();
                overridePendingTransition(R.anim.right_out,R.anim.left_in);

            }
        });

        findViewById(R.id.save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String question = ((EditText) findViewById(R.id.editQuestion)).getText().toString();
                String answerRight = ((EditText) findViewById(R.id.editAnswerRight)).getText().toString();
                String answerWrong1 = ((EditText) findViewById(R.id.editAnswerWrong1)).getText().toString();

                String answerWrong2 = ((EditText) findViewById(R.id.editAnswerWrong2)).getText().toString();
                if (question.equals("") || answerRight.equals("") || answerWrong1.equals("") || answerWrong2.equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(), "You need to enter both Question & Answer", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    Intent data = new Intent();// create a new Intent, this is where we will put our data
                    data.putExtra("question", question); // puts one string into the Intent, with the key as 'string1'
                    data.putExtra("answerRight", answerRight); // puts another string into the Intent, with the key as 'string2
                    data.putExtra("answerWrong1", answerWrong1);
                    data.putExtra("answerWrong2", answerWrong2);
                    setResult(RESULT_OK, data); // set result code and bundle data for response
                    finish(); // closes this activity and pass data to the original activity that launched this activity
                }
            }
        });

        String question = getIntent().getStringExtra("question");
        String answerRight = getIntent().getStringExtra("answerRight");
        String answerWrong1 = getIntent().getStringExtra("answerWrong1");
        String answerWrong2 = getIntent().getStringExtra("answerWrong2");

        ((TextView) findViewById(R.id.editQuestion)).setText(question);
        ((TextView) findViewById(R.id.editAnswerRight)).setText(answerRight);
        ((TextView) findViewById(R.id.editAnswerWrong1)).setText(answerWrong1);
        ((TextView) findViewById(R.id.editAnswerWrong2)).setText(answerWrong2);
    }


}