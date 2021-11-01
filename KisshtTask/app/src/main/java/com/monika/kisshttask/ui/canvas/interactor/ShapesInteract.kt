package com.monika.kisshttask.ui.canvas.interactor


import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import androidx.annotation.NonNull
import androidx.appcompat.app.AlertDialog
import com.monika.kisshttask.ui.canvas.model.Shape
import com.monika.kisshttask.ui.canvas.util.Constants
import java.util.*
import kotlin.math.roundToInt
import kotlin.math.sqrt

@SuppressLint("StaticFieldLeak")
object ShapesInteract {

    private val LOG_TAG = ""
    private var mContext: Context? = null

    var canvas: CustomView? = null
    var maxX = 0
    var maxY = 0

    /*
    Choose linkedlist (default doubly linkedlist in java ) as the data structure
     since we can add, transform, delete shapes very quickly in the same list without using extra memory
     */
    private var historyList: LinkedList<Shape> = LinkedList<Shape>()
    private var actionSequence = 0

    /**
     *
     * @param oldShape
     * @param index
     * @param initialTouchX
     * @param initialTouchY
     */
    private fun askForDeleteShape(
        oldShape: Shape,
        index: Int,
        initialTouchX: Float,
        initialTouchY: Float
    ) {
        val builder = mContext?.let { AlertDialog.Builder(it) }
        builder?.setMessage("Are you sure you want to delete ?")?.setTitle("Delete Shape")
        builder?.setPositiveButton("ok"
        ) { dialog, id -> deleteShape(oldShape, index) }
        builder?.setNegativeButton("cancel") { dialog, id ->
            // User cancelled the dialog
        }
        // Create the AlertDialog
        builder?.create()?.show()
    }

    private fun deleteShape(oldShape: Shape, i: Int) {
        oldShape.setVisibility(false)
        oldShape.actionNumber = actionSequence++
        getHistoryList()[i] = oldShape
        Log.d(
            LOG_TAG,
            "askForDeleteShape =  " + oldShape.type
        )
        canvas?.setHistoryList(getHistoryList())
        canvas?.invalidate()
    }

    fun changeShapeOnTouch(x: Float, y: Float, changeStatus: Int) {
        val touchX = x.roundToInt()
        val touchY = y.roundToInt()
        //   Toast.makeText(this.getContext(), " Touch at " + touchX + " y= " + touchY, Toast.LENGTH_SHORT).show();
        var oldX: Int
        var oldY: Int
        val list = getHistoryList()
        val newShape: Shape? = null
        //Traverse from end so that we find the last performed action or shape first.
        for (i in list.indices.reversed()) {
            val oldShape = list[i]
            if (oldShape.isVisible) {
                oldX = oldShape.getxCordinate()
                oldY = oldShape.getyCordinate()

                //Find an existing shape where the user has clicked on the canvas
                if (Constants.RADIUS >= calculateDistanceBetweenPoints(
                        oldX.toDouble(),
                        oldY.toDouble(),
                        touchX.toDouble(),
                        touchY.toDouble()
                    )
                ) {
                    if (changeStatus == Constants.ACTION_TRANSFORM) addTransformShape(
                        oldShape,
                        i,
                        oldX,
                        oldY
                    ) else if (changeStatus == Constants.ACTION_DELETE) askForDeleteShape(
                        oldShape,
                        i,
                        oldX.toFloat(),
                        oldY.toFloat()
                    )
                    break
                }
            }
        }
    }

    private fun addTransformShape(oldShape: Shape, index: Int, newX: Int, newY: Int) {
        Log.d(LOG_TAG, " oldShape =  " + oldShape.type)
        oldShape.setVisibility(false)
        getHistoryList()[index] = oldShape
        Log.d(LOG_TAG, " HIDE oldShape =  " + oldShape.type)

        //transform object , rotate into available objects
        val newShapeType: Int =
            ((oldShape.type?.value ?: 0) + 1) % Constants.TOTAL_SHAPES
        val newshapeType: Shape.Type =
            Shape.Type.values()[newShapeType]
        Log.d(
            LOG_TAG,
            " newshape =  $newshapeType"
        )
        val newShape = createShape(newshapeType, newX, newY)
        newShape.lastTranformationIndex = index
        upDateCanvas(newShape)
    }

