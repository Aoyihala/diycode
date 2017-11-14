package com.example.dsad.diycode;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.bean.PicInterface;
import com.example.dsad.diycode.bean.PicStory;
import com.example.dsad.diycode.fragment.NewsFragment;
import com.example.dsad.diycode.fragment.SiteFragment;
import com.example.dsad.diycode.fragment.TopicFragment;
import com.example.dsad.diycode.utils.DataCache;
import com.example.dsad.diycode.utils.ImagReplace;
import com.example.dsad.diycode.utils.RetrofitUtil;
import com.example.dsad.diycode.utils.UiUtlis;
import com.gcssloop.diycode_sdk.api.user.bean.UserDetail;
import com.gcssloop.diycode_sdk.api.user.event.GetMeEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends RequsetActivity {
    @Bind(R.id.btn_home_menu)
    ActionView btnHomeMenu;
    @Bind(R.id.top_home_bar)
    Toolbar topHomeBar;
    @Bind(R.id.tab_home_tabs)
    TabLayout tabHomeTabs;
    @Bind(R.id.nav_home_leftmenu)
    NavigationView navHomeLeftmenu;
    @Bind(R.id.dra_home_layout)
    DrawerLayout draHomeLayout;
    @Bind(R.id.viewpager_home_mainview)
    ViewPager viewpagerHomeMainview;
    @Bind(R.id.btn_home_addtopic)
    FloatingActionButton btnHomeaddTopic;
    @Bind(R.id.app_home_bar)
    AppBarLayout appHomeBar;
    //请求状况描述
    private List<Fragment> allfragment = new ArrayList<>();
    private List<String> top_titles = new ArrayList<>();
    private MyViewPagerAdpter main_adpter;
    private ImageView img_userHead;
    private TextView tv_username;
    private TextView tv_userword;
    private ImageView left_menubg;
    private RelativeLayout re_laytive;
    private UserDetail user_info;
    private DataCache mCache;
    private TopicFragment topic_f;
    private NewsFragment news_f;
    private SiteFragment site_f;
    private PicInterface picInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }

    private void inintdata()
    {
        mCache = new DataCache(getApplicationContext());
        //获取titles
        getTitles();
        //初始化fragment
        inintfragment();
        //设置toolbar
        configtoolbar();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topbar_home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notfication:
                startActivity(UserReplyActivity.class, false);
                break;
            case R.id.action_home_setting:
                startActivity(SettingActivity.class,false);
                break;
        }
        return true;
    }

    public void getTitles() {
        top_titles = UiUtlis.getViewPagerTitle(R.array.titles);
    }

    private void inintfragment()
    {
        topic_f = new TopicFragment();
        news_f= new NewsFragment();
        site_f= new SiteFragment();
        allfragment.add(topic_f);
        allfragment.add(news_f);
        allfragment.add(site_f);
    }

    private void configtoolbar() {
        setSupportActionBar(topHomeBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void inintui() {
        //设置viewpager
        configviewpager();
        //设置tab
        configtablayout();
        //设置侧滑菜单相关
        configleftmenu();
    }

    private void configleftmenu() {
        navHomeLeftmenu.setItemIconTintList(null);
        img_userHead = navHomeLeftmenu.getHeaderView(0).findViewById(R.id.img_left_myhead);
        tv_username = navHomeLeftmenu.getHeaderView(0).findViewById(R.id.tv_left_username);
        tv_userword = navHomeLeftmenu.getHeaderView(0).findViewById(R.id.tv_left_userword);
        re_laytive = navHomeLeftmenu.getHeaderView(0).findViewById(R.id.rela_left_layout);
        left_menubg = navHomeLeftmenu.getHeaderView(0).findViewById(R.id.img_leftmenu_background);
        //初始化id完毕
        picInterface=RetrofitUtil.getInstance().getRetrofit().create(PicInterface.class);
        picInterface.getpicstory().enqueue(new Callback<PicStory>() {
            @Override
            public void onResponse(retrofit2.Call<PicStory> call, Response<PicStory> response) {
                Glide.with(getApplicationContext()).load(response.body().getLink())
                  .into(left_menubg);
            }
            @Override
            public void onFailure(retrofit2.Call<PicStory> call, Throwable t) {

            }
        });
        //加载背景图片
        re_laytive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //跳往登陆界面
                if (MyApplication.getmDiycode().isLogin()&&mCache.getMe()!=null) {
                    Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                    intent.putExtra("user", user_info.getLogin());
                    startActivity(intent);

                } else {
                    startActivity(LoginActivity.class, false);
                }
            }
        });
        loadmenudata();
    }

    private void configviewpager() {
        main_adpter = new MyViewPagerAdpter(getSupportFragmentManager(), allfragment, top_titles);
        viewpagerHomeMainview.setAdapter(main_adpter);
        viewpagerHomeMainview.setOffscreenPageLimit(main_adpter.getCount());
    }

    private void configtablayout() {
        //可滚动(在确定固定条数时不要设置这个选项)
        //tabHomeTabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        //与viewpager绑定
        tabHomeTabs.setupWithViewPager(viewpagerHomeMainview);
    }

    private void inintsession() {
        btnHomeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (draHomeLayout.isDrawerOpen(Gravity.LEFT)) {
                    draHomeLayout.closeDrawers();
                    return;
                }
                draHomeLayout.openDrawer(Gravity.LEFT);
            }
        });
        navHomeLeftmenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_mytie:
                        //我的话题
                        if (!MyApplication.getmDiycode().isLogin()) {
                            startActivity(LoginActivity.class, false);
                        } else {
                            startActivity(MyTopicActivity.class, false);
                        }
                        break;
                    case R.id.action_myfavorite:
                        //我的收藏
                        if (!MyApplication.getmDiycode().isLogin()) {
                            startActivity(LoginActivity.class, false);
                        } else {
                            startActivity(MyFavoriteActivity.class, false);
                        }
                        break;
                    case R.id.action_about:
                        //关于
                        Uri myBlogUri = Uri.parse("https://github.com/Aoyihala/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, myBlogUri);
                        startActivity(intent);
                        break;
                    case R.id.action_setting:
                        //设置
                        startActivity(SettingActivity.class,false);
                        break;
                    case R.id.action_nodeselect:
                        //节点选择
                        startActivity(NodeActivity.class,false);
                        break;
                }
                return true;
            }
        });
        //添加帖子
        btnHomeaddTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(HomeActivity.this,AddTopicActivity.class);
                //可见(viewpager)
                if (viewpagerHomeMainview.getCurrentItem()==0)
                {
                    intent.putExtra("code",1);
                }
                if (viewpagerHomeMainview.getCurrentItem()==1)
                {
                    intent.putExtra("code",2);
                }
                startActivity(intent);
            }
        });
        viewpagerHomeMainview.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==2)
                {
                    btnHomeaddTopic.setVisibility(View.INVISIBLE);
                }
                else
                {
                    btnHomeaddTopic.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    static class MyViewPagerAdpter extends FragmentPagerAdapter {
        private List<Fragment> allfragment;
        private List<String> top_titles;

        public MyViewPagerAdpter(FragmentManager fm, List<Fragment> allfragment, List<String> top_titles) {
            super(fm);
            this.top_titles = top_titles;
            this.allfragment = allfragment;
        }

        @Override
        public Fragment getItem(int position) {
            return allfragment.get(position);
        }

        @Override
        public int getCount() {
            return allfragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return top_titles.get(position);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getMe(GetMeEvent eva) {
        if (eva.isOk()) {
            user_info = eva.getBean();
            mCache.saveMe(user_info);
            loadmenudata();
        }
    }

    private void loadmenudata() {
        user_info = mCache.getMe();
        if (user_info != null && MyApplication.getmDiycode().isLogin()) {
            tv_userword.setText(user_info.getTagline());
            tv_username.setText(user_info.getName());
            img_userHead.setVisibility(View.VISIBLE);
            Glide.with(MyApplication.getmContext()).load(ImagReplace.getImageUrl(user_info.getAvatar_url()))
                    .into(img_userHead);
        } else {
            tv_username.setText("点击登录");
            tv_userword.setText("");
            img_userHead.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadmenudata();
    }
}
