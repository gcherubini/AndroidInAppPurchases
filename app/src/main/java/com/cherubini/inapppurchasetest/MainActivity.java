package com.cherubini.inapppurchasetest;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cherubini.inapppurchasetest.util.IabHelper;
import com.cherubini.inapppurchasetest.util.IabResult;
import com.cherubini.inapppurchasetest.util.Purchase;

public class MainActivity extends AppCompatActivity implements IabHelper.OnIabSetupFinishedListener, IabHelper.OnIabPurchaseFinishedListener, InAppBuyIntention {

    private static final String TAG = "InAppPurchaseTest";
    private static final String base64PublicKey = "fromGooglePlayDeveloperConsole";
    private static final int PURCHASE_INTENT_REQUEST_CODE = 1;
    private IabHelper helper;

    // Define sku for an auto purchase subscription (just for test)
    private static final String SKU_TO_PURCHASE_FOR_TEST = "skuForTest";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        helper = new IabHelper(this, base64PublicKey);
        helper.enableDebugLogging(true, TAG);
        helper.startSetup(this);
    }

    @Override
    public void onIabSetupFinished(IabResult result) {
        if (result.isFailure()) {
            Log.e(TAG, "Some error on Billing setup/connection: " + result.getMessage());
            return;
        }

        purchase(SKU_TO_PURCHASE_FOR_TEST);
    }


    private void purchase(String sku) {
        try {
            helper.launchSubscriptionPurchaseFlow(
                    this,
                    sku,
                    PURCHASE_INTENT_REQUEST_CODE,
                    this,
                    this);
        } catch (Exception e) {
            Log.e(TAG, "Some exception on launchSubscriptionPurchaseFlow: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onPendingIntentPrepared(InAppPurchaseData data, PendingIntent pendingIntent) {
        syncBuyIntentionData(data);
        purchaseWithPendingIntent(pendingIntent);
    }

    private void syncBuyIntentionData(InAppPurchaseData data) {
        // TODO: Sync backend with data: orderId, purchaseToken, productId, etc.
    }

    private void purchaseWithPendingIntent(PendingIntent pendingIntent) {
        try {
            startIntentSenderForResult(pendingIntent.getIntentSender(),
                    PURCHASE_INTENT_REQUEST_CODE, new Intent(),
                    Integer.valueOf(0), Integer.valueOf(0),
                    Integer.valueOf(0));
        } catch (IntentSender.SendIntentException e) {
            Log.e(TAG, "Some exception on startIntentSenderForResult. Subscribing in-app purchase with pending intent:" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        helper.handleActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onIabPurchaseFinished(IabResult result, Purchase info) {
        if (result.isFailure()) {
            Log.e(TAG, "Some error purchasing this SKU: " + result.getMessage());
            return;
        }

        Log.i(TAG, "SKU purchased !!!");
        Log.i(TAG, "--> Order ID: " + info.getOrderId());
        Log.i(TAG, "--> Purchase Token: " + info.getToken());
        Log.i(TAG, "--> Original JSON: " + info.getOriginalJson());
    }
}
