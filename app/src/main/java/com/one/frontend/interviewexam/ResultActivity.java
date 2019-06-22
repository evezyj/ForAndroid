package com.one.frontend.interviewexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.one.frontend.interviewexam.model.UserInfo;
import com.one.frontend.interviewexam.task.GetUserInfoTask;

public class ResultActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView count;
    private Button returnHome;
    private TextView resultTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        int userId = getIntent().getIntExtra("userId", 0);
        Log.e("activity info userid", String.valueOf(userId));
        bindView();
        GetUserInfoTask task = new GetUserInfoTask();
        resultTxt = findViewById(R.id.resultTxt);
        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
        returnHome.setOnClickListener(this);
        task.setCallBack(new GetUserInfoTask.Callback() {
            @Override
            public void setObj(UserInfo userInfo) {
                Log.i("ac ", "userInfo " + userInfo.toString());
                count.setText(String.valueOf(userInfo.getCountCorrect()*10));

            }
        });
        task.execute(userId);

    }

    public void bindView() {
        count = findViewById(R.id.count);
        returnHome = findViewById(R.id.returnHome);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.returnHome:
                Intent intent = new Intent(ResultActivity.this, UserInfoActivity.class);
                startActivity(intent);

            case R.id.leftButton:
                Intent intent2 = new Intent(ResultActivity.this, UserInfoActivity.class);
                startActivity(intent2);
        }
    }
}
