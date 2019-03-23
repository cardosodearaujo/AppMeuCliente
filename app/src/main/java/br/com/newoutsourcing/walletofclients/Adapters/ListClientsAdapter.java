package br.com.newoutsourcing.walletofclients.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import br.com.newoutsourcing.walletofclients.App.RecyclerViewTouchListenerApp;
import br.com.newoutsourcing.walletofclients.R;
import br.com.unclephill.meuddd.App.RecyclerViewTouchListenerApp;
import br.com.unclephill.meuddd.Object.DDDCityObject;
import br.com.unclephill.meuddd.R;

public class ListClientsAdapter extends RecyclerView.Adapter<ListClientsAdapter.MyViewHolder>{

    private Context context;
    private List<DDDCityObject> lstDDDCidades;
    private LayoutInflater layoutInflater;
    private float scale;
    private int width;
    private int height;
    private RecyclerViewTouchListenerApp.RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public ListClientsAdapter(Context context, List<DDDCityObject> lstDDDCidades){
        this.context = context;
        this.lstDDDCidades = lstDDDCidades;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.scale = this.context.getResources().getDisplayMetrics().density;
        this.width = this.context.getResources().getDisplayMetrics().widthPixels - (int)(14 * this.scale + 0.5f);
        this.height = (this.width / 16) * 9;
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewTouchListenerApp.RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHack = r;
    }

    public void addListItem(DDDCityObject User){
        this.lstDDDCidades.add(User);
        notifyItemInserted(lstDDDCidades.size());
    }

    public void removeListItem(int position){
        this.lstDDDCidades.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(this.layoutInflater.inflate(R.layout.card_view_ddd_cidades, parent,false));
    }

    @Override
    public int getItemCount() {
        return lstDDDCidades.size();
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final DDDCityObject DDDCidades = this.lstDDDCidades.get(position);
        if (holder.itemView.getTag() == null){
            holder.itemView.setTag(DDDCidades);

            holder.idTxwDDD.setText(DDDCidades.getDdd());
            holder.idTxwUF.setText(DDDCidades.getEstado());
            holder.idTxwCidade.setText(DDDCidades.getCidade());
            holder.idTxwOperadora.setText(DDDCidades.getOperadora());
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView idTxwDDD;
        public TextView idTxwUF;
        public TextView idTxwCidade;
        public TextView idTxwOperadora;
        public RecyclerView idRwDDCidades;

        public MyViewHolder(View view){
            super(view);
            idTxwDDD = view.findViewById(R.id.idTxwDDD);
            idTxwUF = view.findViewById(R.id.idTxwUF);
            idTxwCidade = view.findViewById(R.id.idTxwCidade);
            idTxwOperadora = view.findViewById(R.id.idTxwOperadora);
            idRwDDCidades = view.findViewById(R.id.idRwDDDCidades);
        }

        @Override
        public void onClick(View view) {
            if(recyclerViewOnClickListenerHack != null){
                recyclerViewOnClickListenerHack.onClickListener(view, getPosition());
            }
        }
    }
}
