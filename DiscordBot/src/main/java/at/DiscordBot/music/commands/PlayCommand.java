/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.music.commands;

import at.DiscordBot.DiscordBaumBot;
import at.DiscordBot.commands.types.ServerCommand;
import at.DiscordBot.music.AudioLoadResult;
import at.DiscordBot.music.MusicController;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
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
public class PlayCommand implements ServerCommand {

    @Override
    public void performCommand(Member member, TextChannel channel, Message message ) {

        String[] args = message.getContentDisplay().split(" ");
        if (args.length > 1) {
            GuildVoiceState state;
            if ((state = member.getVoiceState()) != null) {
                VoiceChannel vc;
                if ((vc = state.getChannel()) != null) {
                    MusicController controller = DiscordBaumBot.INSTANCE.playerManager.getController(vc.getGuild().getIdLong());
                    AudioPlayerManager apm = DiscordBaumBot.INSTANCE.audioPlayerManager;
                    AudioManager manager = vc.getGuild().getAudioManager();
                    manager.openAudioConnection(vc);

                    StringBuilder strBuilder = new StringBuilder();
                    for (int i = 1; i < args.length; i++) {
                        strBuilder.append(args[i] + " ");
                    }
                    String url = strBuilder.toString().trim();

                    ///youtube
                    if (!url.startsWith("http")) {
                        url = "ytsearch: " + url;
                    }

                    apm.loadItem(url, new AudioLoadResult(controller, url, channel));

                } else {
                    EmbedBuilder builder = new EmbedBuilder();
                    builder.setDescription("Gehe bitte in einen VoiceChannel um mich zu starten");
                    builder.setColor(Color.decode("#00ff00"));
                    channel.sendMessage(builder.build()).queue();
                }
            } else {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setDescription("Gehe bitte in einen VoiceChannel um mich zu starten");
                builder.setColor(Color.RED);
                channel.sendMessage(builder.build()).queue();
            }

        } else {
            EmbedBuilder builder = new EmbedBuilder();
            builder.setDescription("Bitte benutze !play <url/search query>");
            builder.setColor(Color.RED);
            channel.sendMessage(builder.build()).queue();

        }

    }

}
