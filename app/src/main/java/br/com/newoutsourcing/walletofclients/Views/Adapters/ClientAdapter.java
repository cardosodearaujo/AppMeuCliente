package br.com.newoutsourcing.walletofclients.Views.Adapters;

import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import br.com.newoutsourcing.walletofclients.App.FunctionsApp;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Activitys.RegisterClientActivity;
import br.com.newoutsourcing.walletofclients.Views.Adapters.ViewHolder.ClientViewHolder;
import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_CLIENT;

public class ClientAdapter extends RecyclerView.Adapter<ClientViewHolder>{

    private List<Client> clientList = new ArrayList<>();
    private List<Client> clientListRepository = new ArrayList<>();

    public ClientAdapter(List<Client> clientList){
        this.clientList.addAll(clientList);
        this.clientListRepository.addAll(clientList);
    }

    @Override
    public ClientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ClientViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_client, parent, false));
    }

    @Override
    public void onBindViewHolder(final ClientViewHolder viewHolder, final int position) {
        if (this.clientListRepository.get(position).getImage() != null && !this.clientListRepository.get(position).getImage().equals("")){
            Picasso.get()
                    .load(Uri.fromFile(new File(this.clientListRepository.get(position).getImage())))
                    .error(R.mipmap.ic_client_circle)
                    .into(viewHolder.idPhotoProfile);
            viewHolder.idPhotoProfile.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }

        if(this.clientListRepository.get(position).getType() == 1){
            viewHolder.idTxwName.setText(this.clientListRepository.get(position).getPhysicalPerson().getName());
            viewHolder.idTxwCPF_CNPJ.setText(this.clientListRepository.get(position).getPhysicalPerson().getCPF());
            viewHolder.idTxwTipo.setText("PF");
        }else{
            viewHolder.idTxwName.setText(this.clientListRepository.get(position).getLegalPerson().getSocialName());
            viewHolder.idTxwCPF_CNPJ.setText(this.clientListRepository.get(position).getLegalPerson().getCNPJ());
            viewHolder.idTxwTipo.setText("PJ");
        }

        viewHolder.idBtnEdit.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        if (clientListRepository.get(position).getType() == 1){
                            bundle.putString("TipoCadastro","F");
                        }else{
                            bundle.putString("TipoCadastro","J");
                        }
                        bundle.putSerializable("Client",clientListRepository.get(position));
                        FunctionsApp.startActivity(v.getContext(), RegisterClientActivity.class,bundle);
                    }
                }
        );

        viewHolder.idBtnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                        builder.setMessage("Tem certeza que deseja excluir?")
                                .setCancelable(false)
                                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, final int id) {
                                        TB_CLIENT.Delete(clientListRepository.get(position));
                                        removeItem(position);
                                        FunctionsApp.showSnackBarLong(v,"Cliente excluido com sucesso!");
                                        dialog.cancel();
                                    }
                                })
                                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                                    public void onClick(final DialogInterface dialog, final int id) {
                                        dialog.cancel();
                                    }
                                });
                        final AlertDialog alert = builder.create();
                        alert.show();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return this.clientList != null ? this.clientList.size() : 0;
    }

    public void removeItem(int position){
        this.clientList.remove(clientListRepository.get(position));
        notifyItemRemoved(position);
    }
}
