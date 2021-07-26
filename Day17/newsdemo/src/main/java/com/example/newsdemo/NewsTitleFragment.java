package com.example.newsdemo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NewsTitleFragment extends Fragment {

    //  是否为双叶模式
    private boolean mIsTwoPane;

    private RecyclerView mRv;

    private class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

        private List<News> mList = News.getNews();

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(getActivity())
                    .inflate(R.layout.item_news_title, parent, false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            itemView.setOnClickListener(v -> {
                News news = mList.get(viewHolder.getAdapterPosition());
                if (mIsTwoPane) {
                    //  双页模式，刷新Fragment
                    NewsContentFragment fragment = (NewsContentFragment) getFragmentManager()
                            .findFragmentById(R.id.fragment_news_content);
                    fragment.refresh(news.getTitle(), news.getContent());
                } else {
                    //  单页模式，跳转Activity
                    NewsContentActivity.startActivity(getActivity(), news);
                }
            });
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            News news = mList.get(position);
            holder.bind(news);
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView mTvTitle;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                mTvTitle = itemView.findViewById(R.id.tv_title);
            }

            public void bind(News news) {
                mTvTitle.setText(news.getTitle());
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contentView = inflater.inflate(R.layout.fragment_news_title, container, false);

        mRv = contentView.findViewById(R.id.rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRv.setLayoutManager(layoutManager);
        NewsAdapter adapter = new NewsAdapter();
        mRv.setAdapter(adapter);

        return contentView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getActivity().findViewById(R.id.fragment_news_content) != null) {
            //  Activity中可以找到fragment_news_content（详情的Fragment）则为双页模式
            mIsTwoPane = true;
        } else {
            mIsTwoPane = false;
        }
    }
}
