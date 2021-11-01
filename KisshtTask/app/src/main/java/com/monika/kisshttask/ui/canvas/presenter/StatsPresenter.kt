package com.monika.kisshttask.ui.canvas.presenter

import com.monika.kisshttask.ui.canvas.interactor.ShapesInteract
import com.monika.kisshttask.ui.canvas.model.Shape
import java.util.*

class StatsPresenter {

    val countByGroup: HashMap<Shape.Type, Int>?
        get() = ShapesInteract.getCountByGroup()

    fun deleteAllByShape(shapeType: Shape.Type) {
        ShapesInteract.deleteAllByShape(shapeType)
    }
}
