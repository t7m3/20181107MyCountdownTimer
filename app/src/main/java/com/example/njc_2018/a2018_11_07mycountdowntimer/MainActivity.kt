package com.example.njc_2018.a2018_11_07mycountdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000 % 60
            timerText.text = "%1d:%2$02d".format(minute, second)
        }

        override fun onFinish() {
            //timerText.text = "0:00"
            timerText.text = "9:99"                                                         //デバッグ用
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //timerText.text = "3:00"
        //val timer = MyCountDownTimer(3 * 60 * 1000, 100)
        timerText.text = "0:20"                                                             //20秒　デバッグ用
        val timer = MyCountDownTimer(2 * 10 * 1000, 100)     //20秒　デバッグ用

        playStop.setOnClickListener {
            when (timer.isRunning){
                true -> timer.apply {
                    isRunning = false
                    cancel()
                    playStop.setImageResource(
                        R.drawable.ic_play_arrow_black_24dp )
                }
                false -> timer.apply {
                    isRunning = true
                    start()
                    playStop.setImageResource(
                        R.drawable.ic_stop_black_24dp )
                }
            }
        }
    }
}