    fun calculateDistanceBetweenPoints(
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double
    ): Double {
        return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
    }

    /*
    Generate random x,y from 0,0 to screen max width and height
     */
    private fun generateRandomXAndY(): IntArray {
        var x: Int
        var y: Int
        var rn = Random()
        var diff: Int = maxX - Constants.RADIUS
        Log.d("TAG", "generateRandomXAndY: $diff")
        x = rn.nextInt(diff + 1)
        x += Constants.RADIUS
        rn = Random()
        diff = maxY - Constants.RADIUS
        y = rn.nextInt(diff + 1)
        y += Constants.RADIUS
        Log.d(
            LOG_TAG,
            " Random x= " + x + "y" + y
        )
        return intArrayOf(x, y)
    }

    fun addShapeRandom(type: Shape.Type) {
        val randomCordinates = generateRandomXAndY()
        val shape = createShape(type, randomCordinates[0], randomCordinates[1])
        upDateCanvas(shape)
    }

    @NonNull
    private fun createShape(type: Shape.Type, x: Int, y: Int): Shape {
        val shape = Shape(x, y, Constants.RADIUS)
        shape.type = type
        return shape
    }

    fun undo() {
        if (getHistoryList().size > 0) {
            actionSequence--
            val toDeleteShape = getHistoryList().last
            if (toDeleteShape.lastTranformationIndex !== Constants.ACTION_CREATE) {
                val lastVisibleIndex: Int = toDeleteShape.lastTranformationIndex
                if (lastVisibleIndex < getHistoryList().size) {
                    val lastVisibleShape = getHistoryList()[lastVisibleIndex]
                    if (lastVisibleShape != null) {
                        lastVisibleShape.setVisibility(true)
                        getHistoryList()[lastVisibleIndex] = lastVisibleShape
                    }
                }
            }
            getHistoryList().removeLast()
            canvas?.setHistoryList(getHistoryList())
            canvas?.invalidate()
        }
    }

    private fun upDateCanvas(shape: Shape) {
        Log.d(
            LOG_TAG,
            " upDateCanvas " + shape.type.toString() + " actiontype = " + actionSequence
        )
        shape.actionNumber = actionSequence++
        getHistoryList().add(shape)
        canvas?.setHistoryList(getHistoryList())
        canvas?.invalidate()
    }

    private fun getHistoryList(): LinkedList<Shape> {
        return historyList
    }

    fun setHistoryList(historyList: LinkedList<Shape>) {
        this.historyList = historyList
    }

    /*
   Remove all items of a shape
    */
    fun deleteAllByShape(shapeType: Shape.Type?) {
        val itr = getHistoryList().iterator()
        while (itr.hasNext()) {
            val shape = itr.next()
            if (shape.type === shapeType) {
                itr.remove()
            }
        }
    }

    /*
    Get all items in list , grouped by shape
     */
    fun getCountByGroup(): HashMap<Shape.Type, Int>? {
        val shapeTypeCountMap = HashMap<Shape.Type, Int>()
        for (shape in getHistoryList()) {
            if (shape.isVisible) {
                val shapeType: Shape.Type = shape.type!!
                var existingCnt = 0
                if (shapeTypeCountMap.containsKey(shape.type)) shapeTypeCountMap[shape.type].also {
                    if (it != null) {
                        existingCnt = it
                    }
                }
                existingCnt++
                shapeTypeCountMap[shapeType] = existingCnt
            }
        }
        return shapeTypeCountMap
    }
    @JvmName("getCanvas1")
    fun getCanvas(): CustomView? {
        return canvas
    }

    @JvmName("setCanvas1")
    fun setCanvas(canvas: CustomView?) {
        this.canvas = canvas
    }

    fun getmContext(): Context? {
        return mContext
    }

    @JvmName("getMaxX1")
    fun getMaxX(): Int {
        return maxX
    }

    @JvmName("setMaxX1")
    fun setMaxX(maxX: Int) {
        this.maxX = maxX
    }

    @JvmName("getMaxY1")
    fun getMaxY(): Int {
        return maxY
    }

    @JvmName("setMaxY1")
    fun setMaxY(maxY: Int) {
        this.maxY = maxY
    }
    
    fun setContext(mContext: Context?) {
        this.mContext = mContext
    }

}