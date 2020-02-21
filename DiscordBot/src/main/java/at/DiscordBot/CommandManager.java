/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot;

import at.DiscordBot.commands.BeleidigungCommand;
import at.DiscordBot.commands.ClearCommand;
import at.DiscordBot.commands.HelpCommand;
import at.DiscordBot.commands.HelpInChatCommand; 
import at.DiscordBot.commands.SteinCommand;
import at.DiscordBot.commands.types.ServerCommand;
import at.DiscordBot.music.commands.PlayCommand;
import at.DiscordBot.music.commands.DisconnectCommand;
import java.util.concurrent.ConcurrentHashMap;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public class CommandManager {

    public ConcurrentHashMap<String, ServerCommand> commands;

    public CommandManager() {
        this.commands = new ConcurrentHashMap<>();

        this.commands.put("clear", new ClearCommand());
        this.commands.put("help", new HelpCommand());
        this.commands.put("list", new HelpInChatCommand());
        this.commands.put("play", new PlayCommand());
        this.commands.put("disc", new DisconnectCommand()); 
        this.commands.put("rape", new BeleidigungCommand());
        this.commands.put("god", new SteinCommand());

    }

    public boolean perform(String command, Member member, TextChannel channel, Message message) {

        ServerCommand cmd;
        if ((cmd = this.commands.get(command.toLowerCase())) != null) {
            cmd.performCommand(member, channel, message);
            return true;
        }

        return false;
    }

}
