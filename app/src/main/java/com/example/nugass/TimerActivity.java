package com.example.nugass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.nugass.DatabaseHelper;

import java.util.ArrayList;
import java.util.Locale;

public class TimerActivity extends AppCompatActivity {
    private TextView taskTimer, taskName;

    private CountDownTimer countDownTimer;
    private Button btnFinish, btnBreak;
    private long startTimeInMiliseconds, timeLeftInMiliseconds;
    private int workTimeMs, breakTimeMs;
    private int reward = 0;
    private ImageView character;
    private boolean timerRunning;
    private boolean workMode = true;

    ArrayList<String> userCoins, userCharacter;

    private DatabaseHelper db = new DatabaseHelper(TimerActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);

        userCoins = new ArrayList<>();
        userCharacter = new ArrayList<>();

        storeUserDataInArrays();

        workTimeMs = Integer.valueOf(getIntent().getStringExtra("taskWork")) * 60000;
        breakTimeMs = Integer.valueOf(getIntent().getStringExtra("taskBreak")) * 60000;

        startTimeInMiliseconds = workTimeMs;
        timeLeftInMiliseconds = startTimeInMiliseconds;

        taskName = findViewById(R.id.task_timer_name);
        taskTimer = findViewById(R.id.task_timer);
        btnFinish = findViewById(R.id.button_finish);
        btnBreak = findViewById(R.id.button_break);

        character = findViewById(R.id.sprites_animated_character);
        String temp_character = String.valueOf(userCharacter.get(0));
        int characterId = getResources().getIdentifier(temp_character, "drawable", "com.example.nugass");
        character.setImageResource(characterId);

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskFinish();
            }
        });

        btnBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer();
            }
        });

        taskName.setText(getIntent().getStringExtra("taskName"));
        startTimer();
    }

    private void startTimer() {
        if (workMode) {
            taskName.setText(getIntent().getStringExtra("taskName"));
        } else {
            taskName.setText("Lagi istirahat");
        }
        countDownTimer = new CountDownTimer(timeLeftInMiliseconds, 1000) {
            @Override
            public void onTick(long milisUntilFinished) {
                timeLeftInMiliseconds = milisUntilFinished;
                updateCountDownText();
                btnFinish.setVisibility(View.INVISIBLE);
                btnBreak.setVisibility(View.INVISIBLE);
                if (workMode) {
                    reward += 1;
                }
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnFinish.setVisibility(View.VISIBLE);
                btnBreak.setVisibility(View.VISIBLE);
                switchModes();
                if (workMode) {
                    btnBreak.setText("Lanjut!");
                } else {
                    btnBreak.setText("Istirahat dulu");
                }
            }
        }.start();

        Toast.makeText(getApplicationContext(), "Timer running now!", Toast.LENGTH_SHORT).show();
        timerRunning = true;
    }

    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMiliseconds / 1000) / 60;
        int seconds = (int) (timeLeftInMiliseconds / 1000) % 60;

        String timeLeftFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        taskTimer.setText(timeLeftFormatted);
    }

    public void switchModes() {
        if (workMode) {
            startTimeInMiliseconds = breakTimeMs;
            workMode = false;
            resetTimer();
            taskName.setText("Istirahat?");
        } else {
            startTimeInMiliseconds = workTimeMs;
            workMode = true;
            resetTimer();
            taskName.setText(getIntent().getStringExtra("taskName"));
        }
    }

    private void resetTimer() {
        timeLeftInMiliseconds = startTimeInMiliseconds;
        updateCountDownText();
    }

    private void taskFinish() {
        String taskNameTemp = getIntent().getStringExtra("taskName");
        db.addHistoryData(taskNameTemp, reward);
        addReward();
        storeUserDataInArrays();
        reward = 0;
        finish();
    }

    public void addReward() {
        int temp_coins = Integer.valueOf(userCoins.get(0));
        temp_coins += reward;
        db.updateCoins(temp_coins);
    }

    public void storeUserDataInArrays() {
        Cursor cursor = db.readUserData();

        while (cursor.moveToNext()) {
                userCoins.add(cursor.getString(2));
                userCharacter.add(cursor.getString(3));
            }
        }
}