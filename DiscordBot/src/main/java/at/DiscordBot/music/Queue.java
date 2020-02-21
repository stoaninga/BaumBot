/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.music;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import java.util.List;

/**
 *
 * @author Rene Steininger
 */
public class Queue {

    private List<AudioTrack> queueList; ///=queue
    private MusicController controller;

    public boolean next() {
        if (this.queueList.size() >= 1) {
            AudioTrack track = queueList.remove(0);

            if (track != null) {
                this.controller.getPlayer().playTrack(track);
                return true;
            }
        }
        return false;
    }

    public Queue(MusicController controller) {
        this.setController(controller);
    }
    
    public void addTrackToQueue(AudioTrack track){
        this.queueList.add(track);
    }

    public List<AudioTrack> getQueueList() {
        return queueList;
    }

    public void setQueueList(List<AudioTrack> queueList) {
        this.queueList = queueList;
    }

    public MusicController getController() {
        return controller;
    }

    public void setController(MusicController controller) {
        this.controller = controller;
    }

}
