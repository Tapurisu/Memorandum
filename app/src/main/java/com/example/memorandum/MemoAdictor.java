package com.example.memorandum;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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