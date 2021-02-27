package com.kuma.im.entity.packet;

import com.kuma.im.entity.Command;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 登录响应
 *
 * @author kuma 2021-02-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class LoginResponsePacket extends Packet {

    private String userId;

    private String username;

    private boolean success;

    private String reason;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_RESPONSE;
    }
}
