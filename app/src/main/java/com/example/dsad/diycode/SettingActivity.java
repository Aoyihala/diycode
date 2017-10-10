package com.example.dsad.diycode;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.dsad.diycode.api.DiyCodeApi;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.BaseActivity;
import com.example.dsad.diycode.utils.DataCache;
import com.example.dsad.diycode.utils.DataCleanManager;
import com.example.dsad.diycode.utils.FileUtil;
import com.example.dsad.diycode.utils.TosatUtils;
import com.example.dsad.diycode.utils.UiUtlis;
import com.example.dsad.diycode.utils.imgLoader.PictUtil;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SettingActivity extends BaseActivity {

    @Bind(R.id.top_setting_bar)
    Toolbar topSettingBar;
    @Bind(R.id.cache_size)
    TextView cacheSize;
    @Bind(R.id.tv_setting_clearcache)
    RelativeLayout tvSettingClearcache;
    @Bind(R.id.check_version)
    TextView checkVersion;
    @Bind(R.id.tv_setting_loginout)
    TextView tvSettingLoginout;
    private DataCache data_cache;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }
    private void inintdata()
    {
        data_cache = new DataCache(getApplicationContext());
        //获取缓存
        getCacheSize();
        //获取版本号
        getappVersion();

    }
    private void inintui()
    {

    }
    private void inintsession()
    {
        //清理缓存
        tvSettingClearcache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataCleanManager.deleteFolderFile(PictUtil.getSavePath().getPath(),false);
                cacheSize.setText(0+"byets");
            }
        });
        //退出登录
        tvSettingLoginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (MyApplication.getmDiycode().isLogin())
                {
                    data_cache.removeMe();
                    MyApplication.getmDiycode().logout();
                    //清除通知,收藏不用清除因为是本地收藏
                    data_cache.removeDate(DiyCodeApi.GET_USERNOTIFICTION);
                    finish();
                    TosatUtils.ShowToast("退出成功");
                }

            }
        });

    }
    public void getCacheSize()
    {
        try {
            File cacheDir = new File(FileUtil.getExternalCacheDir(this));
            String cacheSize = DataCleanManager.getCacheSize(cacheDir);
            if (!cacheSize.isEmpty()) {
                this.cacheSize.setText(cacheSize);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getappVersion() {
       checkVersion.setText(UiUtlis.getVersion());
    }
}
