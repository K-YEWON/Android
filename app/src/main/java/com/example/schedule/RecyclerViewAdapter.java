package com.example.schedule;

import android.content.Context;
import android.graphics.Color;
import android.util.Pair;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecycleViewAdapter";

    private ArrayList<RecyclerViewItem> mList = null; //장비 리스트
    private SparseBooleanArray mSelectedItems = new SparseBooleanArray(0);

    public RecyclerViewAdapter(ArrayList<RecyclerViewItem> mList) {
        this.mList = mList;
    }

    //OnClickListener Custom
    public interface OnItemClickListener {
        void onItemClick(int pos);
    }

    private OnItemClickListener onItemClickListener = null;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    //OnLongClickListenr Custom
    public interface OnLongItemClickListener {
        void onLongItemClick(int pos);
    }

    private OnLongItemClickListener onLongItemClickListener = null;

    public void setOnLongItemClickListener(OnLongItemClickListener listener) {
        this.onLongItemClickListener = listener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgView_item;
        TextView txt_main;
        //TextView txt_sub;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView_item = (ImageView) itemView.findViewById(R.id.imgView_item);
            txt_main = (TextView) itemView.findViewById(R.id.txt_main);
            //txt_sub = (TextView) itemView.findViewById(R.id.txt_sub);

            imgView_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getBindingAdapterPosition();//Adapter내 위치를 반환
                    ArrayList<Integer> slist = new ArrayList<>();//*********선택된 아이템 저장
                    if(mSelectedItems.get(position,true)){
                        mSelectedItems.put(position,false);
                        itemView.setBackgroundColor(Color.parseColor("#CC3366"));

                        //slist.add(position);
                        //Log.d(TAG,"dd:" + slist);
                        Log.d(TAG,"장비 선택");
                        Log.d(TAG,"mSelectedItems:" + mSelectedItems);
                    } else {
                        //mSelectedItems.put(position, true);
                        mSelectedItems.delete(position);
                        itemView.setBackgroundColor(Color.WHITE);
                        Log.d(TAG,"장비 선택 해제");
                    }
                    if (position != RecyclerView.NO_POSITION) {
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(position);
                        }
                    }
                }
            });

            imgView_item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getBindingAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        if (onLongItemClickListener != null) {
                            onLongItemClickListener.onLongItemClick(position);
                            return true;
                        }
                    }
                    return false;
                }
            });
        }
    }

    // 아이템 뷰를 위한 뷰홀더 객체를 생성하여 리턴
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.activity_recycler_item, parent, false);

        RecyclerViewAdapter.ViewHolder vh = new RecyclerViewAdapter.ViewHolder(view);

        return vh;
    }

    // position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecyclerViewItem item = mList.get(position);

        //Log.d(TAG, "mList: " + mList);
        holder.imgView_item.setImageResource(R.drawable.ic_launcher_background);   // 사진 없어서 기본 파일로 이미지 띄움
        holder.txt_main.setText(item.getMainText());
        //holder.txt_sub.setText(item.getSubText());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

}
