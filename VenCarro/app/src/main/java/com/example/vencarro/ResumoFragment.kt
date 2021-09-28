package com.example.vencarro

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.vencarro.databinding.FragmentResumoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class ResumoFragment : Fragment(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var binding:FragmentResumoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentResumoBinding.inflate(inflater, container, false)

        binding.txtMarcaResumo.text = arguments!!.getString("MARCA")
        binding.txtModeloResumo.text = arguments!!.getString("MODELO")
        binding.txtAnoResumo.text = arguments!!.getString("ANO")
        binding.txtValorResumo.text = arguments!!.getString("PRECO")
        binding.menu.setOnNavigationItemSelectedListener(this)

        return binding.root
    }

    fun foto() {

        val acessoCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (acessoCameraIntent.resolveActivity(activity!!.packageManager) != null) {
            activity!!.startActivityForResult(acessoCameraIntent, 1)
        }
    }

    fun salvar() {

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuFoto -> foto()
            R.id.mnuSalvar -> salvar()
        }

        return true
    }
}