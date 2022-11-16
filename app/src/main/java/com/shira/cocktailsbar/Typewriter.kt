package com.shira.cocktailsbar

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import androidx.appcompat.widget.AppCompatTextView
import com.shira.cocktailsbar.action.IsFinishText
import java.lang.Runnable

@SuppressLint("ViewConstructor")
@Suppress("DEPRECATION")
class Typewriter(context: Context?, var isFinishText: IsFinishText) :
    AppCompatTextView(context!!) {
    private var mText: CharSequence? = null
    private var mIndex = 0
    private var mDelay: Long = 5000

    private val mHandler = Handler()
    private val characterAdder: Runnable = object : Runnable {
        override fun run() {
            text = mText!!.subSequence(0, mIndex++)
            if (mIndex <= mText!!.length) {
                mHandler.postDelayed(this, mDelay)
            }
            if (mText!!.length == text.length){
                isFinishText.isTextFinish()
            }

        }

    }

    fun animateText(text: CharSequence?) {
        mText = text
        mIndex = 0
        setText("")
        setTextColor(Color.WHITE)
        textSize = 20f
        mHandler.removeCallbacks(characterAdder)
        mHandler.postDelayed(characterAdder, mDelay)
    }

    fun setCharacterDelay(millis: Long) {
        mDelay = millis
    }


}