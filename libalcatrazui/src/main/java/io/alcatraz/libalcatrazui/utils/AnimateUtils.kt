package io.alcatraz.libalcatrazui.utils

import android.animation.Animator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.widget.TextView
import kotlin.math.hypot


object AnimateUtils {
    fun playStart(
        v: View,
        animateInterface: SimpleAnimateInterface?
    ) {
        v.visibility = View.INVISIBLE
        v.post{
            val animator = ViewAnimationUtils.createCircularReveal(
                v,
                v.x.toInt(),
                v.y.toInt(),
                0f,
                Math.hypot(
                    v.width.toDouble(),
                    v.height.toDouble()
                ).toFloat()
            )
            animator.interpolator = AccelerateInterpolator()
            animator.duration = 600

            animator.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(p1: Animator) {
                    val animationSet = AnimationSet(true)
                    val alphaAnimation = AlphaAnimation(0f, 1f)
                    alphaAnimation.duration = 700
                    animationSet.addAnimation(alphaAnimation)
                    v.startAnimation(animationSet)
                    animationSet.setAnimationListener(object : Animation.AnimationListener {
                        override fun onAnimationStart(p1: Animation) {}
                        override fun onAnimationEnd(p1: Animation) {}
                        override fun onAnimationRepeat(p1: Animation) {}
                    })
                }

                override fun onAnimationEnd(p1: Animator) {
                    v.visibility = View.VISIBLE
                    animateInterface?.onEnd()
                }

                override fun onAnimationCancel(p1: Animator) {}
                override fun onAnimationRepeat(p1: Animator) {}
            })
            animator.start()
        }
    }


    fun playEnd(v: View) {
        val animator = ViewAnimationUtils.createCircularReveal(
            v,
            v.x.toInt(),
            v.y.toInt(),
            hypot(v.width.toDouble(), v.height.toDouble()).toFloat(),
            0f
        )
        animator.interpolator = AccelerateInterpolator()
        animator.duration = 450
        animator.start()
        animator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p1: Animator) {}
            override fun onAnimationEnd(p1: Animator) {
                v.visibility = View.GONE
            }

            override fun onAnimationCancel(p1: Animator) {}
            override fun onAnimationRepeat(p1: Animator) {}
        })
    }

    fun textChange(textView: TextView, text: CharSequence) {
        val animationSet = AnimationSet(true)
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = 200
        animationSet.addAnimation(alphaAnimation)
        textView.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(p1: Animation) {

            }

            override fun onAnimationEnd(p1: Animation) {
                textView.visibility = View.GONE
                textView.text = text
                val animationSet1 = AnimationSet(true)
                val alphaAnimation1 = AlphaAnimation(0f, 1f)
                alphaAnimation1.duration = 200
                animationSet1.addAnimation(alphaAnimation1)
                textView.startAnimation(animationSet1)
                animationSet1.setAnimationListener(object : Animation.AnimationListener {

                    override fun onAnimationStart(p1: Animation) {

                    }

                    override fun onAnimationEnd(p1: Animation) {
                        textView.visibility = View.VISIBLE

                    }

                    override fun onAnimationRepeat(p1: Animation) {

                    }
                })
            }

            override fun onAnimationRepeat(p1: Animation) {

            }
        })

    }

    fun fadeIn(target: View, animateInterface: SimpleAnimateInterface) {
        val animationSet = AnimationSet(true)
        val alphaAnimation = AlphaAnimation(0f, 1f)
        alphaAnimation.duration = 1200
        animationSet.addAnimation(alphaAnimation)
        target.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(p1: Animation) {}

            override fun onAnimationEnd(p1: Animation) {
                target.visibility = View.VISIBLE
                animateInterface.onEnd()
            }

            override fun onAnimationRepeat(p1: Animation) {

            }
        })
    }

    fun fadeOut(target: View, animateInterface: SimpleAnimateInterface) {
        val animationSet = AnimationSet(true)
        val alphaAnimation = AlphaAnimation(1f, 0f)
        alphaAnimation.duration = 300
        animationSet.addAnimation(alphaAnimation)
        target.startAnimation(animationSet)
        animationSet.setAnimationListener(object : Animation.AnimationListener {

            override fun onAnimationStart(p1: Animation) {}

            override fun onAnimationEnd(p1: Animation) {
                target.visibility = View.GONE
                animateInterface.onEnd()
            }

            override fun onAnimationRepeat(p1: Animation) {

            }
        })
    }

    interface SimpleAnimateInterface {
        fun onEnd()
    }
}