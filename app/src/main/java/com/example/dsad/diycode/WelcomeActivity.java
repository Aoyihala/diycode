package com.example.dsad.diycode;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.dsad.diycode.base.BaseActivity;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import pub.devrel.easypermissions.EasyPermissions;


public class WelcomeActivity extends BaseActivity implements EasyPermissions.PermissionCallbacks {
    @Bind(R.id.img_welcome_bg)
    ImageView imgWelcomeBg;
    @Bind(R.id.img_welcome_logo)
    ImageView imgWelcomeLogo;
    @Bind(R.id.tv_welcome_title)
    TextView tvWelcomeTitle;
    @Bind(R.id.re_welcome_layout)
    RelativeLayout reWelcomeLayout;
    private int num = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        inintdata();
    }

    private void inintdata()
    {
        //请求权限
        requestEasyPermission();

    }

    /***
     * 请求权限
     */
    private void requestEasyPermission() {
        String[] permissones = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (!EasyPermissions.hasPermissions(getApplicationContext(), permissones)) {
            EasyPermissions.requestPermissions(this, getString(R.string.app_name), num, permissones);
        } else {
            configAnimation();
        }
    }

    private void configAnimation()
    {
        //单纯的开个动画
        AlphaAnimation anm = new AlphaAnimation(0, 1);
        anm.setDuration(2000);
        anm.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation)
            {
                startActivity(HomeActivity.class,true);
            }

            @Override
            public void onAnimationRepeat(Animation animation)
            {

            }
        });
        anm.start();
        reWelcomeLayout.setAnimation(anm);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults,this);
    }


    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms)
    {
        startActivity(HomeActivity.class, true);
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms)
    {
        startActivity(HomeActivity.class, true);
    }


}
