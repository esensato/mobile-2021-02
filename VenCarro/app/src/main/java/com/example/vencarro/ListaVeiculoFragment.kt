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
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.JsonRequest
import com.android.volley.toolbox.Volley
import com.example.vencarro.databinding.FragmentListaVeiculoBinding
import com.example.vencarro.databinding.LinhaListaBinding
import org.json.JSONArray
import org.json.JSONObject

class ListaVeiculoFragment : Fragment() {

    lateinit var binding:FragmentListaVeiculoBinding
    lateinit var activityPrincipal: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.listaVeiculo.layoutManager = LinearLayoutManager(context!!)
        activityPrincipal = activity as MainActivity
        binding.listaVeiculo.adapter = VeiculoAdapter(context!!, activityPrincipal)
        activityPrincipal = activity as MainActivity
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

    class VeiculoAdapter(context: Context, var activityPrincipal: MainActivity):RecyclerView.Adapter<VeiculoHolder>(){

        var veiculos = ArrayList<Veiculo>()
        var veiculoSelecionado = Veiculo()

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
                        veiculos.add(Veiculo(obj.getInt("codigo"), obj.getString("nome"), 0, "", "", ""))
                    }
                    notifyDataSetChanged()
                },
                Response.ErrorListener {
                        error -> Log.e("CARRO", "ERRO: " + error.message)
                }
            )

            )

        }

        fun obterModelos(codigoMarca:Int) {

            queue.add(JsonObjectRequest(
                Request.Method.GET,
                "https://parallelum.com.br/fipe/api/v1/carros/marcas/${codigoMarca}/modelos",
                null,
                Response.Listener<JSONObject> { response ->
                    veiculos.clear()
                    for (i in 0 until response.getJSONArray("modelos").length()) {
                        val obj = response.getJSONArray("modelos").getJSONObject(i)
                        veiculos.add(Veiculo(veiculoSelecionado.idMarca, veiculoSelecionado.marca, obj.getInt("codigo"), obj.getString("nome"), "", ""))
                    }
                    notifyDataSetChanged()
                },
                Response.ErrorListener { error ->  Log.e("CARRO", "ERRO: " + error.message)
                }
            )
            )

        }

        fun obterAnos(codigoMarca: Int, codigoModelo: Int){

            queue.add(JsonArrayRequest(
                Request.Method.GET,
                "https://parallelum.com.br/fipe/api/v1/carros/marcas/${codigoMarca}/modelos/${codigoModelo}/anos",
                null,
                Response.Listener<JSONArray>{ response ->
                    veiculos.clear()
                    for (i in 0 until response.length()) {
                        val obj = response.getJSONObject(i)
                        veiculos.add(Veiculo(veiculoSelecionado.idMarca,
                                             veiculoSelecionado.marca,
                                             veiculoSelecionado.idModelo,
                                             veiculoSelecionado.modelo,
                                             obj.getString("codigo"),
                                             obj.getString("nome")))
                    }
                    notifyDataSetChanged()
                },
                Response.ErrorListener {
                        error -> Log.e("CARRO", "ERRO: " + error.message)
                }
            )

            )
        }

        fun obterPreco(codigoMarca: Int, codigoModelo: Int, codigoAno:String) {

            queue.add(JsonObjectRequest(
                Request.Method.GET,
                "https://parallelum.com.br/fipe/api/v1/carros/marcas/${codigoMarca}/modelos/${codigoModelo}/anos/${codigoAno}",
                null,
                Response.Listener<JSONObject> { response ->
                    Log.i("PRECO", response.getString("Valor"))
                    veiculoSelecionado.preco = response.getString("Valor")
                    activityPrincipal.exibirResumo(veiculoSelecionado)

                },
                Response.ErrorListener { error ->  Log.e("CARRO", "ERRO: " + error.message)
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

                if (veiculoSelecionado.idMarca == 0) {
                    veiculoSelecionado.idMarca = veiculos.get(position).idMarca
                    veiculoSelecionado.marca = veiculos.get(position).marca
                    obterModelos(veiculoSelecionado.idMarca)
                } else if (veiculoSelecionado.idMarca != 0 && veiculoSelecionado.idModelo == 0) {
                    veiculoSelecionado.idModelo = veiculos.get(position).idModelo
                    veiculoSelecionado.modelo = veiculos.get(position).modelo
                    obterAnos(veiculoSelecionado.idMarca, veiculoSelecionado.idModelo)
                } else if (veiculoSelecionado.idMarca != 0 && veiculoSelecionado.idModelo != 0 && veiculoSelecionado.idAno == "") {
                    veiculoSelecionado.idAno = veiculos.get(position).idAno
                    veiculoSelecionado.ano = veiculos.get(position).ano
                    obterPreco(veiculoSelecionado.idMarca, veiculoSelecionado.idModelo, veiculos.get(position).idAno)
                }
            }

        }

        override fun getItemCount(): Int {
            return veiculos.size
        }

    }


}