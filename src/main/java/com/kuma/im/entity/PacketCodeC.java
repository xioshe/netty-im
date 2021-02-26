package com.kuma.im.entity;

import com.kuma.im.entity.packet.*;
import com.kuma.im.serialize.JsonSerializer;
import com.kuma.im.serialize.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.HashMap;
import java.util.Map;

import static com.kuma.im.entity.Command.*;

/**
 * @author kuma 2021-02-25
 */
public class PacketCodeC {

    public static final int MAGIC_NUMBER = 0x12345678;
    public static final PacketCodeC INSTANCE = new PacketCodeC();
    private final Map<Byte, Class<? extends Packet>> packetTypeMap;
    private final Map<Byte, Serializer> serializerMap;

    public PacketCodeC() {
        packetTypeMap = new HashMap<>();
        packetTypeMap.put(LOGIN_REQUEST, LoginRequestPacket.class);
        packetTypeMap.put(LOGIN_RESPONSE, LoginResponsePacket.class);
        packetTypeMap.put(MESSAGE_REQUEST, MessageRequestPacket.class);
        packetTypeMap.put(MESSAGE_RESPONSE, MessageResponsePacket.class);

        serializerMap = new HashMap<>();
        Serializer serializer = new JsonSerializer();
        serializerMap.put(serializer.getSerializerAlgorithm(), serializer);
    }

    public ByteBuf encode(ByteBufAllocator byteBufAllocator, Packet packet) {
        ByteBuf buf = byteBufAllocator.ioBuffer();
        encode(buf, packet);
        return buf;
    }

    public void encode(ByteBuf buf, Packet packet) {
        byte[] bytes = Serializer.DEFAULT.serialize(packet);

        buf.writeInt(MAGIC_NUMBER);
        buf.writeByte(packet.getVersion());
        buf.writeByte(Serializer.DEFAULT.getSerializerAlgorithm());
        buf.writeByte(packet.getCommand());
        buf.writeInt(bytes.length);
        buf.writeBytes(bytes);
    }

    public Packet decode(ByteBuf buf) {
        buf.skipBytes(4);
        buf.skipBytes(1);

        byte serializerAlgorithm = buf.readByte();
        Serializer serializer = getSerializer(serializerAlgorithm);
        byte command = buf.readByte();
        Class<? extends Packet> requestType = getRequestType(command);

        int length = buf.readInt();
        byte[] bytes = new byte[length];
        buf.readBytes(bytes);

        if (serializer != null && requestType != null) {
            return serializer.deserialize(requestType, bytes);
        }
        return null;
    }

    private Serializer getSerializer(byte serializeAlgorithm) {
        return serializerMap.get(serializeAlgorithm);
    }

    private Class<? extends Packet> getRequestType(byte command) {
        return packetTypeMap.get(command);
    }
}
