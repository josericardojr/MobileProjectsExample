package com.josericardojr.quizgame;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.josericardojr.quizgame.databinding.ActivityMainBinding;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    private QuizViewModel quizViewModel;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == Activity.RESULT_OK){
                            quizViewModel.setCheater(result.getData().getBooleanExtra(
                                    CheatActivity.EXTRA_ANSWER_SHOWN, false));
                        }
                    }
                });


                // Get the ViewModel
                quizViewModel = new ViewModelProvider(this).get(QuizViewModel.class);

        updateQuestion();

        binding.trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        binding.falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        binding.cheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = quizViewModel.currentQuestionAnswer();

                Intent intent = new Intent(MainActivity.this, CheatActivity.class);
                intent.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);

                activityResultLauncher.launch(intent);

            }
        });

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizViewModel.moveToNext();
                updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        int questionTextResId = quizViewModel.currentQuestionText();
        binding.questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userAnswer){
        boolean correct = quizViewModel.currentQuestionAnswer();

        int messageResId = 0;

        if (quizViewModel.isCheater()) messageResId = R.string.judgment_toast;
        else if (userAnswer == correct) messageResId = R.string.correct_toast;
        else messageResId = R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}