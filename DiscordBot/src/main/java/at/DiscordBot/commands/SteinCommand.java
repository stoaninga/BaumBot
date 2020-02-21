/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.commands;

import at.DiscordBot.DiscordBaumBot;
import at.DiscordBot.commands.types.ServerCommand;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public class SteinCommand implements ServerCommand {

   @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        EmbedBuilder builder = new EmbedBuilder();
        DiscordBaumBot b = new DiscordBaumBot(true);
        List<String> zeilen = b.getSteinUnserZeilen();
        
        String output = "";
        for (String zeilen1 : zeilen) {
            output+=zeilen1+"\n";
        }
        builder.setDescription(output);
        builder.setColor(Color.decode("#b50015"));
        builder.setFooter("Powered by SiegStein!");
        builder.setTimestamp(OffsetDateTime.now());
        
        channel.sendMessage(builder.build()).queue();
    }

}
