package com.monika.kisshttask.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.monika.kisshttask.R
import com.monika.kisshttask.ui.canvas.CanvasActivity
import com.monika.kisshttask.ui.jetpack.JetpackActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnScreen1 = findViewById<Button>(R.id.btnScreen1)
        btnScreen1.setOnClickListener {
            startActivity(Intent(this, CanvasActivity::class.java))
        }

        val btnScreen2 = findViewById<Button>(R.id.btnScreen2)
        btnScreen2.setOnClickListener {
            startActivity(Intent(this, JetpackActivity::class.java))
        }
    }
}