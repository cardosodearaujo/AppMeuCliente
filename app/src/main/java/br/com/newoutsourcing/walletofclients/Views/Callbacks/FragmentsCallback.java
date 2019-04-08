package br.com.newoutsourcing.walletofclients.Views.Callbacks;

import br.com.newoutsourcing.walletofclients.Objects.Client;

public interface FragmentsCallback {
    boolean onValidate();
    Client onSave(Client client);
    void onClear();
}
