package com.example.dsad.diycode;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dsad.diycode.appliction.MyApplication;
import com.example.dsad.diycode.base.RequsetActivity;
import com.example.dsad.diycode.utils.TosatUtils;
import com.gcssloop.diycode_sdk.api.login.event.LoginEvent;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import at.markushi.ui.ActionView;
import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends RequsetActivity {

    @Bind(R.id.btn_login_back)
    ActionView btnLoginBack;
    @Bind(R.id.top_login_bar)
    Toolbar topLoginBar;
    @Bind(R.id.edit_login_username)
    TextInputEditText editLoginUsername;
    @Bind(R.id.edit_login_usernameinputlayout)
    TextInputLayout editLoginUsernameinputlayout;
    @Bind(R.id.edit_login_password)
    TextInputEditText editLoginPassword;
    @Bind(R.id.edit_login_passwordinputlayout)
    TextInputLayout editLoginPasswordinputlayout;
    @Bind(R.id.btn_login_login)
    Button btnLoginLogin;
    @Bind(R.id.tv_login_register)
    TextView tvLoginRegister;
    private String msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        inintdata();
        inintui();
        inintsession();
    }

    private void inintdata() {
        configtab();
    }

    private void configtab() {
        setSupportActionBar(topLoginBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void inintui() {
        editLoginUsernameinputlayout.setHint("请输入用户名");
        editLoginPasswordinputlayout.setHint("请输入密码");

    }

    private void inintsession() {
        btnLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = editLoginUsername.getText().toString();
                String password = editLoginPassword.getText().toString();
                if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
                    editLoginPasswordinputlayout.setErrorEnabled(true);
                    editLoginPasswordinputlayout.setError("用户名或密码不能为空");
                    return;
                }
                MyApplication.getmDiycode().login(username, password);
                MyApplication.getmDiycode().getUser(username);


            }
        });
        btnLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void login(LoginEvent eva) {
        if (eva.isOk()) {
            MyApplication.getmDiycode().getMe();
            Log.e("user", eva.getBean().getAccess_token());
            finish();
        }
        else
            {
            switch (eva.getCode()) {
                case -1:
                    msg = "请检查网络连接";
                    break;
                case 400:
                case 401:
                    msg = "请检查用户名和密码是否正确";
                    break;
            }
            TosatUtils.ShowToast(msg);

        }

    }

}
