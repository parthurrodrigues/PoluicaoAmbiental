package com.example.poluicaoambiental

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UsuariosAdapter : RecyclerView.Adapter<UsuariosAdapter.UsuarioViewHolder>() {
    private var usuariosList: MutableList<Usuario> = mutableListOf()
    private var onItemClickListener: OnItemClickListener? = null


    interface OnItemClickListener {
        fun onItemClick(usuario: Usuario)
    }


    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.onItemClickListener = listener
    }


    fun setUsuarios(usuarios: MutableList<Usuario>) {
        usuariosList = usuarios
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsuarioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_usuario, parent, false)
        return UsuarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsuarioViewHolder, position: Int) {
        val usuario = usuariosList[position]
        holder.bind(usuario)
        holder.itemView.setOnClickListener {
            onItemClickListener?.onItemClick(usuario)
        }
    }

    override fun getItemCount(): Int {
        return usuariosList.size
    }

    class UsuarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewNome: TextView = itemView.findViewById(R.id.textViewNome)

        fun bind(usuario: Usuario) {
            textViewNome.text = usuario.nome
        }
    }
}
