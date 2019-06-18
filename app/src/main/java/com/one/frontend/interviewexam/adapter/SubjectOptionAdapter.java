package com.one.frontend.interviewexam.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.one.frontend.interviewexam.R;
import com.one.frontend.interviewexam.model.SubjectOption;

import java.util.List;

public class SubjectOptionAdapter extends BaseAdapter {

    private Context context;

    private List<SubjectOption> subjectOptions;

    private boolean flag;

    private List<Integer> checkBoxIDList;         //存储checkBox的值

    //get set
    public List<Integer> getCheckBoxIDList() {
        return checkBoxIDList;
    }

    public void setCheckBoxIDList(List<Integer> checkBoxIDList) {
        this.checkBoxIDList = checkBoxIDList;
    }

    public SubjectOptionAdapter() {
    }

    public SubjectOptionAdapter(List<SubjectOption> subjectOptions, Context context, List<Integer> checkBoxIDList) {
        this.subjectOptions = subjectOptions;
        this.context = context;
        this.checkBoxIDList = checkBoxIDList;
    }

    @Override
    public int getCount() {
        return subjectOptions.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder = new ViewHolder();

        convertView = LayoutInflater.from(context).inflate(R.layout.optionitem, parent, false);

        viewHolder.answerDesc = (TextView) convertView.findViewById(R.id.answerDesccription);
        viewHolder.answerOption = (CheckBox) convertView.findViewById(R.id.answerOption);
        convertView.setTag(viewHolder);

        viewHolder.answerDesc.setText(subjectOptions.get(position).getAnswerDesccription());
        //viewHolder.answerOption.setText(subjectOptions.get(position).getAnswerOption());
        viewHolder.answerOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean b) {
                if (b ) {
                    // Log.e("activity info viewHolder be", "executing..." + viewHolder.answerOption.getText().toString());
                    checkBoxIDList.add(subjectOptions.get(position).getId());
                    // Log.e("activity info viewHolder", "executing..." + viewHolder.answerOption.getText().toString());
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        TextView answerDesc;
        CheckBox answerOption;
    }
}
