package com.rbkmoney.jrekt8583.netty.pipeline;

import com.solab.iso8583.IsoMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ChannelHandler.Sharable
public class IdleEventHandler extends ChannelInboundHandlerAdapter {

    private final IdleMessageFactory idleMessageFactory;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE || e.state() == IdleState.ALL_IDLE) {
                final IsoMessage echoMessage = idleMessageFactory.newMessage();
                ctx.write(echoMessage);
                ctx.flush();
            }
        }
    }
}
