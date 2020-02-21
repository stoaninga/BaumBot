/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.commands.types;

import at.DiscordBot.DiscordBaumBot;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public interface ServerCommand {
    
    public void performCommand(Member member, TextChannel channel, Message message);
   
}
