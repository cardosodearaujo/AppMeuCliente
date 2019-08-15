package br.com.newoutsourcing.walletofclients.Views.Bases;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import br.com.newoutsourcing.walletofclients.Views.Callbacks.FragmentsCallback;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class FragmentBase extends Fragment implements FragmentsCallback {

    private int viewId;
    private Unbinder unbind;

    public FragmentBase(int viewId){
        this.viewId = viewId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        View view = inflater.inflate(viewId, container, false);
        this.unbind = ButterKnife.bind(this,view);
        this.onConfiguration();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.unbind.unbind();
    }

    protected abstract void onConfiguration();


}
