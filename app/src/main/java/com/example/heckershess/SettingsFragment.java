package com.example.heckershess;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

import com.example.heckershess.activities.MainActivity;

public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);

        SharedPreferences pref = getActivity().getSharedPreferences("preferences", Context.MODE_PRIVATE);

        SwitchPreference nightModePreference = findPreference("night_mode");
        nightModePreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                SharedPreferences.Editor editor = pref.edit();

                if (!nightModePreference.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    editor.putBoolean("night_mode", true);
                    editor.commit();

                    // Перезагружаем активность, чтобы обновить тему
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                } else if (nightModePreference.isChecked()) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    editor.putBoolean("night_mode", false);
                    editor.commit();
                    getActivity().finish();
                    startActivity(getActivity().getIntent());
                }

                return true;
            }
        });

        ListPreference localePreferences = findPreference("locale");
//        localePreferences.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
//            @Override
//            public boolean onPreferenceClick(@NonNull Preference preference) {
////                localePreferences.
//
//                if (localePreferences.getValue().equals("Русский")) {
//                    Log.e("PREF_LANG", "RU");
//                } else if (localePreferences.getValue().equals("Английский")) {
//                    Log.e("PREF_LANG", "EN");
//                }
//
//
//                return false;
//            }
//        });

        localePreferences.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(@NonNull Preference preference, Object newValue) {
                String stringValue = newValue.toString();

                // Обработчик нажатия на элементы ListPreference
                if (stringValue.equals("Русский")) {
                    // Действие для элемента "строка 1"
                    Log.e("PREF_LANG", "RU");
                    LocaleHelper.setLocale(getActivity(), "ru");
                    restartMainActivity();

                } else if (stringValue.equals("Английский")) {
                    // Действие для элемента "строка 2"
                    Log.e("PREF_LANG", "EN");
                    LocaleHelper.setLocale(getActivity(), "en");
                    restartMainActivity();
                }
                return true;
            }
        });
    }
    private void restartMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

