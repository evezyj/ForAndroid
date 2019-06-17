package com.one.frontend.interviewexam;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import com.one.frontend.interviewexam.adapter.SubjectOptionAdapter;
import com.one.frontend.interviewexam.api.SubjectInfoApi;
import com.one.frontend.interviewexam.model.AnswerChecked;
import com.one.frontend.interviewexam.model.SubjectInfo;
import com.one.frontend.interviewexam.model.SubjectOption;
import com.one.frontend.interviewexam.model.UserInfo;
import com.one.frontend.interviewexam.task.AddUserBaseInfoTask;
import com.one.frontend.interviewexam.task.SubjectInfoListTask;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class PaperActivity extends AppCompatActivity implements View.OnClickListener {

    private SubjectInfoApi subjectInfoApi;
    private ListView listView;
    private SubjectOptionAdapter adapter;
    private Context mContext = null;
    private int position = 0;
    private Button lastBtn;
    private Button nextBtn;
    private SubjectInfo subjectInfoObj;
    private CheckBox answerOption;
    private List<Integer> checkBoxIDList;
    private List<String> trueAnswer;
    private int userId;
    private int pdId;
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        trueAnswer = new ArrayList<String>();
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Log.e("activity info", "executing...456");
        pdId = getIntent().getIntExtra("pdId", 0);
        userId = getIntent().getIntExtra("userId", 0);
        Log.e("activity info userid", "executing...456" + userId);
        mContext = PaperActivity.this;
        listView = findViewById(R.id.list_options);
        subjectInfoApi = new SubjectInfoApi();
        checkBoxIDList = new ArrayList<Integer>();
        answerOption = (CheckBox) findViewById(R.id.answerOption);
        lastBtn = (Button) findViewById(R.id.lastBtn);
        nextBtn = (Button) findViewById(R.id.nextBtn);
        lastBtn.setOnClickListener(this);
        nextBtn.setOnClickListener(this);
        SubjectInfoListTask task = new SubjectInfoListTask();
        task.setCallBack(new SubjectInfoListTask.Callback() {
            @Override
            public void setObj(SubjectInfo subjectInfo) {
                Log.e("activity info", "executing..." + subjectInfo.getPaperDetail().getDescription());
                adapter = new SubjectOptionAdapter(subjectInfo.getPaperSubjects().get(position).getSubjectOptions(), mContext, checkBoxIDList);
                Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().size());
                listView.setAdapter(adapter);
                subjectInfoObj = initUI(subjectInfo);
            }
        });
        task.execute(pdId);

    }

    private SubjectInfo initUI(SubjectInfo subjectInfo) {
        TextView question = (TextView) findViewById(R.id.question);
        TextView totalCount = (TextView) findViewById(R.id.totalCount);
        totalCount.setText(String.valueOf(subjectInfo.getPaperDetail().getCount()));
        question.setText(subjectInfo.getPaperSubjects().get(position).getSubjectName());
        if (position == (subjectInfo.getPaperDetail().getCount() - 1)) {
            // Toast.makeText(this, "已经是最后一题了" + position, Toast.LENGTH_SHORT).show();
            nextBtn.setText("提交");
        }
        return subjectInfo;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.lastBtn:
                if (position < 1) {
                    Toast.makeText(this, "已经是第一题了" + position, Toast.LENGTH_SHORT).show();
                    break;
                }
                nextBtn.setText("下一题");
                position--;
                adapter.notifyDataSetChanged();
                adapter = new SubjectOptionAdapter(subjectInfoObj.getPaperSubjects().get(position).getSubjectOptions(), mContext, checkBoxIDList);
                listView.setAdapter(adapter);
                initUI(subjectInfoObj);
                Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().size());
                for (int i = 0; i < adapter.getCheckBoxIDList().size(); i++) {

                    Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().get(i).toString());
                }
                break;
            case R.id.nextBtn:
                if (nextBtn.getText().equals("提交")) {
                    Log.e("activity info option 提交", "executing..." + adapter.getCheckBoxIDList().toString());
                    List<Integer> trueAnswers = new ArrayList<Integer>();
                    List<Integer> falseAnswers = new ArrayList<Integer>();
                    List<SubjectOption> subjectOptions = null;
                    List<Integer> a = null;
                    for (int i = 0; i < subjectInfoObj.getPaperSubjects().size(); i++) {
                        subjectOptions = subjectInfoObj.getPaperSubjects().get(i).getSubjectOptions();
                        a = new ArrayList<Integer>();
                        for (SubjectOption so : subjectOptions) {
                            if (!so.isTrue()) {
                                a.add(so.getId());
                            }
                        }
                        Log.e("activity info option", "executing..." + a.toString());
                        boolean flag = false;
                        for (Integer inta : adapter.getCheckBoxIDList()) {
                            if (a.contains(inta)) {
                                flag = true;
                            }
                        }

                        if (flag) {
                            falseAnswers.add(subjectInfoObj.getPaperSubjects().get(i).getId());
                        } else {
                            trueAnswers.add(subjectInfoObj.getPaperSubjects().get(i).getId());
                        }
                    }


                    Log.e("activity info option", "executing..." + trueAnswers.toString());

                    //添加记录2 user
                    AddUserBaseInfoTask task = new AddUserBaseInfoTask();
                    UserInfo userInfo = new UserInfo();
                    userInfo.setId(userId);
                    userInfo.setPdId(pdId);
                    userInfo.setCountCorrect(trueAnswers.size());
                    userInfo.setFalseSubject(falseAnswers.toString());
                    userInfo.setTrueSubject(trueAnswers.toString());

                    task.execute(userInfo);
                    task.setCallBack(new AddUserBaseInfoTask.Callback() {
                        @Override
                        public void setResult(Integer result) {

                        }
                    });
                    Log.e("activity info option userInfo", "executing..." + userInfo.toString());

                    Intent intent = new Intent(PaperActivity.this, ResultActivity.class);

                    intent.putExtra("userId", userId);


                    setResult(RESULT_OK, intent);
                    startActivity(intent);
                    finish();
                    break;
                } else {
                    //不是提交
                    position++;
                    if (position >= (subjectInfoObj.getPaperDetail().getCount() - 1)) {
                        // Toast.makeText(this, "已经是最后一题了" + position, Toast.LENGTH_SHORT).show();
                        nextBtn.setText("提交");
                        adapter.notifyDataSetChanged();
                        adapter = new SubjectOptionAdapter(subjectInfoObj.getPaperSubjects().get(position).getSubjectOptions(), mContext, checkBoxIDList);
                        listView.setAdapter(adapter);
                        initUI(subjectInfoObj);
                        Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().size());

                        break;
                    }
                }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish(); // back button
        return true;
    }
}
