package com.example.sdlquiz;

import android.app.Activity;                                //AppCompatActivity 라이브러리 기본 import문
import android.content.Intent;                              //화면을 넘길 때 사용한 인텐드 import문
import android.content.SharedPreferences;                   //SharedPreferences에 데이터 저장하기 위한 import문
import android.os.Bundle;                                   //다양한 타입들의 값을 mapping 해주는 기본 import문
import android.view.View;                                   //view를 사용하기 위한 import문
import android.widget.Button;                               //button 위젯을 사용하기 위한 import문
import android.widget.RadioButton;                          //radiobutton 위젯을 사용하기 위한 import문
import android.widget.TextView;                             //textview 위젯을 사용하기 위한 import문
import android.widget.Toast;                                //toast 위젯을 사용하기 위한 import문
import java.util.ArrayList;                                 //arraylist를 사용하기 위한 import문
import com.google.gson.Gson;                                //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 import문


public class quizWrongActivity extends Activity {

    Button btnBack, btnNext, btnExit, btnGoBack;    //변수 선언 - 버튼 btnBack, btnNext, btnExit, btnGoBack
    TextView tvQuestion,tvAnswer;                   //변수 선언 - 텍스트뷰 tvQuestion, tvAnswer
    SharedPreferences pref;                         //내부 저장 및 불러오기를 위해 필요한 변수
    SharedPreferences.Editor ed;                    //내부 저장 및 불러오기를 위해 필요한 변수
    RadioButton rbt1, rbt2, rbt3, rbt4;             //변수 선언 - 라디오버튼 rbt1, rbt2, rbt3, rbt4
    ArrayList<quizClass2> quizList, quizWrongList;  //변수 선언 - quizClass2의 ArrayList객체 quizList, quizWrongList
    int i = 0;                                      //문제 출력할 때 사용할 전역변수 i

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_wrong);                //quiz_wrong.xml 파일을 화면에 출력

        btnBack = (Button) findViewById(R.id.btnBack);          //xml에 생성한 Button 위젯을 아이디(btnBack)를 사용하여 변수(btnBack)에 대입
        btnNext = (Button) findViewById(R.id.btnNext);          //xml에 생성한 Button 위젯을 아이디(btnNext)를 사용하여 변수(btnNext)에 대입
        btnExit = (Button) findViewById(R.id.btnExit);          //xml에 생성한 Button 위젯을 아이디(btnExit)를 사용하여 변수(btnExit)에 대입
        btnGoBack = (Button) findViewById(R.id.btnGoBack);      //xml에 생성한 Button 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입

        tvQuestion = (TextView) findViewById(R.id.tvQuestion);  //xml에 생성한 TextView 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입
        tvAnswer = (TextView) findViewById(R.id.tvAnswer);      //xml에 생성한 TextView 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입

        rbt1 = (RadioButton) findViewById(R.id.rbt1);           //xml에 생성한 RadioButton 위젯을 아이디(rbt1)를 사용하여 변수(rbt1)에 대입
        rbt2 = (RadioButton) findViewById(R.id.rbt2);           //xml에 생성한 RadioButton 위젯을 아이디(rbt2)를 사용하여 변수(rbt2)에 대입
        rbt3 = (RadioButton) findViewById(R.id.rbt3);           //xml에 생성한 RadioButton 위젯을 아이디(rbt3)를 사용하여 변수(rbt3)에 대입
        rbt4 = (RadioButton) findViewById(R.id.rbt4);           //xml에 생성한 RadioButton 위젯을 아이디(rbt4)를 사용하여 변수(rbt4)에 대입

        quizList = new ArrayList<quizClass2>();                 //퀴즈들을 담아두는 quizClass2형식의 arraylist quizList
        quizWrongList = new ArrayList<quizClass2>();            //퀴즈들을 담아두는 quizClass2형식의 arraylist quizWrongList

        Gson gson = new Gson();         //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성
        pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);    //myPref라는 json파일의 객체들을 담아 pref 변수에 저장

        String conveted = pref.getString("index", null);        //pref파일의 객체들을 index를 식별자로 하여 String형식으로 변환하여 conveted 변수에 저장
        if (conveted == null) {         //객체가 들어있지 않다면 (저장되어 있는 퀴즈. 즉, 오답 문제가 없다면)
            Toast.makeText(getApplicationContext(), "오답 퀴즈가 없습니다!", Toast.LENGTH_LONG).show();     //오답 퀴즈가 없음을 toast 메세지로 출력
            Intent intent = new Intent(getApplicationContext(), sdlMainMenuActivity.class);    //인텐트를 이용해 메뉴화면(sdlMainMenuActivity)으로 넘기기
            startActivity(intent);      //인텐트 시작
        } else {      //객체가 들어있다면 (저장되어 있는 퀴즈가 있다면)
            String tmp;     //퀴즈 형식을 변환할 때 사용할 변수 선언

            /* -------저장된 파일에서 quizList로 넘어오는 부분------- */
            for (int j = 0; j < Integer.parseInt(conveted); j++) {           //퀴즈가 저장되어있는 갯수 만큼 반복문 돌리면서
                tmp = pref.getString(Integer.toString(j + 1), null);   //pref파일의 객체들 중 index가 j+1인 객체를 String형식으로 변환하여 tmp 변수에 저장
                quizClass2 cl = gson.fromJson(tmp, quizClass2.class);       //JSON 파일에 저장된 tmp 퀴즈를 quizClass2 객체로 변환하여 cl 변수에 저장
                quizList.add(cl);                                           //quizList에 cl객체를 추가
            }

            /* -------quizList에서 틀린 문제를 골라 quizWrongList를 만드는 부분------- */
            for (int j = 0; j < quizList.size(); j++) {     //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
                if (quizList.get(j).getCorrect() == 0) {    //틀린 퀴즈가 있다면
                    quizWrongList.add(quizList.get(j));     //quizWrongList에 퀴즈를 추가
                }
            }

            if (quizWrongList.isEmpty()) {      //틀린 퀴즈가 없어서 quizWrongList가 비어있다면
                Toast.makeText(getApplicationContext(), "오답 퀴즈가 없습니다!", Toast.LENGTH_LONG).show();     //오답 퀴즈가 없음을 toast 메세지로 출력
                Intent intent = new Intent(getApplicationContext(), sdlMainMenuActivity.class);    //인텐트를 이용해 메뉴화면(sdlMainMenuActivity)으로 넘기기
                startActivity(intent);      //인텐트 시작
            } else {    //quizWrongList에 객체가 담겨있다면
                setWrongQuiz(i);    //전역변수로 지정한 i의 값(0)으로 setWrongQuiz 메소드를 호출하여 오답퀴즈 출력

                btnNext.setOnClickListener(new View.OnClickListener() {       //btnNext(다음)버튼을 클릭하면
                    @Override
                    public void onClick(View v) {
                        if (i == quizWrongList.size() - 1)       //마지막 오답퀴즈라면(더 이상 다음 퀴즈가 없음)
                            Toast.makeText(getApplicationContext(), "마지막 퀴즈입니다!", Toast.LENGTH_LONG).show();    //마지막 퀴즈임을 toast 메세지로 출력
                        else        //뒤에 오답퀴즈들이 있다면
                            setWrongQuiz(++i);   //setWrongQuiz 메소드를 호출하여 다음 오답퀴즈 출력
                    }
                });

                btnBack.setOnClickListener(new View.OnClickListener() {       //btnBack(이전)버튼을 클릭하면
                    @Override
                    public void onClick(View v) {
                        if (i == 0)       //첫 오답퀴즈라면(더 이상 이전 퀴즈가 없음)
                            Toast.makeText(getApplicationContext(), "첫 퀴즈입니다!", Toast.LENGTH_LONG).show();    //첫 퀴즈임을 toast 메세지로 출력
                        else        //앞에 오답퀴즈들이 있다면
                            setWrongQuiz(--i);   //setWrongQuiz 메소드를 호출하여 이전 오답퀴즈 출력
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
    }

    public void setWrongQuiz(int i) {   //오답퀴즈를 볼 수 있도록 세팅하는 메소드
        tvQuestion.setText("Q : " + quizWrongList.get(i).getQuestion());     //quizWrongList의 문제를 받아와서 tvQuestion 변수에 set
        rbt1.setText(quizWrongList.get(i).getMultipleChoice()[0]);           //quizWrongList의 객관식 보기 1을 받아와서 rbt1 변수에 set
        rbt2.setText(quizWrongList.get(i).getMultipleChoice()[1]);           //quizWrongList의 객관식 보기 2을 받아와서 rbt2 변수에 set
        rbt3.setText(quizWrongList.get(i).getMultipleChoice()[2]);           //quizWrongList의 객관식 보기 2을 받아와서 rbt2 변수에 set
        rbt4.setText(quizWrongList.get(i).getMultipleChoice()[3]);           //quizWrongList의 객관식 보기 2을 받아와서 rbt2 변수에 set

        int user = quizWrongList.get(i).getUserAnswer();    //user가 선택했던 답을 체크해놓기 위해 사용하는 변수 user

        if (user == 1)              //1번으로 체크했었다면
            rbt1.setChecked(true);  //1번을 체크
        else if (user == 2)         //2번으로 체크했었다면
            rbt2.setChecked(true);  //2번을 체크
        else if (user == 3)         //3번으로 체크했었다면
            rbt3.setChecked(true);  //3번을 체크
        else if (user == 4)         //4번으로 체크했었다면
            rbt4.setChecked(true);  //4번을 체크
        else if (user == 0) {       //답 체크를 하지 않았다면
            rbt1.setChecked(false); //1번 언체크
            rbt2.setChecked(false); //2번 언체크
            rbt3.setChecked(false); //3번 언체크
            rbt4.setChecked(false); //4번 언체크    (모두 언체크)
        }
        //체크를 변경하지 않고 볼 수만 있도록 모두 사용불가모드로 변경
        rbt1.setEnabled(false);     //1번 disabled 모드로 변경
        rbt2.setEnabled(false);     //2번 disabled 모드로 변경
        rbt3.setEnabled(false);     //3번 disabled 모드로 변경
        rbt4.setEnabled(false);     //4번 disabled 모드로 변경
        int answer = quizWrongList.get(i).getAnswer();      //정답을 반환하는 메소드를 호출하여 반환값은 answer에 저장
        tvAnswer.setText("정답 : " + answer +") " + quizWrongList.get(i).getMultipleChoice()[answer-1] );     //tvAnswer에 정답이 무엇인지 번호와 내용을 출력
    }
}