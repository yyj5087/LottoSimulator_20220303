package com.nepplus.lottosimulator_20220303

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    //    컴퓨터가 뽑은 당첨번호 6개를 저장할 ArrayList
    val mWinNumberList = ArrayList<Int>()
    var mBonusNum = 0 //보너스 번호는, 매판마다 새로 뽑아야함. 변경소지 0, 화면이 어딘지는 줄 필요 x,바로 대입 var
    var mMyNumbers = arrayOf(13,17,18,19,20,21)
    //    당첨번호를 보여줄 6개의 텍스트뷰를 담아둘 ArrayList
    val mWinNumTextViewList = ArrayList<TextView>()

    var mUseMoney = 0
    var mEarnMoney = 0L

//    각 등수별 랭크
    var rank1 = 0
    var rank2 = 0
    var rank3 = 0
    var rank4 = 0
    var rank5 = 0
    var rankFail = 0
    var isAutoNow = false

    lateinit var mHandler: Handler

    val buyLottoRunnable = object : Runnable{
        override fun run() {
            if(mUseMoney <= 10000000){
                buyLotto()
                mHandler.post(this)
            }
            else{
                Toast.makeText(this@MainActivity, "작은 구매가 완료되었습니다.", Toast.LENGTH_SHORT).show()
            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setupValues()
    }

    private fun setupEvents() {
        btnAuto.setOnClickListener {
            if(!isAutoNow){
                mHandler.post(buyLottoRunnable)
                isAutoNow = true
                btnAuto.text = "자동 구매 중단하기"
            }
            else{
                mHandler.removeCallbacks(buyLottoRunnable)
                isAutoNow = false
                btnAuto.text ="자동 구매 재개하기"
            }
        }





        btnBuylOTOTO.setOnClickListener {
            buyLotto()


        }
    }


    private fun buyLotto() {

        mUseMoney += 1000
//        6개의 당첨번호 생성
//        코틀린의 for문은 for-each 문법으로 기반
        mWinNumberList.clear()
        for (i in 0..5) {
//            괜찮은 번호가 나올때 까지 무한 반복
            while (true) {
//                1 ~ 45의 랜덤 숫자
//                Math.random()은 0~1 => 1~45.xxx 로 가공 => Int로 캐스팅
                val randomNum = (Math.random() * 45 + 1).toInt()
//                중복 검사 통과 시 while 깨자

                if (!mWinNumberList.contains(randomNum)) {
//                    당첨번호로, 뽑은 랜덤 숫자 등록
                    mWinNumberList.add(randomNum)
                    break
                }
            }
        }
//        ArrayList는 목록을 계속 누적 가능.
//        당첨번호 뽑기 전에, 기존의 당첨번호는 전부 삭제하고 다시 뽑자
        // mWinNumberList.clear()

//        만들어진 당첨번호 6개를 -> 작은수 ~ 큰수로 텍스트뷰에 표현
        mWinNumberList.sort() //자바로 직접 짜던 로직을 > 객체지향의 특성, 만들어져 있는 기능 활용으로 대체
        Log.d("당첨번호", mWinNumberList.toString())

        mWinNumberList.forEachIndexed { index, winNum ->
            //mWinNumberList 당첨번호
//    순서에 맞는 텍스트뷰 추출 => 문구로 당첨번호 설정
            mWinNumTextViewList[index].text = winNum.toString()
        }
//        보너스번호 생성

        while (true) {
            val randomNum = (Math.random() * 45 + 1).toInt()

            if (!mWinNumberList.contains(randomNum)) {
                mBonusNum = randomNum
                break
            }
        }
//        텍스트뷰에 배치
        txtBonusNum.text = mBonusNum.toString()
        checkLottoRank()
    }
    private fun checkLottoRank(){
        var correctCount = 0
        for(myNum in mMyNumbers){
            if (mWinNumberList.contains(myNum)){
                correctCount++
            }
        }
        when(correctCount){
            6->{
                mEarnMoney += 3000000000
                rank1++
            }
            5->{
                if (mMyNumbers.contains(mBonusNum)){
                    mEarnMoney += 50000000
                    rank2++
                }
                else{
                    mEarnMoney += 2000000
                    rank3++
                }
            }
            4->{
                mEarnMoney += 50000
                rank4++
            }
            3->{
                mUseMoney -= 5000
                rank5++
            }
            else->{
                rankFail++
            }
        }
        txtUseMoney.text = "${NumberFormat.getInstance().format(mUseMoney)}"
        txtEarnMoney.text = "${NumberFormat.getInstance().format(mEarnMoney)}"

        txtRankCount1.text = "${rank1}"
        txtRankCount2.text = "${rank2}"
        txtRankCount3.text = "${rank3}"
        txtRankCount4.text = "${rank4}"
        txtRankCount5.text = "${rank5}"
        txtRankCount6.text = "${rankFail}"



    }


    private fun setupValues() {

        mHandler = Handler(Looper.getMainLooper())
        mWinNumTextViewList.add(txtWinNum01)
        mWinNumTextViewList.add(txtWinNum02)
        mWinNumTextViewList.add(txtWinNum03)
        mWinNumTextViewList.add(txtWinNum04)
        mWinNumTextViewList.add(txtWinNum05)
        mWinNumTextViewList.add(txtWinNum06)
    }
}