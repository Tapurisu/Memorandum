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
        final SwipeRefreshLayout swiper = findViewById(R.id.swiper);
        swiper.setColorSchemeResources(R.color.colorPrimary);
        listView = findViewById(R.id.list_item);

        DBManager dbManager = new DBManager(MainActivity.this);
        memolist = dbManager.listAll();
        MyAdapter adapter = new MyAdapter(this, memolist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);

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

    public void searchMemo(View view) {

    }

    public void createMemo(View view) {
        Toast.makeText(getApplicationContext(),"新建备忘录",Toast.LENGTH_LONG).show();
    }
}