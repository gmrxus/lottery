package com.zzrh.lottery.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.zzrh.lottery.R;

public class QrCodeDialog extends DialogFragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    View view = inflater.inflate(R.layout.qrcode_dialog, null);
//    builder.setView(view)


    return view;
  }


}
