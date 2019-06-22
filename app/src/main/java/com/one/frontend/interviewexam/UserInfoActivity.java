package com.one.frontend.interviewexam;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.one.frontend.interviewexam.model.UserInfo;
import com.one.frontend.interviewexam.task.AddUserBaseInfoTask;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInfoActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name;
    private EditText email;
    private EditText age;
    private Button nextBtn;
    private int userId = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
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


                if(name.getText().toString().isEmpty()){
                    Toast.makeText(this, "Name can not be null!", Toast.LENGTH_LONG).show();
                }else if(email.getText().toString().isEmpty()){
                    Toast.makeText(this, "Email can not be null!", Toast.LENGTH_LONG).show();
                }else if(!isEmail(email.getText().toString().trim()) ){
                    Toast.makeText(this, "Email format incorrect!", Toast.LENGTH_LONG).show();
                }else if(age.getText().toString().isEmpty()){
                    Toast.makeText(this, "Age can not be null!", Toast.LENGTH_LONG).show();
                }else if(!isNumeric(age.getText().toString())){
                    Toast.makeText(this, "Age should be number!", Toast.LENGTH_LONG).show();
                }else{
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
    }

    private void startNextActivity(Integer result) {
        Intent intent = new Intent(UserInfoActivity.this, ExamlistActivity.class);
        Log.i("userId intent","userId： " + " 12 " +result);
        intent.putExtra("userId",result);
        setResult(RESULT_OK, intent);
        startActivity(intent);
    }

    public static boolean isEmail(String email){
        if (null==email || "".equals(email)) return false;
        Pattern p = Pattern.compile("\\w+@(\\w+.)+[a-z]{2,3}"); //简单匹配
        Matcher m = p.matcher(email);
        Log.i("m.matches()","m.matches()" + " 12 " +m.matches());
        return m.matches();
    }

    public boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if( !isNum.matches() ){
            return false;
        }
        return true;
    }
}
