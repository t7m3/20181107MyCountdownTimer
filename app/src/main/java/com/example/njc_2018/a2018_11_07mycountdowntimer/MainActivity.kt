package com.example.njc_2018.a2018_11_07mycountdowntimer

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.util.DisplayMetrics



class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private  var soundResID = 0
    private var dir = 1  //アニメーション用の変数の宣言

    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000 % 60
            timerText.text = "%1d:%2$02d".format(minute, second)

            enemyMove(5)

        }

        override fun onFinish() {
            //timerText.text = "0:00"
            timerText.text = "9:99"                                                         //デバッグ用
            soundPool.play(soundResID, 1.0f, 100f, 0, 0, 1.0f)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        timerText.text = "3:00"
        val timer = MyCountDownTimer(3 * 60 * 1000, 100)
        //timerText.text = "0:20"                                                             //20秒　デバッグ用
        //val timer = MyCountDownTimer(2 * 10 * 1000, 100)     //20秒　デバッグ用

        // imageViewEnemy の初期位置の設定
        imageViewEnemy.x = 50F
        imageViewEnemy.y = 100F

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

    override fun onResume() {
        super.onResume()
        soundPool = SoundPool(2, AudioManager.STREAM_ALARM, 0)
        soundResID = soundPool.load(this, R.raw.bellsound, 1)
    }

    override fun onPause() {
        super.onPause()
        soundPool.release()
    }

    //アニメーション用のメソッド
    fun enemyMove(x: Int){

        val dMetrics = DisplayMetrics()  //DisplayMetrics のインスタンスを生成する
        windowManager.defaultDisplay.getMetrics(dMetrics)  //スクリーンサイズを取得しているらしい

        val screenWidth = dMetrics.widthPixels  //スクリーンの幅を取得
        val screenHeight = dMetrics.heightPixels  //スクリーンの高さを取得

        imageViewEnemy.x = imageViewEnemy.x + x * dir

        if(imageViewEnemy.x < 0 ||  screenWidth - imageViewEnemy.width < imageViewEnemy.x ){
            dir = dir * -1; //移動の左右の向きを反転する
        }

    }
}
