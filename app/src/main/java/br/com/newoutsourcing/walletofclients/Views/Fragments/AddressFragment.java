package br.com.newoutsourcing.walletofclients.Views.Fragments;

import android.widget.EditText;
import android.widget.Spinner;
import br.com.jansenfelipe.androidmask.MaskEditTextChangedListener;
import br.com.newoutsourcing.walletofclients.Tools.FunctionsTools;
import br.com.newoutsourcing.walletofclients.Objects.Client;
import br.com.newoutsourcing.walletofclients.R;
import br.com.newoutsourcing.walletofclients.Views.Bases.FragmentBase;
import butterknife.BindView;

public class AddressFragment extends FragmentBase {

    protected @BindView(R.id.idEdtClientAddressCEP) EditText idEdtClientAddressCEP;
    protected @BindView(R.id.idEdtClientAddressStreet) EditText idEdtClientAddressStreet;
    protected @BindView(R.id.idEdtClientAddressNumber) EditText idEdtClientAddressNumber;
    protected @BindView(R.id.idEdtClientAddressNeighborhood) EditText idEdtClientAddressNeighborhood;
    protected @BindView(R.id.idEdtClientAddressCity) EditText idEdtClientAddressCity;
    protected @BindView(R.id.idSpnClientAddressState) Spinner idSpnClientAddressState;
    protected @BindView(R.id.idEdtClientAddressCounty) EditText idEdtClientAddressCounty;

    public AddressFragment() {
        super(R.layout.fragment_address);
    }

    public static AddressFragment newInstance() {
        return new AddressFragment();
    }

    @Override
    protected void onConfiguration(){
        this.onLoad((Client) getArguments().getSerializable("Client"));
        this.idEdtClientAddressCEP.addTextChangedListener(new MaskEditTextChangedListener(FunctionsTools.MASCARA_CEP, this.idEdtClientAddressCEP));
    }

    @Override
    public boolean onValidate(){
        boolean save = true;

        if (!this.idEdtClientAddressCEP.getText().toString().isEmpty()){
            if (FunctionsTools.formatCEP(this.idEdtClientAddressCEP.getText().toString()).length()!=8){
                this.idEdtClientAddressCEP.setError("O CEP deve conter ");
                save = false;
            }else{
                this.idEdtClientAddressCEP.setError(null);
            }
        }

        return save;
    }

    @Override
    public Client onSave(Client client) {
        try{
            if (this.onValidate()){
                client.getAddress().setCEP(this.idEdtClientAddressCEP.getText().toString());
                client.getAddress().setStreet(this.idEdtClientAddressStreet.getText().toString());
                if (!this.idEdtClientAddressNumber.getText().toString().isEmpty()) client.getAddress().setNumber(Integer.parseInt(this.idEdtClientAddressNumber.getText().toString()));
                client.getAddress().setNeighborhood(this.idEdtClientAddressNeighborhood.getText().toString());
                client.getAddress().setCity(this.idEdtClientAddressCity.getText().toString());
                if (!this.idSpnClientAddressState.getSelectedItem().toString().isEmpty()) client.getAddress().setState(this.idSpnClientAddressState.getSelectedItem().toString().substring(0,2));
                client.getAddress().setCountry(this.idEdtClientAddressCounty.getText().toString());
                client.getAddress().setSuccess(true);
            }else{
                client.getAddress().setSuccess(false);
            }
            return client;
        }catch (Exception ex) {
            throw ex;
        }
    }

    @Override
    public void onLoad(Client client){
        if (client != null){
            this.idEdtClientAddressCEP.setText(client.getAddress().getCEP());
            this.idEdtClientAddressStreet.setText(client.getAddress().getStreet());
            this.idEdtClientAddressNumber.setText(String.valueOf(client.getAddress().getNumber()));
            this.idEdtClientAddressNeighborhood.setText(client.getAddress().getNeighborhood());
            this.idEdtClientAddressCity.setText(client.getAddress().getCity());
            this.idSpnClientAddressState.setSelection(FunctionsTools.getState(client.getAddress().getState()));
            this.idEdtClientAddressCounty.setText(client.getAddress().getCountry());
        }
    }

    @Override
    public void onClear() {
        this.idEdtClientAddressCEP.setText("");
        this.idEdtClientAddressStreet.setText("");
        this.idEdtClientAddressNumber.setText("");
        this.idEdtClientAddressNeighborhood.setText("");
        this.idEdtClientAddressCity.setText("");
        this.idSpnClientAddressState.setSelection(0);
        this.idEdtClientAddressCounty.setText("Brasil");
    }
}
