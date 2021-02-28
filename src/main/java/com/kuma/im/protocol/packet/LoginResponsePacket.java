package com.kuma.im.protocol.packet;

import com.kuma.im.protocol.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录响应
 *
 * @author kuma 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends BaseResponsePacket {

    private String userId;

    private String username;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
