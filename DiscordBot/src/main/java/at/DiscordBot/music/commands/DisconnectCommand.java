/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.music.commands;

import at.DiscordBot.DiscordBaumBot;
import at.DiscordBot.commands.types.ServerCommand;
import at.DiscordBot.music.MusicController;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.managers.AudioManager;

/**
 *
 * @author Rene Steininger
 */
public class DisconnectCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message) {
        GuildVoiceState state;
        if ((state = member.getVoiceState()) != null) {
            VoiceChannel vc;
            if ((vc = state.getChannel()) != null) {
                MusicController controller = DiscordBaumBot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                AudioManager manager = vc.getGuild().getAudioManager();
                AudioPlayer player = controller.getPlayer();

                player.stopTrack();
                manager.closeAudioConnection();
                
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Powered by SiegStein!");
                builder.setColor(Color.decode("#00FF00"));
                channel.sendMessage(builder.build()).queue();
            }

        }
    }
}
