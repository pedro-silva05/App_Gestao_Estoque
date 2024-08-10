package com.example.controledeestoque.view.utilidades

import android.content.Context
import android.graphics.Color
import android.content.res.ColorStateList
import android.widget.Button
import android.widget.EditText
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.CornerFamily

object MaterialShape {

    fun applyCutCorners(context: Context, button: Button) {
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(CornerFamily.CUT, 30f)
            .setBottomLeftCorner(CornerFamily.CUT, 30f)
            .build()

        val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(Color.rgb(255,209,102))
        }

        button.background = materialShapeDrawable
    }

    fun applyStyleTextInput(context: Context, editText: EditText){
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopRightCorner(CornerFamily.CUT, 30f)
            .setBottomLeftCorner(CornerFamily.CUT, 30f)
            .build()

        val materialShapeDrawable = MaterialShapeDrawable(shapeAppearanceModel).apply{
            fillColor = ColorStateList.valueOf(Color.WHITE)
            strokeColor = ColorStateList.valueOf(Color.BLACK)
            strokeWidth = 3f
        }

        val materialShapeDrawablefocused = MaterialShapeDrawable(shapeAppearanceModel).apply {
            fillColor = ColorStateList.valueOf(Color.WHITE)
            strokeColor = ColorStateList.valueOf(Color.BLUE)
            strokeWidth = 5f
        }

        editText.background = materialShapeDrawable

        editText.setOnFocusChangeListener { view, b ->
            if (b){
                editText.background = materialShapeDrawablefocused
            } else{
                editText.background = materialShapeDrawable
            }
        }
    }
}
