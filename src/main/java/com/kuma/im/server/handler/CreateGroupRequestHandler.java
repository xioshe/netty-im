package com.kuma.im.server.handler;

import com.kuma.im.protocol.packet.CreateGroupRequestPacket;
import com.kuma.im.protocol.packet.CreateGroupResponsePacket;
import com.kuma.im.util.IDUtils;
import com.kuma.im.util.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 创建群聊请求处理器
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class CreateGroupRequestHandler extends SimpleChannelInboundHandler<CreateGroupRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, CreateGroupRequestPacket msg) {
        List<String> userIdList = msg.getUserIdList();

        List<String> usernameList = new ArrayList<>();
        // 1. 创建一个 channel 分组
        ChannelGroup channelGroup = new DefaultChannelGroup(ctx.executor());

        // 2. 筛选出待加入群聊的用户的 channel 和 userName
        for (String userId : userIdList) {
            Channel channel = SessionUtils.getChannel(userId);
            if (channel != null) {
                channelGroup.add(channel);
                usernameList.add(SessionUtils.getSession(channel).getUsername());
            }
        }

        // 3. 创建群聊创建结果的响应
        CreateGroupResponsePacket createGroupResponsePacket = new CreateGroupResponsePacket();
        createGroupResponsePacket.setSuccess(true);
        String groupId = IDUtils.randomId();
        createGroupResponsePacket.setGroupId(groupId);
        createGroupResponsePacket.setUsernameList(usernameList);

        // 4. 给每个客户端发送拉群通知
        channelGroup.writeAndFlush(createGroupResponsePacket);

        log.info("群创建成功, id 为 [{}]", createGroupResponsePacket.getGroupId());
        log.info("群成员列表: {}", createGroupResponsePacket.getUsernameList());

        // 5. 保存群组相关的信息
        SessionUtils.bindChannelGroup(groupId, channelGroup);
    }
}
