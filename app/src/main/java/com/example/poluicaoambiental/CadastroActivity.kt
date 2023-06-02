package com.example.poluicaoambiental

import androidx.appcompat.app.AppCompatActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

class CadastroActivity : AppCompatActivity() {
    private lateinit var database: FirebaseDatabase
    private lateinit var ref: DatabaseReference
    private lateinit var textViewLogin: TextView

    private fun exibirMensagem(resourceId: Int) {
        Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        textViewLogin = findViewById(R.id.textViewCadastro)
        database = FirebaseDatabase.getInstance()
        ref = database.getReference("usuarios")

        val buttonCadastrar = findViewById<Button>(R.id.buttonCadastrar)
        buttonCadastrar.setOnClickListener {
            cadastrar()
        }
        textViewLogin.setOnClickListener {
            redirecionarParaLoginActivity()
        }
    }

    private fun cadastrar() {
        val editTextNome = findViewById<EditText>(R.id.editTextNome)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextUsuario = findViewById<EditText>(R.id.editTextUsuario)
        val editTextSenha = findViewById<EditText>(R.id.editTextSenha)
        val editTextOpiniao = findViewById<EditText>(R.id.editTextOpiniao)

        val nome = editTextNome.text.toString()
        val email = editTextEmail.text.toString()
        val usuario = editTextUsuario.text.toString()
        val senha = editTextSenha.text.toString()
        val opiniao = editTextOpiniao.text.toString()

        val novoUsuarioRef = ref.child(email)

        val usuarioQuery = ref.orderByChild("usuario").equalTo(usuario)
        usuarioQuery.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    exibirMensagem(R.string.jaemuso)
                } else if (email.isEmpty() || usuario.isEmpty() || senha.isEmpty()) {
                    exibirMensagem(R.string.campoobrigatorio)
                } else {
                    exibirMensagem(R.string.cadastrorealizado)

                    val novoUsuario = Usuario(nome, email, usuario, senha, opiniao)
                    novoUsuarioRef.setValue(novoUsuario)

                    editTextNome.text.clear()
                    editTextEmail.text.clear()
                    editTextUsuario.text.clear()
                    editTextSenha.text.clear()
                    editTextOpiniao.text.clear()

                    val intent = Intent(this@CadastroActivity, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                exibirMensagem("Erro ao verificar o cadastro: " + databaseError.message)
            }
        })
    }

    private fun redirecionarParaLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }

    private fun exibirMensagem(mensagem: String) {
        Toast.makeText(this, mensagem, Toast.LENGTH_SHORT).show()
    }
}
