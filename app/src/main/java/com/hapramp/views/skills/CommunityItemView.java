package com.hapramp.views.skills;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hapramp.R;
import com.hapramp.api.URLS;
import com.hapramp.models.CommunityModel;
import com.hapramp.utils.FontManager;
import com.hapramp.utils.ImageHandler;
import com.hapramp.utils.SkillsUtils;

import static com.hapramp.utils.SkillsUtils.DRAMATICS;
import static com.hapramp.utils.SkillsUtils.ART;
import static com.hapramp.utils.SkillsUtils.DANCE;
import static com.hapramp.utils.SkillsUtils.LITERATURE;
import static com.hapramp.utils.SkillsUtils.MUSIC;
import static com.hapramp.utils.SkillsUtils.PHOTOGRAPHY;
import static com.hapramp.utils.SkillsUtils.TRAVEL;

/**
 * Created by Ankit on 6/22/2017.
 */

public class CommunityItemView extends FrameLayout {

    ImageView communityIv;
    TextView skillSelectionOverlay;
    TextView communityItemTitle;
    private Context mContext;
    private CommunityModel mCommunity;

    public CommunityItemView(Context context) {
        super(context);
        init(context);
    }

    public CommunityItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CommunityItemView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {

        this.mContext = context;
        View view = LayoutInflater.from(context).inflate(R.layout.skills_view, this);
        communityIv = view.findViewById(R.id.skills_bg_image);
        skillSelectionOverlay = view.findViewById(R.id.skill_selection_overlay);
        communityItemTitle = view.findViewById(R.id.skill_title);
        skillSelectionOverlay.setTypeface(new FontManager().getTypeFace(FontManager.FONT_MATERIAL));

    }

    @Deprecated
    private void setCommunityIv(int id) {

        String resId = URLS.URL_ART;
        String color = "#795548";

        switch (id) {
            case PHOTOGRAPHY:
                resId = URLS.URL_PHOTO;
                color = "#f44336";
                break;
            case DANCE:
                resId = URLS.URL_DANCE;
                color = "#e91e63";
                break;
            case DRAMATICS:
                resId = URLS.URL_ACTION;
                color = "#9c27b0";
                break;
            case ART:
                resId = URLS.URL_ART;
                color = "#3949ab";
                break;
            case MUSIC:
                resId = URLS.URL_MUSIC;
                color = "#009688";
                break;
            case TRAVEL:
                resId = URLS.URL_TRAVEL;
                color = "#607d8b";
                break;
            case LITERATURE:
                resId = URLS.URL_LIT;
                color = "#ff5722";
                break;
        }

        ImageHandler.loadCircularImage(mContext, communityIv, resId);
        setOverlayColor(color);

    }

    private void setOverlayColor(String color) {

        GradientDrawable background = (GradientDrawable) skillSelectionOverlay.getBackground();
        background.setColor(Color.parseColor(color));

    }

    public void setSelection(boolean selected) {
        if (selected) {
            skillSelectionOverlay.setVisibility(VISIBLE);
        } else {
            skillSelectionOverlay.setVisibility(GONE);
        }
    }

    public String getCommunityTitle() {
        return mCommunity.getmName();
    }

    public int getCommunityId() {
        return mCommunity.getmId();
    }

    public void setCommunityDetails(CommunityModel communityModel) {
        this.mCommunity = communityModel;
        // set Image
        setCommunityImage(mCommunity.getmImageUri());
        // set Overlay Color
        setCommunityOverlayColor(mCommunity.getmColor());
        // setTitle
        setCommunityTitle(mCommunity.getmName());
    }

    private void setCommunityTitle(String title) {
        communityItemTitle.setText(title);
    }

    private void setCommunityOverlayColor(String color) {
        setOverlayColor(color);
    }

    private void setCommunityImage(String imageUri) {
        ImageHandler.loadCircularImage(mContext, communityIv, imageUri);
    }
}
