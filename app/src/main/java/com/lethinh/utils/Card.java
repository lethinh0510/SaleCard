package com.lethinh.utils;

import java.io.Serializable;

/**
 * Created by Thinh on 18/07/2015.
 */
public class Card implements Serializable {
    private int id;
    private String product;
    private String code;
    private String seri;
    private int price;
    private String created_at;
    private int issold;
    private String note;

    public Card(int id, String product, String code, String seri, int price,
                String created_at, int isSold, String note){
        this.id= id;
        this.product=product;
        this.code=code;
        this.seri=seri;
        this.price=price;
        this.created_at=created_at;
        this.issold=isSold;
        this.note=note;

    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSeri() {
        return seri;
    }

    public void setSeri(String seri) {
        this.seri = seri;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public int getIssold() {
        return issold;
    }

    public void setIssold(int issold) {
        this.issold = issold;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
