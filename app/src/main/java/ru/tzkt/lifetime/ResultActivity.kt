package ru.tzkt.lifetime

import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import kotlinx.android.synthetic.main.activity_result.*
import java.util.*

class ResultActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val date = Utils.getDate(this)

        if (date.get(Calendar.YEAR) == 1812) {
            startActivity(Intent(this, WelcomeActivity::class.java))
            return
        }

        val daysLived = Utils.getLivedDays(date)

        val tf =  Typeface.createFromAsset(assets,"fonts/Montserrat-Medium.ttf")

        tvLivedDays.typeface = tf
        tvLivedPercent.typeface = tf
        tvLeftDays.typeface = tf
        tvLeftPercent.typeface = tf


        tvLivedDays.text = getLivedDaysString(daysLived)
        tvLivedPercent.text = getLivedPercentString(daysLived)
        tvLeftDays.text = getLeftDaysString(daysLived)
        tvLeftPercent.text = getLeftPercentString(daysLived)

        val anim1 = AnimationUtils.loadAnimation(this, R.anim.fade_in)
        tvLivedDays.startAnimation(anim1)
        tvLivedPercent.startAnimation(anim1)

        val anim2 = AnimationUtils.loadAnimation(this, R.anim.fade_in_1)
        tvLeftDays.startAnimation(anim2)
        tvLeftPercent.startAnimation(anim2)

        anim2.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(p0: Animation?) {
            }

            override fun onAnimationEnd(p0: Animation?) {

                val startX = ivLifeLine.x
                val xOnLifeLine = ivLifeLine.width * daysLived / 25550
                val endX = startX + xOnLifeLine - ivMarker.width / 2

                ivMarker.animate().x(endX).setInterpolator(AnticipateOvershootInterpolator()).setDuration(1000).start()

            }

            override fun onAnimationStart(p0: Animation?) {
            }

        })

        flReset.setOnClickListener {
            startActivity(Intent(this, WelcomeActivity::class.java))
        }

    }

    private fun getLeftPercentString(daysLived: Int): String {
        val percent = "%.3f".format(100 - daysLived.toFloat() / 25550 * 100)
        return "$percent %"
    }

    private fun getLeftDaysString(daysLived: Int): String {
        return "and ${25550 - daysLived} days more"
    }

    private fun getLivedDaysString(daysLived: Int): String {
        return "and $daysLived days"

    }

    private fun getLivedPercentString(daysLived: Int): String {
        val percent = "%.3f".format(daysLived.toFloat() / 25550 * 100)
        return "$percent %"
    }

}
