package com.example.dsad.diycode;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
<<<<<<< HEAD
import android.view.MenuItem;
=======
>>>>>>> origin/master
import android.view.View;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;

import butterknife.Bind;
import butterknife.ButterKnife;

/***
 * 浏览大图
 */
public class ImageActivity extends AppCompatActivity {
    @Bind(R.id.img_image_bg)
    PhotoView imgImageBg;
    @Bind(R.id.progress_image_bar)
    ProgressBar progressImageBar;
    @Bind(R.id.top_image_bar)
    Toolbar topImageBar;
    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);
        inintdata();
<<<<<<< HEAD
=======
        inintsession();
>>>>>>> origin/master
    }
    private void inintdata() {
        setSupportActionBar(topImageBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //创建缩放
        final PhotoViewAttacher attacher = new PhotoViewAttacher(imgImageBg);
        url = getIntent().getStringExtra("imgurl");
        Glide.with(getApplicationContext()).load(url)
<<<<<<< HEAD
             .listener(new RequestListener<Drawable>() {
                 @Override
                 public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                     return false;
                 }

                 @Override
                 public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                     //成功
                     progressImageBar.setVisibility(View.GONE);
                     //返回false往下走
                     attacher.update();
                     return false;
                 }
             })
                .into(imgImageBg);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return true;
=======
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        //失败
                        return false;
                    }
                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        //成功
                        progressImageBar.setVisibility(View.GONE);
                        //返回false往下走
                        attacher.update();
                        return false;
                    }
                })
                .into(imgImageBg);
    }
    private void inintsession()
    {

>>>>>>> origin/master
    }
}
