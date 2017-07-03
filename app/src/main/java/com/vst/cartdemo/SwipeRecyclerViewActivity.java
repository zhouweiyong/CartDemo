package com.vst.cartdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zwy on 2017/7/3.
 * email:16681805@qq.com
 */

public class SwipeRecyclerViewActivity extends Activity {
    private SwipeRecyclerView srv;
    private MyAdapter mAdapter;
    private List<String> mList;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_recyclerview);
        initView();


    }

    private void initView() {
        srv = (SwipeRecyclerView) findViewById(R.id.srv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        srv.setLayoutManager(linearLayoutManager);
        mList = getData();
        mAdapter = new MyAdapter();
        srv.setAdapter(mAdapter);
        mAdapter.setList(mList);
    }

    private List<String> getData() {
        ArrayList<String> strs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            strs.add("item" + i);
        }
        return strs;
    }

    private  class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        private List<String> list;

        public MyAdapter() {
        }

        public void setList(List<String> list) {
            this.list = list;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.tv.setText(list.get(position));
            holder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mList.remove(position);
                    srv.removeAllSwipeLayout();

                    MyAdapter adapter2 = new MyAdapter();
                    srv.setAdapter(adapter2);
                    adapter2.setList(mList);
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Log.i("zwy",">>>"+srv.swipeLayouts.size());
                        }
                    },1000);

                }
            });
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public  class ViewHolder extends RecyclerView.ViewHolder {
            public TextView tv;
            public Button delBtn;

            public ViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv_item);
                delBtn = (Button) itemView.findViewById(R.id.btn_del);
            }
        }
    }
}
