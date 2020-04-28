package com.example.boxofficeapplication;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class movie_info_activity extends Activity {
    TextView tv,actorName,movieYear,directorName,link,userRating;
    ImageView movieMain_img;
    Bitmap bitmap;
    RatingBar r;

    ArrayList<movieRankList> mrl;
    Movie m;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_info);

        directorName=(TextView)findViewById(R.id.directorName);
        tv=(TextView)findViewById(R.id.movie_name);
        actorName=(TextView)findViewById(R.id.actorName);
        movieMain_img=(ImageView)findViewById(R.id.movie_image);
        movieYear=(TextView) findViewById(R.id.movie_year);
        link=(TextView)findViewById(R.id.link1);
        userRating=(TextView)findViewById(R.id.userRating);
        r=(RatingBar)findViewById(R.id.movieRate);



        m=(Movie)getIntent().getSerializableExtra("subMovieInfo");
        bitmap=(Bitmap)getIntent().getParcelableExtra("Bit");

        byte[] bytes = getIntent().getByteArrayExtra("Bit");
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

        String tmp;
        tmp=movieYear.getText().toString()+" "+m.getPubDate();
        link.setText(m.getLink());
        movieYear.setText(tmp);

        tmp=directorName.getText().toString();
        directorName.setText(tmp+" "+m.getDirector());

        tv.setText(m.getTitle());
        tmp=actorName.getText().toString();
        actorName.setText(tmp+" "+m.getActor());
        tmp=userRating.getText().toString();
        userRating.setText(tmp+" "+m.getUserRating());

        movieMain_img.setImageBitmap(bmp);

        r.setRating((float)Math.floor(Double.parseDouble(m.getUserRating())/2*100)/100);

    }
}
