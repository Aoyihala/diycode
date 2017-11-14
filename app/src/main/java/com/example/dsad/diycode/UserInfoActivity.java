package com.example.dsad.diycode;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.bean.PicInterface;
import com.example.dsad.diycode.bean.PicStory;
import com.example.dsad.diycode.fragment.userinof.UserCollectFragment;
import com.example.dsad.diycode.fragment.userinof.UserFollerFragment;
import com.example.dsad.diycode.fragment.userinof.UserTopicsFragment;
import com.example.dsad.diycode.utils.DataCache;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.RetrofitUtil;
import com.example.dsad.diycode.utils.UiUtlis;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetUserEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserInfoActivity extends RequsetActivity {


    @Bind(R.id.img_userinfo_bg)
    ImageView imgUserinfoBg;
    @Bind(R.id.tv_userinfo_title)
    TextView tvUserinfoTitle;
    @Bind(R.id.top_userinfo_bar)
    Toolbar topUserinfoBar;
    @Bind(R.id.colltop_userinfo_bar)
    CollapsingToolbarLayout colltopUserinfoBar;
    @Bind(R.id.app_userinfo_bar)
    AppBarLayout appUserinfoBar;
    @Bind(R.id.img_userinfo_userhead)
    CircleImageView imgUserinfoUserhead;
    @Bind(R.id.tv_userinfo_username)
    TextView tvUserinfoUsername;
    @Bind(R.id.tv_userinfo_userword)
    TextView tvUserinfoUserword;
    @Bind(R.id.tab_userinfo_tabs)
    TabLayout tabUserinfoTabs;
    @Bind(R.id.view_userinfo_)
    ViewPager viewUserinfo;
    private String user_login;
    private DataCache data_cache;
    private List<String> titles;
    private UserDetail nowuser;
    private List<Fragment> fragments;
    private MyUserinfoAdpter userinfo_adpter;
    private CollapsingToolbarLayoutState state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        ininitsession();
    }

    private void inintdata() {
        getLoginName();
        gettitles();
        inintfragment();

    }

    private void inintfragment() {
        fragments = new ArrayList<>();
        Bundle budle = new Bundle();
        budle.putString("user",user_login);
        UserTopicsFragment topic_fragment = new UserTopicsFragment();
        topic_fragment.setArguments(budle);
        UserCollectFragment collect_fragment = new UserCollectFragment();
        collect_fragment.setArguments(budle);
        UserFollerFragment following = new UserFollerFragment();
        following.setArguments(budle);
        fragments.add(topic_fragment);
        fragments.add(collect_fragment);
        fragments.add(following);
    }

    private void inintui() {
        setSupportActionBar(topUserinfoBar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void ininitsession() {
        appUserinfoBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (state != CollapsingToolbarLayoutState.EXPANDED) {
                        state = CollapsingToolbarLayoutState.EXPANDED;//修改状态标记为展开
                        colltopUserinfoBar.setTitle("用户详情");//设置title为EXPANDED
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (state != CollapsingToolbarLayoutState.COLLAPSED) {
                        colltopUserinfoBar.setTitle("");//设置title不显示
                        //playButton.setVisibility(View.VISIBLE);//隐藏播放按钮
                        state = CollapsingToolbarLayoutState.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (state != CollapsingToolbarLayoutState.INTERNEDIATE) {
                        if(state == CollapsingToolbarLayoutState.COLLAPSED){
                            //playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        colltopUserinfoBar.setTitle("INTERNEDIATE");//设置title为INTERNEDIATE
                        state = CollapsingToolbarLayoutState.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });
    }

    public void getLoginName() {
        user_login = getIntent().getStringExtra("user");
        if (user_login != null && !user_login.equals("")) {
            MyApplication.getmDiycode().getUser(user_login);
        }
    }

    public void gettitles() {
        titles = UiUtlis.getViewPagerTitle(R.array.usertitle);
    }

    //---------------------------请求----------------------------------------
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getUser(GetUserEvent eva) {
        if (eva.isOk()) {
            nowuser = eva.getBean();
            //拆分出title
            cofiguserinfo();
        }
    }

    private void cofiguserinfo() {
        //发帖
        //收藏
        //关注
        String topic_count = titles.get(0)+"{"+nowuser.getTopics_count()+"}";
        String favorte_count = titles.get(1)+"{"+nowuser.getFavorites_count()+"}";
        String following_count =  titles.get(2)+"{"+nowuser.getFollowing_count()+"}";
        titles.set(0,topic_count);
        titles.set(1,favorte_count);
        titles.set(2,following_count);
        //设置activty该显示的
        tvUserinfoUsername.setText(nowuser.getName());
        tvUserinfoUserword.setText(nowuser.getTagline());
        Glide.with(getApplicationContext()).load(ImagReplace.getImageUrl(nowuser.getAvatar_url()))
                .into(imgUserinfoUserhead);
        RetrofitUtil.getInstance().getRetrofit().create(PicInterface.class)
        .getpicstory().enqueue(new Callback<PicStory>() {
            @Override
            public void onResponse(Call<PicStory> call, Response<PicStory> response) {
                Glide.with(getApplicationContext()).load(response.body().getLink()).into(imgUserinfoBg);
            }
            @Override
            public void onFailure(Call<PicStory> call, Throwable t) {
                Snackbar.make(imgUserinfoBg,"访问背景图失败",2000).show();
            }
        });
        //设置viewpager
        userinfo_adpter = new MyUserinfoAdpter(getSupportFragmentManager());
        viewUserinfo.setAdapter(userinfo_adpter);
        //设置滚动范围
        viewUserinfo.setOffscreenPageLimit(userinfo_adpter.getCount());
        tabUserinfoTabs.setupWithViewPager(viewUserinfo);
    }

    class MyUserinfoAdpter extends FragmentPagerAdapter
    {

        public MyUserinfoAdpter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }
    private enum CollapsingToolbarLayoutState {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

}
