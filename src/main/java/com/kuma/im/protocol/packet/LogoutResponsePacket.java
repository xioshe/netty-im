package com.kuma.im.protocol.packet;

import com.kuma.im.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登出响应
 *
 * @author kuma 2021-02-27
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LogoutResponsePacket extends BaseResponsePacket {

    @Override
    public Byte getCommand() {
        return Command.LOGOUT_RESPONSE;
    }
}
