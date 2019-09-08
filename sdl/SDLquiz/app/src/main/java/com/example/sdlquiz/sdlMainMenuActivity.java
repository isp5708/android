package com.example.sdlquiz;

import android.app.Activity;                                //AppCompatActivity 라이브러리 기본 import문
import android.content.Intent;                              //화면을 넘길 때 사용한 인텐드 import문
import android.os.Bundle;                                   //다양한 타입들의 값을 mapping 해주는 기본 import문
import android.view.View;                                   //view를 사용하기 위한 import문
import android.widget.Button;                               //button 위젯을 사용하기 위한 import문


public class sdlMainMenuActivity extends Activity {

    Button btnMake, btnLogout, btnTakequiz, btnWrong;      //변수 선언 - 버튼 btnMake, btnLogout, btnTakequiz, btnWrong

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdlmainmenu);               //sdlmainmenu.xml 파일을 화면에 출력

        btnMake = (Button) findViewById(R.id.btnMakeQuiz);      //xml에 생성한 Button 위젯을 아이디(btnMakeQuiz)를 사용하여 변수(btnMake)에 대입
        btnLogout = (Button) findViewById(R.id.btnLogout);      //xml에 생성한 Button 위젯을 아이디(btnLogout)를 사용하여 변수(btnLogout)에 대입
        btnTakequiz = (Button) findViewById(R.id.btnTakeQuiz);  //xml에 생성한 Button 위젯을 아이디(btnTakeQuiz)를 사용하여 변수(btnTakequiz)에 대입
        btnWrong = (Button)findViewById(R.id.btnWrong);         //xml에 생성한 Button 위젯을 아이디(btnWrong)를 사용하여 변수(btnWrong)에 대입

        btnMake.setOnClickListener(new View.OnClickListener() {       //btnMake(퀴즈만들기)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), quizlistActivity.class);    //인텐트를 이용해 퀴즈리스트화면(quizlistActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            }
        });

        btnTakequiz.setOnClickListener(new View.OnClickListener() {       //btnTakequiz(퀴즈 보기)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), takequizActivity.class);    //인텐트를 이용해 퀴즈보기화면(takequizActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            }
        });

        btnWrong.setOnClickListener(new View.OnClickListener() {       //btnWrong(오답 체크)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), quizWrongActivity.class);    //인텐트를 이용해 오답확인화면(quizlistActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            }
        });

        btnLogout.setOnClickListener(new View.OnClickListener() {       //btnLogout(나가기)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);    //인텐트를 이용해 시작화면(quizlistActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            }
        });
    }
}
