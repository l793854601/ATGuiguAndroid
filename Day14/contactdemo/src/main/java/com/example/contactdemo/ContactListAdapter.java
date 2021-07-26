package com.example.contactdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class ContactListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Contact> mList;

    public ContactListAdapter(Context context, List<Contact> list) {
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

        ViewHolder holder = null;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Contact contact = mList.get(position);
        holder.bind(contact);

        return convertView;
    }

    private class ViewHolder {
        private TextView mTvNo;
        private TextView mTvName;
        private TextView mTvNumber;

        public ViewHolder(View itemView) {
            mTvNo = itemView.findViewById(R.id.tv_no);
            mTvName = itemView.findViewById(R.id.tv_name);
            mTvNumber = itemView.findViewById(R.id.tv_number);
        }

        public void bind(Contact contact) {
            mTvNo.setText("编号：" + String.valueOf(contact.getId()));
            mTvName.setText("姓名：" + contact.getName());
            mTvNumber.setText("号码：" + contact.getNumber());
        }
    }
}
