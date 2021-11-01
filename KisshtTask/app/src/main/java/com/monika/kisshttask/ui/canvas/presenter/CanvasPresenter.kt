package com.monika.kisshttask.ui.canvas.presenter

import android.content.Context
import android.util.Log
import android.view.MotionEvent
import com.monika.kisshttask.ui.canvas.interactor.CustomView
import com.monika.kisshttask.ui.canvas.interactor.ShapesInteract
import com.monika.kisshttask.ui.canvas.model.Shape.Type
import com.monika.kisshttask.ui.canvas.util.Constants
import java.util.HashMap

class CanvasPresenter(private val canvas: CustomView, private val mContext: Context) {

    /**
     * Respons to click and long press events on canvas
     */
    private val onTouchListener = object : CanvasTouch {
        override fun onClickEvent(event: MotionEvent) {
            Log.d(LOG_TAG, " onClickEvent done ")
            ShapesInteract.changeShapeOnTouch(event.x, event.y, Constants.ACTION_TRANSFORM)
        }

        override fun onLongPressEvent(initialTouchX: Float, initialTouchY: Float) {
            Log.d(LOG_TAG, " onLongPressEvent done ")
            ShapesInteract.changeShapeOnTouch(initialTouchX, initialTouchY, Constants.ACTION_DELETE)
        }
    }

    val countByGroup: HashMap<Type, Int>?
        get() = ShapesInteract.getCountByGroup()

    init {
        canvas.canvasTouch = onTouchListener
        initializeUIComponents(canvas, mContext)
    }

    private fun initializeUIComponents(canvas: CustomView, mContext: Context) {
        ShapesInteract.canvas = canvas
        ShapesInteract.setContext(mContext)
    }


    fun setMaxX(maxX: Int) {
        ShapesInteract.maxX = maxX
    }

    fun setMaxY(maxY: Int) {
        ShapesInteract.maxY = maxY
    }

    fun addShapeRandom(type: Type) {
        ShapesInteract.addShapeRandom(type)
    }

    fun undo() {
        ShapesInteract.undo()
    }

    companion object {
        private val LOG_TAG = CanvasPresenter.javaClass.simpleName
    }

}
