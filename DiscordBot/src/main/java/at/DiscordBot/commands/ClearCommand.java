/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.commands;

import at.DiscordBot.DiscordBaumBot;
import at.DiscordBot.commands.types.ServerCommand;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public class ClearCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        if (member.hasPermission(channel, Permission.MESSAGE_MANAGE)) {
            ///message.delete().queue();
            String[] args = message.getContentDisplay().split(" ");

            // bsp.: !clear 3
            if (args.length == 2) {
                try {
                    int amount = Integer.parseInt(args[1]);
                    channel.purgeMessages(get(channel, amount));
                    channel.sendMessage(amount + " Nachrichten gelöscht").complete().delete().queueAfter(5, TimeUnit.SECONDS);
                    return;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<Message> get(MessageChannel channel, int amount) {
        List<Message> messages = new ArrayList<>();
        int i = amount + 1;

        for (Message message : channel.getIterableHistory().cache(false)) {
            if (!message.isPinned()) {
                messages.add(message);

            }
            if (--i <= 0) {
                break;
            }
        }
        return messages;
    }

}
