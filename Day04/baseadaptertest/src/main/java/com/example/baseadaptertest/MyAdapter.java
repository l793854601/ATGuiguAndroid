package com.example.baseadaptertest;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private static final String TAG = "MyAdapter";

    private Context mContext;
    private List<ShopInfo> mList;

    public MyAdapter(Context context, List<ShopInfo> list) {
        mContext = context;
        mList = list;
    }

    /*
        返回集合数据的数量
     */
    @Override
    public int getCount() {
        Log.d(TAG, "getCount: " + mList.size());
        return mList.size();
    }

    /*
        返回指定下标对应的数据对象
     */
    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * 返回指定下标对应的View对象
     * @param position 下标
     * @param convertView 指定下标对应的被复用的View对象（一开始为null，被复用后就不为null了）
     * @param parent 父视图，即ListView
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView, convertView: " + convertView + ", position: " + position);

        //  优化1：针对List的复用机制
//        if (convertView == null) {
//            //  convertView为null，则加载布局
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
//        }
//        //  初始化子View
//        ImageView ivIcon = convertView.findViewById(R.id.iv_icon);
//        TextView tvName = convertView.findViewById(R.id.tv_name);
//        TextView tvContent = convertView.findViewById(R.id.tv_content);
//        //  给子View设置数据
//        ShopInfo shopInfo = mList.get(position);
//        ivIcon.setImageResource(shopInfo.getIcon());
//        tvName.setText(shopInfo.getName());
//        tvContent.setText(shopInfo.getContent());

        //  优化2：减少findViewById的次数
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_layout, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ShopInfo shopInfo = mList.get(position);
        viewHolder.bind(shopInfo);

        return convertView;
    }

    private class ViewHolder {
        private ImageView mIvIcon;
        private TextView mTvName;
        private TextView mTvContent;

        public ViewHolder(View itemView) {
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvContent = itemView.findViewById(R.id.tv_content);
        }

        public void bind(ShopInfo shopInfo) {
            mIvIcon.setImageResource(shopInfo.getIcon());
            mTvName.setText(shopInfo.getName());
            mTvContent.setText(shopInfo.getContent());
        }
    }
}
