package com.zhyen.common;

import android.content.Context;
import android.util.Log;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.OnBackPressedDispatcher;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

/**
 * author : fengxing
 * date : 2022/5/23 下午3:03
 * description :
 */
public class BaseFragment extends Fragment {

    protected static final String TAG = BaseFragment.class.getSimpleName();
    protected OnBackPressedDispatcher onBackPressedDispatcher;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        onBackPressedDispatcher = requireActivity().getOnBackPressedDispatcher();
    }
}
