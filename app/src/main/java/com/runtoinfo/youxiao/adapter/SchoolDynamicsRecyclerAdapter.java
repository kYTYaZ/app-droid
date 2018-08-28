package com.runtoinfo.youxiao.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtoinfo.teacher.utils.HttpUtils;
import com.runtoinfo.youxiao.R;
import com.runtoinfo.youxiao.entity.SchoolDynamicsEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by QiaoJunChao on 2018/8/15.
 */

public class SchoolDynamicsRecyclerAdapter extends RecyclerView.Adapter {

    public final static int ONE_PIC_TYPE = 0;
    public final static int SECOND_PIC_TYPE = 1;
    public final static int THREE_PIC_TYPE = 2;

    public List<SchoolDynamicsEntity> dataList = new ArrayList<>();
    public Activity context;
    public int type;
    public Handler handler;

    public OnItemClickListener oneItemClickListener = new OnItemClickListener() {
        @Override
        public void OnItemClick(View v, int position, int type) {
            listener(position, type);
        }
    };
    public OnItemClickListener secondItemClickListener = new OnItemClickListener() {
        @Override
        public void OnItemClick(View v, int position, int type) {
            listener(position, type);
        }
    };
    public OnItemClickListener threeItemClickListener = new OnItemClickListener() {
        @Override
        public void OnItemClick(View v, int position, int type) {
            listener(position, type);
        }
    };

    public SchoolDynamicsRecyclerAdapter(Activity mContext, List<SchoolDynamicsEntity> mDatas, int mLayoutId) {
        this.dataList = mDatas;
        this.context = mContext;
    }

    public SchoolDynamicsRecyclerAdapter(Activity mContext, List<SchoolDynamicsEntity> mDatas, Handler handler){
        this.dataList = mDatas;
        this.context = mContext;
        this.handler = handler;
    }

    public void setOneOnItemClickListener(OnItemClickListener listener){
        this.oneItemClickListener = listener;
    }

    public void setSecondItemClickListener(OnItemClickListener listener){
        this.secondItemClickListener = listener;
    }

    public void setThreeItemClickListener(OnItemClickListener listener){
        this.threeItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(context);
        switch (viewType){
            case ONE_PIC_TYPE:
                view = inflater.inflate(R.layout.school_dynamics_one_pic_layout, parent, false);
                return new ViewHolder(view, oneItemClickListener);
            case SECOND_PIC_TYPE:
                view = inflater.inflate(R.layout.school_dynamics_second_layout, parent, false);
                return new SecondViewHolder(view, secondItemClickListener);
            case THREE_PIC_TYPE:
                view = inflater.inflate( R.layout.school_dynamics_three_layout, parent, false);
                return new ThreeViewHolder(view, threeItemClickListener);
                default:
                    view = inflater.inflate(R.layout.school_dynamics_one_pic_layout, parent, false);
                    return new ViewHolder(view, oneItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        SchoolDynamicsEntity schoolDynamicsEntity = dataList.get(position);
        type = schoolDynamicsEntity.getType();
        if (type == ONE_PIC_TYPE){
            ViewHolder viewHolder =(ViewHolder) holder;
            viewHolder.oneTitle.setText(schoolDynamicsEntity.getTile());
            HttpUtils.postAsynchronous(context, schoolDynamicsEntity.getImagList().get(0), viewHolder.one_img);
            viewHolder.onReader.setText(schoolDynamicsEntity.getReadNumber());
        }else if (type == SECOND_PIC_TYPE){
            SecondViewHolder secondViewHolder = (SecondViewHolder) holder;
            secondViewHolder.secondTitle.setText(schoolDynamicsEntity.getTile());
            secondViewHolder.secondMsg.setText(schoolDynamicsEntity.getMessage());
            HttpUtils.postAsynchronous(context, schoolDynamicsEntity.getImagList().get(0), secondViewHolder.second_img);
            secondViewHolder.secondRead.setText(schoolDynamicsEntity.getReadNumber() + "");
        }else{
            ThreeViewHolder threeViewHolder = (ThreeViewHolder) holder;
            threeViewHolder.threeTitle.setText(schoolDynamicsEntity.getTile());
            HttpUtils.postAsynchronous(context, schoolDynamicsEntity.getImagList().get(0), threeViewHolder.three_img1);
            HttpUtils.postAsynchronous(context, schoolDynamicsEntity.getImagList().get(1), threeViewHolder.three_img2);
            HttpUtils.postAsynchronous(context, schoolDynamicsEntity.getImagList().get(2), threeViewHolder.three_img3);
        }
    }

    @Override
    public int getItemViewType(int position) {
        SchoolDynamicsEntity dynamicsEntity = dataList.get(position);
        if (dynamicsEntity.getType() == 0){
            return ONE_PIC_TYPE;
        }else if (dynamicsEntity.getType() == 1){
            return SECOND_PIC_TYPE;
        }else{
            return THREE_PIC_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView one_img;
        TextView oneTitle, onReader;
        OnItemClickListener oneListener;

        public ViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.oneListener = listener;
            one_img = itemView.findViewById(R.id.school_one_image);
            oneTitle = itemView.findViewById(R.id.school_one_title);
            onReader = itemView.findViewById(R.id.school_one_reader);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            oneListener.OnItemClick(v, getAdapterPosition(), ONE_PIC_TYPE);
        }
    }

    class SecondViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView second_img;
        TextView secondTitle, secondMsg, secondRead;
        OnItemClickListener secondListener;
        public SecondViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.secondListener = listener;
            secondTitle = itemView.findViewById(R.id.school_second_title);
            secondMsg = itemView.findViewById(R.id.school_second_summary);
            second_img = itemView.findViewById(R.id.school_second_image);
            secondRead = itemView.findViewById(R.id.school_second_reader);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            secondListener.OnItemClick(v, getAdapterPosition(), SECOND_PIC_TYPE);
        }
    }

    class ThreeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView three_img1, three_img2, three_img3;
        TextView threeTitle;
        public OnItemClickListener threeListener;
        public ThreeViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.threeListener = listener;
            threeTitle = itemView.findViewById(R.id.school_three_tile);
            three_img1 = itemView.findViewById(R.id.school_three_img1);
            three_img2 = itemView.findViewById(R.id.school_three_img2);
            three_img3 = itemView.findViewById(R.id.school_three_img3);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            threeListener.OnItemClick(v, getAdapterPosition(), THREE_PIC_TYPE);
        }
    }

    public interface OnItemClickListener{
        void OnItemClick(View v, int position, int type);
    }

    public void listener(int position, int type){
        SchoolDynamicsEntity dynamicsEntity = dataList.get(position);
        //String html = dataList.get(position).getContent();
        Message message = new Message();
        message.what = type;
        message.obj = dynamicsEntity;
        handler.sendMessage(message);
    }
}