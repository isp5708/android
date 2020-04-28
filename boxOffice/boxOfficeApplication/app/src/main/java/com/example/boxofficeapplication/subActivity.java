package com.example.boxofficeapplication;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class subActivity extends Activity {
    Intent intent;
    MyAdapter mAdapter;
    String keyword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sub_activity);
        intent=getIntent();
        keyword= intent.getStringExtra("keyword");
        getNaverApi p = new getNaverApi(keyword);
        p.start();
        try {
            p.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        setRecycleView(mAdapter,p.mArrayList,p.b);
        //getNaverApi();
    }

    public void setRecycleView(MyAdapter mAdapter,final ArrayList<Movie> mArrayList,final ArrayList<Bitmap> b){

        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_main_list);
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);



        mAdapter = new MyAdapter( mArrayList,b);
        mAdapter.notifyDataSetChanged();

        mAdapter.setItemClick(new MyAdapter.ItemClick() {
            @Override
            public void onClick(View view, int position) {
                //클릭시 실행될 함수 작성

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                b.get(position).compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] bytes = stream.toByteArray();


                intent=new Intent(subActivity.this,movie_info_activity.class);
                intent.putExtra("subMovieInfo",mArrayList.get(position));
                intent.putExtra("Bit",bytes);
                startActivity(intent);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                mLinearLayoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);
    }
}
