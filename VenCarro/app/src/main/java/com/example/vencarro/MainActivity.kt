package com.example.vencarro

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.example.vencarro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding:ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction().
                add(R.id.principal, ListaVeiculoFragment()).
                commit()

    }

    fun exibirResumo(veiculoSelecionado:Veiculo) {

        val bundle = Bundle()
        bundle.putString("MARCA", veiculoSelecionado.marca)
        bundle.putString("MODELO", veiculoSelecionado.modelo)
        bundle.putString("ANO", veiculoSelecionado.ano)
        bundle.putString("PRECO", veiculoSelecionado.preco)

        val resumoFragment = ResumoFragment()
        resumoFragment.arguments = bundle

        supportFragmentManager.beginTransaction().
        replace(R.id.principal, resumoFragment).
        commit()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

            if (resultCode == RESULT_OK) {
                val imageBitmap = data?.extras?.get("data") as Bitmap
                findViewById<ImageView>(R.id.imgFoto).setImageBitmap(imageBitmap)
            }
            super.onActivityResult(requestCode, resultCode, data)

        super.onActivityResult(requestCode, resultCode, data)
    }
}