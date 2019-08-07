package br.com.newoutsourcing.walletofclients.Views.Callbacks;

import br.com.newoutsourcing.walletofclients.Objects.Client;

public interface FragmentsCallback {
    void onLoad(Client client);

    boolean onValidate();

    Client onSave(Client client);

    void onClear();
}
