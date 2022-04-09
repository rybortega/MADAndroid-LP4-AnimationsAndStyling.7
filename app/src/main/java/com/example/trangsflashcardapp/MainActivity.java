package com.example.trangsflashcardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.SearchRecentSuggestionsProvider;
import android.os.Bundle;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView questionTextView;
    TextView answerTextView;
    TextView answer1TextView;
    TextView answer2TextView;
    TextView answer3TextView;

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    int ADD_CARD_REQUEST_CODE = 0;
    int EDIT_CARD_REQUEST_CODE = 1;

    Flashcard cardToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        questionTextView = findViewById(R.id.flashcard_question);
        answerTextView = findViewById(R.id.flashcard_answer);

        answer1TextView = findViewById(R.id.flashcard_answer1);
        answer2TextView = findViewById(R.id.flashcard_answer2);
        answer3TextView = findViewById(R.id.flashcard_answer3);


        questionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


// get the center for the clipping circle
                int cx = answerTextView.getWidth() / 2;
                int cy = answerTextView.getHeight() / 2;

// get the final radius for the clipping circle
                float finalRadius = (float) Math.hypot(cx, cy);

// create the animator for this view (the start radius is zero)
                Animator anim = ViewAnimationUtils.createCircularReveal(answerTextView, cx, cy, 0f, finalRadius);

// hide the question and show the answer to prepare for playing the animation!
                questionTextView.setVisibility(View.INVISIBLE);
                answerTextView.setVisibility(View.VISIBLE);

                anim.setDuration(1000);
                anim.start();

            }
        });
        answerTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answerTextView.setVisibility(View.INVISIBLE);
                questionTextView.setVisibility(View.VISIBLE);
            }
        });


        answer1TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1TextView.setBackgroundColor(getResources().getColor(R.color.red));
                answer3TextView.setBackgroundColor(getResources().getColor(R.color.teal_700));
            }
        });

        answer2TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer2TextView.setBackgroundColor(getResources().getColor(R.color.red));
                answer3TextView.setBackgroundColor(getResources().getColor(R.color.teal_700));
            }
        });

        answer3TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer3TextView.setBackgroundColor(getResources().getColor(R.color.teal_700));
            }
        });

        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                answer1TextView.setBackgroundColor(getResources().getColor(R.color.mint));
                answer2TextView.setBackgroundColor(getResources().getColor(R.color.mint));
                answer3TextView.setBackgroundColor(getResources().getColor(R.color.mint));
            }
        });

        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            boolean isShowingAnswers = true;

            @Override
            public void onClick(View view) {

                if (isShowingAnswers) {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_5);
                    answer1TextView.setVisibility(View.INVISIBLE);
                    answer2TextView.setVisibility(View.INVISIBLE);
                    answer3TextView.setVisibility(View.INVISIBLE);
                    isShowingAnswers = false;
                } else {
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.ic_iconmonstr_eye_8);
                    answer1TextView.setVisibility(View.VISIBLE);
                    answer2TextView.setVisibility(View.VISIBLE);
                    answer3TextView.setVisibility(View.VISIBLE);
                    isShowingAnswers = true;
                }
            }
        });


        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, ADD_CARD_REQUEST_CODE);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
                currentCardDisplayedIndex = allFlashcards.size();


            }
        });

        findViewById(R.id.edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);


                String question = questionTextView.getText().toString();
                String answerRight = answerTextView.getText().toString();
                String answerWrong1 = answer2TextView.getText().toString();
                String answerWrong2 = answer3TextView.getText().toString();


                intent.putExtra("question", question);
                intent.putExtra("answerRight", answerRight);
                intent.putExtra("answerWrong1", answerWrong1);
                intent.putExtra("answerWrong2", answerWrong2);


                for (int i = 0; i < allFlashcards.size(); i++) {
                    if (allFlashcards.get(i).getQuestion() == questionTextView.getText()) {
                        cardToEdit = allFlashcards.get(i);
                        break;
                    }
                }


                MainActivity.this.startActivityForResult(intent, EDIT_CARD_REQUEST_CODE);

            }
        });

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();
        if (allFlashcards != null && allFlashcards.size() > 0) {
            Flashcard firstCard = allFlashcards.get(0);
            questionTextView.setText(firstCard.getQuestion());
            answerTextView.setText(firstCard.getAnswer());
            answer1TextView.setText(firstCard.getAnswer());
            answer2TextView.setText(firstCard.getWrongAnswer1());
            answer3TextView.setText(firstCard.getWrongAnswer2());
        }


        findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allFlashcards.size() == 0) {
                    return;
                }

                // SHOW FLASHCARDS IN ORDER

//                advance our pointer index so we can show the next card
//                currentCardDisplayedIndex += 1;

