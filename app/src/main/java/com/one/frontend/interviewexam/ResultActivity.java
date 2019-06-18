package com.one.frontend.interviewexam;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import com.one.frontend.interviewexam.model.UserInfo;
import com.one.frontend.interviewexam.task.GetUserInfoTask;

public class ResultActivity extends AppCompatActivity {
    private TextView count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int userId = getIntent().getIntExtra("userId", 0);
        Log.e("activity info userid", String.valueOf(userId));
        bindView();
        GetUserInfoTask task = new GetUserInfoTask();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        task.setCallBack(new GetUserInfoTask.Callback() {
            @Override
            public void setObj(UserInfo userInfo) {
                Log.i("ac ", "userInfo " + userInfo.toString());
                count.setText(String.valueOf(userInfo.getCountCorrect()*10));
                String str = userInfo.getTrueSubject();
                Log.i("ac ", " str: " + str);
                str = str.substring(str.indexOf("[") + 1, str.indexOf("]"));
                Log.i("ac ", " str: " + str);

                String[] a = str.split(",");
                for(String s : a){
                    Log.i("ac ", " s: " + s);
                }

                Log.i("ac ", " " + userInfo.toString());
            }
        });
        task.execute(userId);

    }

    public void bindView() {
        count = findViewById(R.id.count);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish(); // back button
        return true;
    }
}
