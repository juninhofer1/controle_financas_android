package com.buffalo.controlefinancas

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.buffalo.controlefinancas.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private val ANIM_DURATION = 600

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val lLogoView: View = findViewById(R.id.logo_app)
        val lBackgroundLogoView: View = findViewById(R.id.logo_background)
        lBackgroundLogoView.visibility = View.VISIBLE
        lBackgroundLogoView.alpha = 0f
        lBackgroundLogoView.scaleX = 0f
        lBackgroundLogoView.scaleY = 0f
        lBackgroundLogoView.animate()
            .alpha(1f)
            .scaleX(1f)
            .scaleY(1f)
            .setDuration(ANIM_DURATION.toLong())
            .setInterpolator(OvershootInterpolator())
            .setListener(onFinishBackGround(lLogoView))
            .start()
    }

    private fun onFinishBackGround(aLogoView: View): Animator.AnimatorListener {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                aLogoView.visibility = View.VISIBLE
                aLogoView.alpha = 0f
                aLogoView.scaleX = 0f
                aLogoView.scaleY = 0f
                aLogoView.animate()
                    .alpha(1f)
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(ANIM_DURATION.toLong())
                    .setListener(onFinishLogo())
                    .setInterpolator(OvershootInterpolator())
                    .start()
            }
        }
    }

    private fun onFinishLogo(): Animator.AnimatorListener {
        return object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                startActivity(Intent(applicationContext, MainActivity::class.java))
                finish()
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }
    }

}
