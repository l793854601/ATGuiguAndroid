package com.example.articlelistdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ArticleListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Article> mList;

    public ArticleListAdapter(Context context, List<Article> list) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_article_list, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Article article = mList.get(position);
        viewHolder.bindHolder(article);

        return convertView;
    }

    private class ViewHolder {

        private ImageView mIvIcon;
        private TextView mTvTitle;
        private TextView mTvSummary;

        public ViewHolder(View itemView) {
            mIvIcon = itemView.findViewById(R.id.iv_icon);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvSummary = itemView.findViewById(R.id.tv_summary);
        }

        public void bindHolder(Article article) {
            //  使用Glide加载图片
            Glide.with(mContext)
                    .load(article.getShowCover())
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher_round)
                    .into(mIvIcon);

            //  使用图片工具类加载图片
//            ImageUtils.load(mContext, article.getShowCover(), mIvIcon);

            mTvTitle.setText(article.getTitle());
            mTvSummary.setText(article.getSummary());
        }
    }
}
