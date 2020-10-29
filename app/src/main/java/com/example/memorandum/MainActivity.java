package com.example.memorandum;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends ListActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    List<MemoItem> memolist = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager dbManager = new DBManager(MainActivity.this);
//        for(int i=0;i<10; i++){
//            MemoItem memo = new MemoItem();
//            memo.setId(i);
//            memo.setMemoName("Memo"+i);
//            memo.setMemoContent("This is the content of memo " + i);
//            SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm");
//            memo.setLastModificationTime(formatter.format(new Date(System.currentTimeMillis())));
//            dbManager.add(memo);
//        }

        memolist = dbManager.listAll();
        Log.i(TAG, "memolist.size() = " + memolist.size() + "\n");
        MyAdapter adapter = new MyAdapter(MainActivity.this,memolist);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //Toast.makeText(MainActivity.this, "打开备忘录:" + memolist.get((int)id).getMemoName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MemoAdictor.class);
        intent.putExtra("id", memolist.get(position).getId());
        intent.putExtra("memoName", memolist.get(position).getMemoName());
        intent.putExtra("memoContent", memolist.get(position).getMemoContent());
        intent.putExtra("lastModificationTime", memolist.get(position).getLastModificationTime());
        startActivity(intent);
    }
}