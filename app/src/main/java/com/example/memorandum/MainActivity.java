package com.example.memorandum;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends ListActivity {
    private static final String TAG = "MainActivity";
    private String[] list_data =new String[5];
    private SimpleAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DBManager dbManager = new DBManager(MainActivity.this);
//        List<MemoItem> memolist = dbManager.listAll();
//        Log.i(TAG, "memolist.size() = " + memolist.size() + "\n");
//        int i = 0;
//        for(MemoItem item : memolist){
//            list_data[i] = item.getMemoName();
//            Log.i(TAG, "list_data["+ i + "] = " + item.getMemoName() + "\n");
//            i++;
//        }
        for(int i = 0; i<5; i++){
            list_data[i] = dbManager.findById(i).getMemoName();
            Log.i(TAG, "list_data["+ i + "] = " + list_data[i] + "\n");
        }

        ListAdapter adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list_data);
        setListAdapter(adapter);
    }
}