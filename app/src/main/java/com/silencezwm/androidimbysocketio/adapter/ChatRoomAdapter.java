package com.silencezwm.androidimbysocketio.adapter;

import android.view.View;

import com.silencezwm.androidimbysocketio.R;
import com.silencezwm.androidimbysocketio.base.BaseAdapterRV;
import com.silencezwm.androidimbysocketio.base.BaseHolderRV;
import com.silencezwm.androidimbysocketio.holer.ChatAcceptViewHolder;


public class ChatRoomAdapter extends BaseAdapterRV {

    @Override
    protected int getLayoutResID(int viewType) {
        return R.layout.item_chat;
    }

    @Override
    protected BaseHolderRV createViewHolder(View view, int viewType) {
        return new ChatAcceptViewHolder(view);
    }


}
