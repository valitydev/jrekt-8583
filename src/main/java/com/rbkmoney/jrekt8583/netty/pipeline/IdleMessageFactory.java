package com.rbkmoney.jrekt8583.netty.pipeline;

import com.solab.iso8583.IsoMessage;

public interface IdleMessageFactory {
    IsoMessage newMessage();
}
