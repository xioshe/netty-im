package com.kuma.im.client.console;

import com.kuma.im.util.SessionUtils;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 控制台统一管理类
 * 门面模式
 *
 * @author kuma 2021-02-27
 */
@Slf4j
public class ConsoleCommandManager implements ConsoleCommander {
    private final Map<String, ConsoleCommander> consoleCommanderMap;

    public ConsoleCommandManager() {
        consoleCommanderMap = new HashMap<>(3);
        consoleCommanderMap.put("sendToUser", new SendToUserConsoleCommander());
        consoleCommanderMap.put("createGroup", new CreateGroupConsoleCommander());
        consoleCommanderMap.put("logout", new LogoutConsoleCommander());
        consoleCommanderMap.put("quitGroup", new QuitGroupConsoleCommander());
        consoleCommanderMap.put("joinGroup", new JoinGroupConsoleCommander());
        consoleCommanderMap.put("listGroupMembers", new ListGroupMembersConsoleCommander());
        consoleCommanderMap.put("sendToGroup", new GroupMessageConsoleCommander());
    }

    @Override
    public void exec(Scanner scanner, Channel channel) {
        if (!SessionUtils.hasLogin(channel)) {
            return;
        }

        String command = scanner.next();
        ConsoleCommander commander = consoleCommanderMap.get(command);
        if (commander != null) {
            commander.exec(scanner, channel);
        } else {
            System.err.println("无法识别指令[{" + command + "}], 请重新输入");
        }
    }
}
