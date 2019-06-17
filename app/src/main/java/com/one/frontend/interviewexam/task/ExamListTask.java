package com.one.frontend.interviewexam.task;

import android.os.AsyncTask;
import com.one.frontend.interviewexam.api.PaperDetailApi;
import com.one.frontend.interviewexam.model.PaperDetail;

import java.util.List;

public class ExamListTask extends AsyncTask<Void,Void,List<PaperDetail>> {
     PaperDetailApi paperDetailApi = new PaperDetailApi();
     private Callback callback;

    public void setCallBack(Callback callback){
        this.callback = callback;
    }
    @Override
    public List<PaperDetail> doInBackground(Void... voids) {
        return paperDetailApi.fetchPaperDetail();
    }

    @Override
    protected void onPostExecute(List<PaperDetail> sisters) {
        if (sisters != null){
            callback.setList(sisters);
        }
    }

    public interface Callback {
        void setList(List<PaperDetail> paperDetails);
    }
}
