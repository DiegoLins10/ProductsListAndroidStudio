package com.example.products

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_cadastro.*
import org.jetbrains.anko.toast
import org.jetbrains.anko.db.insert
import java.text.DecimalFormat


//import kotlinx.android.synthetic.main.activity_cadastro.*
///import kotlinx.android.synthetic.main.list_view_item.*


class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnInserir.setOnClickListener {
            val produto = txtProduto.text.toString()
            val qtde = txtQtde.text.toString()
            val valor = txtValor.text.toString()


            if (produto.isNotEmpty() && qtde.isNotEmpty() && valor.isNotEmpty()) {

                //produtosAdapter.add(produto)
                //apagar essas linhas CadastroActivity.kt
                //val prod = Produto(produto, qtde.toInt(), valor.toDouble())
                //produtosGlobal.add(prod)

                database.use {
                    val idProduto = insert(
                            "Produtos",
                            "nome" to produto,
                            "quantidade" to qtde,
                            "valor" to valor.toDouble(),
                            "foto" to imageBitMap?.toBYteArray()
                    )

                    if (idProduto != -1L) {
                        toast("Item inserido com sucesso")
                        txtProduto.text.clear()
                        txtQtde.text.clear()
                        txtValor.text.clear()
                    } else{
                        toast("Erro ao inserir no banco de dados")
                    }
                }


            } else {
                txtProduto.error =
                        if (txtProduto.text.isEmpty()) "Preencha o nome do Produto" else null
                txtQtde.error = if (txtQtde.text.isEmpty()) "Preencha a quantidade" else null
                txtValor.error = if (txtValor.text.isEmpty()) "Preecha o valor" else null
            }

        }

        imgFotoProduto.setOnClickListener {
            abrirGaleria()
        }
    }

    fun abrirGaleria() {
        //definindo a ação de conteudo
        val intent = Intent(Intent.ACTION_GET_CONTENT)

        //definindo filtro para imagens
        intent.type = "image/*"

        //inicializando a activity com resultado
        startActivityForResult(Intent.createChooser(intent, "Selecione uma imagem"), COD_IMAGE)

    }
}