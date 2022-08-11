package com.josericardojr.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.josericardojr.quizgame.databinding.ActivityMainBinding;

import java.util.List;


public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Question[] questions = new Question[]{
            new Question(R.string.question_australia, true),
            new Question(R.string.question_oceans, true),
            new Question(R.string.question_mideast, false),
            new Question(R.string.question_africa, false),
            new Question(R.string.question_americas, true),
            new Question(R.string.question_asia, true),
    };

    int current_index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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

        binding.nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                current_index = (current_index + 1) % questions.length;
                updateQuestion();
            }
        });
    }

    private void updateQuestion(){
        int questionTextResId = questions[current_index].resId;
        binding.questionTextView.setText(questionTextResId);
    }

    private void checkAnswer(boolean userAnswer){
        boolean correct = questions[current_index].answer;

        int messageResId = userAnswer == correct ? R.string.correct_toast
                : R.string.incorrect_toast;

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}