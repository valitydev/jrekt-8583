package com.rbkmoney.jrekt8583;

import com.solab.iso8583.IsoType;

public enum IsoField {

    PAN(2, IsoType.LLVAR, 19),
    SYSTEM_TRACE_AUDIT_NUMBER(11, IsoType.NUMERIC, 6),
    PAN_EXTENDED(34, IsoType.LLVAR, 28),
    TRACK_2_DATA(35, IsoType.LLVAR, 37),
    TRACK_3_DATA(36, IsoType.LLLVAR, 104),
    TRACK_1_DATA(45, IsoType.LLVAR, 76);

    private int id;

    private IsoType isoType;

    private int length;

    IsoField(int id, IsoType isoType, int length) {
        this.id = id;
        this.isoType = isoType;
        this.length = length;
    }

    public int getId() {
        return id;
    }

    public IsoType getIsoType() {
        return isoType;
    }

    public int getLength() {
        return length;
    }
}
