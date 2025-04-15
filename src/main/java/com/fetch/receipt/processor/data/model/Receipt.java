package com.fetch.receipt.processor.data.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;
import java.util.UUID;

public class Receipt {

    @Schema(hidden = true)
    private UUID receiptId;

    @NotBlank(message = "Retailer is required")
    private String retailer;

    @NotBlank(message = "PurchaseDate is required")
    private String purchaseDate;

    @NotBlank(message = "PurchaseTime is required")
    private String purchaseTime;

//    @NotBlank(message = "Item/s is/are required")
    private List<Item> items;

    @NotBlank(message = "Total is required")
    private String total;

    public UUID getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(UUID receiptId) {
        this.receiptId = receiptId;
    }

    public String getRetailer() {
        return retailer;
    }

    public void setRetailer(String retailer) {
        this.retailer = retailer;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
