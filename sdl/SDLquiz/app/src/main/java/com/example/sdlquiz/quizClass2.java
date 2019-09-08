package com.example.sdlquiz;

import android.content.SharedPreferences;                   //SharedPreferences에 데이터 저장하기 위한 import문
import android.os.Parcel;                                   //parcel 사용을 위한 import문
import android.os.Parcelable;                               //parcelable 사용을 위한 import문
import com.google.gson.Gson;                                //Java Object를 JSON으로 또는, JSON을 Java Object로의 변환하기 위한 import문
import java.util.ArrayList;                                 //arraylist를 사용하기 위한 import문

import static android.content.Context.MODE_PRIVATE;

public class quizClass2 implements Parcelable {
    String Question;            //문제를 저장 할 string형 변수 - Question
    String[] multipleChoice;    //객관식 4개의 선택지를 저장 할 String형 배열 - multipleChioce
    int answer;                 //정답을 저장할  int형 변수 - answer
    int correct;                //문제를 풀었을 때 맞았는지 틀렸는지 여부를 저장 할 int형 변수 - correct
    int userAnswer;             //문제를 풀었을 때 유저가 선택한 답을 저장 할 int형 변수 - userAnswer

    public quizClass2(String q,String m1,String m2,String m3,String m4,int answer){ //퀴즈 생성자 - 문제 생성시 (문제, 객관식문항 4개, 정답)을 저장
        multipleChoice= new String[4];          //multipleChoice 배열은 4개의 String으로 되어있음
        this.Question=q;                        //매개변수 q는 Question 변수에 저장
        multipleChoice[0]=m1;                   //매개변수 m1은 multipleChoice 배열의 0번에 저장
        multipleChoice[1]=m2;                   //매개변수 m2은 multipleChoice 배열의 1번에 저장
        multipleChoice[2]=m3;                   //매개변수 m3은 multipleChoice 배열의 2번에 저장
        multipleChoice[3]=m4;                   //매개변수 m4은 multipleChoice 배열의 3번에 저장
        this.answer=answer;                     //매개변수 answer은 answer 변수에 저장
    }

    public void saveQuiz(SharedPreferences pref, ArrayList<quizClass2> quizlist){       //퀴즈들을 myPref파일에 저장하는 메소드

        SharedPreferences.Editor ed;                               //내부 저장소를 사용하기위해 필요한 변수 선언
        ed=pref.edit();                                            //내부 저장소에 업데이트하기 위한 변수 선언

        Gson gson = new Gson();                                    //제이슨 파일로 변환하기 위한 변수 생성

        for( int i=0;i<quizlist.size();i++) {                      //quizlist에 담긴 모든 퀴즈를 저장하는 for문
            String convetedString = gson.toJson(quizlist.get(i));  //수정된 퀴즈를 JSON 파일에 저장하기 위해 String으로 변환
            ed.putString(Integer.toString(i+1),convetedString);  //변환한 것을 myPref에 저장
        }
        ed.putString("index",Integer.toString(quizlist.size())); //quizlist의 크기를 이용하여 index 저장
        ed.commit();                                                //업데이트 - 파일 저장
    }


    public void setQuestion(String question){ this.Question=question; }   // Question 변수값을 넣는 setter
    public String getQuestion(){ return Question; }                       //Question 변수값을 가져오는 getter

    public void setCorrect(int correct){this.correct=correct;}  //Correct 변수값을 넣는 setter
    public int getCorrect(){return correct;}                    // Correct 변수값을 가져오는 getter

    public void setMultipleChoice(String a,String b,String c,String d){ // multipleChoice 에 객관식 문항을 넣는 setter
        multipleChoice[0]=a;    //매개변수 a를 0번에
        multipleChoice[1]=b;    //매개변수 b를 1번에
        multipleChoice[2]=c;    //매개변수 c를 2번에
        multipleChoice[3]=d;    //매개변수 d를 3번에
    }
    public String[] getMultipleChoice(){ return multipleChoice; }       //multipleChoice 배열값을 가져오는 getter

    public void setAnswer(int answer){
        this.answer=answer;
    }   //answer 변수값을 넣는 setter
    public int getAnswer(){
        return answer;
    }                   //answer 변수값을 가져오는 getter

    public void setUserAnswer(int userAnswer) { this.userAnswer=userAnswer; }   //userAnswer 변수값을 넣는 setter
    public int getUserAnswer() { return userAnswer;}                            //userAnswer 변수값을 가져오는 getter


    @Override
    public int describeContents() {
        return 0;
    }   //parcelable 형태로 객체를 넘길때 필수로 구현해야하는 메소드

    @Override
    public void writeToParcel(Parcel dest, int flags) {     //arraylist 객체를 parcelable 형태로 변환할 때 구현하는 메소드
        dest.writeString(this.Question);                    //Question 변수를 Parcel 형식으로 변환해 쓰기
        dest.writeStringArray(this.multipleChoice);         //multipleChoice 배열 변수를 Parcel 형식으로 변환해 쓰기
        dest.writeInt(this.answer);                         //answer 변수를 Parcel 형식으로 변환해 쓰기
        dest.writeInt(this.correct);                        //correct 변수를 Parcel 형식으로 변환해 쓰기
        dest.writeInt(this.userAnswer);                     //userAnswer 변수를 Parcel 형식으로 변환해 쓰기
    }

    protected quizClass2(Parcel in) {                       //parcelable 형태의 arraylist 객체를 원래 형태로 다시 변환할 때 구현하는 메소드
        this.Question = in.readString();                    //String 형식을 읽어서 Question 변수에 넣기
        this.multipleChoice = in.createStringArray();       //StringArray 형식을 읽어서 multipleChoide 배열 변수에 넣기
        this.answer=in.readInt();                           //저장된 순서대로 int 형식을 읽어서 answer 변수에 넣기
        this.correct=in.readInt();                          //int 형식을 읽어서 correct 변수에 넣기
        this.userAnswer=in.readInt();                       //int 형식을 읽어서 userAnswer 변수에 넣기
    }

    public static final Parcelable.Creator<quizClass2> CREATOR = new Parcelable.Creator<quizClass2>() { //parcelable 형태를 사용하려면 필수로 구현해야 하는 메소드
        @Override
        public quizClass2 createFromParcel(Parcel source) { return new quizClass2(source); }       //quizClass2의 소스를 반환하는 메소드

        @Override
        public quizClass2[] newArray(int size) { return new quizClass2[size]; }                    //quizClass2의 사이즈를 반환하는 메소드
    };
}