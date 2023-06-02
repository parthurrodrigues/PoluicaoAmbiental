package com.example.poluicaoambiental

class Usuario {
    var nome: String = ""
    var email: String = ""
    var usuario: String = ""
    var senha: String = ""
    var opiniao: String = ""

    constructor() {

    }

    constructor(nome: String, email: String, usuario: String, senha: String, opiniao: String) {
        this.nome = nome
        this.email = email
        this.usuario = usuario
        this.senha = senha
        this.opiniao = opiniao
    }
}
