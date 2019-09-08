package com.example.sdlquiz;

import android.content.Intent;                              //화면을 넘길 때 사용한 인텐드 import문
import android.support.v7.app.AppCompatActivity;            //AppCompatActivity v7 라이브러리 기본 import문
import android.os.Bundle;                                   //다양한 타입들의 값을 mapping 해주는 기본 import문
import android.view.View;                                   //view를 사용하기 위한 import문
import android.widget.Button;                               //button 위젯을 사용하기 위한 import문

public class MainActivity extends AppCompatActivity {

    Button btnLogin;                                                //변수 선언 - 버튼 btnLogin

    @Override
    protected void onCreate(Bundle savedInstanceState) {            //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);                     //activity_main.xml 파일을 화면에 출력
        setTitle("[ SDL quiz ]");                                   //화면의 제목을 [ SDL quiz ] 로 설정

        btnLogin=(Button)findViewById(R.id.Find);                   //xml에 생성한 Button 위젯을 아이디(Find)를 사용하여 변수(btnLogin)에 대입

        btnLogin.setOnClickListener(new View.OnClickListener() {    //btnLogin 버튼을 누르면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),sdlMainMenuActivity.class); //인텐트를 이용해 메뉴화면(sdlMainMenuActivity)으로 넘기기
                startActivity(intent);                      //Intent 시작 (화면 넘기기)
            }
        });
    }
}
