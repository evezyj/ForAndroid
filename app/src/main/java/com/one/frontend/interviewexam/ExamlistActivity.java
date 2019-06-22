package com.one.frontend.interviewexam;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.one.frontend.interviewexam.adapter.ExamListAdapter;
import com.one.frontend.interviewexam.api.PaperDetailApi;
import com.one.frontend.interviewexam.model.PaperDetail;
import com.one.frontend.interviewexam.task.ExamListTask;

import java.util.List;

public class ExamlistActivity extends AppCompatActivity implements View.OnClickListener{
    private PaperDetailApi paperDetailApi;
    private ListView listView;
    private List<PaperDetail> data;
    private ExamListAdapter adapter;
    private String[] i;
    private int userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_examlist);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        userId = getIntent().getIntExtra("userId",0);
        Log.e("activity info ", "userId：" + userId);
        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
        try {
            listView = findViewById(R.id.list_exam);

            paperDetailApi = new PaperDetailApi();
            Log.e("activity info", "executing...");
            ExamListTask task = new ExamListTask();
            task.setCallBack(new ExamListTask.Callback() {
                @Override
                public void setList(List<PaperDetail> paperDetails) {
                    Log.e("activity info", "executing...");
                    adapter = new ExamListAdapter(ExamlistActivity.this, R.layout.examitem, paperDetails);
                    listView.setAdapter(adapter);
                }
            });
            task.execute();
        } catch (Exception e) {
            Log.e("activity info error", "executing..." + e.getMessage());
        }
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(ExamlistActivity.this, "you click", Toast.LENGTH_LONG).show();
                Log.d("item", "onItemClick");
                Intent intent = new Intent(ExamlistActivity.this, PaperActivity.class);
                intent.putExtra("pdId", position + 1);
                intent.putExtra("userId",userId);
                setResult(RESULT_OK, intent);
                startActivity(intent);
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish(); // back button
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.leftButton:
                Intent intent = new Intent(ExamlistActivity.this, UserInfoActivity.class);
                startActivity(intent);
        }

    }
}
