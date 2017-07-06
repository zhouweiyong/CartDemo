package com.vst.cartdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

/**
 * Created by zwy on 2017/7/5.
 * email:16681805@qq.com
 */

public class CartActionViewActivity extends Activity implements View.OnClickListener,CartActionView.OnCartActionListener {

    private CartActionView cav;
    private Button btn1;
    private int num = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_action_view);
        initView();
    }

    private void initView() {
        cav = (CartActionView) findViewById(R.id.cav);
        btn1 = (Button) findViewById(R.id.btn1);
        btn1.setOnClickListener(this);
        cav.setCartNum(num);
        cav.setOnCartActionListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                cav.setCartNum(10);
                break;
        }
    }

    @Override
    public void onReduce() {
        cav.setCartNum(--num);
    }

    @Override
    public void onIncrease() {
        cav.setCartNum(++num);
    }

    @Override
    public void onCartNumPress() {

    }
}
