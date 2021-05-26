package com.example.products
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.db.delete
import org.w3c.dom.Text

import    org.jetbrains.anko.startActivity
import java.text.NumberFormat
import java.util.*

import org.jetbrains.anko.db.parseList
import org.jetbrains.anko.db.rowParser
import org.jetbrains.anko.db.select
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val produtosAdapter = ProdutoAdapter(this)
        val listViewProdutos = findViewById<ListView>(R.id.listViewProdutos)

        listViewProdutos.adapter = produtosAdapter

        btnAdicionar.setOnClickListener {
            //val intent = Intent(this, CadastroActivity::class.java)
            //startActivity(intent)

            startActivity<CadastroActivity>()
        }

        listViewProdutos.setOnItemLongClickListener { adapterView: AdapterView<*>, view, position: Int, id: Long ->

            //buscando o item clicado
            val item = produtosAdapter.getItem(position)
            //removendo o item
            produtosAdapter.remove(item)

            deletarProduto(item!!.id)
            toast("item deletado com sucesso")
            true

        }
    }


    override fun onResume() {
        super.onResume()

        val adapter = listViewProdutos.adapter as ProdutoAdapter
        database.use {

            select("produtos").exec {
                //criando o parser que montará o objeto produto
                val parser = rowParser { id: Int,
                                         nome: String,
                                         quantidade: Int,
                                         valor: Double,
                                         foto: ByteArray? ->
                    //Colunas do banco de dados
                    //Montagem do objeto Produto com as colunas do banco
                    Produto(id, nome, quantidade, valor, foto?.toBitmap())
                }

                //criando a lista de produtos com dados do banco
                var listaProdutos = parseList(parser)

                //limpado os dados da lista e carregando as novas informações
                adapter.clear()
                adapter.addAll(listaProdutos)

                //efetuando a multiplicado e soma da quantidade e valor
                var soma = 0.0
                //val soma: Double = listaProdutos.sumByDouble { it.valor * it.quantidade }
                for (item in listaProdutos) {
                    soma += item.valor * item.quantidade
                }
                //formando em formato moeda
                val f = NumberFormat.getCurrencyInstance(Locale("pt", "br"))
                txtTotal.text = "TOTAL: ${f.format(soma)}"
            }
        }
    }
fun deletarProduto(idProduto: Int){
    database.use {
        delete("Produtos", "id = {id}", "id" to idProduto)
        }
    }

}