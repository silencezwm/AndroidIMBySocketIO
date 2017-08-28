package com.silencezwm.androidimbysocketio.holer;

import android.view.View;
import android.widget.TextView;

import com.silencezwm.androidimbysocketio.R;
import com.silencezwm.androidimbysocketio.base.BaseHolderRV;
import com.silencezwm.androidimbysocketio.bean.MessageRecord;

import butterknife.BindView;

public class ChatAcceptViewHolder extends BaseHolderRV<MessageRecord> {

    @BindView(R.id.name_tv)
    TextView mNameTv;
    @BindView(R.id.content_tv)
    TextView mContentTv;

    public ChatAcceptViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    protected void bindData() {
        mNameTv.setText(mDataBean.getUserName() + ":");
        mContentTv.setText(mDataBean.getContent());
    }


}
