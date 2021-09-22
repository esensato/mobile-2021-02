package com.example.vencarro

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vencarro.databinding.FragmentListaVeiculoBinding
import com.example.vencarro.databinding.LinhaListaBinding
import org.json.JSONArray

class ListaVeiculoFragment : Fragment() {

    lateinit var binding:FragmentListaVeiculoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.listaVeiculo.layoutManager = LinearLayoutManager(context!!)
        binding.listaVeiculo.adapter = VeiculoAdapter(context!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentListaVeiculoBinding.inflate(inflater, container, false)
        return binding.root
    }

    class VeiculoHolder(itemLista:LinhaListaBinding):RecyclerView.ViewHolder(itemLista.root){

        var txtMarca = itemLista.txtMarca
        var txtModelo = itemLista.txtModelo
        var txtAno = itemLista.txtAno
        var item = itemLista.item

    }

    class VeiculoAdapter(context: Context):RecyclerView.Adapter<VeiculoHolder>(){

        var veiculos = ArrayList<Veiculo>()

        var queue:RequestQueue

        init {
            //veiculos.add(Veiculo(marca="Ford", modelo="Ka", ano="2020"))
            //veiculos.add(Veiculo(marca="Fiat", modelo="Uno", ano="2021"))
            queue = Volley.newRequestQueue(context)
            obterMarcas()
        }

        fun obterMarcas() {

            queue.add(JsonArrayRequest(
                Request.Method.GET,
                "https://parallelum.com.br/fipe/api/v1/carros/marcas",
                null,
                Response.Listener<JSONArray>{ response ->
                    // [{"nome":"Acura","codigo":"1"},{"nome":"Agrale","codigo":"2"},{"nome":"Alfa Romeo","codigo":"3"}...
                    for (i in 0 until response.length()) {
                        val obj = response.getJSONObject(i)
                        veiculos.add(Veiculo(obj.getInt("codigo"), obj.getString("nome"), 0, "",0, ""))
                    }
                    notifyDataSetChanged()
                },
                Response.ErrorListener {
                        error -> Log.e("CARRO", "ERRO: " + error.message)
                }
            )

            )

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VeiculoHolder {
            val layout = LinhaListaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return VeiculoHolder(layout)
        }

        override fun onBindViewHolder(holder: VeiculoHolder, position: Int) {
            holder.txtMarca.text = veiculos.get(position).marca
            holder.txtModelo.text = veiculos.get(position).modelo
            holder.txtAno.text = veiculos.get(position).ano
            holder.item.setOnClickListener {
                Log.i("Veiculo", veiculos.get(position).marca)
            }

        }

        override fun getItemCount(): Int {
            return veiculos.size
        }

    }


}