//                make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
//                if (currentCardDisplayedIndex >= allFlashcards.size() || currentCardDisplayedIndex == 0) {
//                    Snackbar.make(questionTextView, "You've reached the last card, going back to start",
//                            Snackbar.LENGTH_SHORT)
//                            .show();
//                    currentCardDisplayedIndex = 0;
//                }


                int randomIndex = getRandomNumber(0, allFlashcards.size() - 1);

                while (randomIndex == currentCardDisplayedIndex) {
                    randomIndex = getRandomNumber(0, allFlashcards.size() - 1);
                }
                Flashcard currentCard = allFlashcards.get(randomIndex);
                questionTextView.setText(currentCard.getQuestion());
                answerTextView.setText(currentCard.getAnswer());
                answer1TextView.setText(currentCard.getAnswer());
                answer2TextView.setText(currentCard.getWrongAnswer1());
                answer3TextView.setText(currentCard.getWrongAnswer2());
                currentCardDisplayedIndex = randomIndex;


                final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });

                findViewById(R.id.flashcard_question).startAnimation(leftOutAnim);
                rightInAnim.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {
                        // this method is called when the animation first starts
                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        // this method is called when the animation is finished playing
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {
                        // we don't need to worry about this method
                    }
                });
                findViewById(R.id.flashcard_question).startAnimation(rightInAnim);


            }
        });

        findViewById(R.id.delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Flashcard currentCard = allFlashcards.get(currentCardDisplayedIndex);
                flashcardDatabase.deleteCard(currentCard.getQuestion());
                allFlashcards = flashcardDatabase.getAllCards();

                if (allFlashcards.size() == 0) {
                    Intent intent = new Intent(MainActivity.this, EmptyState.class);
                    MainActivity.this.startActivity(intent);
                }

                currentCardDisplayedIndex -= 1;
                currentCardDisplayedIndex = currentCardDisplayedIndex < 0 ? currentCardDisplayedIndex += 1 : currentCardDisplayedIndex;


                currentCard = allFlashcards.get(currentCardDisplayedIndex);
                questionTextView.setText(currentCard.getQuestion());
                answerTextView.setText(currentCard.getAnswer());
                answer1TextView.setText(currentCard.getAnswer());
                answer2TextView.setText(currentCard.getWrongAnswer1());
                answer3TextView.setText(currentCard.getWrongAnswer2());

            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CARD_REQUEST_CODE && resultCode == RESULT_OK) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            if (data != null) {
                String question = data.getExtras().getString("question"); // 'question' needs to match the key we used when we put the string in the Intent
                String answerRight = data.getExtras().getString("answerRight");
                String answerWrong1 = data.getExtras().getString("answerWrong1");
                String answerWrong2 = data.getExtras().getString("answerWrong2");


                questionTextView.setText(question);
                answerTextView.setText(answerRight);

                answer1TextView.setText(answerRight);
                answer2TextView.setText(answerWrong1);
                answer3TextView.setText(answerWrong2);
                Snackbar.make(findViewById(R.id.flashcard_question),
                        "Card successfully created",
                        Snackbar.LENGTH_SHORT)
                        .show();


                answer1TextView.setBackgroundColor(getResources().getColor(R.color.mint));
                answer2TextView.setBackgroundColor(getResources().getColor(R.color.mint));
                answer3TextView.setBackgroundColor(getResources().getColor(R.color.mint));

                flashcardDatabase.insertCard(new Flashcard(question, answerRight, answerWrong1, answerWrong2));
                allFlashcards = flashcardDatabase.getAllCards();
            }
        } else if (requestCode == EDIT_CARD_REQUEST_CODE && resultCode == RESULT_OK) {


            String question = data.getExtras().getString("question"); // 'question' needs to match the key we used when we put the string in the Intent
            String answerRight = data.getExtras().getString("answerRight");
            String answerWrong1 = data.getExtras().getString("answerWrong1");
            String answerWrong2 = data.getExtras().getString("answerWrong2");

            questionTextView.setText(question);
            answerTextView.setText(answerRight);

            answer1TextView.setText(answerRight);
            answer2TextView.setText(answerWrong1);
            answer3TextView.setText(answerWrong2);


            cardToEdit.setQuestion(question);
            cardToEdit.setAnswer(answerRight);
            cardToEdit.setWrongAnswer1(answerWrong1);
            cardToEdit.setWrongAnswer2(answerWrong2);

            flashcardDatabase.updateCard(cardToEdit);

        }
    }

    // returns a random number between minNumber and maxNumber, inclusive.
// for example, if i called getRandomNumber(1, 3), there's an equal chance of it returning either 1, 2, or 3.
    public int getRandomNumber(int minNumber, int maxNumber) {
        Random rand = new Random();
        return rand.nextInt((maxNumber - minNumber) + 1) + minNumber;
    }

}