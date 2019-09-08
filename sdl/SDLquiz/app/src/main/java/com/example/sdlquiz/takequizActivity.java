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


public class takequizActivity extends Activity {

    Button btnBack, btnNext, btnStop, btnGoBack;    //변수 선언 - 버튼 btnBack, btnNext, btnStop, btnGoBack
    TextView tvQuestion;                            //변수 선언 - 텍스트뷰 tvQuestion
    SharedPreferences pref;                         //내부 저장 및 불러오기를 위해 필요한 변수
    SharedPreferences.Editor ed;                    //내부 저장 및 불러오기를 위해 필요한 변수
    RadioButton rbt1, rbt2, rbt3, rbt4;             //변수 선언 - 라디오버튼 rbt1, rbt2, rbt3, rbt4
    ArrayList<quizClass2> quizList;                 //변수 선언 - quizClass2의 ArrayList객체 quizList
    int i =0;                                       //문제 출력할 때 사용할 전역변수 i

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takequiz);                  //takequiz.xml 파일을 화면에 출력

        btnBack = (Button) findViewById(R.id.btnBack);          //xml에 생성한 Button 위젯을 아이디(btnBack)를 사용하여 변수(btnBack)에 대입
        btnNext = (Button) findViewById(R.id.btnNext);          //xml에 생성한 Button 위젯을 아이디(btnNext)를 사용하여 변수(btnNext)에 대입
        btnStop = (Button) findViewById(R.id.btnStop);          //xml에 생성한 Button 위젯을 아이디(btnStop)를 사용하여 변수(btnStop)에 대입
        btnGoBack = (Button) findViewById(R.id.btnGoBack);      //xml에 생성한 Button 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입

        tvQuestion = (TextView) findViewById(R.id.tvQuestion);  //xml에 생성한 TextView 위젯을 아이디(tvQuestion)를 사용하여 변수(tvQuestion)에 대입

        rbt1 = (RadioButton) findViewById(R.id.rbt1);           //xml에 생성한 RadioButton 위젯을 아이디(rbt1)를 사용하여 변수(rbt1)에 대입
        rbt2 = (RadioButton) findViewById(R.id.rbt2);           //xml에 생성한 RadioButton 위젯을 아이디(rbt2)를 사용하여 변수(rbt2)에 대입
        rbt3 = (RadioButton) findViewById(R.id.rbt3);           //xml에 생성한 RadioButton 위젯을 아이디(rbt3)를 사용하여 변수(rbt3)에 대입
        rbt4 = (RadioButton) findViewById(R.id.rbt4);           //xml에 생성한 RadioButton 위젯을 아이디(rbt4)를 사용하여 변수(rbt4)에 대입

        quizList = new ArrayList<quizClass2>();                 //퀴즈들을 담아두는 quizClass2형식의 arraylist quizList

        Gson gson = new Gson();         //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성
        pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);      //myPref라는 json파일의 객체들을 담아 pref 변수에 저장

        String conveted = pref.getString("index", null);        //pref파일의 객체들을 index를 식별자로 하여 String형식으로 변환하여 conveted 변수에 저장
        if (conveted == null) {         //객체가 들어있지 않다면 (저장되어 있는 퀴즈가 없다면)
            Toast.makeText(getApplicationContext(), "등록된 퀴즈가 없습니다!", Toast.LENGTH_LONG).show();     //등록된 퀴즈가 없음을 toast 메세지로 출력
            Intent intent = new Intent(getApplicationContext(),sdlMainMenuActivity.class);    //인텐트를 이용해 메뉴화면(sdlMainMenuActivity)으로 넘기기
            startActivity(intent);      //인텐트 시작
        }
        else {      //객체가 들어있다면 (저장되어 있는 퀴즈가 있다면)
            String tmp;     //퀴즈 형식을 변환할 때 사용할 변수 선언

            /* -------저장된 파일에서 quizList로 넘어오는 부분------- */
            for (int j = 0; j < Integer.parseInt(conveted); j++) {           //퀴즈가 저장되어있는 갯수 만큼 반복문 돌리면서
                tmp = pref.getString(Integer.toString(j + 1), null);   //pref파일의 객체들 중 index가 j+1인 객체를 String형식으로 변환하여 tmp 변수에 저장
                quizClass2 cl = gson.fromJson(tmp, quizClass2.class);       //JSON 파일에 저장된 tmp 퀴즈를 quizClass2 객체로 변환하여 cl 변수에 저장
                quizList.add(cl);                                           //quizList에 cl객체를 추가
            }

            /* -------전에 풀었다면 userAnswer 값이 저장되어 있으므로 초기화시키는 부분------- */
            for (int j = 0; j < quizList.size(); j++) {     //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
                quizList.get(j).setUserAnswer(0);           //모든 문제의 userAnswer을 0으로 저장
            }


            setQuiz(i);     //전역변수로 지정한 i의 값(0)으로 setQuiz 메소드를 호출하여 퀴즈 출력

            btnNext.setOnClickListener(new View.OnClickListener() {       //btnNext(다음)버튼을 클릭하면
                @Override
                public void onClick(View v) {
                    int userAnswer = userAnswerReturn();            //userAnswerReturn 메소드(현재 userAnswer체크 값 확인)를 호출하여 반환값을 변수에 저장
                    int correct = correntReturn();                  //correntReturn 메소드(userAnswer의 값과 정답이 맞는지 확인)를 호출하여 반환값을 correct 변수에 저장
                    quizList.get(i).setUserAnswer(userAnswer);      //useranswer 변수 값을 quizList의 useranswer 값에 저장
                    quizList.get(i).setCorrect(correct);            //correct 변수 값을 quizList의 correct 값에 저장

                    if (i == quizList.size() - 1)       //마지막 퀴즈라면(더 이상 다음 퀴즈가 없음)
                        Toast.makeText(getApplicationContext(), "마지막 퀴즈입니다!", Toast.LENGTH_LONG).show();    //마지막 퀴즈임을 toast 메세지로 출력
                    else        //뒤에 퀴즈들이 있다면
                        setQuiz(++i);   //setQuiz 메소드를 호출하여 다음 퀴즈 출력

                }
            });

            btnBack.setOnClickListener(new View.OnClickListener() {       //btnBack(이전)버튼을 클릭하면
                @Override
                public void onClick(View v) {
                    int userAnswer = userAnswerReturn();            //userAnswerReturn 메소드(현재 userAnswer체크 값 확인)를 호출하여 반환값을 변수에 저장
                    int correct = correntReturn();                  //correntReturn 메소드(userAnswer의 값과 정답이 맞는지 확인)를 호출하여 반환값을 correct 변수에 저장
                    quizList.get(i).setUserAnswer(userAnswer);      //useranswer 변수 값을 quizList의 useranswer 값에 저장
                    quizList.get(i).setCorrect(correct);            //correct 변수 값을 quizList의 correct 값에 저장

                    if (i == 0)       //첫 퀴즈라면(더 이상 이전 퀴즈가 없음)
                        Toast.makeText(getApplicationContext(), "첫 퀴즈입니다!", Toast.LENGTH_LONG).show();    //첫 퀴즈임을 toast 메세지로 출력
                    else        //앞에 퀴즈들이 있다면
                        setQuiz(--i);   //setQuiz 메소드를 호출하여 이전 퀴즈 출력

                }
            });

            btnStop.setOnClickListener(new View.OnClickListener() {       //btnStop(응시종료)버튼을 클릭하면
                @Override
                public void onClick(View v) {

                    int userAnswer = userAnswerReturn();            //userAnswerReturn 메소드(현재 userAnswer체크 값 확인)를 호출하여 반환값을 변수에 저장
                    int correct = correntReturn();                  //correntReturn 메소드(userAnswer의 값과 정답이 맞는지 확인)를 호출하여 반환값을 correct 변수에 저장
                    quizList.get(i).setUserAnswer(userAnswer);      //useranswer 변수 값을 quizList의 useranswer 값에 저장
                    quizList.get(i).setCorrect(correct);            //correct 변수 값을 quizList의 correct 값에 저장

                    SharedPreferences pref;         //내부 저장소에 저장하기 위한 변수 pref
                    SharedPreferences.Editor ed;    //내부 저장소에 업데이트하기 위한 Editor변수 ed

                    pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);    //myPref라는 json파일의 객체들을 담아 pref 변수에 저장
                    ed = pref.edit();             //퀴즈를 업데이트할 수 있도록 pref파일을 edit하여 ed에 저장

                    Gson gson = new Gson();     //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성

                    for (i = 0; i < quizList.size(); i++) {     //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
                        String convetedString = gson.toJson(quizList.get(i)); // 퀴즈를 JSON 파일에 저장하기 위해 String으로 변환
                        ed.putString(Integer.toString(i + 1), convetedString); //제이쓴파일에 저장된것도 바꿔줌
                        ed.commit();        //ed를 업데이트
                    }

                    Intent intent = new Intent(getApplicationContext(), quizResultActivity.class);    //인텐트를 이용해 퀴즈결과화면(quizResultActivity)으로 넘기기
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

    public void setQuiz(int i) {    //퀴즈를 풀 수 있도록 세팅하는 메소드
        tvQuestion.setText("Q : " + quizList.get(i).getQuestion());     //quizList의 문제를 받아와서 tvQuestion 변수에 set
        rbt1.setText(quizList.get(i).getMultipleChoice()[0]);           //quizList의 객관식 보기 1을 받아와서 rbt1 변수에 set
        rbt2.setText(quizList.get(i).getMultipleChoice()[1]);           //quizList의 객관식 보기 2을 받아와서 rbt2 변수에 set
        rbt3.setText(quizList.get(i).getMultipleChoice()[2]);           //quizList의 객관식 보기 3을 받아와서 rbt3 변수에 set
        rbt4.setText(quizList.get(i).getMultipleChoice()[3]);           //quizList의 객관식 보기 4을 받아와서 rbt4 변수에 set

        int user=quizList.get(i).getUserAnswer();       //이전이나 다음을 눌렀을 때 이미 풀은 퀴즈는 답을 체크 해 놓기 위해서 만든 변수 user

        if(user==1)                 //이미 1번으로 체크했었다면
            rbt1.setChecked(true);  //1번을 체크
        else if(user==2)            //2번으로 체크했었다면
            rbt2.setChecked(true);  //2번체크
        else if(user==3)            //3번으로 체크했었다면
            rbt3.setChecked(true);  //3번체크
        else if(user==4)            //4번으로 체크했었다면
            rbt4.setChecked(true);  //4번체크
        else if(user==0){           //아직 풀지 않은 문제라면
            rbt1.setChecked(false); //1번 언체크
            rbt2.setChecked(false); //2번 언체크
            rbt3.setChecked(false); //3번 언체크
            rbt4.setChecked(false); //4번 언체크
        }

    }

    public int userAnswerReturn(){  //화면에 userAnswer이 어디에 체크되어있는지 알려주는 메소드
        if(rbt1.isChecked())        //rbt1이 체크되어 있다면
            return 1;               //1 반환
        else if(rbt2.isChecked())   //rbt2이 체크되어 있다면
            return 2;               //2 반환
        else if(rbt3.isChecked())   //rbt3이 체크되어 있다면
            return 3;               //3 반환
        else if(rbt4.isChecked())   //rbt4이 체크되어 있다면
            return 4;               //4 반환

        return 0;                   //어느곳도 체크되어 있지 않다면 0반환
    }

    public int correntReturn(){     //퀴즈를 맞았는지 틀렸는지 채점하는 메소드
        if(quizList.get(i).getAnswer() == userAnswerReturn())   //정답과 user가 체크한 답이 같다면
            return 1;   //1 반환
        else            //정답과 user가 체크한 답이 다르다면
            return 0;   //0 반환
    }
}

