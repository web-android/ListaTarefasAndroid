package br.edu.ifpr.webandroid.todolist.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by everaldo on 21/08/16.
 */
public class Tarefa extends RealmObject {

    @PrimaryKey
    private long id;

    private String descricao;

    private boolean feita;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFeita() {
        return feita;
    }

    public void setFeita(boolean feita) {
        this.feita = feita;
    }
}
