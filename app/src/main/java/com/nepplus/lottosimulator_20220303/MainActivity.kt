package com.nepplus.lottosimulator_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
//    컴퓨터가 뽑은 당첨번호 6개를 저장할 ArrayList
    val mWinNumberList = ArrayList<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setupValues()
    }

    private fun setupEvents(){
        btnBuylOTOTO.setOnClickListener {
            buyLotto()
        }
    }
    private fun buyLotto(){
//        6개의 당첨번호 생성
//        코틀린의 for문은 for-each 문법으로 기반
        for(i in 0..5){
//            괜찮은 번호가 나올때 까지 무한 반복
            while (true){
//                1 ~ 45의 랜덤 숫자
//                Math.random()은 0~1 => 1~45.xxx 로 가공 => Int로 캐스팅
                val randomNum = (Math.random() * 45 + 1).toInt()
//                중복 검사 통과 시 while 깨자

                if (!mWinNumberList.contains(randomNum)){
//                    당첨번호로, 뽑은 랜덤 숫자 등록
                    mWinNumberList.add(randomNum)
                    break
                }
            }
        }
//        ArrayList는 목록을 계속 누적 가능.
//        당첨번호 뽑기 전에, 기존의 당첨번호는 전부 삭제하고 다시 뽑자
        mWinNumberList.clear()

//        만들어진 당첨번호 6개를 -> 텍스트뷰에 표현
        Log.d("당청번호",mWinNumberList.toString())
        for (winNum in mWinNumberList){

        }

//        보너스번호 생성

//        텍스트뷰에 배치
    }
    private fun setupValues(){

    }
}