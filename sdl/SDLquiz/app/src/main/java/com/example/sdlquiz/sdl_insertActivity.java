package com.example.sdlquiz;

import android.app.Activity;                                //AppCompatActivity 라이브러리 기본 import문
import android.content.Intent;                              //화면을 넘길 때 사용한 인텐드 import문
import android.content.SharedPreferences;                   //SharedPreferences에 데이터 저장하기 위한 import문
import android.os.Bundle;                                   //다양한 타입들의 값을 mapping 해주는 기본 import문
import android.view.View;                                   //view를 사용하기 위한 import문
import android.widget.Button;                               //button 위젯을 사용하기 위한 import문
import android.widget.EditText;                             //edittext 위제를 사용하기 위한 import문
import android.widget.RadioButton;                          //radiobutton 위젯을 사용하기 위한 import문
import android.widget.Toast;                                //toast 위젯을 사용하기 위한 import문
import java.util.ArrayList;                                 //arraylist를 사용하기 위한 import문


public class sdl_insertActivity extends Activity {

    Button btnGoBack,btnAdd;                //변수 선언 - 버튼 btnGoBack,btnAdd
    EditText etQuestion,et1,et2,et3,et4;    // 문제, 객관식문항 1,객관식문항 2,객관식문항 3,객관식문항 4
    SharedPreferences pref;                 //내부 저장 및 불러오기를 위해 필요한 변수
    SharedPreferences.Editor ed;            //내부 저장 및 불러오기를 위해 필요한 변수
    RadioButton rbtn1,rbtn2,rbtn3,rbtn4;    //변수 선언 - 라디오버튼 rbt1, rbt2, rbt3, rbt4

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sdl_insert);                //sdl_insert.xml 파일을 화면에 출력

        Intent inIntent = getIntent();                      // quizlistActivity에서 값을 받아오는 intent

        btnGoBack=(Button)findViewById(R.id.btnGoBack);     //xml에 생성한 Button 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입
        btnAdd=(Button)findViewById(R.id.btnAdd);           //xml에 생성한 Button 위젯을 아이디(btnAdd)를 사용하여 변수(btnAdd)에 대입
        etQuestion=(EditText)findViewById(R.id.etQuestion); //xml에 생성한 EditText 위젯을 아이디(etQuestion)를 사용하여 변수(etQuestion)에 대입
        et1=(EditText)findViewById(R.id.et1);               //xml에 생성한 EditText 위젯을 아이디(et1)를 사용하여 변수(et1)에 대입
        et2=(EditText)findViewById(R.id.et2);               //xml에 생성한 EditText 위젯을 아이디(et2)를 사용하여 변수(et2)에 대입
        et3=(EditText)findViewById(R.id.et3);               //xml에 생성한 EditText 위젯을 아이디(et3)를 사용하여 변수(et3)에 대입
        et4=(EditText)findViewById(R.id.et4);               //xml에 생성한 EditText 위젯을 아이디(et4)를 사용하여 변수(et4)에 대입

        rbtn1=(RadioButton)findViewById(R.id.radioButton1); //xml에 생성한 RadioButton 위젯을 아이디(radioButton1)를 사용하여 변수(rbtn1)에 대입
        rbtn2=(RadioButton)findViewById(R.id.radioButton2); //xml에 생성한 RadioButton 위젯을 아이디(radioButton2)를 사용하여 변수(rbtn2)에 대입
        rbtn3=(RadioButton)findViewById(R.id.radioButton3); //xml에 생성한 RadioButton 위젯을 아이디(radioButton3)를 사용하여 변수(rbtn3)에 대입
        rbtn4=(RadioButton)findViewById(R.id.radioButton4); //xml에 생성한 RadioButton 위젯을 아이디(radioButton4)를 사용하여 변수(rbtn4)에 대입

        btnAdd.setOnClickListener(new View.OnClickListener() {       //btnAdd(추가)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals("")||et2.getText().toString().equals("")||et3.getText().toString().equals("")||et4.getText().toString().equals("")||etQuestion.getText().toString().equals(""))
                    //문제나 객관식 답안 4개 중에 빈 칸이 있다면
                    Toast.makeText(getApplicationContext(),"빈공간이 존재합니다 입력하여주세요.",Toast.LENGTH_LONG).show();    //빈칸이 있음을 toast 메세지로 출력
                else if((rbtn1.isChecked()||rbtn2.isChecked()||rbtn3.isChecked()||rbtn4.isChecked())==false)    //라디오버튼에 정답이 체크되어 있지 않다면
                    Toast.makeText(getApplicationContext(),"정답을 체크하여 주세요.",Toast.LENGTH_LONG).show();    //정답이 체크되지 않았음을 toast 메세지로 출력
                else{
                    ArrayList<quizClass2> quizlist=getIntent().getParcelableArrayListExtra("key");  //저장된 Parcelable의 배열을 Key를 식별자로 하여금 받아서 quizlist에 저장

                    int n=answerReturn();   //정답이 체크된곳의 값을 받아서 n에 저장
                    quizlist.add(new quizClass2(etQuestion.getText().toString(),et1.getText().toString(),et2.getText().toString(),et3.getText().toString(),et4.getText().toString(),n));
                    // 문제와 객관식 답 4개와 정답의 값을 받아 quizClass2의 퀴즈 하나를 만들어 quizList에 추가
                    Intent outIntent = new Intent();    //내보내는 인텐트 변수 outIntent 선언
                    outIntent.putParcelableArrayListExtra("outkey",quizlist);   //parcelable형식으로 quizlist를 인텐트에 담음
                    setResult(RESULT_OK,outIntent);     //인텐트를 사용하여 quizlistActivity로 반환

                    pref=getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);     //myPref라는 json파일의 객체들을 담아 pref 변수에 저장
                    quizlist.get(0).saveQuiz(pref,quizlist);    //퀴즈들을 파일에 저장하는 메소드인 saveQuiz를 사용하여 pref파일에 추가된 퀴즈를 저장

                    finish();       //현재 실행중인 Activity가 종료되면서 전 화면인 quizlistActivity로 돌아감
                }
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {       //btnGoBack(뒤로)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                finish();       //현재 실행중인 Activity가 종료되면서 뒤로가기 기능을 함
            }
        });
    }

    public int answerReturn(){      //화면에 라디오버튼이 어디에 체크되어있는지 알려주는 메소드

        if(rbtn1.isChecked())       //rbtn1이 체크되어 있다면
            return 1;               //1 반환
        else if(rbtn2.isChecked())  //rbtn2이 체크되어 있다면
            return 2;               //2 반환
        else if(rbtn3.isChecked())  //rbtn3이 체크되어 있다면
            return 3;               //3 반환
        else if(rbtn4.isChecked())  //rbtn4이 체크되어 있다면
            return 4;               //4 반환

        return 0;                   //어느곳도 체크되어 있지 않다면 0반환
    }
}


