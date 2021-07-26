package com.example.gridviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FunctionAdapter extends BaseAdapter {

    private Context mContext;
    private List<Function> mList;

    public FunctionAdapter(Context context, List<Function> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_function, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Function function = mList.get(position);
        viewHolder.bind(function);

        return convertView;
    }

    private class ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvName;

        public ViewHolder(View itemView) {
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
        }

        public void bind(Function function) {
            mIvIcon.setImageDrawable(function.getIcon());
            mTvName.setText(function.getName());
        }
    }
}
