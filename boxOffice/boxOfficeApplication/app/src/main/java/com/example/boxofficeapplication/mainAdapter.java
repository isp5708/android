package com.example.boxofficeapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class mainAdapter extends RecyclerView.Adapter<mainAdapter.CustomViewHolder> {
    private ArrayList<movieRankList> mList;
    private ItemClick itemClick;

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected TextView idx;


        public CustomViewHolder(View view) {
            super(view);
            this.idx = (TextView) view.findViewById(R.id.tv_listitem);
            this.title = (TextView) view.findViewById(R.id.tv_mtitle);
        }
    }


    public mainAdapter(ArrayList<movieRankList> list) {//생성자
        this.mList = list;
    }



    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_main_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, final int position) {
        viewholder.idx.setText(Integer.toString(position+1));
        viewholder.title.setText(mList.get(position).getMovieNm());

        viewholder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(itemClick != null){
                    itemClick.onClick(v, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public interface ItemClick {
        public void onClick(View view,int position);
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

}
