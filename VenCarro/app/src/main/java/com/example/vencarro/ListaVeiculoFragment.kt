package com.example.vencarro

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.vencarro.databinding.FragmentListaVeiculoBinding

class ListaVeiculoFragment : Fragment() {

    lateinit var binding:FragmentListaVeiculoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListaVeiculoBinding.inflate(inflater, container, false)
        return binding.root
    }

}