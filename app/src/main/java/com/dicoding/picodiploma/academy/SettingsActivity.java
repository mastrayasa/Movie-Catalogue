package com.dicoding.picodiploma.academy;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.dicoding.picodiploma.academy.reminder.DailyReminder;
import com.dicoding.picodiploma.academy.reminder.ReleaseReminder;

public class SettingsActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.settings, new SettingsFragment())
                .commit();
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }






    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener{

        private String REMINDER_DAILY = "daily_reminder";
        private String REMINDER_RELEASE ="release_reminder";

        private DailyReminder dailyReminder;
        private ReleaseReminder releaseReminder;



        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {

            dailyReminder = new DailyReminder();
            releaseReminder = new ReleaseReminder();
            //Log.e("Berubah", " " + rootKey );
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }


        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

           // Log.e("Berubah", "berubah" + key );
            if (key.equals(REMINDER_DAILY)) {

                if( sharedPreferences.getBoolean(REMINDER_DAILY,false) ){
                    setReminderDaily();
                }else{
                    unSetReminderDaily();
                }

            }

            else if (key.equals(REMINDER_RELEASE)) {
                if(sharedPreferences.getBoolean(REMINDER_RELEASE,false) ){
                    setReminderRelease();
                }else{
                    unSetReminderRelease();
                }
            }
        }

        private void setReminderDaily(){
            String TimeAlarm = "07:00";
            dailyReminder.setRepeatingAlarm(getActivity(), DailyReminder.TYPE_REPEATING,TimeAlarm , getResources().getString(R.string.missing_you));
        }

        private void unSetReminderDaily(){
            dailyReminder.cancelAlarm(getActivity(), DailyReminder.TYPE_REPEATING);
        }

        private void setReminderRelease(){
            String TimeAlarm = "08:00";
            releaseReminder.setRepeatingAlarm(getActivity(), ReleaseReminder.TYPE_REPEATING,TimeAlarm );
        }

        private void unSetReminderRelease(){
            releaseReminder.cancelAlarm(getActivity(), ReleaseReminder.TYPE_REPEATING);
        }
    }
}