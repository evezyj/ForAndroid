package com.one.frontend.interviewexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.one.frontend.interviewexam.model.UserInfo;
import com.one.frontend.interviewexam.task.AddUserBaseInfoTask;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText email;
    private EditText age;
    private Button nextBtn;
    private int userId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userinfo);
        bindView();
        nextBtn = findViewById(R.id.nextBtn);
        nextBtn.setOnClickListener(this);

    }

    public void bindView(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        age = findViewById(R.id.age);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.nextBtn:
                Log.i("userActivity","start ");
                AddUserBaseInfoTask task = new AddUserBaseInfoTask();

                UserInfo userInfo = new UserInfo();
                userInfo.setAge(Integer.parseInt(age.getText().toString()));
                userInfo.setEmail(email.getText().toString());
                userInfo.setName(name.getText().toString());
                task.execute(userInfo);
                task.setCallBack(new AddUserBaseInfoTask.Callback() {
                    @Override
                    public void setResult(Integer result) {
                        //userId = result;
                        userId = result;
                        startNextActivity(result);
                        Log.i("userId","result： "+result);
                    }

                });
                Log.i("userActivity","end "+userInfo.toString());

        }
    }

    private void startNextActivity(Integer result) {
        Intent intent = new Intent(UserInfoActivity.this, ExamlistActivity.class);
        Log.i("userId intent","userId： " + " 12 " +result);
        intent.putExtra("userId",result);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }
}
