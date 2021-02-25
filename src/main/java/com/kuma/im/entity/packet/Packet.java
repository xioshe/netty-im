package com.kuma.im.entity.packet;

import lombok.Data;

/**
 * 数据包基类
 *
 * @author kuma 2021-02-25
 */
@Data
public abstract class Packet {

    /**
     * 1 字节的版本号字段
     */
    private Byte version = 1;

    /**
     * 1 字节的指令字段
     *
     * @see com.kuma.im.entity.Command
     * @return Byte, code of Request Command
     */
    public abstract Byte getCommand();
}