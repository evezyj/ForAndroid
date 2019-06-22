package com.one.frontend.interviewexam;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
        Button leftButton = (Button) findViewById(R.id.leftButton);
        leftButton.setOnClickListener(this);
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
        TextView questionId = findViewById(R.id.questionId);
        TextView question = (TextView) findViewById(R.id.question);
        TextView title = findViewById(R.id.titleText);
        title.setText(subjectInfo.getPaperDetail().getName());
        questionId.setText(subjectInfo.getPaperSubjects().get(position).getSubjectId()+". ");
        question.setText(subjectInfo.getPaperSubjects().get(position).getSubjectName());
        if (position == (subjectInfo.getPaperDetail().getCount() - 1)) {
            // Toast.makeText(this, "已经是最后一题了" + position, Toast.LENGTH_SHORT).show();
            nextBtn.setText("Submit");
        }
        return subjectInfo;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.leftButton:
                showDialog();
            case R.id.lastBtn:
                if (position < 1) {
                    Toast.makeText(this, "It is the first question!" + position, Toast.LENGTH_SHORT).show();
                    break;
                }
                nextBtn.setText("Next");
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
                if (nextBtn.getText().equals("Submit")) {
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
                        nextBtn.setText("Submit");
                        adapter.notifyDataSetChanged();
                        adapter = new SubjectOptionAdapter(subjectInfoObj.getPaperSubjects().get(position).getSubjectOptions(), mContext, checkBoxIDList);
                        listView.setAdapter(adapter);
                        initUI(subjectInfoObj);
                        Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().size());

                        break;
                    }else{
                        adapter.notifyDataSetChanged();
                        adapter = new SubjectOptionAdapter(subjectInfoObj.getPaperSubjects().get(position).getSubjectOptions(), mContext, checkBoxIDList);
                        listView.setAdapter(adapter);
                        initUI(subjectInfoObj);
                        Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().size());
                        for (int i = 0; i < adapter.getCheckBoxIDList().size(); i++) {

                            Log.e("activity info option", "executing..." + adapter.getCheckBoxIDList().get(i).toString());
                        }
                        break;
                    }
                }
        }
    }

    private void showDialog() {
        AlertDialog alert=new AlertDialog.Builder(PaperActivity.this).create();

        alert.setTitle("ONE？");
        alert.setMessage("Are you sure return home？");
        //添加取消按钮
        alert.setButton(DialogInterface.BUTTON_NEGATIVE,"NO",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });
        //添加"确定"按钮
        alert.setButton(DialogInterface.BUTTON_POSITIVE,"YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                Intent intent = new Intent(PaperActivity.this, ExamlistActivity.class);
                startActivity(intent);
            }
        });
        alert.show();
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish(); // back button
        return true;
    }
}
