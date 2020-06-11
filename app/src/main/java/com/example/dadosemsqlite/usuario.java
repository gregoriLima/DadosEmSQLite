package com.example.dadosemsqlite;

import androidx.annotation.NonNull;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class usuario {

    @org.greenrobot.greendao.annotation.Id (autoincrement = true)
    private long Id;
    private String nome;
    private String faixaIdade;
    

    @Generated(hash = 983843025)
    public usuario(long Id, String nome, String faixaIdade) {
        this.Id = Id;
        this.nome = nome;
        this.faixaIdade = faixaIdade;
    }

    @Generated(hash = 126709763)
    public usuario() {
    }

    public long getId() {
        return this.Id;
    }

    public void setId(long Id) {
        this.Id = Id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @NonNull
    @Override
    public String toString() {
        return "Usuário: " + this.nome + ", " + faixaIdade;
    }

    public String getFaixaIdade() {
        return this.faixaIdade;
    }

    public void setFaixaIdade(String faixaIdade) {
        this.faixaIdade = faixaIdade;
    }
}
