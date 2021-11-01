package com.monika.kisshttask.ui.canvas.interactor

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.monika.kisshttask.ui.canvas.model.Shape
import com.monika.kisshttask.ui.canvas.presenter.CanvasTouch
import com.monika.kisshttask.ui.canvas.util.Constants
import java.util.*

class CustomView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val LOG_TAG = CustomView::class.java.simpleName
    private val TAG = CustomView::class.java.simpleName
    val RADIUS: Int = Constants.RADIUS
    private var canvas: Canvas? = null
    var historyList: List<Shape> = ArrayList<Shape>()
    var canvasTouch: CanvasTouch? = null
    private var longPressDone = false

    init {
        isFocusable = true
        isFocusableInTouchMode = true
        setupPaint()
        Log.d(TAG, "  constructor called")
    }

    // defines paint and canvas
    private var drawPaint: Paint? = null

    // Setup paint with color and stroke styles
    private fun setupPaint() {
        drawPaint = Paint()
        drawPaint?.run {
            color = Color.BLUE
            isAntiAlias = true
            strokeWidth = 5f
            style = Paint.Style.FILL_AND_STROKE
            strokeJoin = Paint.Join.ROUND
            strokeCap = Paint.Cap.ROUND
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        Log.d(TAG, "  onDraw called")
        for (shape in getHistoryList()) {
            if (shape.isVisible) {
                when (shape.type) {
                    Shape.Type.CIRCLE -> {
                        drawPaint!!.color = Color.BLUE
                        canvas.drawCircle(
                            shape.getxCordinate().toFloat(),
                            shape.getyCordinate().toFloat(), RADIUS.toFloat(), drawPaint!!
                        )
                    }
                    Shape.Type.SQUARE -> drawRectangle(shape.getxCordinate(), shape.getyCordinate())
                    Shape.Type.TRIANGLE -> drawTriangle(
                        shape.getxCordinate(),
                        shape.getyCordinate(),
                        (2 * RADIUS)
                    )
                }
            }
        }
    }

    private var longClickActive = false
    var initialTouchX = 0f
    var initialTouchY = 0f
    private val MIN_CLICK_DURATION = 1000
    private var startClickTime: Long = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                Log.d(LOG_TAG, " ACTION_DOWN")
                initialTouchX = event.x
                initialTouchY = event.y
                longPressDone = false
                if (!longClickActive) {
                    longClickActive = true
                    startClickTime = Calendar.getInstance().timeInMillis
                }
            }
            MotionEvent.ACTION_UP -> {
                Log.d(LOG_TAG, " ACTION_UP")
                val currentTime = Calendar.getInstance().timeInMillis
                val clickDuration = currentTime - startClickTime
                if (clickDuration <= MIN_CLICK_DURATION && !longPressDone) {
                    //normal click
                    if (canvasTouch != null) {
                        canvasTouch!!.onClickEvent(event)
                    }
                    longClickActive = false
                    startClickTime = Calendar.getInstance().timeInMillis
                    return false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                Log.d(LOG_TAG, " ACTION_MOVE")
                val currentTime = Calendar.getInstance().timeInMillis
                val clickDuration = currentTime - startClickTime
                if (clickDuration >= MIN_CLICK_DURATION) {
                    if (canvasTouch != null) {
                        canvasTouch!!.onLongPressEvent(initialTouchX, initialTouchY)
                    }
                    longClickActive = false
                    longPressDone = true
                    startClickTime = Calendar.getInstance().timeInMillis
                    return false
                }
            }
        }
        return true
    }

    var squareSideHalf = 1 / Math.sqrt(2.0)
    //Consider pivot x,y as centroid.

    //Consider pivot x,y as centroid.
    fun drawRectangle(x: Int, y: Int) {
        drawPaint!!.color = Color.RED
        val rectangle = Rect(
            (x - squareSideHalf * RADIUS).toInt(),
            (y - squareSideHalf * RADIUS).toInt(),
            (x + squareSideHalf * RADIUS).toInt(),
            (y + squareSideHalf * RADIUS).toInt()
        )
        canvas!!.drawRect(rectangle, drawPaint!!)
    }

    /*
    select three vertices of triangle. Draw 3 lines between them to form a traingle
     */
    fun drawTriangle(x: Int, y: Int, width: Int) {
        drawPaint!!.color = Color.GREEN
        val halfWidth = width / 2
        val path = Path()
        path.moveTo(x.toFloat(), (y - halfWidth).toFloat()) // Top
        path.lineTo((x - halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom left
        path.lineTo((x + halfWidth).toFloat(), (y + halfWidth).toFloat()) // Bottom right
        path.lineTo(x.toFloat(), (y - halfWidth).toFloat()) // Back to Top
        path.close()
        canvas!!.drawPath(path, drawPaint!!)
    }

    @JvmName("getHistoryList1")
    fun getHistoryList(): List<Shape> {
        return historyList
    }

    @JvmName("setHistoryList1")
    fun setHistoryList(historyList: List<Shape?>?) {
        this.historyList = historyList as List<Shape>
    }

    @JvmName("getCanvasTouch1")
    fun getCanvasTouch(): CanvasTouch? {
        return canvasTouch
    }

    @JvmName("setCanvasTouch1")
    fun setCanvasTouch(canvasTouch: CanvasTouch?) {
        this.canvasTouch = canvasTouch
    }
}