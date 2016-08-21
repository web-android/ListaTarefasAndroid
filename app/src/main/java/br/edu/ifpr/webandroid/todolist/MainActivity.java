package br.edu.ifpr.webandroid.todolist;

import android.content.DialogInterface;
import android.graphics.Paint;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.UUID;

import br.edu.ifpr.webandroid.todolist.model.Tarefa;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private ListView listaTarefasListView;
    private TarefaAdapter adaptadorTarefas;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfiguration);

        listaTarefasListView = (ListView) findViewById(R.id.list_todo);
        atualizaUI();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_task:
                Log.d(TAG, "Adicionar nova Tarefa");
                adicionarTarefa();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void adicionarTarefa() {
        final EditText textoEditarTarefa = new EditText(this);
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Adicionar Nova Tarefa")
                .setMessage("Nova Tarefa:")
                .setView(textoEditarTarefa)
                .setPositiveButton("Criar", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which){
                        String descricao = String.valueOf(textoEditarTarefa.getText());
                        Log.d(TAG, "Tarefa a ser adicionada: " + descricao);
                        realm.beginTransaction();
                        Tarefa tarefa = new Tarefa();
                        Long proximoId =   realm.where(Tarefa.class).max("id").longValue() + 1;
                        tarefa.setId(proximoId);
                        tarefa.setDescricao(descricao);
                        tarefa.setFeita(false);
                        tarefa = realm.copyToRealm(tarefa);
                        realm.commitTransaction();
                        atualizaUI();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create();
        dialog.show();
    }

    private void atualizaUI(){
        RealmResults<Tarefa> listaTarefas = realm.where(Tarefa.class).findAll();

        if(adaptadorTarefas == null){
            adaptadorTarefas = new TarefaAdapter(this);
            adaptadorTarefas.setData(listaTarefas);
            listaTarefasListView.setAdapter(adaptadorTarefas);
            adaptadorTarefas.notifyDataSetChanged();
        }
        else{
            adaptadorTarefas.setData(listaTarefas);
            adaptadorTarefas.notifyDataSetChanged();
        }


    }

    public void atualizarTarefa(TextView descricaoTarefa, Tarefa tarefaModificada) {
        final Tarefa tarefa = realm.where(Tarefa.class).equalTo("id", tarefaModificada.getId()).findFirst();

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                tarefa.setFeita(! tarefa.isFeita());
            }
        });


        Toast.makeText(this, "A tarefa " + tarefa.getDescricao() + " foi atualizada", Toast.LENGTH_SHORT).show();
        atualizaUI();
    }
}
