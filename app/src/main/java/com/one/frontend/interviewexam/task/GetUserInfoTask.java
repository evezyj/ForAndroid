package com.one.frontend.interviewexam.task;

import android.os.AsyncTask;
import com.one.frontend.interviewexam.api.UserInfoApi;
import com.one.frontend.interviewexam.model.SubjectInfo;
import com.one.frontend.interviewexam.model.UserInfo;

public class GetUserInfoTask extends AsyncTask<Integer, Void, UserInfo> {

    UserInfoApi api = new UserInfoApi();
    private Callback callback;

    public void setCallBack(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected UserInfo doInBackground(Integer... integers) {
        return api.findUserInfo(integers[0]);
    }

    @Override
    protected void onPostExecute(UserInfo userInfo) {
        if (userInfo != null) {
            callback.setObj(userInfo);
        }
    }

    public interface Callback {
        void setObj(UserInfo userInfo);
    }
}
