package com.driftingbottle.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.List;

/**
 * Created by jidan on 18-3-11.
 */

public abstract class CommonAdapter<T> extends RecyclerView.Adapter<CommonAdapter.ViewHolder>
{
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private OnItemClickListener mItemClickListener;

    public CommonAdapter(Context context, int layoutId ,List<T> datas)
    {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        this.mDatas = datas;
    }
    public CommonAdapter(Context context,int layoutId){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
    }
    public void setData(List<T> datas){
        mDatas = datas;
    }
    @Override
    public ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        ViewHolder viewHolder = ViewHolder.get(mContext, parent, mLayoutId);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        holder.mConvertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mItemClickListener != null){
                    mItemClickListener.onItemClick(position);
                }
            }
        });
        convert(holder, mDatas.get(position));
    }
    public interface OnItemClickListener{
        void onItemClick(int position);
    }
    public abstract void convert(ViewHolder holder, T t);

    public void setOnItemClickListener(OnItemClickListener listener){
        mItemClickListener = listener;

    }
    @Override
    public int getItemCount()
    {
        if(mDatas == null) return  0;
        return mDatas.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private SparseArray<View> mViews;
        private View mConvertView;
        private Context mContext;

        public ViewHolder(Context context, View itemView, ViewGroup parent)
        {
            super(itemView);
            mContext = context;
            mConvertView = itemView;
            mViews = new SparseArray<View>();
        }


        public static ViewHolder get(Context context, ViewGroup parent, int layoutId)
        {

            View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                    false);
            ViewHolder holder = new ViewHolder(context, itemView, parent);
            return holder;
        }


        /**
         * 通过viewId获取控件
         *
         * @param viewId
         * @return
         */
        public <T extends View> T getView(int viewId)
        {
            View view = mViews.get(viewId);
            if (view == null)
            {
                view = mConvertView.findViewById(viewId);
                mViews.put(viewId, view);
            }
            return (T) view;
        }
    }
}
