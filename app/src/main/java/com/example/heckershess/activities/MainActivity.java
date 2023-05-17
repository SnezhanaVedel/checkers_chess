package com.example.heckershess.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.heckershess.LocaleHelper;
import com.example.heckershess.R;
import com.example.heckershess.checkers.CheckersBoardActivity;
import com.example.heckershess.checkers.algorithm.AlgorithmType;
import com.example.heckershess.chess.ChessField;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferencesLocale;
    String locale;
    Boolean night_mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        sharedPreferences = getSharedPreferences("preferences", MODE_PRIVATE);
        sharedPreferencesLocale = getSharedPreferences("preferences", MODE_PRIVATE);
        locale = sharedPreferencesLocale.getString("locale", "idk");
        Log.e("language", locale);

        //TODO: TODO
        LocaleHelper.setLocale(this, "ru");

        night_mode = sharedPreferences.getBoolean("night_mode", false);

        if (night_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.custom_title_bar);

        TextView title = findViewById(R.id.title_menu_TV);
        title.setText(R.string.CheckersChess);

        ImageButton backButton = findViewById(R.id.custom_title_back_button);

        backButton.setOnClickListener(view -> {
            Intent switcher = new Intent(MainActivity.this, Settings.class);
            switcher.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(switcher);
        });

        ImageButton playsCheckersBtn = findViewById(R.id.playsCheckersBtn);
        ImageButton playsChessBtn = findViewById(R.id.playsChessBtn);
        Button exitMainBtn = findViewById(R.id.exitMainBtn);

        playsCheckersBtn.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CheckersBoardActivity.class);
            intent.putExtra("algorithm_type", AlgorithmType.HUMAN.toString());
            startActivity(intent);
        });
        playsChessBtn.setOnClickListener(view -> {
            Intent switcher = new Intent(MainActivity.this, ChessField.class);
            switcher.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(switcher);
        });
        exitMainBtn.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        if (night_mode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        LocaleHelper.setLocale(this, locale);
    }
}