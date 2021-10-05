package com.example.vencarro

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.cloudant.sync.documentstore.DocumentBodyFactory
import com.cloudant.sync.documentstore.DocumentRevision
import com.cloudant.sync.documentstore.DocumentStore
import com.cloudant.sync.documentstore.UnsavedFileAttachment
import com.cloudant.sync.replication.ReplicatorBuilder
import com.example.vencarro.databinding.FragmentResumoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.net.URI

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

        val ds = DocumentStore.getInstance(File("${context!!.filesDir.absoluteFile.absolutePath}", "vencar_datastore"))
        val revision = DocumentRevision()
        val body = HashMap<String, Any>()

        body["MARCA"] = binding.txtMarcaResumo.text.toString()
        body["MODELO"] = binding.txtModeloResumo.text.toString()
        body["ANO"] = binding.txtAnoResumo.text.toString()
        body["PRECO"] = binding.txtValorResumo.text.toString()

        revision.body = DocumentBodyFactory.create(body)
        val saved = ds.database().create(revision)
        val imagem = (activity as MainActivity).imagem
        val att1 = UnsavedFileAttachment(imagem, "image/jpeg")
        saved.attachments[imagem.name] = att1
        val updated = ds.database().update(saved)
        Log.i("SAVE", "Atualizado: ${updated.id}")

        val uri = URI("https://13c9925a-139c-4d54-a991-4d57d27137bd-bluemix.cloudantnosqldb.appdomain.cloud/vencar")

        val replicator = ReplicatorBuilder.push()
            .from(ds)
            .to(uri)
            .iamApiKey("rwslhAHvtPLWxwDw5W-vUkTuNMdlkRRXjOUioz_v4d2z")
            .build()

        replicator.start()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuFoto -> foto()
            R.id.mnuSalvar -> salvar()
        }

        return true
    }
}