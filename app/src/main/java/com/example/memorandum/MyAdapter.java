package com.example.memorandum;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

//适配器 用于配置列表的item
class ViewHolder{
    public ImageView itemIcon;
    public TextView itemNoteTitle;
    public ImageView itemNoteDate;
    public TextView itemNoteDes;
    public TextView itemNotePre;
    View itemView;

    public ViewHolder(View itemView) {
        if (itemView == null){
            throw new IllegalArgumentException("item View can not be null!");
        }
        this.itemView = itemView;
        itemIcon = (ImageView) itemView.findViewById(R.id.rand_icon);
        itemNoteTitle = (TextView) itemView.findViewById(R.id.item_note_title);
        itemNoteDate = (ImageView) itemView.findViewById(R.id.item_note_date);
        itemNoteDes = (TextView) itemView.findViewById(R.id.des);
        itemNotePre = (TextView) itemView.findViewById(R.id.pre);
    }
}

public class MyAdapter extends BaseAdapter {
    private List<MemoItem> memoList;
    private LayoutInflater layoutInflater;

    public MyAdapter(Context context, List<MemoItem> memoList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.memoList = memoList;
        //layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return memoList.size();
    }

    @Override
    public Object getItem(int position) {
        return memoList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.memo_list,null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        MemoItem memo = memoList.get(position);
        holder.itemNoteTitle.setText(memo.getMemoName());
        if(memo.getIsStar() == 1){
            holder.itemNoteDate.setImageResource(R.drawable.ic_baseline_star_24);
        }
        holder.itemNoteDes.setText("");
        holder.itemNotePre.setText(memo.getLastModificationTime());
        return convertView;
    }
}
