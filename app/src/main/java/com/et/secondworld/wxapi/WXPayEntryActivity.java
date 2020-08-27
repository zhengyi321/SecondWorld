package com.et.secondworld.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.et.secondworld.R;
import com.et.secondworld.widget.wxpay.Constants;
import com.et.secondworld.widget.wxpay.WXPay;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

public class WXPayEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxpay_call_back);
//        Log.d("zz88","zzzzz");

//        api = WXAPIFactory.createWXAPI(this, Constants.APP_ID);
//        api.handleIntent(getIntent(), this);

        if(WXPay.getInstance() != null) {
            WXPay.getInstance().getWXApi().handleIntent(getIntent(), this);
//            Log.d("zz88","zzzzz111");
        } else {
//            Log.d("zz88","zzzzz11122");
            finish();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
//        Log.d("zz88","zzzzz22");
//        if(WXPay.getInstance() != null) {
//            WXPay.getInstance().getWXApi().handleIntent(intent, this);
//        }
    }

    @Override
    public void onReq(BaseReq baseReq) {
//        Log.d("wxpay11", "errstr=" + baseReq);
    }

    @Override
    public void onResp(BaseResp baseResp) {
//        Log.d("wxpay11", "errstr=" + baseResp.errStr);
//        Log.d("zz88","zzzzz333");
        if(baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            if(WXPay.getInstance() != null) {
                if(baseResp.errStr != null) {
//                    Log.e("wxpay11", "errstr=" + baseResp.errStr);
                }
                Log.d("zz88","zzzzz"+baseResp.errCode);
                WXPay.getInstance().onResp(baseResp.errCode);
                finish();
            }
        }
    }
}
