package com.example.njc_2018.a2018_11_07mycountdowntimer

import android.media.AudioManager
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var soundPool: SoundPool
    private  var soundResID = 0

    private var dir = 1  //アニメーション用の変数の宣言
    private var screenWidth = 0  //スクリーンの幅を格納する変数の宣言
    private var screenHeight = 0   //スクリーンの高さ格納する変数の宣言

    //val image: ImageView = imageViewEnemy
    //var image: ImageView = imageViewEnemy
    //var image: ImageView = findViewById(R.id.imageViewEnemy)
    //var image: ImageView? = null  // <---成功したもの

    //var image = arrayOfNulls<ImageView>(5)  // <---成功したもの（配列で）
    val image = arrayOfNulls<ImageView>(5)  // <---成功したもの（配列で）
    //val buttons = Array(3, { arrayOfNulls<ImageView>(5)})



    inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long) :
        CountDownTimer(millisInFuture, countDownInterval) {

        var isRunning = false

        override fun onTick(millisUntilFinished: Long) {
            val minute = millisUntilFinished / 1000L / 60L
            val second = millisUntilFinished / 1000 % 60
            timerText.text = "%1d:%2$02d".format(minute, second)

            enemyMove(5)  //アニメーション

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

        // スクリーンの幅と高さを取得する
        val dMetrics = DisplayMetrics()  //DisplayMetrics のインスタンスを生成する
        windowManager.defaultDisplay.getMetrics(dMetrics)  //スクリーンサイズを取得しているらしい
        screenWidth = dMetrics.widthPixels  //スクリーンの幅を取得
        screenHeight = dMetrics.heightPixels  //スクリーンの高さを取得

        timerText.text = "3:00"
        val timer = MyCountDownTimer(3 * 60 * 1000, 100)
        //timerText.text = "0:20"                                                             //20秒　デバッグ用
        //val timer = MyCountDownTimer(2 * 10 * 1000, 100)     //20秒　デバッグ用


        //image = findViewById(R.id.imageViewEnemy) // <---成功したもの
        image[0] = findViewById(R.id.imageViewEnemy)  // <---成功したもの（配列で）
        image[0] = imageViewEnemy  // <---成功したもの（配列で）
        image[1] = imageViewEnemy1
        // imageViewEnemy の初期位置の設定
        //image?.x = 50F // <---成功したもの
        image[0]?.x = 50F  // <---成功したもの（配列で）
        imageViewEnemy.y = 100F
        image[1]?.x = 50F
        image[1]?.y = 200F

        // mageViewPlayer の初期位置の設定
        imageViewPlayer.x = 50F
        imageViewPlayer.y = screenHeight.toFloat() * 0.6F

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

        imageViewEnemy.x = imageViewEnemy.x + x * dir

        if(imageViewEnemy.x < 0 ||  screenWidth - imageViewEnemy.width < imageViewEnemy.x ){
            dir = dir * -1; //移動の左右の向きを反転する
        }

    }

    //画面タッチのメソッドの定義
    override fun onTouchEvent(event: MotionEvent): Boolean {

        val x = event.x                //タッチした場所のＸ座標
        val y = event.y                //タッチした場所のＹ座標

        textView.text = "X座標：$x　Y座標：$y"

        when (event.action) {

            MotionEvent.ACTION_DOWN -> {
                textView.append("　ACTION_DOWN")
                imageViewPlayer.setX(x)
            }

            MotionEvent.ACTION_UP -> textView.append("　ACTION_UP")

            MotionEvent.ACTION_MOVE -> {
                textView.append("　ACTION_MOVE")
                imageViewPlayer.setX(x)
            }

            MotionEvent.ACTION_CANCEL -> textView.append("　ACTION_CANCEL")
        }

        return true

    }
}
