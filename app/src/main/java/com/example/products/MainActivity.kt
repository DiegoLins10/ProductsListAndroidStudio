package com.example.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //criando o adaptador

        val produtoAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val listViewProdutos = findViewById<ListView>(R.id.listViewProdutos)
        val btnInserir = findViewById<Button>(R.id.btnInserir)
        val txtProduto = findViewById<TextView>(R.id.txtProduto)

        listViewProdutos.adapter = produtoAdapter

        btnInserir.setOnClickListener {
            val produto = txtProduto.text.toString()
            var numero = produtoAdapter.count + 1;
            if (produto.isNotEmpty()) {
                var concatenar = "$numero - $produto"
                produtoAdapter.add(concatenar)
                //numero+= 1;
                txtProduto.text = ""
            } else {
                txtProduto.error = "Coloque um produto"
            }
            listViewProdutos.setOnItemClickListener{adapterView: AdapterView<*>, view, position : Int, Id: Long->
                val item = produtoAdapter.getItem(position)
                produtoAdapter.remove(item)

            }
        }
    }
}