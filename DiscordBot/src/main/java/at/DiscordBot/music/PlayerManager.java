/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot.music;

import at.DiscordBot.DiscordBaumBot;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author Rene Steininger
 */
public class PlayerManager {

    public ConcurrentHashMap<Long, MusicController> controller;

    public PlayerManager() {
        this.controller = new ConcurrentHashMap<>();
    }

    public MusicController getController(long guildID) {
        MusicController mc = null;

        if (this.controller.containsKey(guildID)) {
            mc = this.controller.get(guildID);
        } else {
            mc = new MusicController(DiscordBaumBot.INSTANCE.shardMgr.getGuildById(guildID));

            this.controller.put(guildID, mc);

        }
        return mc;
    }

}
