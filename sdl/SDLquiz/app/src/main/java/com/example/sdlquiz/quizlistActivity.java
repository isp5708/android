package com.example.sdlquiz;

import android.app.Activity;                                //AppCompatActivity 라이브러리 기본 import문
import android.app.AlertDialog;                             //alertdialog를 사용하기 위한 import문
import android.content.DialogInterface;                     //dialog의 인터페이스 import문
import android.content.Intent;                              //화면을 넘길 때 사용한 인텐드 import문
import android.content.SharedPreferences;                   //SharedPreferences에 데이터 저장하기 위한 import문
import android.os.Bundle;                                   //다양한 타입들의 값을 mapping 해주는 기본 import문
import android.view.View;                                   //view를 사용하기 위한 import문
import android.widget.Button;                               //button 위젯을 사용하기 위한 import문
import android.widget.ListView;                             //listview 위젯을 사용하기 위한 import문
import android.widget.Toast;                                //toast 위젯을 사용하기 위한 import문
import com.google.gson.Gson;                                //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 import문
import android.widget.ArrayAdapter;                         //arrayadapter 위젯을 사용하기 위한 import문
import java.util.ArrayList;                                 //arraylist를 사용하기 위한 import문


public class quizlistActivity extends Activity {

    Button btnGoBack, btnAdd, btnModify, btnDelete, btnAllDelete;   //변수 선언 - 버튼 btnGoBack, btnAdd, btnModify, btnDelete, btnAllDelete
    ListView list2;                                                 //변수 선언 - 리스트뷰 list2
    ArrayList<quizClass2> quizList;                                 //변수 선언 - quizClass2의 ArrayList객체 quizList
    SharedPreferences pref;                                         //내부 저장 및 불러오기를 위해 필요한 변수
    SharedPreferences.Editor ed;                                    //내부 저장 및 불러오기를 위해 필요한 변수

    @Override
    protected void onCreate(Bundle savedInstanceState) {    //메인 메소드
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizlist);                  //quizlist.xml 파일을 화면에 출력

        btnGoBack = (Button) findViewById(R.id.btnGoBack);                  //xml에 생성한 Button 위젯을 아이디(btnGoBack)를 사용하여 변수(btnGoBack)에 대입
        btnAdd = (Button) findViewById(R.id.btnAdd);                        //xml에 생성한 Button 위젯을 아이디(btnAdd)를 사용하여 변수(btnAdd)에 대입
        btnModify = (Button) findViewById(R.id.btnModify);                  //xml에 생성한 Button 위젯을 아이디(btnModify)를 사용하여 변수(btnModify)에 대입
        btnDelete = (Button) findViewById(R.id.btnDelete);                  //xml에 생성한 Button 위젯을 아이디(btnDelete)를 사용하여 변수(btnDelete)에 대입
        btnAllDelete = (Button) findViewById(R.id.btnAllDelete);            //xml에 생성한 Button 위젯을 아이디(btnAllDelete)를 사용하여 변수(btnAllDelete)에 대입

        list2 = (ListView) findViewById(R.id.lvQuiz);                       //xml에 생성한 ListView 위젯을 아이디(LvQuiz)를 사용하여 변수(list2)에 대입
        quizList = new ArrayList<quizClass2>();                             //퀴즈들을 담아두는 quizClass2형식의 arraylist quizList

        getquiz();  //myPref에 저장된 퀴즈들을 받아와 리스트뷰에 세팅하는 메소드 호출

