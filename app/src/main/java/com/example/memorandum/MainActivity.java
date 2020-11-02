package com.example.memorandum;

import android.app.AppComponentFactory;
import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import cn.refactor.lib.colordialog.ColorDialog;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    List<MemoItem> memolist = null;
    ListView listView;
    DBManager dbManager = new DBManager(MainActivity.this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_item);
        memolist = dbManager.listAll();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("action.refreshMain");
        registerReceiver(mRefreshBroadcastReceiver, intentFilter);

        inite(memolist);
    }
    // broadcast receiver
    private BroadcastReceiver mRefreshBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("action.refreshMain")){
                memolist = dbManager.listAll();
                inite(memolist);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        memolist = dbManager.listAll();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this,MemoAdictor.class);
        intent.putExtra("id", memolist.get(position).getId());
        intent.putExtra("memoName", memolist.get(position).getMemoName());
        intent.putExtra("memoContent", memolist.get(position).getMemoContent());
        intent.putExtra("creationTime", memolist.get(position).getCreationTime());
        intent.putExtra("lastModificationTime", memolist.get(position).getLastModificationTime());
        intent.putExtra("isStar", memolist.get(position).getIsStar());
        startActivity(intent);
    }

    public void inite(List<MemoItem> list){
        MyAdapter adapter = new MyAdapter(this, list);
        if(!list.isEmpty()){
            listView.setAdapter(adapter);
            listView.setOnItemClickListener(this);
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                    Toast.makeText(getApplicationContext(),"长按目标ID:"+i,Toast.LENGTH_LONG).show();
                    ColorDialog dialog = new ColorDialog(MainActivity.this);
                    dialog.setContentText("确定要删除吗？");
                    dialog.setColor("#0099F2");
                    dialog.setPositiveListener("确定", new ColorDialog.OnPositiveListener() {
                        @Override
                        public void onClick(ColorDialog dialog) {
                            DBManager dbManager = new DBManager(MainActivity.this);
                            dbManager.delete(i);
                            memolist.remove(i);
                            Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                            inite(memolist);
                            dialog.dismiss();
                        }
                    })
                            .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {//取消监听
                                @Override
                                public void onClick(ColorDialog dialog) {
                                    dialog.dismiss();
                                }
                            }).show();//展示
                    return true;
                }
            });
            final SwipeRefreshLayout swiper = findViewById(R.id.swiper);
            swiper.setColorSchemeResources(R.color.colorPrimary);
            swiper.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            swiper.setRefreshing(false);
                        }
                    },600);
                }
            });
        }
        else {
            TextView emptyText = new TextView(this);
            emptyText.setText("还没有备忘录");
            emptyText.setGravity(Gravity.CENTER);
            emptyText.setTextSize(25);
            //设置字体大小
            RelativeLayout.LayoutParams params =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
            addContentView(emptyText, params);
            listView.setEmptyView(emptyText);
        }
    }

    public void searchMemo(View view) {
        List<MemoItem> searchResult = null;
        EditText searchET = findViewById(R.id.searchEditText);
        String searchName = searchET.getText().toString().trim();
        if(searchName.length() != 0){
            searchResult = dbManager.findByName(searchName);
        }else{
            searchResult = memolist;
            //Toast.makeText(getApplicationContext(),"请输入备忘录名字",Toast.LENGTH_LONG).show();
        }
        inite(searchResult);
    }

    public void createMemo(View view) {
        int validID = dbManager.findValidId();
        //Toast.makeText(getApplicationContext(),"新建备忘录ID:"+validID,Toast.LENGTH_LONG).show();
        MemoItem newmemo = new MemoItem(validID);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd/ HH:mm");
        String date_of_now = formatter.format(new Date(System.currentTimeMillis()));
        newmemo.setMemoName("新的备忘录"+validID);
        newmemo.setMemoContent("");
        newmemo.setCreationTime(date_of_now);
        newmemo.setLastModificationTime(date_of_now);
        newmemo.setIsStar(0);
        dbManager.add(newmemo);
        memolist = dbManager.listAll();
        inite(memolist);

        Intent intent = new Intent(this,MemoAdictor.class);
        intent.putExtra("id", validID);
        intent.putExtra("memoName", "新的备忘录"+validID);
        intent.putExtra("memoContent", "");
        intent.putExtra("creationTime", date_of_now);
        intent.putExtra("lastModificationTime", date_of_now);
        intent.putExtra("isStar", 0);
        startActivity(intent);
    }
}