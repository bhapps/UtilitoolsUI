/*
    *
    * BH Apps
    * version 0.0.1
    * Contains methods for animating views
    * bhapps.utilitools.ui.kotlin.animations
    *
*/

package bhapps.utilitools.ui.kotlin.animations

import android.animation.*
import android.app.Activity
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import android.os.Handler
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import android.widget.ImageView
import androidx.core.graphics.drawable.toBitmap
import kotlin.random.Random

class AnimateView {

    internal var v: View? = null

    fun animateTheView(view: View, toVisibility: Int, toAlpha: Float, duration: Int) {

        val show = toVisibility == View.VISIBLE
        if (show) {
            view.alpha = 0f
        }
        view.visibility = View.VISIBLE
        view.animate()
            .setDuration(duration.toLong())
            .alpha(if (show) toAlpha else 0f)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = toVisibility
                }
            })


    }

    fun expandOrCollapseView(v: View, expand: Boolean) {

        val _v = v

        if (expand) {
            _v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val targetHeight = _v.measuredHeight
            _v.layoutParams.height = 0
            _v.visibility = View.VISIBLE
            val valueAnimator = ValueAnimator.ofInt(targetHeight)
            valueAnimator.addUpdateListener { animation ->
                _v.layoutParams.height = animation.animatedValue as Int
                _v.requestLayout()
            }
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = 250
            valueAnimator.start()
        } else {
            val initialHeight = _v.measuredHeight
            val valueAnimator = ValueAnimator.ofInt(initialHeight, 0)
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.addUpdateListener { animation ->
                _v.layoutParams.height = animation.animatedValue as Int
                _v.requestLayout()
                if (animation.animatedValue as Int == 0)
                    _v.visibility = View.GONE
            }
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = 250
            valueAnimator.start()
        }
    }

    fun expand(v: View, duration: Int, targetHeight: Int) {
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        v.layoutParams.height = 0
        v.visibility = View.VISIBLE
        val valueAnimator = ValueAnimator.ofInt(0, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    fun collapse(v: View, duration: Int, targetHeight: Int) {
        val valueAnimator = ValueAnimator.ofInt(0, targetHeight)
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.addUpdateListener { animation ->
            v.layoutParams.height = animation.animatedValue as Int
            v.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    fun expandBottomSheet(v: View, bottomSheet: View, duration: Int) {
        var targetHeight = 20
        v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        targetHeight = v.layoutParams.height
        val valueAnimator = ValueAnimator.ofInt(0, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            bottomSheet.layoutParams.height = animation.animatedValue as Int
            bottomSheet.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    fun collapseBottomSheet(v: View, bottomSheet: View, duration: Int) {
        val targetHeight = 20
        //v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        //targetHeight = v.getLayoutParams().height;
        val valueAnimator = ValueAnimator.ofInt(0, targetHeight)
        valueAnimator.addUpdateListener { animation ->
            bottomSheet.layoutParams.height = animation.animatedValue as Int
            bottomSheet.requestLayout()
        }
        valueAnimator.interpolator = DecelerateInterpolator()
        valueAnimator.duration = duration.toLong()
        valueAnimator.start()
    }

    fun slideInSlideOutView(v: View, expand: Boolean) {

        val _v = v

        if (expand) {
            _v.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            val targetHeight = _v.measuredHeight
            _v.layoutParams.width = 0
            _v.visibility = View.VISIBLE
            val valueAnimator = ValueAnimator.ofInt(targetHeight)
            valueAnimator.addUpdateListener { animation ->
                _v.layoutParams.width = animation.animatedValue as Int
                _v.requestLayout()
            }
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = 250
            valueAnimator.start()
        } else {
            val initialHeight = _v.measuredHeight
            val valueAnimator = ValueAnimator.ofInt(initialHeight, 0)
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.addUpdateListener { animation ->
                _v.layoutParams.width = animation.animatedValue as Int
                _v.requestLayout()
                if (animation.animatedValue as Int == 0)
                    _v.visibility = View.GONE
            }
            valueAnimator.interpolator = DecelerateInterpolator()
            valueAnimator.duration = 250
            valueAnimator.start()
        }
    }

    /* animation type */
    val BOTTOM_UP = 1
    val FADE_IN = 2
    val LEFT_RIGHT = 3
    val RIGHT_LEFT = 4
    val NONE = 0

    /* animation duration */
    private val DURATION_IN_BOTTOM_UP: Long = 150
    private val DURATION_IN_FADE_ID: Long = 500
    private val DURATION_IN_LEFT_RIGHT: Long = 150
    private val DURATION_IN_RIGHT_LEFT: Long = 150

    fun animate(view: View, position: Int, type: Int) {
        when (type) {
            BOTTOM_UP -> animateBottomUp(view, position)
            FADE_IN -> animateFadeIn(view, position)
            LEFT_RIGHT -> animateLeftRight(view, position)
            RIGHT_LEFT -> animateRightLeft(view, position)
        }
    }

    private fun animateBottomUp(view: View, position: Int) {
        var position = position
        val not_first_item = position == -1
        position = position + 1
        view.translationY = (if (not_first_item) 800 else 500).toFloat()
        view.alpha = 0f
        val animatorSet = AnimatorSet()
        val animatorTranslateY = ObjectAnimator.ofFloat(view, "translationY", if (not_first_item) 800f else 500f, 0f)
        val animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1f)
        animatorTranslateY.setStartDelay(if (not_first_item) 0 else position * DURATION_IN_BOTTOM_UP)
        animatorTranslateY.setDuration((if (not_first_item) 3 else 1) * DURATION_IN_BOTTOM_UP)
        animatorSet.playTogether(animatorTranslateY, animatorAlpha)
        animatorSet.start()
    }

    private fun animateFadeIn(view: View, position: Int) {
        var position = position
        val not_first_item = position == -1
        position = position + 1
        view.alpha = 0f
        val animatorSet = AnimatorSet()
        val animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 0f, 0.5f, 1f)
        ObjectAnimator.ofFloat(view, "alpha", 0f).start()
        animatorAlpha.startDelay = if (not_first_item) DURATION_IN_FADE_ID / 2 else position * DURATION_IN_FADE_ID / 3
        animatorAlpha.duration = DURATION_IN_FADE_ID
        animatorSet.play(animatorAlpha)
        animatorSet.start()
    }

    private fun animateLeftRight(view: View, position: Int) {
        var position = position
        val not_first_item = position == -1
        position = position + 1
        view.translationX = -400f
        view.alpha = 0f
        val animatorSet = AnimatorSet()
        val animatorTranslateY = ObjectAnimator.ofFloat(view, "translationX", -400f, 0f)
        val animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1f)
        ObjectAnimator.ofFloat(view, "alpha", 0f).start()
        animatorTranslateY.setStartDelay(if (not_first_item) DURATION_IN_LEFT_RIGHT else position * DURATION_IN_LEFT_RIGHT)
        animatorTranslateY.setDuration((if (not_first_item) 2 else 1) * DURATION_IN_LEFT_RIGHT)
        animatorSet.playTogether(animatorTranslateY, animatorAlpha)
        animatorSet.start()
    }

    private fun animateRightLeft(view: View, position: Int) {
        var position = position
        val not_first_item = position == -1
        position = position + 1
        view.translationX = view.x + 400
        view.alpha = 0f
        val animatorSet = AnimatorSet()
        val animatorTranslateY = ObjectAnimator.ofFloat(view, "translationX", view.x + 400f, 0f)
        val animatorAlpha = ObjectAnimator.ofFloat(view, "alpha", 1f)
        ObjectAnimator.ofFloat(view, "alpha", 0f).start()
        animatorTranslateY.setStartDelay(if (not_first_item) DURATION_IN_RIGHT_LEFT else position * DURATION_IN_RIGHT_LEFT)
        animatorTranslateY.setDuration((if (not_first_item) 2 else 1) * DURATION_IN_RIGHT_LEFT)
        animatorSet.playTogether(animatorTranslateY, animatorAlpha)
        animatorSet.start()
    }

    fun loadAnimationOnView(view: View, activity: Activity, startDelay: Long, duration: Long, animationResource: Int) {

        var loadAnimationOnViewHandler = Handler()
        loadAnimationOnViewHandler.postDelayed(Runnable {

            var loadAnimation = AnimationUtils.loadAnimation(activity, animationResource)
            loadAnimation.duration = duration
            view.startAnimation(loadAnimation)

        }, startDelay)

    }

    public fun animateToChangedImageDrawable(view: View, view_id: Int, activity: Activity, drawable: Int, isCrossFadeEnabled: Boolean, duration: Int) {

        var view = activity.findViewById<ImageView>(view_id)
        val td = TransitionDrawable(
            arrayOf<Drawable>(
                ColorDrawable(Color.TRANSPARENT),
                BitmapDrawable(activity.resources, activity.resources.getDrawable(drawable).toBitmap())
            )
        )
        td.startTransition(duration)
        td.isCrossFadeEnabled = isCrossFadeEnabled
        view.setImageDrawable(td)

    }

}