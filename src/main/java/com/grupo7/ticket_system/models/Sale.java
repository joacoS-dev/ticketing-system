package com.grupo7.ticket_system.models;

import java.time.LocalDateTime;

public class Sale {
    int saleId;
    LocalDateTime saleDate;
    boolean state;
    int totalSalePrice;
    double comissionRate;
    int userId;
    int comissionRateId;

    public int getSaleId() {
        return saleId;
    }
    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }
    public LocalDateTime getSaleDate() {
        return saleDate;
    }
    public void setSaleDate(LocalDateTime sellDate) {
        this.saleDate = sellDate;
    }
    public boolean getState() {
        return true;
    }
    public void setState(boolean state) {
        this.state = state;
    }
    public int getTotalSalePrice() {
        return totalSalePrice;
    }
    public void setTotalSalePrice(int totalSalePrice) {
        this.totalSalePrice = totalSalePrice;
    }
    public double getComissionRate() {
        return comissionRate;
    }
    public void setComissionRate(double comissionRate) {
        this.comissionRate = comissionRate;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getComissionRateId() {
        return comissionRateId;
    }
    public void setComissionRateId(int comissionRateId) {
        this.comissionRateId = comissionRateId;
    }
}
    
    