package br.com.newoutsourcing.walletofclients.Views.Adapters.Task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Views.Activitys.NewTaskActivity;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;

public class TaskAdapter extends RecyclerView.Adapter<TaskViewHolder> {
    private List<Tasks> list = new ArrayList<>();

    public TaskAdapter(List<Tasks> list){
        this.list.addAll(list);
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TaskViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Tasks tasks = this.list.get(position);

        holder.idTxwTitle.setText(tasks.getTitle());

        if(tasks.getClienteId() > 0){
            List<Client> clientList = TB_CLIENT.Select(tasks.getClienteId());
            if (clientList != null && clientList.size() > 0 && clientList.get(0) != null && clientList.get(0).getClientId() > 0){
                Client client = clientList.get(0);
                if (client.getType() == 1){
                    holder.idTxwClient.setText(client.getPhysicalPerson().getName());
                }else{
                    holder.idTxwClient.setText(client.getLegalPerson().getSocialName());
                }
            }
        }

        holder.idTxwDate.setText(tasks.getDate());

        if (tasks.getAllDay() == 0){
            holder.idTxwHour.setVisibility(View.VISIBLE);
            holder.idTxwHour.setText(tasks.getHour());
        }else{
            holder.idTxwHour.setVisibility(View.INVISIBLE);
        }

        holder.idCardView.setOnClickListener(
                v -> onClickDetail(v,position)
        );
    }

    @Override
    public int getItemCount() {
        return this.list != null ? this.list.size() : 0;
    }

    private void onClickDetail(View v, int position){
        Bundle bundle = new Bundle();

        bundle.putSerializable("Tasks", list.get(position));
        FunctionsTools.startActivity(v.getContext(), NewTaskActivity.class,bundle);
    }
}
