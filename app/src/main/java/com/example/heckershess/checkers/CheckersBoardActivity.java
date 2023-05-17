package com.example.heckershess.checkers;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.heckershess.R;
import com.example.heckershess.checkers.algorithm.AlgorithmType;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class CheckersBoardActivity extends AppCompatActivity {

    private long startTime;
    private Handler handler;
    private Runnable runnable;
    private TextView textView;
    private CheckersBoardView checkersBoardView;
    private LinearLayout rootLayout;
    TextView statusTextView;
    private int difficulty = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkers_board);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.menu_buttons);

        TextView title = findViewById(R.id.menu_label);
        title.setText(R.string.checkers);

        ImageButton rules = findViewById(R.id.rulers);

        rules.setOnClickListener(view -> {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.rules))
                    .setMessage(getResources().getString(R.string.rules_checkers))
                    .setPositiveButton("ОК", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    }).show();
        });

        // Получаем ссылку на TextView
        textView = findViewById(R.id.timeCheckersTV);

        // Сохраняем текущее время в миллисекундах
        startTime = System.currentTimeMillis();

        // Создаем Handler и Runnable для обновления TextView
        handler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                long elapsedTime = System.currentTimeMillis() - startTime;
                // Вычисляем время в нужном формате
                String time = String.format(Locale.getDefault(), "%02d:%02d",
                        TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                        TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60,
                        elapsedTime % 1000);
                // Обновляем TextView
                textView.setText(time);
                // Постим Runnable снова через 10 миллисекунд
                handler.postDelayed(this, 10);
            }
        };
        // Запускаем Runnable
        handler.post(runnable);

        ImageButton back_refresh = findViewById(R.id.back_refresh);
        ImageButton cancelBtn = findViewById(R.id.back_arrow);
        cancelBtn.setVisibility(View.GONE);

        back_refresh.setOnClickListener(view -> {
            rootLayout.removeViewAt(0);
            checkersBoardView = new CheckersBoardView(this, statusTextView, AlgorithmType.HUMAN, difficulty);
            checkersBoardView.invalidate();
            rootLayout.addView(checkersBoardView, 0);

            // Получаем ссылку на TextView
            textView = findViewById(R.id.timeCheckersTV);

            // Сохраняем текущее время в миллисекундах
            startTime = System.currentTimeMillis();

            handler = new Handler();
            runnable = new Runnable() {
                @Override
                public void run() {
                    long elapsedTime = System.currentTimeMillis() - startTime;
                    // Вычисляем время в нужном формате
                    String time = String.format(Locale.getDefault(), "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(elapsedTime),
                            TimeUnit.MILLISECONDS.toSeconds(elapsedTime) % 60,
                            elapsedTime % 1000);
                    // Обновляем TextView
                    textView.setText(time);
                    // Постим Runnable снова через 10 миллисекунд
                    handler.postDelayed(this, 10);
                }
            };
            // Запускаем Runnable
            handler.post(runnable);

            Button backCheckersFieldBtn = findViewById(R.id.backCheckersFieldBtn);
            backCheckersFieldBtn.setOnClickListener(view1 -> {
                finish();
            });
        });

        Button backCheckersFieldBtn = findViewById(R.id.backCheckersFieldBtn);
        backCheckersFieldBtn.setOnClickListener(view -> {
            finish();
        });

        rootLayout = (LinearLayout) findViewById(R.id.root_layout);
        statusTextView = (TextView) findViewById(R.id.statusTextView);
        setLayoutOrientation();

        checkersBoardView = (CheckersBoardView) getLastNonConfigurationInstance();
        if (checkersBoardView == null) {
            Intent intent = getIntent();
            AlgorithmType type = AlgorithmType.valueOf(intent.getStringExtra("algorithm_type"));
            checkersBoardView = new CheckersBoardView(this, statusTextView, type, difficulty);
        }
        checkersBoardView.invalidate();
        rootLayout.addView(checkersBoardView, 0);
    }

    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence("statusText", statusTextView.getText());
    }

    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        statusTextView.setText(savedInstanceState.getCharSequence("statusText"));
    }

    private void setLayoutOrientation(){
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        int width = displaymetrics.widthPixels;
        if (height < width)
            rootLayout.setOrientation(LinearLayout.HORIZONTAL);
        else
            rootLayout.setOrientation(LinearLayout.VERTICAL);
    }
}
