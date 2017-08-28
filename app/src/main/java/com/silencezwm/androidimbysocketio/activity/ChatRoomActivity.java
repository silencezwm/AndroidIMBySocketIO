package com.silencezwm.androidimbysocketio.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.silencezwm.androidimbysocketio.R;
import com.silencezwm.androidimbysocketio.adapter.ChatRoomAdapter;
import com.silencezwm.androidimbysocketio.bean.MessageRecord;
import com.silencezwm.androidimbysocketio.bean.ObserverModel;
import com.silencezwm.androidimbysocketio.chatroom.BaseChatRoom;
import com.silencezwm.androidimbysocketio.chatroom.MainChatRoom;
import com.silencezwm.androidimbysocketio.listener.IConstants;
import com.silencezwm.androidimbysocketio.listener.IEventType;
import com.silencezwm.androidimbysocketio.socket.AppSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author silencezwm on 2017/8/25 下午2:31
 * @email silencezwm@gmail.com
 * @description 聊天室
 */
public class ChatRoomActivity extends AppCompatActivity implements Observer {

    @BindView(R.id.recycler)
    RecyclerView mRecycler;
    @BindView(R.id.et_input_msg)
    EditText mEtInputMsg;
    @BindView(R.id.btn_send)
    ImageButton mBtnSend;

    private String mUsername;
    private int mNumUsers;

    private ChatRoomAdapter mChatRoomAdapter;
    private List<MessageRecord> mMsgRecordList = new ArrayList<>();

    private boolean mTyping = false;
    private Handler mTypingHandler = new Handler();
    private static final int TYPING_TIMER_LENGTH = 600;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);
        ButterKnife.bind(this);
        MainChatRoom.getInstance().addObserver(this);

        getData();
        initUI();
    }

    private void getData() {
        mUsername = getIntent().getStringExtra(IConstants.USERNAME);
        mNumUsers = getIntent().getIntExtra(IConstants.NUMUSERS, 1);
    }

    private void initUI() {
        mChatRoomAdapter = new ChatRoomAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mChatRoomAdapter.setDatas(mMsgRecordList);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mChatRoomAdapter);
        mChatRoomAdapter.setRecycler(mRecycler);

        MessageRecord record = new MessageRecord();
        record.setUserName("聊天室");
        record.setContent("欢迎  " + mUsername + "  的到来!聊天室现在有" + mNumUsers + "人");
        mChatRoomAdapter.addBeanToEnd(record);

        mEtInputMsg.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int id, KeyEvent event) {
                if (id == R.id.send || id == EditorInfo.IME_NULL) {
                    attemptSend();
                    return true;
                }
                return false;
            }
        });
        mEtInputMsg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (null == mUsername) return;
                if (!AppSocket.getInstance().isConnected()) return;

                if (!mTyping) {
                    mTyping = true;
                    AppSocket.getInstance().typing();
                }

                mTypingHandler.removeCallbacks(onTypingTimeout);
                mTypingHandler.postDelayed(onTypingTimeout, TYPING_TIMER_LENGTH);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        mBtnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptSend();
            }
        });

    }

    private void attemptSend() {
        if (null == mUsername) return;
        if (!AppSocket.getInstance().isConnected()) return;

        mTyping = false;

        String message = mEtInputMsg.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            mEtInputMsg.requestFocus();
            return;
        }

        mEtInputMsg.setText("");
        addBeanToRecycler(mUsername, message);

        AppSocket.getInstance().sendMessage(message);
    }

    private void addBeanToRecycler(String username, String content) {
        MessageRecord messageRecord = new MessageRecord();
        messageRecord.setUserName(username);
        messageRecord.setContent(content);
        mChatRoomAdapter.addBeanToEnd(messageRecord);
    }

    private Runnable onTypingTimeout = new Runnable() {
        @Override
        public void run() {
            if (!mTyping) return;

            mTyping = false;
            AppSocket.getInstance().stopTyping();
        }
    };

    @Override
    public void update(final Observable observable, final Object o) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (observable instanceof BaseChatRoom) {
                    ObserverModel model = (ObserverModel) o;
                    switch (model.getEventType()) {
                        case IEventType.NEW_MESSAGE:
                            ObserverModel.NewMessage newMessage = model.getNewMessage();
                            addBeanToRecycler(newMessage.getUsername(), newMessage.getMessage());
                            break;

                        case IEventType.USER_JOINED:
                            ObserverModel.UserJoined userJoined = model.getUserJoined();

                            MessageRecord joinRecord = new MessageRecord();
                            joinRecord.setUserName("聊天室");
                            joinRecord.setContent("欢迎  " + userJoined.getUsername()
                                    + "  的到来!聊天室现在有" + userJoined.getNumUsers() + "人");
                            mChatRoomAdapter.addBeanToEnd(joinRecord);

                            break;

                        case IEventType.USER_LEFT:
                            ObserverModel.UserLeft userLeft = model.getUserLeft();

                            MessageRecord leftRecord = new MessageRecord();
                            leftRecord.setUserName("聊天室");
                            leftRecord.setContent("欢送  " + userLeft.getUsername()
                                    + "  暂时离开!聊天室现在有" + userLeft.getNumUsers() + "人");
                            mChatRoomAdapter.addBeanToEnd(leftRecord);

                            break;

                        case IEventType.TYPING:
                            ObserverModel.Typing typing = model.getTyping();

                            MessageRecord typingRecord = new MessageRecord();
                            typingRecord.setUserName("聊天室");
                            typingRecord.setContent(typing.getUsername() + "正在输入...");
                            mChatRoomAdapter.addBeanToEnd(typingRecord);

                            break;

                        case IEventType.STOP_TYPING:
                            ObserverModel.StopTyping stopTyping = model.getStopTyping();

                            MessageRecord stopTypingRecord = new MessageRecord();
                            stopTypingRecord.setUserName("聊天室");
                            stopTypingRecord.setContent(stopTyping.getUsername() + "停止输入");
                            mChatRoomAdapter.addBeanToEnd(stopTypingRecord);
                            break;
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainChatRoom.getInstance().deleteObserver(this);
        AppSocket.getInstance().disConnnect();
    }
}
