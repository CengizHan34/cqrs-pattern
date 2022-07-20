package com.example.common.query;

import java.io.Serializable;

public class GetUserPaymentDetailQuery implements Serializable {
    private String userId;

    public GetUserPaymentDetailQuery() {
    }

    public GetUserPaymentDetailQuery(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
