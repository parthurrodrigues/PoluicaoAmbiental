package com.example.poluicaoambiental

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class DetalhesUsuario : AppCompatActivity() {
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var editTextOpiniao: TextView
    private lateinit var database: DatabaseReference

    private fun exibirMensagem(resourceId: Int) {
        Toast.makeText(this, getString(resourceId), Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes_usuario)

        nameTextView = findViewById(R.id.editTextNome)
        emailTextView = findViewById(R.id.editTextEmail)
        editTextOpiniao = findViewById(R.id.editTextOpiniao)

        val intent: Intent = intent
        val email: String? = intent.getStringExtra("email")


        database = FirebaseDatabase.getInstance().reference


        recuperarUsuarioDoBancoDeDados(email)

        val inicioButton: Button = findViewById(R.id.inicioButton)
        inicioButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@DetalhesUsuario, MainActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun recuperarUsuarioDoBancoDeDados(email: String?) {
        if (email == null) {
            return
        }

        val usuarioRef = database.child("usuarios").child(email.replace('.', ','))

        usuarioRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuario = dataSnapshot.getValue(Usuario::class.java)

                if (usuario != null) {
                    nameTextView.text = getString(R.string.nome) + " " + usuario.nome
                    emailTextView.text = getString(R.string.emaillista) + " " + usuario.email
                    editTextOpiniao.text = getString(R.string.opiniaolista) + " " + usuario.opiniao
                } else {
                    exibirMensagem(R.string.nome)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                exibirMensagem(R.string.erro)
            }
        })
    }
}
