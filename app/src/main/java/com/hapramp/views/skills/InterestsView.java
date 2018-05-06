package com.hapramp.views.skills;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.hapramp.R;
import com.hapramp.datamodels.CommunityModel;

import java.util.List;

/**
 * Created by Ankit on 12/26/2017.
 */

public class InterestsView extends FrameLayout {

    private Context mContext;
    private ViewGroup parentView;
    private List<CommunityModel> communities;
    private TextView noInterestMessage;

    public InterestsView(@NonNull Context context) {
        super(context);
        mContext = context;
        init();
    }

    public InterestsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public InterestsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {

        View view = LayoutInflater.from(mContext).inflate(R.layout.community_view_container, this);
        parentView = view.findViewById(R.id.viewWrapper);
        noInterestMessage = view.findViewById(R.id.no_interest_msg);

    }

    private void addViews() {

        if (parentView.getChildCount() > 0) {
            // already added, no need to add more duplicate views
            return;
        }

        for (int i = 0; i < communities.size(); i++) {

            final CommunityItemView view = new CommunityItemView(mContext);
            Log.d("InterestView", communities.get(i).toString());
            view.setCommunityDetails(communities.get(i));
            view.setSelection(false);

            parentView.addView(view, i,
                    new ViewGroup.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT));
        }

        noInterestMessage.setVisibility(GONE);

    }

    public void setCommunities(List<CommunityModel> communities) {
        this.communities = communities;
        if (communities != null && communities.size() > 0) {
            addViews();
        }else{
            noInterestMessage.setVisibility(VISIBLE);
        }
    }

}
