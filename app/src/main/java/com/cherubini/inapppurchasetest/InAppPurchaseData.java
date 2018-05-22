package com.cherubini.inapppurchasetest;

//https://developer.android.com/google/play/billing/billing_reference
public class InAppPurchaseData {
    Boolean autoRenewing;
    String orderId;
    String packageName;
    String productId;
    String purchaseToken;

    public InAppPurchaseData(Boolean autoRenewing, String orderId, String packageName, String productId, String purchaseToken) {
        this.autoRenewing = autoRenewing;
        this.orderId = orderId;
        this.packageName = packageName;
        this.productId = productId;
        this.purchaseToken = purchaseToken;
    }

    public Boolean getAutoRenewing() {
        return autoRenewing;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getProductId() {
        return productId;
    }

    public String getPurchaseToken() {
        return purchaseToken;
    }
}
