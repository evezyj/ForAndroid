package com.one.frontend.interviewexam.task;

import android.os.AsyncTask;
import com.one.frontend.interviewexam.api.SubjectInfoApi;
import com.one.frontend.interviewexam.model.SubjectInfo;

public class SubjectInfoListTask extends AsyncTask<Integer, Void, SubjectInfo> {
    SubjectInfoApi subjectInfoApi = new SubjectInfoApi();
    private Callback callback;

    public void setCallBack(Callback callback) {
        this.callback = callback;
    }

    @Override
    protected SubjectInfo doInBackground(Integer... integers) {
        return subjectInfoApi.getSubjectInfo(integers[0]);
    }

    @Override
    protected void onPostExecute(SubjectInfo subjectInfo) {
        if (subjectInfo != null) {
            callback.setObj(subjectInfo);
        }
    }

    public interface Callback {
        void setObj(SubjectInfo subjectInfo);
    }
}
