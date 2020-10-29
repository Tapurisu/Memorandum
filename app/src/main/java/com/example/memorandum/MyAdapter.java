package com.example.memorandum;
import android.content.Context;
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
    public TextView itemNoteDate;
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
        itemNoteDate = (TextView) itemView.findViewById(R.id.item_note_date);
        itemNoteDes = (TextView) itemView.findViewById(R.id.des);
        itemNotePre = (TextView) itemView.findViewById(R.id.pre);
    }
}
public class MyAdapter extends BaseAdapter {

    private List<MemoItem> memoList;
    private LayoutInflater layoutInflater;
    private Context context;
    private ViewHolder holder = null;

    public MyAdapter(Context context,List<MemoItem> memoList) {
        this.memoList = memoList;
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return memoList.size();
    }

    @Override
    public Object getItem(int position) {
        return memoList.get(position).getMemoName();
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(memoList.get(position).getId()+"");
    }

    public void remove(int index){
        memoList.remove(index);
    }

    public void refreshDataSet(){
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_main,null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.itemNoteTitle.setText(memoList.get(position).getMemoName());
        holder.itemNoteDate.setText("");
        holder.itemNoteDes.setText("");
        holder.itemNotePre.setText(memoList.get(position).getLastModificationTime());
        return convertView;
    }
}
