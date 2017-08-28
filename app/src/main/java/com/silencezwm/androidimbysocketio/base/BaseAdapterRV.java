package com.silencezwm.androidimbysocketio.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.silencezwm.androidimbysocketio.listener.IAdapterView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseAdapterRV<T> extends RecyclerView.Adapter implements IAdapterView {

    protected List<T> mList = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayoutResID(viewType), parent, false);
        RecyclerView.ViewHolder holder = createViewHolder(view, viewType);
        return holder;
    }

    /**
     * @return 布局文件
     */
    protected abstract int getLayoutResID(int viewType);

    /**
     * @param view
     * @return BaseHolderRV
     */
    protected abstract BaseHolderRV createViewHolder(View view, int viewType);


    protected Object getHolderData(int position) {
        return mList.get(position);
    }

    protected int getPositionOffset(int position) {
        return 0;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseHolderRV viewHolder = (BaseHolderRV) holder;
        viewHolder.setData(getHolderData(position), position, getPositionOffset(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    /**
     * 初始化数据
     *
     * @param list
     */
    public void setDatas(List<T> list) {
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    /**
     * 清空数据
     */
    public void clearDatas() {
        mList.clear();
        notifyDataSetChanged();
    }

    /**
     * 添加一条数据
     *
     * @param t
     */
    public void addData(T t) {
        mList.add(t);
        notifyItemInserted(getItemCount());
    }

    /**
     * 添加单条数据到尾部
     *
     * @param t
     */
    public void addLastDast(RecyclerView recyclerView, T t) {
        mList.add(t);
        notifyItemInserted(getItemCount());
        recyclerView.smoothScrollToPosition(getItemCount());
        if (mList.size() > 50) {
            notifyItemRemoved(0);
            mList.remove(0);
            notifyItemChanged(0, getItemCount());
        }
    }

    /**
     * 添加多条数据到尾部
     */
    public void addLastDast(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void addToStart(T message, boolean scrollToBottom) {
        mList.add(0, message);
        notifyItemRangeInserted(0, 1);
        if (mRecyclerView != null && scrollToBottom) {
            mRecyclerView.scrollToPosition(0);
        }
    }

    public void addBeanToEnd(T message) {
        mList.add(message);
        notifyItemInserted(getItemCount());
        if (mRecyclerView != null) {
            mRecyclerView.scrollToPosition(getItemCount() - 1);
        }
    }


    public void addToEnd(List<T> messages, boolean reverse) {
        if (reverse) {
            Collections.reverse(messages);
        }

        int oldSize = mList.size();
        for (int i = 0; i < messages.size(); i++) {
            T message = messages.get(i);
            mList.add(0, message);
        }
        notifyItemRangeInserted(0, messages.size());
    }


    /**
     * @param list 添加多条数据
     */
    public void addAllDatas(List<T> list) {
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public T getItemByPosition(int position) {
        return mList.get(position);
    }

    /**
     * @return 所有数据
     */
    public List<T> getDatas() {
        return mList;
    }

    /**
     * 删除一个对象, 并刷新界面
     *
     * @param bean
     */
    public void remove(T bean) {
        mList.remove(bean);
        notifyDataSetChanged();
    }

    /**
     * 根据position删除一个对象, 并刷新界面
     *
     * @param position
     */
    public void removeByPosition(int position) {
        mList.remove(position);
        notifyDataSetChanged();
    }

    public void setRecycler(RecyclerView recycler) {
        mRecyclerView = recycler;
    }

    public void onCreate() {

    }

    public void onStart() {

    }

    public void onResume() {

    }

    public void onPause() {

    }


    public void onStop() {

    }

    public void onDestroy() {

    }

}
