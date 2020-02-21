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
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public class SoundboardInfos implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {

        EmbedBuilder builder = new EmbedBuilder();
        builder.setDescription("Willkommen beim Baum Bot!\n"
                + "**/help**    listet dir alle Befehle auf!\n"
                + "**/clear n** löscht dir die letzten n Nachrichten aus dem Chat.\n"
                + "**/play url**spielt die gewünschte url ab (Youtube!).\n"
                + "**/disc**    Disconnect des Bots.");

        builder.setColor(Color.decode("#b50015"));
        builder.setFooter("Powered by SiegStein!");
        builder.setTimestamp(OffsetDateTime.now());

        member.getUser().openPrivateChannel().queue((ch) -> {
            ch.sendMessage(builder.build()).queue();
        });

    }

}
