package com.variety.rahuld.variety;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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
    private LayoutInflater inflater;
    private int resource;
    public CardAdapter(Context context,int resource,List<CardItem> objects) {
        this.context = context;
        this.resource = resource;
        cardItems = objects;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cards, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CardAdapter.ViewHolder holder, final int position) {
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
        holder.postTitle.setText(Html.fromHtml(cardItem.getTitle()));
        holder.postAuthor.setText(cardItem.getAuthorName());
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ContentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("title",cardItem.getTitle());
                intent.putExtra("author",cardItem.getAuthorName());
                intent.putExtra("image",cardItem.getImage());
                intent.putExtra("description",cardItem.getContent());
                context.startActivity(intent);
                }
        });
        holder.currentItem = cardItems.get(position);
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
        public View view;
        public CardItem currentItem;

        public ViewHolder(View itemView) {
            super(itemView);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.relative_layout_cards);
            frameLayout = (FrameLayout) itemView.findViewById(R.id.frame_layout_cards);
            postImage = (ImageView) itemView.findViewById(R.id.card_image);
            postTitle = (TextView) itemView.findViewById(R.id.post_title);
            postAuthor = (TextView) itemView.findViewById(R.id.post_author);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
            view = itemView;




        }
    }
}
