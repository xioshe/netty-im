package com.kuma.im.protocol.packet;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author kuma 2021-02-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class BaseResponsePacket extends Packet {

    private boolean success;
    private String reason;
}
