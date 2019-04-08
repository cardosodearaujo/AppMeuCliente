package br.com.newoutsourcing.walletofclients.Views.Callbacks;

import br.com.newoutsourcing.walletofclients.Objects.Client;

public interface FragmentsCallback {
    boolean onValidate();
    boolean onSave(Client client);
    void onClear();
}
