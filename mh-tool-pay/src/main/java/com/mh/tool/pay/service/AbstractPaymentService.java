package com.mh.tool.pay.service;

public class AbstractPaymentService implements IPaymentService{
    @Override
    public String getIfCode() {
        return null;
    }

    @Override
    public boolean isSupport(String wayCode) {
        return false;
    }
}
