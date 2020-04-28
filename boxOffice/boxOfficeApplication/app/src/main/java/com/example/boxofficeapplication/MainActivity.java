package com.example.boxofficeapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    Button btn;
    Intent intent;
    Thread thread;
    mainAdapter a;
    ArrayList<movieRankList> mrl;
    final String noneImage="https://static8.depositphotos.com/1241729/873/v/950/depositphotos_8737960-stock-illustration-vector-red-x-cross-sign.jpg";
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.re_main);
        final LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        mrl=new ArrayList<>();
        btn=(Button)findViewById(R.id.searchBtn);
        search=(EditText)findViewById(R.id.searchText) ;

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String keyword;

                keyword= search.getText().toString();

                intent=new Intent(MainActivity.this,subActivity.class);
                intent.putExtra("keyword",keyword);
                startActivity(intent);
            }
        });

        search.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                //Enter key Action
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {

                    btn.performClick();
                    return true;
                }
                return false;
            }
        });


        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String tmp=readUrl();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {


                            Gson gson= new Gson();

                            try{
                                JSONObject j=new JSONObject(tmp);
                                JSONObject json =  (JSONObject) j.get("boxOfficeResult");
                                JSONArray array = (JSONArray)json.get("dailyBoxOfficeList");

                                int index=0;
                                while(index<array.length()){

                                    movieRankList  m=gson.fromJson(array.get(index).toString(),movieRankList.class);
                                    mrl.add(m);
                                    index++;
                                }

                                //btn.setText(mrl.get(0).getMovieNm());

                                a= new mainAdapter(mrl);
                                a.notifyDataSetChanged();

                                mRecyclerView.setAdapter(a);

                                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                                        mLinearLayoutManager.getOrientation());
                                mRecyclerView.addItemDecoration(dividerItemDecoration);

                                a.setItemClick(new mainAdapter.ItemClick() {
                                    @Override
                                    public void onClick(View view, int position) {
                                        //클릭시 실행될 함수 작성

                                        intent=new Intent(MainActivity.this,subActivity.class);
                                        intent.putExtra("keyword",mrl.get(position).getMovieNm());
                                        startActivity(intent);
                                    }
                                });

                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    });
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        });
        thread.start();



    }
    public String readUrl() {
        try{
            String date=getDate();

            int tmp;

            tmp=Integer.parseInt(date);
            date=Integer.toString(tmp-1);

            URL url=new URL( "https://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/"
                    + "searchDailyBoxOfficeList.json"
                    + "?key=2ff1bd0719ae5d40d581e7b10a5adbf8"
                    + "&targetDt="+date);

            HttpsURLConnection myConnection=(HttpsURLConnection)url.openConnection();
            myConnection.setReadTimeout(10000);
            myConnection.setConnectTimeout(10000);
            myConnection.setRequestMethod("GET");
            myConnection.setUseCaches(false);
            myConnection.setAllowUserInteraction(false);
            myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            int responceCode = myConnection.getResponseCode();
            //myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

            String response=null;
            if(responceCode== HttpURLConnection.HTTP_OK) { // 정상 호출

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(myConnection.getInputStream()));
                while ((line = br.readLine()) != null)
                {
                    response = "";// String variable declared global
                    response += line;
                    Log.i("response_line", response);
                }
                return response;
            } else {  // 에러 발생
                return "error";
            }
        }catch(Exception e){
            e.printStackTrace();
            return "nodata";
        }
    }

    public String getDate(){
        //20191119
        String today=new SimpleDateFormat("yyyyMMdd").format(new Date());
        return today;
    }

}


