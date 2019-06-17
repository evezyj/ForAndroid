package com.one.frontend.interviewexam.task;

import android.os.AsyncTask;
import com.one.frontend.interviewexam.api.UserInfoApi;
import com.one.frontend.interviewexam.model.PaperDetail;
import com.one.frontend.interviewexam.model.UserInfo;

import java.util.List;

public class AddUserBaseInfoTask extends AsyncTask<UserInfo,Void,Integer> {

    UserInfoApi userInfoApi = new UserInfoApi();
    private Callback callback;

    public void setCallBack(Callback callback){
        this.callback = callback;
    }

    @Override
    protected Integer doInBackground(UserInfo... userInfos) {
        return userInfoApi.saveUserBaseInfo(userInfos[0]);
    }

    @Override
    protected void onPostExecute(Integer result){
        if(result!=0){
            callback.setResult(result);
        }
    }

    public interface Callback {
        void setResult(Integer result);
    }
}
