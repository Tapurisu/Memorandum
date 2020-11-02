package com.example.memorandum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import cn.refactor.lib.colordialog.ColorDialog;

public class MemoAdictor extends AppCompatActivity {
    private final String TAG = "MemoAdictor";
    public MemoItem choosedMemo = new MemoItem();

    public MemoItem getMemo(){
        Intent intent = getIntent();
        MemoItem memo = new MemoItem();
        memo.setId(intent.getIntExtra("id", -1));
        memo.setMemoName(intent.getStringExtra("memoName"));
        memo.setMemoContent(intent.getStringExtra("memoContent"));
        memo.setCreationTime(intent.getStringExtra("creationTime"));
        memo.setLastModificationTime(intent.getStringExtra("lastModificationTime"));
        memo.setIsStar(intent.getIntExtra("isStar", 0));
        return memo;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_adictor);
        choosedMemo = getMemo();
        EditText memoNameEditText = findViewById(R.id.memoNameEditText),
                memoContentEditText = findViewById(R.id.memoContentEditText);
        TextView lastModificationTime = findViewById(R.id.LMTTextView);
        memoNameEditText.setText(choosedMemo.getMemoName());
        memoContentEditText.setText(choosedMemo.getMemoContent());
        lastModificationTime.setText(choosedMemo.getLastModificationTime());
        if(choosedMemo.getIsStar()==1){
            ImageButton starbtn = findViewById(R.id.starButton);
            starbtn.setImageResource(R.drawable.ic_baseline_star_24_new);
        }
    }

    public void starMemo(View view) {
        DBManager db = new DBManager(MemoAdictor.this);
        db.changeStarStatus(choosedMemo.getId());

        //广播通知MainActivity刷新
        Intent broadcast = new Intent();
        broadcast.setAction("action.refreshMain");
        sendBroadcast(broadcast);
        ImageButton starbtn = findViewById(R.id.starButton);
        if(choosedMemo.getIsStar() == 1){
            starbtn.setImageResource(R.drawable.ic_baseline_star_border_24);
            Toast.makeText(getApplicationContext(),"取消收藏",Toast.LENGTH_LONG).show();
        }
        else {
            starbtn.setImageResource(R.drawable.ic_baseline_star_24_new);
            Toast.makeText(getApplicationContext(),"收藏成功",Toast.LENGTH_LONG).show();
        }
    }

    public void deleteMemo(View view) {
        ColorDialog dialog = new ColorDialog(this);
        dialog.setContentText("确定要删除吗？");
        dialog.setColor("#0099F2");
        dialog.setPositiveListener("确定", new ColorDialog.OnPositiveListener() {
            @Override
            public void onClick(ColorDialog dialog) {
                  Intent choosedMemo = getIntent();
                  DBManager dbManager = new DBManager(MemoAdictor.this);
                  dbManager.delete(choosedMemo.getIntExtra("id", -1));
                  Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                  Intent intent = new Intent(MemoAdictor.this,MainActivity.class);
                  startActivityForResult(intent, 1);
                  dialog.dismiss();
            }
        })
        .setNegativeListener("取消", new ColorDialog.OnNegativeListener() {//取消监听
            @Override
            public void onClick(ColorDialog dialog) {
                 dialog.dismiss();
            }
        }).show();//展示
    }

    public void saveMemo(View view) {
        EditText memoNameEditText = findViewById(R.id.memoNameEditText),
                memoContentEditText = findViewById(R.id.memoContentEditText);
        DBManager dbManager = new DBManager(MemoAdictor.this);
        MemoItem memo = new MemoItem();
        memo.setId(choosedMemo.getId());
        memo.setMemoName(memoNameEditText.getText().toString());
        memo.setMemoContent(memoContentEditText.getText().toString());
        memo.setCreationTime(choosedMemo.getCreationTime());
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        memo.setLastModificationTime(formatter.format(new Date(System.currentTimeMillis())));
        memo.setIsStar(choosedMemo.getIsStar());
        dbManager.update(memo);
        Toast.makeText(getApplicationContext(),"保存成功",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent, 1);
    }
}