package br.edu.ifpr.webandroid.todolist;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import br.edu.ifpr.webandroid.todolist.model.Tarefa;

/**
 * Created by everaldo on 21/08/16.
 */
public class TarefaAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private Context mContext;

    private List<Tarefa> tarefas = null;

    public TarefaAdapter(Context context){
        this.mContext = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setData(List<Tarefa> tarefas){
        this.tarefas = tarefas;
    }

    @Override
    public int getCount(){
        if(tarefas == null){
            return 0;
        }
        return tarefas.size();
    }

    @Override
    public Object getItem(int position){
        if(tarefas == null || tarefas.get(position) == null){
            return null;
        }
        return tarefas.get(position);
    }

    @Override
    public long getItemId(int i){
        return tarefas.get(i).getId();
    }

    @Override
    public View getView(int position, View currentView, final ViewGroup parent){
        if(currentView == null){
            currentView = inflater.inflate(R.layout.item_todo, parent, false);
        }

        final Tarefa tarefa = tarefas.get(position);

        if(tarefa != null){
            TextView descricao = (TextView) currentView.findViewById(R.id.task_title);
            descricao.setText(tarefa.getDescricao());
            if(tarefa.isFeita()){
                descricao.setPaintFlags(descricao.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            }
            else{
                descricao.setPaintFlags(descricao.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));

            }
            Button feita = (Button) currentView.findViewById(R.id.task_done);
            final TextView descricaoTarefa = descricao;
            feita.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((MainActivity)mContext).atualizarTarefa(descricaoTarefa, tarefa);
                }
            });
        }

        return currentView;
    }

}
