package com.example.reddit_top_posts.view.listeners

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.View
import android.view.View.OnTouchListener
import android.widget.ImageView


class ZoomListener(
    private val imageView: ImageView,
    private val screenHeight: Int,
    private val screenWidth: Int
) :
    ScaleGestureDetector.SimpleOnScaleGestureListener(), OnTouchListener {
    private val nullCoord = -1f
    private var prevX: Float = nullCoord
    private var prevY: Float = nullCoord
    private var trueX: Float = 0f
    private var trueY: Float = 0f

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        imageView.scaleX *= detector.scaleFactor
        imageView.scaleY *= detector.scaleFactor
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        var measuredH = imageView.measuredHeight.toFloat()
        var measuredW = imageView.measuredWidth.toFloat()
        val intrinsicH = imageView.drawable.intrinsicHeight.toFloat()
        val intrinsicW = imageView.drawable.intrinsicWidth.toFloat()
        if (measuredH / intrinsicH < measuredW / intrinsicW) {
            measuredW = ((intrinsicW * measuredH / intrinsicH) * imageView.scaleX)
        } else {
            measuredH = ((intrinsicH * measuredW / intrinsicW) * imageView.scaleY)
        }

        val trueWidth = measuredW * imageView.scaleX
        val trueHeight = measuredH

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                if (prevX != nullCoord && prevY != nullCoord) {
                    val deltaX = prevX - event.x
                    val deltaY = prevY - event.y

                    if (trueX <= 0 && trueX + trueWidth >= screenWidth) {
                        if (trueX - deltaX > 0) {
                            trueX = 0f
                            imageView.x = (trueWidth - screenWidth) / 2
                        } else if (trueX - deltaX + trueWidth > screenWidth) {
                            imageView.x -= deltaX
                        } else if (trueX - deltaX + trueWidth < screenWidth) {
                            trueX = screenWidth - trueWidth
                            imageView.x = ((trueWidth - screenWidth) / 2) + trueX
                        }
                    }

                    if (trueY + trueHeight >= screenHeight && trueY <= 0) {
                        if (trueY - deltaY > 0) {
                            trueY = 0f
                            imageView.y = -(screenHeight - trueHeight) / 2
                        } else if (trueY - deltaY + trueHeight > screenHeight) {
                            imageView.y -= deltaY
                        } else if (trueY - deltaY + trueHeight > screenHeight) {
                            trueY = screenHeight - trueHeight
                            imageView.y = ((trueHeight - screenHeight) / 2) + trueY
                        }
                    }
                }
            }
            MotionEvent.ACTION_UP -> {
                if (imageView.scaleX < 1f || imageView.scaleY < 1f) {
                    imageView.scaleX = 1f
                    imageView.scaleY = 1f
                    imageView.x = 0f
                    imageView.y = 0f
                } else if (imageView.scaleX > 1f || imageView.scaleY > 1f) {
                    if (trueX + trueWidth < screenWidth) {
                        imageView.x += screenWidth - (trueX + trueWidth)
                    } else if (trueX > 0 && trueX + trueWidth > screenWidth) {
                        imageView.x -= trueX
                    }

                    if (trueY + trueHeight <= screenHeight && imageView.y != 0f) {
                        imageView.y = 0f
                    } else if (trueY > 0 && trueY + trueHeight > screenHeight) {
                        imageView.y -= trueY
                    }
                }
            }
        }

        trueX = imageView.x - ((trueWidth - screenWidth) / 2)
        trueY = imageView.y - ((trueHeight - screenHeight) / 2)
        prevX = event.x
        prevY = event.y
        return true
    }
}