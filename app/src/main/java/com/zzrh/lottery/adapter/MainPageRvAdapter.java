package com.zzrh.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.zzrh.lottery.R;

public class MainPageRvAdapter extends RecyclerView.Adapter {
  private Context mContext;

  public MainPageRvAdapter(Context mContext) {
    this.mContext = mContext;
  }

  @NonNull
  @Override
  public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_vp, null);
    //自定义view的宽度，控制一屏显示个数
    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    int width = mContext.getResources().getDisplayMetrics().widthPixels;
    params.width = width / 5;
    view.setLayoutParams(params);
    return new RecyclerHolder(view);

  }

  @Override
  public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

  }

  @Override
  public int getItemCount() {
    return 5;
  }

  private class RecyclerHolder extends RecyclerView.ViewHolder {
    private View view;

    public RecyclerHolder(View itemView) {
      super(itemView);
      view = itemView;
    }

    public View getView() {
      return view;
    }
  }


}


