package com.fh.service;

import com.fh.commons.ServerResult;

public interface IPayService {
    ServerResult createPayCode(String outTradeNo, String phone);

    ServerResult checkPayStatus(String outTradeNo, String phone);
}
