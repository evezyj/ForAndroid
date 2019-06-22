package com.one.frontend.interviewexam.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.one.frontend.interviewexam.R;
import com.one.frontend.interviewexam.model.PaperDetail;

import java.util.List;

public class ExamListAdapter extends ArrayAdapter {

    private final int resourceId;
    public ExamListAdapter(@NonNull Context context, int resource, List<PaperDetail> paperDetails) {
        super(context, resource,paperDetails);
        resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PaperDetail paperDetail = (PaperDetail) getItem(position); // 获取当前项的Fruit实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象
        ImageView image = (ImageView) view.findViewById(R.id.image);//获取该布局内的图片视图
        TextView name = (TextView) view.findViewById(R.id.name);//获取该布局内的文本视图
        TextView count = (TextView) view.findViewById(R.id.count);
        TextView time = (TextView) view.findViewById(R.id.time);
        //为图片视图设置图片资源
        if(paperDetail.getType().equals("C")){
            image.setImageResource(R.mipmap.c);
        }else if(paperDetail.getType().equals("S")){
            image.setImageResource(R.mipmap.spring);
        }else if(paperDetail.getType().equals("J")){
            image.setImageResource(R.mipmap.java);
        }else{
            image.setImageResource(R.mipmap.commonimage);
        }
        name.setText(paperDetail.getName());//为文本视图设置文本内容
        time.setText(paperDetail.getTime());
        String a = String.valueOf(paperDetail.getCount());
        count.setText(a);
        return view;
    }
}
