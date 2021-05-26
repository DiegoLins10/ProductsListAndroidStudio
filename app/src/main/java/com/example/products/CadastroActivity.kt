package com.example.products

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import kotlinx.android.synthetic.main.activity_cadastro.*
import kotlinx.android.synthetic.main.list_view_item.*

import android.graphics.Bitmap
import android.graphics.BitmapFactory

class CadastroActivity : AppCompatActivity() {

    val COD_IMAGE = 101
    var imageBitMap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        btnInserir.setOnClickListener {
            val produto = txtProduto.text.toString()
            val qtd = txtQtde.text.toString()
            val valor = txtValor.text.toString()
            if (produto.isNotEmpty() && qtd.isNotEmpty() && valor.isNotEmpty()) {
                val prod = Produto(produto, qtd.toInt(), valor.toDouble(), imageBitMap)
                produtosGlobal.add(prod)
                txtProduto.text.clear()
                txtQtde.text.clear()
                txtValor.text.clear()
            } else {
                txtProduto.error = if (txtProduto.text.isEmpty()) "Preencha o nome do Produto"
                else null

                txtQtde.error = if (txtQtde.text.isEmpty()) "Preencha a quantidade"
                else null

                txtValor.error = if (txtValor.text.isEmpty()) "Preencha o valor"
                else null
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == COD_IMAGE && resultCode == Activity.RESULT_OK) {

            if (data != null) {
                //vamos acessar a imagem escolhida através da variável "data"
                //lendo a URI com a imagem

                val inputStream = contentResolver.openInputStream(data.getData()!!);


                //transformando o resultado em bitmap
                imageBitMap = BitmapFactory.decodeStream(inputStream)

                //exibir a imagem no aplicativo
                imgFotoProduto.setImageBitmap(imageBitMap)
            }
        }
    }
}