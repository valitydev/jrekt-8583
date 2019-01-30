package com.rbkmoney.jrekt8583.example;

import com.rbkmoney.jrekt8583.IsoField;
import com.rbkmoney.jrekt8583.IsoMessageListener;
import com.rbkmoney.jrekt8583.server.Iso8583Server;
import com.rbkmoney.jrekt8583.util.IsoUtil;
import com.solab.iso8583.IsoMessage;
import com.solab.iso8583.IsoType;
import io.netty.channel.ChannelHandlerContext;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;

import java.util.AbstractMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.slf4j.LoggerFactory.getLogger;

public class ClientSendAsyncIT extends AbstractIT {

    private static final Logger log = getLogger(ClientSendAsyncIT.class);

    @Override
    protected void configureServer(Iso8583Server<IsoMessage> server) {
        server.addMessageListener(new IsoMessageListener<IsoMessage>() {

            @Override
            public boolean applies(IsoMessage isoMessage) {
                return isoMessage.getType() == 0x200;
            }

            @Override
            public boolean onMessage(ChannelHandlerContext ctx, IsoMessage isoMessage) {
                ctx.channel().writeAndFlush(isoMessage);
                return true;
            }
        });
    }

    @Test
    public void testSendAsync() {
        IntStream.rangeClosed(1, 10000)
                .parallel()
                .mapToObj(
                        value -> {
                            log.info("Running sending task {}", value);
                            IsoMessage request = client.getIsoMessageFactory().newMessage(0x0200);
                            return new AbstractMap.SimpleEntry<>(request, client.sendAsync(request));
                        })
                .forEach(data -> {
                    try {
                        assertEquals(
                                IsoUtil.getStringFieldValue(data.getKey(), IsoField.SYSTEM_TRACE_AUDIT_NUMBER),
                                IsoUtil.getStringFieldValue(data.getValue().get(), IsoField.SYSTEM_TRACE_AUDIT_NUMBER)
                        );
                    } catch (InterruptedException | ExecutionException ex) {
                        throw new RuntimeException(ex);
                    }
                });
    }

    @Test
    public void testSendWhenStanHasALeadZeros() throws ExecutionException, InterruptedException, TimeoutException {
        IsoMessage request = client.getIsoMessageFactory().newMessage(0x0200);
        request.setValue(11, 0, IsoType.NUMERIC, 6);
        client.send(request);
        request.setValue(11, "000000", IsoType.NUMERIC, 6);
        client.send(request);
        request.setValue(11, "000001", IsoType.NUMERIC, 6);
        client.send(request);
    }

}
