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
import com.google.gson.Gson;                                //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 import문


public class quizlist_modify_Activity extends Activity {

    Button btnGoBack,btnModify;                         //변수 선언 - 버튼 btnGoBack, btnModify
    EditText etQuestion_2,et1_2,et2_2,et3_2,et4_2;      //변수 선언 - edittext etQuestion_2, et1_2, et2_2, et3_2, et4_2
    RadioButton rbtn1,rbtn2,rbtn3,rbtn4;                //변수 선언 - 라디오버튼 rbtn1, rbtn2, rbtn3, rbtn4

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlist_modify);               //quizlist_modify.xml 파일을 화면에 출력

        Intent inIntent=getIntent();                            // quizlistActivity에서 값을 받아오는 intent

        btnGoBack=(Button)findViewById(R.id.btnGoBack2);        //xml에 생성한 Button 위젯을 아이디(btnGoBack2)를 사용하여 변수(btnGoBack)에 대입
        btnModify=(Button)findViewById(R.id.btnModify);         //xml에 생성한 Button 위젯을 아이디(btnModify)를 사용하여 변수(btnModify)에 대입

        etQuestion_2=(EditText)findViewById(R.id.etQuestion_2); //xml에 생성한 EditText 위젯을 아이디(etQuestion_2)를 사용하여 변수(etQuestion_2)에 대입
        et1_2=(EditText)findViewById(R.id.et1_2);               //xml에 생성한 EditText 위젯을 아이디(et1_2)를 사용하여 변수(et1_2)에 대입
        et2_2=(EditText)findViewById(R.id.et2_2);               //xml에 생성한 EditText 위젯을 아이디(et2_2)를 사용하여 변수(et2_2)에 대입
        et3_2=(EditText)findViewById(R.id.et3_2);               //xml에 생성한 EditText 위젯을 아이디(et3_2)를 사용하여 변수(et3_2)에 대입
        et4_2=(EditText)findViewById(R.id.et4_2);               //xml에 생성한 EditText 위젯을 아이디(et4_2)를 사용하여 변수(et4_2)에 대입

        rbtn1=(RadioButton)findViewById(R.id.radioButton1);     //xml에 생성한 RadioButton 위젯을 아이디(radioButton1)를 사용하여 변수(rbtn1)에 대입
        rbtn2=(RadioButton)findViewById(R.id.radioButton2);     //xml에 생성한 RadioButton 위젯을 아이디(radioButton2)를 사용하여 변수(rbtn2)에 대입
        rbtn3=(RadioButton)findViewById(R.id.radioButton3);     //xml에 생성한 RadioButton 위젯을 아이디(radioButton3)를 사용하여 변수(rbtn3)에 대입
        rbtn4=(RadioButton)findViewById(R.id.radioButton4);     //xml에 생성한 RadioButton 위젯을 아이디(radioButton4)를 사용하여 변수(rbtn4)에 대입

        final ArrayList<quizClass2> quizlist;                           //퀴즈들을 담아두는 quizClass2형식의 arraylist
        quizlist=getIntent().getParcelableArrayListExtra("key");  //quizlistActivity 로부터 quizClass2로이루어진 arraylist 를 받아옴
        final int n;                                                    //수정할 것의 인덱스를 담는 final 변수 n

        n=inIntent.getIntExtra("indexNum",0);          //넘어온 퀴즈의 인덱스를 받아서 n에 저장

        int input=quizlist.get(n).getAnswer();                          //넘어온 인덱스번호 문제의 정답 번호를 받아서 input에 저장

        answerSet(input);                                       //정답 번호에 라디오버튼에 체크
        etQuestion_2.setText(quizlist.get(n).getQuestion());    //넘어온 인덱스의 문제를 표시
        et1_2.setText(quizlist.get(n).getMultipleChoice()[0]);  //넘어온 인덱스의 객관식조항을 표시
        et2_2.setText(quizlist.get(n).getMultipleChoice()[1]);  //넘어온 인덱스의 객관식조항을 표시
        et3_2.setText(quizlist.get(n).getMultipleChoice()[2]);  //넘어온 인덱스의 객관식조항을 표시
        et4_2.setText(quizlist.get(n).getMultipleChoice()[3]);  //넘어온 인덱스의 객관식조항을 표시

        btnGoBack.setOnClickListener(new View.OnClickListener() {       //btnGoBack(뒤로)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                finish();       //현재 실행중인 Activity가 종료되면서 뒤로가기 기능을 함

            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {       //btnModify(수정)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                if(et1_2.getText().toString().equals("")||et2_2.getText().toString().equals("")||et3_2.getText().toString().equals("")||et4_2.getText().toString().equals("")||etQuestion_2.getText().toString().equals(""))
                    //문제나 객관식 답안 4개 중에 빈 칸이 있다면
                    Toast.makeText(getApplicationContext(),"빈공간이 존재합니다 입력해주세요.",Toast.LENGTH_LONG).show();    //빈칸이 있음을 toast 메세지로 출력
                else if((rbtn1.isChecked()||rbtn2.isChecked()||rbtn3.isChecked()||rbtn4.isChecked())==false)    //라디오버튼에 정답이 체크되어 있지 않다면
                        Toast.makeText(getApplicationContext(),"정답을 체크하여 주세요.",Toast.LENGTH_LONG).show();    //정답이 체크되지 않았음을 toast 메세지로 출력
                else{                       //모두 잘 입력하였다면

                    int a=answerReturn();   //정답이 체크된곳의 값을 받아서 a에 저장

                    quizlist.set(n,new quizClass2(etQuestion_2.getText().toString(),et1_2.getText().toString(),et2_2.getText().toString(),et3_2.getText().toString(),et4_2.getText().toString(),a)); //수정한 값들을 가져와 인덱스에 해당하는 문제에 덮어씌기
                    SharedPreferences pref;         //내부 저장소에 저장하기 위한 변수 pref
                    SharedPreferences.Editor ed;    //내부 저장소에 업데이트하기 위한 Editor변수 ed

                    pref = getApplicationContext().getSharedPreferences("myPref",MODE_PRIVATE);  //myPref라는 json파일의 객체들을 담아 pref 변수에 저장
                    ed=pref.edit();             //퀴즈를 업데이트할 수 있도록 pref파일을 edit하여 ed에 저장

                    Gson gson = new Gson();     //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성

                    String convetedString = gson.toJson(quizlist.get(n)); //수정된 퀴즈를 JSON 파일에 저장하기 위해 String으로 변환
                    ed.putString(Integer.toString(n+1),convetedString); //변환한 String 형식을 문제 번호와 함께 ed에 추가
                    ed.commit();        //ed를 업데이트

                    Intent outIntent = new Intent();    //내보내는 인텐트 변수 outIntent 선언
                    outIntent.putParcelableArrayListExtra("outkey",quizlist);   //parcelable형식으로 quizlist를 인텐트에 담음
                    setResult(RESULT_OK,outIntent);     //인텐트를 사용하여 quizlistActivity로 반환
                    finish();       //현재 실행중인 Activity가 종료되면서 전 화면인 quizlistActivity로 돌아감
                }
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

    public void answerSet(int a){   //수정 시 원래 내가 등록했던 정답을 체크 해 놓는 메소드

        if(a==1)                    //내가 등록했던 정답이 1이라면
            rbtn1.setChecked(true); //1번째 답을 체크
        else if(a==2)               //내가 등록했던 정답이 2라면
            rbtn2.setChecked(true); //2번째 답을 체크
        else if(a==3)               //내가 등록했던 정답이 3이라면
            rbtn3.setChecked(true); //3번째 답을 체크
        else if(a==4)               //내가 등록했던 정답이 4라면
            rbtn4.setChecked(true); //4번째 답을 체크
    }
}
