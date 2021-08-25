package com.example.tabuada

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlin.random.Random

class MainActivity : AppCompatActivity(), SeekBar.OnSeekBarChangeListener  {

    var n1 = 0
    var n2 = 0
    lateinit var pontos:RatingBar
    lateinit var sbResultado:SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        pontos = findViewById<RatingBar>(R.id.pontos)
        sbResultado = findViewById<SeekBar>(R.id.sbResultado)

        sbResultado.setOnSeekBarChangeListener(this)

        pontos.rating = 0.0f
        sortear()
    }

    fun resultado(view: View) {

        val r = n1 * n2
        val rJogador = findViewById<EditText>(R.id.edtResultado).text.toString()

        var msg = ""
        val intJogador = rJogador.toInt()
        if (r == intJogador) {
            msg = "CORRETO!!!"
            pontos.rating = pontos.rating + 1
        } else {
            msg = "ERRADO! O resultado é: ${r}"
        }

        findViewById<EditText>(R.id.edtResultado).text.clear()
        sbResultado.progress = 0

        Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        sortear()
    }

    fun sortear() {
        n1 = Random.nextInt(1,9)
        n2 = Random.nextInt(1,9)

        findViewById<TextView>(R.id.txtNum1).text = n1.toString()
        findViewById<TextView>(R.id.txtNum2).text = n2.toString()
    }

    override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
        findViewById<EditText>(R.id.edtResultado).setText(p1.toString())
    }

    override fun onStartTrackingTouch(p0: SeekBar?) {

    }

    override fun onStopTrackingTouch(p0: SeekBar?) {

    }
}