package com.kuma.im.protocol.packet;

import com.alibaba.fastjson.annotation.JSONField;
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
    @JSONField(deserialize = false, serialize = false)
    private Byte version = 1;

    /**
     * 1 字节的指令字段
     *
     * @see com.kuma.im.protocol.Command
     * @return Byte, code of Request Command
     */
    @JSONField(serialize = false)
    public abstract Byte getCommand();
}
