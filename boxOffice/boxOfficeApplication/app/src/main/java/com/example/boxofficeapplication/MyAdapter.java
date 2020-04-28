package com.example.boxofficeapplication;


import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.CustomViewHolder> {
    private ArrayList<Movie> mList;
    private ItemClick itemClick;
    private ArrayList<Bitmap> b;
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView title;
        protected ImageView im;

        public CustomViewHolder(View view) {
            super(view);
            this.title = (TextView) view.findViewById(R.id.title_listitem);
            this.im = (ImageView) view.findViewById(R.id.img_listitem);
        }
    }

    public MyAdapter(ArrayList<Movie> list,ArrayList<Bitmap> b) {
        this.mList = list;
        this.b=b;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);

        CustomViewHolder viewHolder = new CustomViewHolder(view);

        return viewHolder;
    }
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder,final int position) {

        viewholder.im.setImageBitmap(b.get(position));
        viewholder.title.setText(mList.get(position).getTitle());

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