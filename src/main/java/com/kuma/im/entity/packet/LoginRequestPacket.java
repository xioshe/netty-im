package com.kuma.im.entity.packet;

import com.kuma.im.entity.Command;
import lombok.Data;

/**
 * 登录数据包
 *
 * @author kuma 2021-02-25
 */
@Data
public class LoginRequestPacket extends Packet {

    private Integer userId;
    private String username;
    private String password;

    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
