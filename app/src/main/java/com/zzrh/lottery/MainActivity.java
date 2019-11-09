package com.zzrh.lottery;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.zzrh.lottery.adapter.MainPageRvAdapter;


import github.hellocsl.layoutmanager.gallery.GalleryLayoutManager;

public class MainActivity extends BaseActivity {
  private static final String TAG = "MainActivity";

  private RecyclerView mRv;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    setView();


  }

  private void setView() {
    mRv = findViewById(R.id.rv);
    MainPageRvAdapter mainPageRvAdapter = new MainPageRvAdapter(this);
    GalleryLayoutManager layoutManager = new GalleryLayoutManager(GalleryLayoutManager.HORIZONTAL);
    layoutManager.attach(mRv, 10);
    mRv.setAdapter(mainPageRvAdapter);
    layoutManager.setItemTransformer(new Transformer());
    layoutManager.setOnItemSelectedListener(
        new GalleryLayoutManager.OnItemSelectedListener() {
          @Override
          public void onItemSelected(RecyclerView recyclerView, View view, int i) {
            Log.d(TAG, "onItemSelected: " + i);
          }
        }
    );
  }
  //滑动过程中的缩放
  public class Transformer implements GalleryLayoutManager.ItemTransformer {
    @Override
    public void transformItem(GalleryLayoutManager layoutManager, View item, float fraction) {
      //以圆心进行缩放
      item.setPivotX(item.getWidth() / 2.0f);
      item.setPivotY(item.getHeight() / 2.0f);
      float scale = 1 - 0.3f * Math.abs(fraction);
      item.setScaleX(scale);
      item.setScaleY(scale);
    }
  }
}
