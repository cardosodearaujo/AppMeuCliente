package br.com.newoutsourcing.walletofclients.Views.Adapters.Task;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import br.com.newoutsourcing.walletofclients.Objects.Tasks;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Views.Activitys.NewTaskActivity;

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
        holder.idTxwTitle.setText(this.list.get(position).getTitle());
        holder.idTxwDate.setText(this.list.get(position).getDate());
        if (list.get(position).getAllDay() == 0){
            holder.idTxwHour.setVisibility(View.VISIBLE);
            holder.idTxwHour.setText(this.list.get(position).getHour());
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
