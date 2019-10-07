package com.dicoding.picodiploma.academy.reminder;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.dicoding.picodiploma.academy.BuildConfig;
import com.dicoding.picodiploma.academy.R;
import com.dicoding.picodiploma.academy.api.ApiClient;
import com.dicoding.picodiploma.academy.api.ApiInterface;
import com.dicoding.picodiploma.academy.api.GetFilm;
import com.dicoding.picodiploma.academy.entitas.Film;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReleaseReminder extends BroadcastReceiver {
    public static final String TYPE_ONE_TIME = "OneTimeAlarm";
    public static final String TYPE_REPEATING = "Release Reminder";
    public static final String EXTRA_MESSAGE = "message";
    public static final String EXTRA_TYPE = "type";
    private static final String API_KEY = BuildConfig.TMDB_API_KEY;

    private final static String TIME_FORMAT = "HH:mm";

    // Siapkan 2 id untuk 2 macam alarm, onetime dna repeating
    private final static int ID_ONETIME = 102;
    private final static int ID_REPEATING = 103;

    ApiInterface mApiInterface;

    public ReleaseReminder() {
        Log.e("ReleaseReminder", "start");
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String type = intent.getStringExtra(EXTRA_TYPE);
        String message = intent.getStringExtra(EXTRA_MESSAGE);
        String title = type.equalsIgnoreCase(TYPE_ONE_TIME) ? TYPE_ONE_TIME : TYPE_REPEATING;
        int notifId = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        showToast(context, title, message);

        loadReleaseFilms(context);


        Log.e("ReleaseReminder", "onReceive ID=" + notifId);
    }

    // Gunakan metode ini untuk menampilkan toast
    private void showToast(Context context, String title, String message) {
        Toast.makeText(context, title + " : " + message, Toast.LENGTH_LONG).show();
    }

    // Gunakan metode ini untuk menampilkan notifikasi
    private void showAlarmNotification(Context context, String title, String message, int notifId) {
        String CHANNEL_ID = "Channel_1";
        String CHANNEL_NAME = "AlarmManager channel";

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound);

        /*
        Untuk android Oreo ke atas perlu menambahkan notification channel
        Materi ini akan dibahas lebih lanjut di modul extended
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            /* Create or update. */
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT);

            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000, 1000, 1000, 1000, 1000});

            builder.setChannelId(CHANNEL_ID);

            if (notificationManagerCompat != null) {
                notificationManagerCompat.createNotificationChannel(channel);
            }
        }

        Notification notification = builder.build();

        if (notificationManagerCompat != null) {
            notificationManagerCompat.notify(notifId, notification);
        }

    }


    // Metode ini digunakan untuk menjalankan alarm repeating
    public void setRepeatingAlarm(final Context context, String type, String time) {

        // Validasi inputan waktu terlebih dahulu
        if (isDateInvalid(time, TIME_FORMAT)) return;

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        intent.putExtra(EXTRA_MESSAGE, "Release Reminder");
        intent.putExtra(EXTRA_TYPE, type);

        String timeArray[] = time.split(":");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]));
        calendar.set(Calendar.SECOND, 0);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, 0);
        if (alarmManager != null) {
            Log.e("SET ReleaseReminder", "Berhasil" );
            alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }else{
            Log.e("SET ReleaseReminder", "GAGAL" );
        }

    }

    private void loadReleaseFilms(final Context context){

        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        Log.e("TGL", date);

        Call<GetFilm> FilmsCall = mApiInterface.getFilmRelease(API_KEY, date,date);
        FilmsCall.enqueue(new Callback<GetFilm>() {
            @Override
            public void onResponse(Call<GetFilm> call, Response<GetFilm> response) {

                List<Film> list_films;
                list_films = response.body().getListFilm();

                if(list_films.size() > 0){

                    for (int i=0; i<list_films.size(); i++) {

                        Film film =list_films.get(i);
                        showAlarmNotification(context, film.getTitle(),film.getTitle() + " " +context.getResources().getString(R.string.has_been_release_today),Integer.parseInt( film.getId()));

                    }

                }

            }

            @Override
            public void onFailure(Call<GetFilm> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
            }
        });
    }


    public void cancelAlarm(Context context, String type) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, ReleaseReminder.class);
        int requestCode = type.equalsIgnoreCase(TYPE_ONE_TIME) ? ID_ONETIME : ID_REPEATING;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0);
        pendingIntent.cancel();

        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            Log.e("Unset ReleaseReminder", "Berhasil" );
        }
    }




    // Metode ini digunakan untuk validasi date dan time
    public boolean isDateInvalid(String date, String format) {
        try {
            DateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            df.setLenient(false);
            df.parse(date);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

}
