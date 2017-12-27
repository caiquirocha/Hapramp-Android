package com.hapramp.activity;

import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hapramp.R;
import com.hapramp.adapters.NotificationsAdapter;
import com.hapramp.api.DataServer;
import com.hapramp.interfaces.NotificationCallback;
import com.hapramp.models.response.NotificationResponse;
import com.hapramp.utils.FontManager;
import com.hapramp.utils.ViewItemDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotificationsActivity extends AppCompatActivity implements NotificationCallback {

    @BindView(R.id.backBtn)
    TextView backBtn;
    @BindView(R.id.notificationsMsg)
    TextView notificationsMsg;
    @BindView(R.id.notificationRV)
    RecyclerView notificationRV;
    @BindView(R.id.toolbar_drop_shadow)
    FrameLayout toolbarDropShadow;
    @BindView(R.id.notificationProgress)
    ProgressBar notificationProgress;
    private Typeface materialTypeface;

    private NotificationsAdapter notificationsAdapter;
    private int start = 1;
    private int limit = 20;
    private ViewItemDecoration viewItemDecoration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_notifications);
        ButterKnife.bind(this);
        init();
        fetchNotification();

    }

    private void init() {

        materialTypeface = FontManager.getInstance().getTypeFace(FontManager.FONT_MATERIAL);
        backBtn.setTypeface(materialTypeface);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        notificationsAdapter = new NotificationsAdapter(this);
        notificationRV.setLayoutManager(new LinearLayoutManager(this));
        notificationRV.setAdapter(notificationsAdapter);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.comment_item_divider_view);
        viewItemDecoration = new ViewItemDecoration(drawable);
        notificationRV.addItemDecoration(viewItemDecoration);

    }

    private void bindNotifications(List<NotificationResponse.Notification> list) {

        if (list.size() == 0) {
            notificationsMsg.setVisibility(View.VISIBLE);
        } else {
            notificationsMsg.setVisibility(View.GONE);
            notificationsAdapter.setNotifications(list);
        }

    }

    private void hideProgress(){

        if(notificationProgress!=null){
            notificationProgress.setVisibility(View.GONE);
        }

    }

    private void fetchNotification() {

        DataServer.getNotifications(start, limit, this);

    }

    @Override
    public void onNotificationsFetched(NotificationResponse notificationResponse) {
        bindNotifications(notificationResponse.results);
        hideProgress();
    }

    @Override
    public void onNotificationFetchError() {
        hideProgress();
    }

}
