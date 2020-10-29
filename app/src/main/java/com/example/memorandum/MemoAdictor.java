package com.example.memorandum;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MemoAdictor extends AppCompatActivity {
    private final String TAG = "MemoAdictor";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_adictor);

        Intent choosedMemo = getIntent();

        EditText memoNameEditText = findViewById(R.id.memoNameEditText),
                memoContentEditText = findViewById(R.id.memoContentEditText);
        TextView lastModificationTime = findViewById(R.id.LMTTextView);
        memoNameEditText.setText(choosedMemo.getStringExtra("memoName"));
        memoContentEditText.setText(choosedMemo.getStringExtra("memoContent"));
        lastModificationTime.setText(choosedMemo.getStringExtra("lastModificationTime"));
    }

    public void starMemo(View view) {

    }

    public void deleteMemo(View view) {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setMessage("确认要删除吗？")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent choosedMemo = getIntent();
                        DBManager dbManager = new DBManager(MemoAdictor.this);
                        dbManager.delete(choosedMemo.getIntExtra("id", -1));
                        Toast.makeText(getApplicationContext(),"删除成功",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(MemoAdictor.this,MainActivity.class);
                        startActivityForResult(intent, 1);
                    }
                })
                .setNegativeButton("取消",null)
                .create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(Color.BLACK);

    }

    public void saveMemo(View view) {
        Intent choosedMemo = getIntent();
        EditText memoNameEditText = findViewById(R.id.memoNameEditText),
                memoContentEditText = findViewById(R.id.memoContentEditText);
        DBManager dbManager = new DBManager(MemoAdictor.this);
        MemoItem memo = new MemoItem();
        memo.setId(choosedMemo.getIntExtra("id", -1));
        memo.setMemoName(memoNameEditText.getText().toString());
        memo.setMemoContent(memoContentEditText.getText().toString());
        memo.setCreationTime(choosedMemo.getStringExtra("creationTime"));
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy/MM/dd HH:mm");
        memo.setLastModificationTime(formatter.format(new Date(System.currentTimeMillis())));
        dbManager.update(memo);
        Intent intent = new Intent(this,MainActivity.class);
        startActivityForResult(intent, 1);
    }
}