package com.example.boxofficeapplication;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

public class getNaverApi extends Thread {
    Bitmap bitmap;
    ArrayList<Movie> mArrayList;
    ArrayList<Bitmap>b;
    String keyword;
    JSONObject obj;
    final String noneImage="https://static8.depositphotos.com/1241729/873/v/950/depositphotos_8737960-stock-illustration-vector-red-x-cross-sign.jpg";
    String str;

    public getNaverApi(String keyword){
        this.keyword=keyword;

    }
    public void run() {
        try {
            str=getNaverSearch();
            obj = new JSONObject(str);
            JSONArray array = obj.getJSONArray("items");

            //Toast myToast = Toast.makeText(subActivity.this,Integer.toString(m.length), Toast.LENGTH_SHORT);
            //myToast.show();

            mArrayList = new ArrayList<>();
            b=new ArrayList<>();
            for(int i=0;i<array.length();i++) {
                //str.substring(0, str.length()-1);
                JSONObject dataObj = array.getJSONObject(i);
                String title = "";
                String subtitle,pubDate,director="",actor="",userRating,link;
                title += dataObj.getString("title");
                title = title.replaceAll("<b>", ""); //전처리
                title = title.replaceAll("</b>", ""); //전처리

                subtitle=dataObj.getString("subtitle");
                pubDate=dataObj.getString("pubDate");
                director=dataObj.getString("director");
                if(!director.equals("")) {
                    director = director.replace("|", ",");
                    director = director.substring(0, director.length() - 1);
                }

                actor = dataObj.getString("actor");
                if(!actor.equals("")) {
                    actor = actor.replace("|", ",");
                    actor = actor.substring(0, actor.length() - 1);
                }


                userRating=dataObj.getString("userRating");
                link=dataObj.getString("link");

                Movie m= new Movie();

                Log.i("mmmmm",link);
                if(dataObj.getString("image").equals("")) {
                    getBitMap(noneImage);
                }
                else {
                    getBitMap(dataObj.getString("image"));
                }
                m.setLink(link);
                m.setTitle(subtitle);
                m.setPubDate(pubDate);
                m.setDirector(director);
                m.setActor(actor);
                m.setUserRating(userRating);
                m.setTitle(title);
                b.add(bitmap);
                mArrayList.add(m);

                //searchResult2.setText(title);
            }


        }catch(JSONException e){
            //Log.v("aaaaa","바보야");
            e.printStackTrace();
            //searchResult2.setText("바보야");
        }
    }

    public void getBitMap(final String u){
        Thread mThread=new Thread(){
            @Override
            public void run(){
                try {

                    URL url = new URL(u);

                    //Web에서 이미지를 가져온 뒤
                    //imageView에 지정할 Bitmap을 만듬
                    HttpURLConnection conn=(HttpURLConnection)url.openConnection();
                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();// 값가저오기
                    bitmap= BitmapFactory.decodeStream(is);//Bitmap으로 변환


                }catch(MalformedURLException e){
                    e.printStackTrace();
                }catch(IOException e){
                    e.printStackTrace();
                }
            }
        };
        mThread.start();

        try{
            mThread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public String getNaverSearch(){
        String clientID="bVlm3o3uAA9aGhJavACE";
        String clientSecret="s3zbCpShCi";
        StringBuffer sb = new StringBuffer();

        try {
            String text = URLEncoder.encode(keyword, "UTF-8");
            String apiURL = "https://openapi.naver.com/v1/search/movie.json?query="+ text; // json 결과

            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("X-Naver-Client-Id", clientID);
            con.setRequestProperty("X-Naver-Client-Secret", clientSecret);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);
            }
            br.close();
            System.out.println(response.toString());
            return response.toString();
        } catch (Exception e) {
            System.out.println(e);
            return "에러";
        }

    }

}


