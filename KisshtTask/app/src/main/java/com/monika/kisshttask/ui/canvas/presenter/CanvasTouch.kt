package com.monika.kisshttask.ui.canvas.presenter

import android.view.MotionEvent

/*
     Interface to detect canvas touch and canvas long press events
 */
interface CanvasTouch {
    fun onClickEvent(event: MotionEvent)
    fun onLongPressEvent(initialTouchX: Float, initialTouchY: Float)
}
