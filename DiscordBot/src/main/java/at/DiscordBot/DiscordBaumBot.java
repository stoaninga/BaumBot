/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package at.DiscordBot;

import at.DiscordBot.commands.BeleidigungCommand;
import at.DiscordBot.listener.CommandListener;
import at.DiscordBot.music.PlayerManager;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

/**
 *
 * @author Rene Steininger
 */
public class DiscordBaumBot {

    public static DiscordBaumBot INSTANCE;

    public ShardManager shardMgr;
    private CommandManager cmdMgr;
    private Thread loop;
    public AudioPlayerManager audioPlayerManager;
    public PlayerManager playerManager;

    //private final String filenameSteinUnser = "../resources/SteinUnser.txt";
    //private final String filenameBeleidigungen = "../resources/Beleidigungen.txt";
    //private final String filenameHelpCommands = "../resources/help.txt";

    private List<String> steinUnserZeilen, beleidigungenZeilen,helpCommandsZeilen;

    public static void main(String[] args) {
        try {
            new DiscordBaumBot();
        } catch (LoginException | IllegalArgumentException ex) {
            Logger.getLogger(DiscordBaumBot.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public DiscordBaumBot() throws LoginException, IllegalArgumentException {
        INSTANCE = this;

        DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder();
        builder.setToken("NjA3NjcyMzk4OTkyNjM3OTUy.XUgFDg.tkias81dGLqAiHPVLHqSpZ6AJgk");

        builder.setActivity(Activity.playing("/help")); ///rechts Anzeige Bot spielt XYZ
        builder.setStatus(OnlineStatus.ONLINE);

        this.audioPlayerManager = new DefaultAudioPlayerManager();
        this.playerManager = new PlayerManager();

        this.cmdMgr = new CommandManager();

        builder.addEventListeners(new CommandListener());

        shardMgr = builder.build();
        System.out.println("Einen Wunderschönen!");

        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        audioPlayerManager.getConfiguration().setFilterHotSwapEnabled(true);

        shutdown();
        ///beleidigungenZeilen = readTextFile(filenameBeleidigungen);
        ///steinUnserZeilen = readTextFile(filenameSteinUnser);
        //helpCommandsZeilen = readTextFile(filenameHelpCommands);
        runLoop();
    }

    public DiscordBaumBot(boolean readDatei) {
        ///beleidigungenZeilen = readTextFile(filenameBeleidigungen);
        ///steinUnserZeilen = readTextFile(filenameSteinUnser);
        //helpCommandsZeilen = readTextFile(filenameHelpCommands);
    }

    public void shutdown() {

        new Thread(() -> {
            String line = "";

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));) {
                while ((line = reader.readLine()) != null) {
                    if (line.equalsIgnoreCase("exit")) {
                        if (shardMgr != null) {
                            shardMgr.setStatus(OnlineStatus.OFFLINE);
                            shardMgr.shutdown();
                            System.out.println("I bims offline!");

                        }
                        if (loop != null) {
                            loop.interrupt();
                        }
                    } else {
                        System.out.println("Benutze 'exit' zum Shotdown!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }).start();
    }

    public void runLoop() {
        this.loop = new Thread(() -> {

            long time = System.currentTimeMillis();

            while (true) {
                if (System.currentTimeMillis() >= time + 1000) {
                    time = System.currentTimeMillis();

                    endSecond();
                }
            }

        });
        this.loop.setName("Loop");
        this.loop.start();
    }

    String[] status = new String[]{"mit %members anderen User", "weil Baum", "/help", "Sieg Stein", "/help"};
    int next = 60; ///in sekunden

    public void endSecond() {

        if (next <= 0) {
            Random rd = new Random();
            int i = rd.nextInt(status.length);

            shardMgr.getShards().forEach(jda -> {
                int onlineUser = jda.getUsers().size() - 1;
                String text = status[i].replaceAll("%members", "" + onlineUser);

                jda.getPresence().setActivity(Activity.playing(text));
            });
            next = 60;
        } else {
            next--;
        }

    }

    public final List<String> readTextFile(String filename) {
        List<String> zeilen = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) { //getResources
            String line = "";
            while ((line = reader.readLine()) != null) {
                zeilen.add(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(BeleidigungCommand.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zeilen;
    }

    public CommandManager getCmdMgr() {
        return cmdMgr;
    }

    public List<String> getSteinUnserZeilen() {
        return steinUnserZeilen;
    }

    public List<String> getBeleidigungenZeilen() {
        return beleidigungenZeilen;
    }

    public List<String> getHelpCommandsZeilen() {
        return helpCommandsZeilen;
    }

    
}
