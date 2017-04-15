package com.github.PCU.android.helper

import android.app.Activity
import android.util.DisplayMetrics
import android.view.View
import android.view.View.GONE
import android.view.View.MeasureSpec
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation


/**
 * Created by tim on 29.03.17.
 */
class ExpandCollapseAnimation(private val mAnimatedView: View, duration: Int, private val mType: Int, activity: Activity,
                              private val post: () -> Unit) : Animation() {
    private val mEndHeight: Int

    init {
        setDuration(duration.toLong())

        setHeightForWrapContent(activity, mAnimatedView)

        //mAnimatedView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
        mEndHeight = mAnimatedView.layoutParams.height
        if (mType == 0) {
            mAnimatedView.layoutParams.height = 0
            mAnimatedView.visibility = View.VISIBLE
        }
    }

    override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)
        if (interpolatedTime < 1.0f) {
            if (mType == 0) {
                mAnimatedView.layoutParams.height = (mEndHeight * interpolatedTime).toInt()
            } else {
                mAnimatedView.layoutParams.height = mEndHeight - (mEndHeight * interpolatedTime).toInt()
            }
            mAnimatedView.requestLayout()
        } else {
            if (mType == 0) {
                mAnimatedView.layoutParams.height = mEndHeight
                mAnimatedView.requestLayout()
            } else {
                mAnimatedView.layoutParams.height = 0
                mAnimatedView.visibility = GONE
                mAnimatedView.requestLayout()
                mAnimatedView.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT     // Return to wrap
            }
            post()
        }
    }

    companion object {

        fun setHeightForWrapContent(activity: Activity, view: View) {
            val metrics = DisplayMetrics()
            activity.windowManager.defaultDisplay.getMetrics(metrics)

            val screenWidth = metrics.widthPixels

            val heightMeasureSpec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
            val widthMeasureSpec = MeasureSpec.makeMeasureSpec(screenWidth, MeasureSpec.EXACTLY)

            view.measure(widthMeasureSpec, heightMeasureSpec)
            val height = view.measuredHeight
            view.layoutParams.height = height
        }
    }
}
