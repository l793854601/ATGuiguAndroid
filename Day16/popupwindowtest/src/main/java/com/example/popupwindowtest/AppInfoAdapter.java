package com.example.popupwindowtest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppInfoAdapter extends BaseAdapter {

    private static final String TAG = "AppInfoAdapter";

    private Context mContext;
    private List<AppInfo> mList;

    public AppInfoAdapter(Context context, List<AppInfo> list) {
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
            Log.d(TAG, "getView: ");
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_app_info, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AppInfo appInfo = mList.get(position);
        viewHolder.bind(appInfo);

        return convertView;
    }

    private class ViewHolder {

        private ImageView mIvIcon;
        private TextView mTvName;

        public ViewHolder(View itemView) {
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
        }

        public void bind(AppInfo appInfo) {
            mIvIcon.setImageDrawable(appInfo.getIcon());
            mTvName.setText(appInfo.getName());
        }
    }
}
