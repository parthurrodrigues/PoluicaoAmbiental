package com.example.poluicaoambiental

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class UsuariosActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var usuariosAdapter: UsuariosAdapter
    private lateinit var database: FirebaseDatabase
    private lateinit var usuariosRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuarios)

        recyclerView = findViewById(R.id.recyclerViewUsuarios)
        recyclerView.layoutManager = LinearLayoutManager(this)
        usuariosAdapter = UsuariosAdapter()
        recyclerView.adapter = usuariosAdapter

        database = FirebaseDatabase.getInstance()
        usuariosRef = database.getReference("usuarios")

        usuariosRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val usuariosList: MutableList<Usuario> = mutableListOf()
                for (snapshot in dataSnapshot.children) {
                    val usuario = snapshot.getValue(Usuario::class.java)
                    if (usuario != null) {
                        usuariosList.add(usuario)
                    }
                }
                usuariosAdapter.setUsuarios(usuariosList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        usuariosAdapter.setOnItemClickListener(object : UsuariosAdapter.OnItemClickListener {
            override fun onItemClick(usuario: Usuario) {
                val intent = Intent(this@UsuariosActivity, DetalhesUsuario::class.java)
                intent.putExtra("name", usuario.nome)
                intent.putExtra("email", usuario.email)
                intent.putExtra("collaboration", usuario.opiniao)
                startActivity(intent)
            }
        })
    }
}