        btnAdd.setOnClickListener(new View.OnClickListener() {       //btnAdd(추가)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                sendIntent(sdl_insertActivity.class); // intent 함수호출
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {       //btnDelete(삭제)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);      //myPref라는 json파일의 객체들을 담아 pref 변수에 저장
                Gson gson = new Gson();                             //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성
                ed=pref.edit();                                     //퀴즈를 업데이트할 수 있도록 pref파일을 edit하여 ed에 저장
                for (int i = 0; i < list2.getCount(); i++) {        //list2에서 퀴즈 수 만큼 돌리면서
                    if (list2.isItemChecked(i)) {           // list2에서 체크된 퀴즈가 있다면
                        quizList.remove(i);                 //체크된 퀴즈를 삭제
                        pref.edit().clear().commit();       //myPref를 전부 초기화
                        for(int j=0;j<quizList.size();j++){                         //삭제하고 남은 퀴즈 수 만큼 돌리면서
                            String convetedString = gson.toJson(quizList.get(j));   //수정된 퀴즈를 JSON 파일에 저장하기 위해 String으로 변환
                            ed.putString(Integer.toString(j+1),convetedString); //변환한 String 형식을 문제 번호와 함께 ed에 추가
                        }
                        ed.putString("index",Integer.toString(quizList.size())); //quizList 사이즈를 index로 ed에 추가
                        ed.commit(); //삭제하고 남은 퀴즈들을 전부 다시 저장
                    }
                }
                quizListSetting();  //리스트뷰에 출력해주는 메소드 호출
             }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {       //btnModify(수정)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                if(btnModifyChecked()) {                    //btnModifyChecked메소드(체크박스가 하나만 체크되어있는지 확인)를 호출하여 true 이면
                    for(int i=0;i<list2.getCount();i++) {   //list2를 퀴즈 수 만큼 돌리면서
                        if (list2.isItemChecked(i)) {       //체크된 퀴즈를 찾으면
                            sendIntentModify(quizlist_modify_Activity.class , i); //인텐드에 보낼 quizClass2 객체인 퀴즈를 담는 메소드 호출
                        }
                    }
                }
                else    //체크박스가 0개 혹은 여러개 체크 되어있으면
                    makeToast("체크박스를 하나만 선택해 주세요.");    //메소드 이용해 toast 메세지 출력
            }
        });

        btnAllDelete.setOnClickListener(new View.OnClickListener() {       //btnAllDelete(전체삭제)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert_confirm = new AlertDialog.Builder(quizlistActivity.this); //정말로 삭제할것인지 메세지 창을 하나 띄움
                alert_confirm.setMessage("정말로 모든 퀴즈를 삭제하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {        // 'YES'라고 답하면
                                pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);      //myPref라는 json파일의 객체들을 담아 pref 변수에 저장
                                pref.edit().clear().commit();   //myPref에 저장된 퀴즈들을 모두 초기화
                                quizList.clear();               //quizList도 초기화
                                quizListSetting();              //리스트뷰 세팅 하는 메소드 호출
                            }
                        }).setNegativeButton("취소",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {        // 'No'라고 답하면
                                return;         //되돌아가기
                            }
                        });
                AlertDialog alert = alert_confirm.create();     //메세지 생성
                alert.show();   //메세지를 띄움
            }
        });

        btnGoBack.setOnClickListener(new View.OnClickListener() {       //btnGoBack(뒤로)버튼을 클릭하면
            @Override
            public void onClick(View v) {
                finish();       //현재 실행중인 Activity가 종료되면서 뒤로가기 기능을 함
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //상위 인텐트에서 값을 보내올 때 실행되는 메소드
        if (resultCode == RESULT_OK) {      //RESULT_OK 값이 온다면
            quizListSet(data); // 리스트뷰에 세팅해주는 메소드 호출
        }
    }

    protected void quizListSet(Intent data) {               //인텐트 값이 오면 리스트뷰 세팅하는 메소드 aㅡ>bㅡ>a
        list2 = (ListView) findViewById(R.id.lvQuiz);           //xml에 생성한 ListView 위젯을 아이디(LvQuiz)를 사용하여 변수(list2)에 대입
        final ArrayList<String> midList = new ArrayList<String>();   //문자열 arraylist인 midList를 생성

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, midList); //리스트뷰를 띄우기 위해 midList를 arrayadapter를 사용하여 생성
        list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);     //list2 모드 선택
        list2.setAdapter(adapter);      //list2 어댑터 선택

        quizList = data.getParcelableArrayListExtra("outkey");  //parcelable형식으로 인텐트에 퀴즈 목록을 받아옴
        quizList.clear();       //quizList 초기화

        /*-----------------------퀴즈를 받아오는 부분------------------------------*/
        Gson gson = new Gson();     //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성
        pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);  //myPref라는 json파일의 객체들을 담아 pref 변수에 저장

        String conveted = pref.getString("index",null);        //pref파일의 객체들을 index를 식별자로 하여 String형식으로 변환하여 conveted 변수에 저장

        String tmp;     //퀴즈 형식을 변환할 때 사용할 변수 선언
        for(int i =0 ;i<Integer.parseInt(conveted);i++){            //퀴즈가 저장되어있는 갯수 만큼 반복문 돌리면서
            tmp=pref.getString(Integer.toString(i+1),null);   //pref파일의 객체들 중 index가 i+1인 객체를 String형식으로 변환하여 tmp 변수에 저장
            quizClass2 cl=gson.fromJson(tmp,quizClass2.class);      //JSON 파일에 저장된 tmp 퀴즈를 quizClass2 객체로 변환하여 cl 변수에 저장
            quizList.add(cl);                                       //quizList에 cl객체를 추가
        }

        for (int i = 0; i < quizList.size(); i++) {        //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
            midList.add(quizList.get(i).getQuestion());    //리스트뷰에 퀴즈의 문제만 추가
        }
        adapter.notifyDataSetChanged();
    }

    public void getquiz(){                            //퀴즈를 받아오는 메소드
        list2 = (ListView) findViewById(R.id.lvQuiz);           //xml에 생성한 ListView 위젯을 아이디(LvQuiz)를 사용하여 변수(list2)에 대입
        final ArrayList<String> midList = new ArrayList<String>();   //문자열 arraylist인 midList를 생성

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, midList); //리스트뷰를 띄우기 위해 midList를 arrayadapter를 사용하여 생성
        list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);     //list2 모드 선택
        list2.setAdapter(adapter);      //list2 어댑터 선택

        Gson gson = new Gson();     //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 객체 gson 생성
        pref = getApplicationContext().getSharedPreferences("myPref", MODE_PRIVATE);  //myPref라는 json파일의 객체들을 담아 pref 변수에 저장
        String conveted = pref.getString("index",null);        //pref파일의 객체들을 index를 식별자로 하여 String형식으로 변환하여 conveted 변수에 저장

        if(conveted!=null) { //퀴즈가 아무것도 없으면 에러가 뜨므로 null이면 퀴즈를 못 받아오게 한다

            String tmp;     //퀴즈 형식을 변환할 때 사용할 변수 선언
            for (int i = 0; i < Integer.parseInt(conveted); i++) {           //퀴즈가 저장되어있는 갯수 만큼 반복문 돌리면서
                tmp = pref.getString(Integer.toString(i + 1), null);   //pref파일의 객체들 중 index가 i+1인 객체를 String형식으로 변환하여 tmp 변수에 저장
                quizClass2 cl = gson.fromJson(tmp, quizClass2.class);       //JSON 파일에 저장된 tmp 퀴즈를 quizClass2 객체로 변환하여 cl 변수에 저장
                quizList.add(cl);                                           //quizList에 cl객체를 추가
            }

            for (int i = 0; i < quizList.size(); i++) {        //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
                midList.add(quizList.get(i).getQuestion());    //리스트뷰에 퀴즈의 문제만 추가
            }
        }
    }

    protected void quizListSetting() {      //리스트뷰를 세팅해주는 메소드
        list2 = (ListView) findViewById(R.id.lvQuiz);           //xml에 생성한 ListView 위젯을 아이디(LvQuiz)를 사용하여 변수(list2)에 대입
        final ArrayList<String> midList = new ArrayList<String>();   //문자열 arraylist인 midList를 생성

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, midList); //리스트뷰를 띄우기 위해 midList를 arrayadapter를 사용하여 생성
        list2.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);     //list2 모드 선택
        list2.setAdapter(adapter);      //list2 어댑터 선택

        for (int i = 0; i < quizList.size(); i++) {        //quizList의 첫 퀴즈부터 마지막 퀴즈까지 돌리면서
            midList.add(quizList.get(i).getQuestion());    //리스트뷰에 퀴즈의 문제만 추가
        }
    }

    //Intent target, CharSequence title, IntentSender sender
    protected  void sendIntent(Class<?> className){                 // 추가 버튼 누를 경우 호출되는 메소드
        Intent intent = new Intent(getApplicationContext(), className);     //인텐트 생성
        intent.putParcelableArrayListExtra("key", quizList);          //parcelabel을 통해 객체 arraylist를 인텐트에 담음
        startActivityForResult(intent, 0);                       //sdl_insertActivity 호출
    }

    protected void sendIntentModify(Class<?> className, int number){    //수정하기 버튼 누를 경 호출되는 메소드
        Intent intent = new Intent(getApplicationContext(), className);     //인텐트 생성
        intent.putParcelableArrayListExtra("key", quizList);          //parcelabel을 통해 객체 arraylist를 인텐트에 담음
        intent.putExtra("indexNum", number);                          //수정할 것의 인덱스도 인텐트에 담아 보냄
        startActivityForResult(intent, 0);                       //수정 화면 시작
    }
    protected boolean btnModifyChecked(){       //수정 할 객체가 체크박스에 하나만 체크되어있는지 확인하는 메소드
        int n=0;        //체크 된 수를 체크하기 위한 변수 초기화

        for(int i=0;i<list2.getCount();i++){    //list2에서 퀴즈 수 만큼 돌리면서
            if(list2.isItemChecked(i)){         //list2에서 체크된 퀴즈가 있다면
                n++;                            //n을 증가
            }
        }
        if(n==1)                    //체크된 퀴즈가 1개라면
            return true;            //true 반환
        else                        //0개 혹은 여러개라면
            return false;           //false 반환
    }
    protected void makeToast(String d){     //문자열만 받아 toast 메세지 띄우는 메소드
        Toast.makeText(this, d, Toast.LENGTH_SHORT).show();     //toast 메세지 출력
    }
}
