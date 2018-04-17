package com.hapramp.youtube;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.hapramp.R;
import com.hapramp.utils.ImageHandler;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Ankit on 4/17/2018.
 */

public class YoutubeResultAdapter extends RecyclerView.Adapter<YoutubeResultAdapter.YtbResultViewHolder> {

    private final Context context;

    private List<YoutubeResultModel.Result> youtubeResults;

    public YoutubeResultAdapter(Context context) {
        this.context = context;
        this.youtubeResults = new ArrayList<>();
    }

    public void setYoutubeResults(List<YoutubeResultModel.Result> youtubeResults) {
        this.youtubeResults = youtubeResults;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public YtbResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.youtube_search_item, null);
        return new YtbResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull YtbResultViewHolder holder, int position) {
        holder.bind(youtubeResults.get(position));
    }

    @Override
    public int getItemCount() {
        return youtubeResults.size();
    }

    class YtbResultViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.youtube_thumbnail)
        ImageView youtubeThumbnail;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.author)
        TextView author;
        @BindView(R.id.views)
        TextView views;
        @BindView(R.id.length)
        TextView length;
        @BindView(R.id.toolbar_drop_shadow)
        FrameLayout toolbarDropShadow;

        public YtbResultViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(YoutubeResultModel.Result youtubeResultModel) {
            String imageUrl = "https://img.youtube.com/vi/" + youtubeResultModel.getId() + "/hqdefault.jpg";
            //thumb
            ImageHandler.load(context, youtubeThumbnail, imageUrl);
            //title
            title.setText(youtubeResultModel.getTitle());
            //author
            author.setText(youtubeResultModel.getUploader());
            //length
            length.setText(youtubeResultModel.getLength());
            //views
            views.setText(youtubeResultModel.getViews()+" Views");

        }

    }

}
