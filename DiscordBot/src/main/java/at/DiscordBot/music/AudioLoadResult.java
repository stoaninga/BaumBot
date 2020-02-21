/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.music;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.awt.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

/**
 *
 * @author Rene Steininger
 */
public class AudioLoadResult implements AudioLoadResultHandler {

    private final MusicController controller;
    private final String uri;
    private final TextChannel channel;
    EmbedBuilder builder = new EmbedBuilder();

    public AudioLoadResult(MusicController controller, String uri, TextChannel channel) {
        this.controller = controller;
        this.uri = uri;
        this.channel = channel;
    }

    @Override
    public void trackLoaded(AudioTrack track) {
        channel.sendMessage(track.getInfo().title + " zur Queue hinzugefÃ¼gt").queue();

        playTrack(controller, track);
    }

    @Override  
    public void playlistLoaded(AudioPlaylist playlist) {  
        AudioTrack firstTrack = playlist.getSelectedTrack();
        AudioTrack nextTrack = playlist.getTracks().get(1);
        
        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        int added = 0;
        for (AudioTrack track : playlist.getTracks()) {
            added++;
        }
        
        channel.sendMessage(firstTrack.getInfo().title + "und weitere"+(added-1)+" zur Queue hinzugefügt\n"
                + "nächster Track: " + nextTrack.getInfo().title).queue(); 

        playTrack(controller, firstTrack);
          
    }

    @Override
    public void noMatches() {
        builder.setDescription("Keine passenden Matches gefunden!");
        builder.setColor(Color.RED);
        channel.sendMessage(builder.toString()).queue();
    }

    @Override
    public void loadFailed(FriendlyException fe) {
        builder.setDescription(fe.getMessage()+"");
        builder.setColor(Color.RED);
        channel.sendMessage(builder.toString()).queue();
    }

    public void playTrack(MusicController controller, AudioTrack track) {
        controller.getPlayer().playTrack(track);
    }
}
