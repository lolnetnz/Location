/*
 * Copyright 2018 Alex Thomson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.lxgaming.location.common.handler;

import io.github.lxgaming.location.common.data.UserImpl;
import io.github.lxgaming.location.common.manager.PacketManager;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class DecodeHandler extends MessageToMessageDecoder<ByteBuf> {
    
    private final UserImpl user;
    
    public DecodeHandler(UserImpl user) {
        this.user = user;
    }
    
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        try {
            msg.markReaderIndex();
            PacketManager.processClientPacket(this.user, msg);
            msg.resetReaderIndex();
        } catch (Exception ex) {
            msg.resetReaderIndex();
        }
        
        msg.retain();
        out.add(msg);
    }
}