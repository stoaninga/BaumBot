/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.commands;

import at.DiscordBot.DiscordBaumBot;
import at.DiscordBot.commands.types.ServerCommand;
import java.awt.Color;
import java.time.OffsetDateTime;
import java.util.List;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public class HelpCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        message.delete().queue();

        EmbedBuilder builder = new EmbedBuilder();
        DiscordBaumBot b = new DiscordBaumBot(true);
        List<String> zeilen = b.getHelpCommandsZeilen();
        
        String output = "";
        for (String zeilen1 : zeilen) {
            output += zeilen1 + "\n";
        }
        builder.setDescription(output);

        builder.setColor(Color.decode("#b50015"));
        builder.setFooter("Powered by SiegStein!");
        builder.setTimestamp(OffsetDateTime.now());

        member.getUser().openPrivateChannel().queue((ch) -> {
            ch.sendMessage(builder.build()).queue();
        });
    }

}
