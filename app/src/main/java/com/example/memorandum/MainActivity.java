package com.example.memorandum;

import android.app.AppComponentFactory;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private static final String TAG = "MainActivity";
    List<MemoItem> memolist = null;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list_item);
        DBManager dbManager = new DBManager(MainActivity.this);
        memolist = dbManager.listAll();
        inite(memolist);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        DBManager dbManager = new DBManager(MainActivity.this);
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
        startActivity(intent);
    }

    public void inite(List<MemoItem> list){
        MyAdapter adapter = new MyAdapter(this, list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),"长按目标ID:"+i,Toast.LENGTH_LONG).show();
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

    public void searchMemo(View view) {
        List<MemoItem> searchResult = null;
        EditText searchET = findViewById(R.id.searchEditText);
        String searchName = searchET.getText().toString().trim();
        if(searchName.length() != 0){
            DBManager dbManager = new DBManager(MainActivity.this);
            searchResult = dbManager.findByName(searchName);
        }else{
            searchResult = memolist;
            //Toast.makeText(getApplicationContext(),"请输入备忘录名字",Toast.LENGTH_LONG).show();
        }
        inite(searchResult);
    }

    public void createMemo(View view) {
        MemoItem newmemo = new MemoItem();
        DBManager dbManager = new DBManager(MainActivity.this);
        int validID = dbManager.findValidId();
        //Toast.makeText(getApplicationContext(),"新建备忘录ID:"+validID,Toast.LENGTH_LONG).show();
        newmemo.setId(validID);
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd/ HH:mm");
        String date_of_now = formatter.format(new Date(System.currentTimeMillis()));
        newmemo.setMemoName("新的备忘录"+validID);
        newmemo.setMemoContent("");
        newmemo.setCreationTime(date_of_now);
        newmemo.setLastModificationTime(date_of_now);
        dbManager.add(newmemo);
        memolist = dbManager.listAll();

        Intent intent = new Intent(this,MemoAdictor.class);
        intent.putExtra("id", validID);
        intent.putExtra("memoName", "新的备忘录"+validID);
        intent.putExtra("memoContent", "");
        intent.putExtra("creationTime", date_of_now);
        intent.putExtra("lastModificationTime", date_of_now);
        startActivity(intent);
    }
}