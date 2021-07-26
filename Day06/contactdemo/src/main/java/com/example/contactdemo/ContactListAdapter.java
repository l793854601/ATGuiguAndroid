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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_contact, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Contact contact = mList.get(position);
        viewHolder.bind(contact);

        return convertView;
    }

    private class ViewHolder {
        private TextView mTvId;
        private TextView mTvName;
        private TextView mTvNumber;

        public ViewHolder(View convertView) {
            mTvId = convertView.findViewById(R.id.tv_id);
            mTvName = convertView.findViewById(R.id.tv_name);
            mTvNumber = convertView.findViewById(R.id.tv_number);
        }

        public void bind(Contact contact) {
            mTvId.setText("编号：" + contact.getId());
            mTvName.setText("姓名：" + contact.getName());
            mTvNumber.setText("号码：" + contact.getNumber());
        }
    }
}
