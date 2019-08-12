package br.com.newoutsourcing.walletofclients.Views.Adapters.Task;

import android.view.View;
import android.widget.TextView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.newoutsourcing.walletofclients.R;

public class TaskViewHolder extends RecyclerView.ViewHolder  {
    public TextView idTxwTitle;
    public TextView idTxwDate;
    public TextView idTxwHour;
    public CardView idCardView;

    public TaskViewHolder(View view){
        super(view);
        this.idTxwTitle = view.findViewById(R.id.idTxwTitle);
        this.idTxwDate = view.findViewById(R.id.idTxwDate);
        this.idTxwHour = view.findViewById(R.id.idTxwHour);
        this.idCardView = view.findViewById(R.id.idCardView);
    }
}
