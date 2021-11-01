package com.monika.kisshttask.ui.canvas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.monika.kisshttask.R
import com.monika.kisshttask.ui.canvas.interactor.CustomView
import com.monika.kisshttask.ui.canvas.model.Shape
import com.monika.kisshttask.ui.canvas.presenter.CanvasPresenter
import com.monika.kisshttask.ui.jetpack.JetpackActivity

class CanvasActivity : AppCompatActivity() {

    private val maxY = 800 // average screen height
    private val maxX = 600 //average screen height

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_canvas)

        val canvas: CustomView = findViewById(R.id.canvasDrawView)
        val canvasPresenter = CanvasPresenter(canvas, this)

        canvasPresenter.setMaxX(maxX)
        canvasPresenter.setMaxY(maxY)

        val btnCircle = findViewById<Button>(R.id.btnCircle)
        btnCircle.setOnClickListener {
            canvasPresenter.addShapeRandom(Shape.Type.CIRCLE)
        }

        val btnRect = findViewById<Button>(R.id.btnRect)
        btnRect.setOnClickListener {
            canvasPresenter.addShapeRandom(Shape.Type.SQUARE)
        }

        val btnUndo = findViewById<Button>(R.id.btnUndo)
        btnUndo.setOnClickListener {
            canvasPresenter.undo()
        }

    }
}