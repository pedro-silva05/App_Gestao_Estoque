package com.example.controledeestoque.view.utilidades

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.controledeestoque.R

class ProgressBar(val activity: Activity){
    private lateinit var isDialog: AlertDialog
    fun startLoading(){
        val inflater = activity.layoutInflater
        val dialogView = inflater.inflate(R.layout.progress_bar_item, null)
        val buider = AlertDialog.Builder(activity)
        buider.setView(dialogView)
        buider.setCancelable(false)
        isDialog = buider.create()
        isDialog.show()
    }

    fun isDimiss(){
        isDialog.dismiss()
    }
}