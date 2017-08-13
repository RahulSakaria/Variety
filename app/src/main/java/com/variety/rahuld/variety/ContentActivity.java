package com.variety.rahuld.variety;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ContentActivity extends AppCompatActivity {
    TextView title;
    TextView author;
    TextView description;
    ImageView contentImage;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);

        title = (TextView) findViewById(R.id.content_title);
        author = (TextView) findViewById(R.id.content_author);
        description = (TextView) findViewById(R.id.content_description);
        contentImage = (ImageView) findViewById(R.id.content_image);
        progressBar = (ProgressBar) findViewById(R.id.content_progress_bar);

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
        String titles = bundle.getString("title");
        String authors = bundle.getString("author");
        String descriptions = bundle.getString("description");
        String image = bundle.getString("image");
        title.setText(Html.fromHtml(titles));
        author.setText(authors);
        description.setText(Html.fromHtml(descriptions).toString());
        ImageLoader.getInstance().displayImage(image,contentImage, new ImageLoadingListener() {
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
    }
}
