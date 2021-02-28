package com.kuma.im.protocol;

import com.kuma.im.protocol.packet.LoginRequestPacket;
import com.kuma.im.protocol.packet.Packet;
import com.kuma.im.serialize.JsonSerializer;
import com.kuma.im.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
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
        loginRequestPacket.setUsername("zhangsan");
        loginRequestPacket.setPassword("password");

        PacketCodeC packetCodeC = new PacketCodeC();
        ByteBuf byteBuf = packetCodeC.encode(ByteBufAllocator.DEFAULT, loginRequestPacket);
        Packet decodedPacket = packetCodeC.decode(byteBuf);

        assertArrayEquals(serializer.serialize(loginRequestPacket), serializer.serialize(decodedPacket));

    }
}