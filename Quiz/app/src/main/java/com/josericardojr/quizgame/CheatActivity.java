package com.josericardojr.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.josericardojr.quizgame.databinding.ActivityCheatBinding;

public class CheatActivity extends AppCompatActivity {

    private ActivityCheatBinding binding;
    public final static String EXTRA_ANSWER_IS_TRUE = "com.josericardojr.quizgame.answer_is_true";
    public final static String EXTRA_ANSWER_SHOWN = "com.josericardojr.quizgame.answer_shown";

    private boolean answerIsTrue = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCheatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);

        binding.showAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answerText = answerIsTrue ? R.string.true_button : R.string.false_button;
                binding.answerTextView.setText(answerText);

                setAnswerShowResult(true);
            }
        });
    }

    private void setAnswerShowResult(boolean isAnswerShown) {
        getIntent().putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
        setResult(Activity.RESULT_OK, getIntent());
    }
}