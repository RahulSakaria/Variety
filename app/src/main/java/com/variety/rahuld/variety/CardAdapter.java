package com.variety.rahuld.variety;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

/**
 * Created by HP on 12-08-2017.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    private ProgressBar progressBar;
    private List<CardItem> cardItems;
    private Context context;

    public CardAdapter(List<CardItem> listItems, Context context) {
        this.cardItems = listItems;
        this.context = context;
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, int position) {
        final CardItem cardItem = cardItems.get(position);

        ImageLoader.getInstance().displayImage(cardItems.get(position).getImage(), holder.postImage, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });
        holder.postTitle.setText(cardItem.getTitle());
        holder.postAuthor.setText(cardItem.getDate());
    }

    @Override
    public int getItemCount() {
        return cardItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView postImage;
        public TextView postTitle;
        public TextView postAuthor;
        public RelativeLayout relativeLayout;
        public FrameLayout frameLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_cards);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_cards);
            postImage = (ImageView) itemView.findViewById(R.id.card_image);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postAuthor = (TextView) itemView.findViewById(R.id.post_author);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);



        }
    }
}
