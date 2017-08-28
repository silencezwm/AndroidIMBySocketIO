package com.silencezwm.androidimbysocketio.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.silencezwm.androidimbysocketio.R;
import com.silencezwm.androidimbysocketio.bean.ObserverModel;
import com.silencezwm.androidimbysocketio.chatroom.BaseChatRoom;
import com.silencezwm.androidimbysocketio.chatroom.MainChatRoom;
import com.silencezwm.androidimbysocketio.listener.IConstants;
import com.silencezwm.androidimbysocketio.listener.IEventType;
import com.silencezwm.androidimbysocketio.socket.AppSocket;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author silencezwm on 2017/8/25 上午11:38
 * @email silencezwm@gmail.com
 * @description 登录页面
 * 使用Java自带观察者模式实现消息监听
 */
public class LoginActivity extends AppCompatActivity implements Observer {

    @BindView(R.id.username_input)
    EditText mUsernameInput;
    @BindView(R.id.sign_in_button)
    Button mSignInButton;

    private String mUsername;
    private int mNumUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        MainChatRoom.getInstance().addObserver(this);
        initUI();
    }

    private void initUI() {
        mUsernameInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

    }

    private void attemptLogin() {
        if (!AppSocket.getInstance().isConnected()) {
            Toast.makeText(this, R.string.socket_connect_fail, Toast.LENGTH_SHORT).show();
            return;
        }

        mUsernameInput.setError(null);

        mUsername = mUsernameInput.getText().toString().trim();

        if (TextUtils.isEmpty(mUsername)) {
            mUsernameInput.setError(getString(R.string.error_field_required));
            mUsernameInput.requestFocus();
            return;
        }

        mUsername = mUsernameInput.getText().toString().trim();

        AppSocket.getInstance().addUser(mUsername);
    }

    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof BaseChatRoom) {
            final ObserverModel model = (ObserverModel) o;
            switch (model.getEventType()) {
                case IEventType.LOGIN:
                    ObserverModel.Login login = model.getLogin();
                    mNumUsers = login.getNumUsers();

                    Intent intent = new Intent(this, ChatRoomActivity.class);
                    intent.putExtra(IConstants.USERNAME, mUsername);
                    intent.putExtra(IConstants.NUMUSERS, mNumUsers);
                    startActivity(intent);
                    finish();

                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainChatRoom.getInstance().deleteObserver(this);
    }
}
