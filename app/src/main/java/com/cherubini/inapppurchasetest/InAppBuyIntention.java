package com.cherubini.inapppurchasetest;

import android.app.PendingIntent;

public interface InAppBuyIntention {
    /**
     * This will method will be used to synchronize the inAppPurchaseData
     * when a subscription purchase intention happen
     * When it is already synced, make the purchase with the pendingIntent param
     * TODO: Can improve this method name
     */
    void onPendingIntentPrepared(InAppPurchaseData data, PendingIntent pendingIntent);
}
