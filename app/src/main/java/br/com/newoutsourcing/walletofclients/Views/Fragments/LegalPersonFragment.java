package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.widget.EditText;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.FragmentBase;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import butterknife.BindView;

import static br.com.newoutsourcing.walletofclients.Repository.Database.Configurations.SessionDatabase.TB_LEGAL_PERSON;

public class LegalPersonFragment extends FragmentBase {

    protected @BindView(R.id.idEdtClientPJSocialName) EditText idEdtClientPJSocialName;
    protected @BindView(R.id.idEdtClientPJFantasyName) EditText idEdtClientPJFantasyName;
    protected @BindView(R.id.idEdtClientPJCNPJ) EditText idEdtClientPJCNPJ;
    protected @BindView(R.id.idEdtClientPJIE) EditText idEdtClientPJIE;
    protected @BindView(R.id.idEdtClientPJIM) EditText idEdtClientPJIM;
    private Toolbar idToolbar;

    private FragmentsCallback imageCallback;
    private ImageFragment imageFragment;
    private String clientId;

    public LegalPersonFragment() {
        super(R.layout.fragment_legal_person);
    }

    public static LegalPersonFragment newInstance() {
        return new LegalPersonFragment();
    }

    @Override
    protected void onConfiguration(){
        this.clientId = null;
        this.idToolbar = this.getActivity().findViewById(R.id.idToolbar);
        this.idToolbar.setSubtitle("Pessoa juridica");
        this.idEdtClientPJCNPJ.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_CNPJ, this.idEdtClientPJCNPJ));
        this.onCreateFragment(false);
        this.onLoad((Client)getArguments().getSerializable("Client"));
    }

    private void onCreateFragment(Boolean createClean){
        this.imageFragment = ImageFragment.newInstance();
        this.imageCallback = imageFragment;
        if (createClean){
            FunctionsTools.startFragment(this.imageFragment,R.id.idFrlImg,this.getFragmentManager(),null);
        }else{
            FunctionsTools.startFragment(this.imageFragment,R.id.idFrlImg,this.getFragmentManager(),this.getArguments());
        }
    }

    @Override
    public boolean onValidate(){
        boolean save = true;
        if (this.idEdtClientPJSocialName.getText().toString().trim().isEmpty()){
            this.idEdtClientPJSocialName.setError("Informe a razão social.");
            save = false;
        }else{
            this.idEdtClientPJSocialName.setError(null);
        }

        if (this.idEdtClientPJFantasyName.getText().toString().trim().isEmpty()){
            this.idEdtClientPJFantasyName.setError("Informe o nome fantásia.");
            save = false;
        }else{
            this.idEdtClientPJFantasyName.setError(null);
        }

        if (this.idEdtClientPJCNPJ.getText().toString().trim().isEmpty()){
            this.idEdtClientPJCNPJ.setError("Informe o CNPJ.");
            save = false;
        }else{
            this.idEdtClientPJCNPJ.setError(null);
        }

        if (!this.idEdtClientPJCNPJ.getText().toString().trim().isEmpty()){
            if (FunctionsTools.formatCNPJ(this.idEdtClientPJCNPJ.getText().toString()).length() != 14){
                this.idEdtClientPJCNPJ.setError("O CNPJ deve conter 14 digitos.");
                this.idEdtClientPJCNPJ.requestFocus();
                save = false;
            }else{
                this.idEdtClientPJCNPJ.setError(null);
            }
        }

        if (!this.idEdtClientPJCNPJ.getText().toString().trim().isEmpty()){
            if (FunctionsTools.formatCNPJ(this.idEdtClientPJCNPJ.getText().toString()).length() == 14){
                if (TB_LEGAL_PERSON.CheckCNPJ(this.idEdtClientPJCNPJ.getText().toString(),this.clientId) > 0) {
                    this.idEdtClientPJCNPJ.setError("O CNPJ está em uso em outro cadastro!");
                    this.idEdtClientPJCNPJ.requestFocus();
                    save = false;
                }else{
                    this.idEdtClientPJCNPJ.setError(null);
                }
            }
        }


        if (this.idEdtClientPJIE.getText().toString().trim().isEmpty()){
            this.idEdtClientPJIE.setError("Informe o inscrição estadual.");
            save = false;
        }else{
            this.idEdtClientPJIE.setError(null);
        }

        return save;
    }

    @Override
    public Client onSave(Client client) {
        try{
            if (this.onValidate()) {
                client = this.imageCallback.onSave(client);
                client.getLegalPerson().setSocialName(this.idEdtClientPJSocialName.getText().toString());
                client.getLegalPerson().setFantasyName(this.idEdtClientPJFantasyName.getText().toString());
                client.getLegalPerson().setCNPJ(this.idEdtClientPJCNPJ.getText().toString());
                client.getLegalPerson().setIE(this.idEdtClientPJIE.getText().toString());
                client.getLegalPerson().setIM(this.idEdtClientPJIM.getText().toString());
                client.getLegalPerson().setSuccess(true);
            }else{
                client.getLegalPerson().setSuccess(false);
            }
            return client;
        }catch (Exception ex){
            throw ex;
        }
    }

    @Override
    public void onLoad(Client client){
        if (client != null){
            this.clientId = String.valueOf(client.getClientId());
            this.idEdtClientPJSocialName.setText(client.getLegalPerson().getSocialName());
            this.idEdtClientPJFantasyName.setText(client.getLegalPerson().getFantasyName());
            this.idEdtClientPJCNPJ.setText(client.getLegalPerson().getCNPJ());
            this.idEdtClientPJIE.setText(client.getLegalPerson().getIE());
            this.idEdtClientPJIM.setText(client.getLegalPerson().getIM());
        }
    }

    @Override
    public void onClear() {
        this.clientId = null;
        this.idEdtClientPJSocialName.setText("");
        this.idEdtClientPJFantasyName.setText("");
        this.idEdtClientPJCNPJ.setText("");
        this.idEdtClientPJIE.setText("");
        this.idEdtClientPJIM.setText("");
        this.setArguments(new Bundle());
        this.onCreateFragment(true);
    }
}
