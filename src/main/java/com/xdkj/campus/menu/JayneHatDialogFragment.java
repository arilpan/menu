package com.xdkj.campus.menu;

/**
 * Created by aril_pan@qq.com on 16-8-17.
 */
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.avast.android.dialogs.core.BaseDialogFragment;
import com.avast.android.dialogs.fragment.SimpleDialogFragment;
import com.avast.android.dialogs.iface.IPositiveButtonDialogListener;

/**
 * Sample implementation of custom dialog by extending {@link SimpleDialogFragment}.
 *
 * @author David Vávra (david@inmite.eu)
 */
public class JayneHatDialogFragment extends SimpleDialogFragment {

    public static String TAG = "jayne";

    public static void show(FragmentActivity activity) {
        new JayneHatDialogFragment().show(activity.getSupportFragmentManager(), TAG);
    }

    @Override
    public int getTheme() {
        return R.style.JayneHatDialogTheme;
    }

    @Override
    public BaseDialogFragment.Builder build(BaseDialogFragment.Builder builder) {
        builder.setTitle("Jayne's hat");
        builder.setMessage("A man walks down the street in that hat, people know he's not afraid of anything.");
        builder.setView(LayoutInflater.from(getActivity()).inflate(R.layout.view_jayne_hat, null));
        builder.setPositiveButton("I want one", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (IPositiveButtonDialogListener listener : getPositiveButtonDialogListeners()) {
                    listener.onPositiveButtonClicked(mRequestCode);
                }
                dismiss();
            }
        });
        return builder;
    }
}