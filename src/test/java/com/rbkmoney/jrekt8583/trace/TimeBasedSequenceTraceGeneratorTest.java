package com.rbkmoney.jrekt8583.trace;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TimeBasedSequenceTraceGeneratorTest {

    @Test
    public void testNextGenerateWhenMaxSequenceValue() {
        TimeBasedSequenceTraceGenerator traceGenerator = new TimeBasedSequenceTraceGenerator(
                (int) TimeBasedSequenceTraceGenerator.MAX_SEQUENCE_VALUE - 1
        );
        assertEquals(1, traceGenerator.nextTrace());
    }

}
