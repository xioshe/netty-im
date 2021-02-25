package com.kuma.im.entity;

import com.kuma.im.entity.packet.LoginRequestPacket;
import com.kuma.im.entity.packet.Packet;
import com.kuma.im.serialize.JsonSerializer;
import com.kuma.im.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

/**
 * @author kuma 2021-02-25
 */
public class PacketCodeCTest {

    @Test
    public void encode() {

        Serializer serializer = new JsonSerializer();
        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        loginRequestPacket.setVersion(((byte) 1));
        loginRequestPacket.setUserId(123);
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}