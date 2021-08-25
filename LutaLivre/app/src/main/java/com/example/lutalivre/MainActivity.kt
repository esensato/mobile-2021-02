package com.example.lutalivre

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun lutar(view:View) {

        val l1 = findViewById<EditText>(R.id.lutadorA)
        val l2 = findViewById<EditText>(R.id.lutadorB)

        val lutador1 = Lutador(l1.text.toString())
        val lutador2 = Lutador(l2.text.toString())

        val r = lutador1.atacar(lutador2)

        findViewById<TextView>(R.id.resultado).text = r
    }

}