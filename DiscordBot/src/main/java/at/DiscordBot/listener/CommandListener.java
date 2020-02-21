/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.listener;

import at.DiscordBot.DiscordBaumBot;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.ChannelType;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/**
 *
 * @author Rene Steininger
 */
public class CommandListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String message = event.getMessage().getContentDisplay();
        EmbedBuilder builder = new EmbedBuilder();
        if (event.isFromType(ChannelType.TEXT)) {
            TextChannel channel = event.getTextChannel();

            if (message.startsWith("/")) {
                String[] args = message.substring(1).split(" ");

                if (args.length > 0) {
                    if (!DiscordBaumBot.INSTANCE.getCmdMgr().perform(args[0], event.getMember(), channel, event.getMessage())) {
                        builder.setDescription("Unknown Command!");
                        builder.setColor(Color.decode("#b50015"));
                        channel.sendMessage(builder.build()).queue();
                    }
                }

            }

        }
    }

}
