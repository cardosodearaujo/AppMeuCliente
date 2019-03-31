package br.com.newoutsourcing.walletofclients.Views.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ViewHolder.ClientViewHolder;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder>{

    private List<Client> clientList;

    public ClientAdapter(ArrayList clientList){
        this.clientList = clientList;
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClientViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_client, parent, false));
    }

    @Override
    public void onBindViewHolder(ClientViewHolder viewHolder, int position) {

        //viewHolder.idTxwName.setText(this.clientList.get(position).get());
        //viewHolder.idTxwCPF_CNPJ.setText(this.clientList.get(position).getCPF_CNPJ());
        //viewHolder.idTxwTipo.setText(this.clientList.get(position).getTipo());
    }

    @Override
    public int getItemCount() {
        return clientList != null ? clientList.size() : 0;
    }

    public void insertItem(Client client){
        this.clientList.add(client);
        this.notifyItemInserted(clientList.size());
    }

    private void updateItem(int position,Client client) {
        this.clientList.set(position,client);
        notifyItemChanged(position);
    }

    public void removeItem(int position){
        this.clientList.remove(position);
        notifyItemRemoved(position);
    }
}
