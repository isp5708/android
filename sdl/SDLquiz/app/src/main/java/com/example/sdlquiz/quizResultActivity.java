package com.example.sdlquiz;

import android.app.Activity;                                //AppCompatActivity 라이브러리 기본 import문
import android.content.Intent;                              //화면을 넘길 때 사용한 인텐드 import문
import android.content.SharedPreferences;                   //SharedPreferences에 데이터 저장하기 위한 import문
import android.os.Bundle;                                   //다양한 타입들의 값을 mapping 해주는 기본 import문
import android.view.View;                                   //view를 사용하기 위한 import문
import android.widget.Button;                               //button 위젯을 사용하기 위한 import문
import android.widget.TextView;                             //textview 위젯을 사용하기 위한 import문
import java.util.ArrayList;                                 //arraylist를 사용하기 위한 import문
import com.google.gson.Gson;                                //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 import문


public class quizResultActivity extends Activity {

    Button btnWrong, btnExit, btnGoBack;    //변수 선언 - 버튼 btnWrong, btnExit, btnGoBack
    ArrayList<quizClass2> quizList;         //변수 선언 - quizClass2의 ArrayList객체 quizList
    TextView tvScore;                       //변수 선언 - 텍스트뷰 tvScore
    SharedPreferences pref;                 //내부 저장 및 불러오기를 위해 필요한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_result);               //quiz_result.xml 파일을 화면에 출력

        btnWrong = (Button) findViewById(R.id.btnWrong);    //xml에 생성한 Button 위젯을 아이디(btnWrong)를 사용하여 변수(btnWrong)에 대입
        btnExit = (Button) findViewById(R.id.btnExit);      //xml에 생성한 Button 위젯을 아이디(btnExit)를 사용하여 변수(btnExit)에 대입
        btnGoBack = (Button) findViewById(R.id.btnGoBack);  //xml에 생성한 Button 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입

        tvScore = (TextView) findViewById(R.id.tvScore);    //xml에 생성한 TextView 위젯을 아이디(tvScore)를 사용하여 변수(tvScare)에 대입
        quizList = new ArrayList<quizClass2>();             //퀴즈들을 담아두는 quizClass2형식의 arraylist quizList


        Gson gson = new Gson();         //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성
        pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);      //myPref라는 json파일의 객체들을 담아 pref 변수에 저장

        String conveted = pref.getString("index", null);        //pref파일의 객체들을 index를 식별자로 하여 String형식으로 변환하여 conveted 변수에 저장

        String tmp;     //퀴즈 형식을 변환할 때 사용할 변수 선언

        /* -------저장된 파일에서 quizList로 넘어오는 부분------- */
        for (int j = 0; j < Integer.parseInt(conveted); j++) {           //퀴즈가 저장되어있는 갯수 만큼 반복문 돌리면서
            tmp = pref.getString(Integer.toString(j + 1), null);   //pref파일의 객체들 중 index가 j+1인 객체를 String형식으로 변환하여 tmp 변수에 저장
            quizClass2 cl = gson.fromJson(tmp, quizClass2.class);        //JSON 파일에 저장된 tmp 퀴즈를 quizClass2 객체로 변환하여 cl 변수에 저장
            quizList.add(cl);                                            //quizList에 cl객체를 추가
        }

        int count = 0;      //맞은 퀴즈 갯수를 셀 count 변수 0으로 초기화
        for(int i=0;i<quizList.size(); i++){        //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
            if (quizList.get(i).getCorrect() == 1)  //맞은 퀴즈가 있다면
                count++;                            //count 값 증가
        }

        tvScore.setText("전체 " + quizList.size() + "문제 중에 " + count +"문제 \n맞추셨습니다!");    //tvScore에 전체 문제 중 몇문제를 맞았는지 출력

        btnWrong.setOnClickListener(new View.OnClickListener() {       //btnWrong(오답 확인)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), quizWrongActivity.class);    //인텐트를 이용해 오답확인화면(quizlistActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            }
        });

        btnExit.setOnClickListener(new View.OnClickListener() {       //btnExit(종료)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), sdlMainMenuActivity.class);    //인텐트를 이용해 메뉴화면(sdlMainMenuActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {       //btnGoBack(뒤로)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                finish();       //현재 실행중인 Activity가 종료되면서 뒤로가기 기능을 함
            }
        });
    }
}