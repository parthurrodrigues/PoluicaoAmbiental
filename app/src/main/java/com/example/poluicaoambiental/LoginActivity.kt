package com.example.poluicaoambiental

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class LoginActivity : AppCompatActivity() {
    private lateinit var editTextUsuario: EditText
    private lateinit var editTextSenha: EditText
    private lateinit var buttonEntrar: Button
    private lateinit var textViewCadastro: TextView
    private lateinit var database: FirebaseDatabase
    private lateinit var usuariosRef: DatabaseReference
    private fun exibirMensagem(resourceId: Int) {
        Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        editTextUsuario = findViewById(R.id.editTextUsuario)
        editTextSenha = findViewById(R.id.editTextSenha)
        buttonEntrar = findViewById(R.id.buttonEntrar)
        textViewCadastro = findViewById(R.id.textViewCadastro)

        database = FirebaseDatabase.getInstance()
        usuariosRef = database.getReference("usuarios")

        buttonEntrar.setOnClickListener {
            val usuario = editTextUsuario.text.toString()
            val senha = editTextSenha.text.toString()
            realizarLogin(usuario, senha)
        }

        textViewCadastro.setOnClickListener {
            redirecionarParaCadastroActivity()
        }
    }

    private fun realizarLogin(usuario: String, senha: String) {
        usuariosRef.orderByChild("usuario").equalTo(usuario)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (snapshot in dataSnapshot.children) {
                        val usuarioSnapshot = snapshot.getValue(Usuario::class.java)
                        if (usuarioSnapshot != null && usuarioSnapshot.senha == senha) {
                            exibirMensagem(R.string.loginrealizado)
                            redirecionarParaUsuariosActivity()
                            return
                        }
                    }
                    exibirMensagem(R.string.falhalogin)
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    exibirMensagem(R.string.tentetarde)
                }
            })
    }

    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }

    private fun redirecionarParaCadastroActivity() {
        val intent = Intent(this, CadastroActivity::class.java)
        startActivity(intent)
    }

    private fun redirecionarParaUsuariosActivity() {
        val intent = Intent(this, UsuariosActivity::class.java)
        startActivity(intent)
    }
}
