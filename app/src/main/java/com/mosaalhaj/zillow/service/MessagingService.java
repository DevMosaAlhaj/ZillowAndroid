package com.mosaalhaj.zillow.service;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mosaalhaj.zillow.R;
import com.mosaalhaj.zillow.api.AuthApiService;
import com.mosaalhaj.zillow.api.RetrofitSingleton;
import com.mosaalhaj.zillow.model.MyRes;
import com.mosaalhaj.zillow.response.LoginResponse;
import com.mosaalhaj.zillow.ui.view.activity.HomeActivity;

import java.util.Map;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.mosaalhaj.zillow.item.Constants.ACCESS_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.CHANNEL_ID;
import static com.mosaalhaj.zillow.item.Constants.IS_FCM_REGISTERED;
import static com.mosaalhaj.zillow.item.Constants.NOT_FOUND;
import static com.mosaalhaj.zillow.item.Constants.REFRESH_TOKEN;
import static com.mosaalhaj.zillow.item.Constants.SHARED_PREFERENCE_FILE;

public class MessagingService extends FirebaseMessagingService {

    private final FirebaseMessaging messaging;
    private final AuthApiService service;
    private String refreshToken, accessToken;
    private SharedPreferences preferences;
    private int notificationId;

    public MessagingService() {
        Retrofit retrofit = RetrofitSingleton.getInstance();
        messaging = FirebaseMessaging.getInstance();
        service = retrofit.create(AuthApiService.class);
        notificationId = 1;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        preferences = getSharedPreferences(SHARED_PREFERENCE_FILE, MODE_PRIVATE);
        refreshToken = preferences.getString(REFRESH_TOKEN, NOT_FOUND);
        accessToken = preferences.getString(ACCESS_TOKEN, NOT_FOUND);
        boolean isFcmRegistered = preferences.getBoolean(IS_FCM_REGISTERED, false);

        if (!isFcmRegistered) {
            messaging.getToken().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    sendFcmTokenToServer(task.getResult(), "Bearer " + accessToken);
                    Log.e("MessagingService", "Get Fcm Token Successfully");
                } else
                    Log.e("MessagingService", "Get Fcm Token Failure");

            });
        } else
            Log.e("MessagingService", "Fcm Token Already Register");
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        notifyUser(remoteMessage.getData());

    }

    @Override
    public void onNewToken(@NonNull String fcmToken) {
        super.onNewToken(fcmToken);

        if (!accessToken.equals(NOT_FOUND))
            sendFcmTokenToServer(fcmToken, "Bearer " + accessToken);
        else if (!refreshToken.equals(NOT_FOUND))
            refreshAndResendFcmToken(fcmToken);

    }

    @SuppressLint("CommitPrefEdits")
    private void sendFcmTokenToServer(String fcmToken, String token) {


        Single<Response<MyRes<String>>> fcmTokenObservable = service.registerFcmToken(fcmToken, token);


        //noinspection ResultOfMethodCallIgnored
        fcmTokenObservable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.body() != null && !response.body().getData().isEmpty()) {
                        Toast.makeText(getBaseContext(), "Register New Token Successfully", Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean(IS_FCM_REGISTERED, true);
                        editor.apply();
                    } else if (response.code() == 401)
                        refreshAndResendFcmToken(fcmToken);

                    else
                        Toast.makeText(getBaseContext(), "Register Fcm Token Failure", Toast.LENGTH_SHORT).show();
                }, error -> {
                    Toast.makeText(getBaseContext(),
                            "Can't Connect With Server\nto Send Fcm Token",
                            Toast.LENGTH_SHORT).show();

                    error.printStackTrace();
                });


    }

    private void refreshAndResendFcmToken(String fcmToken) {

        Single<Response<MyRes<LoginResponse>>> refreshObservable = service.refresh(refreshToken);

        //noinspection ResultOfMethodCallIgnored
        refreshObservable.subscribeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {

                    if (response.isSuccessful() && response.body() != null) {
                        String token = "Bearer " + response.body()
                                .getData().getTokenResponse().getToken();
                        sendFcmTokenToServer(fcmToken, token);
                    }

                }, Throwable::printStackTrace);


    }

    private void notifyUser(Map<String, String> messageData) {

        String title = messageData.get("Title");
        String body = messageData.get("Body");
        String action = messageData.get("Action");


        Intent intent = new Intent(this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 193, intent, 0);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "Zillow Notification Channel";
            String description = "This Channel Created to Push Notification Through it";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel =
                    new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


        }


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());

        notificationManager.notify(notificationId, notificationBuilder.build());
        notificationId++;

    }

}
