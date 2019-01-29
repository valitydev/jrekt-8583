package com.rbkmoney.jrekt8583.util;

import com.rbkmoney.jrekt8583.IsoField;
import com.solab.iso8583.IsoMessage;

public class IsoUtil {

    public static String getStringFieldValue(IsoMessage isoMessage, IsoField isoField) {
        return isoField.getIsoType()
                .format(
                        isoMessage.getObjectValue(isoField.getId()).toString(),
                        isoField.getLength()
                );
    }

}
