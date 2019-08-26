package cc.funkemunky.scoreboard.anticheat;

import cc.funkemunky.api.tinyprotocol.api.packets.reflections.Reflections;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.MiscUtils;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.impl.*;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import org.bukkit.Bukkit;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class AnticheatManager {
    public Anticheat anticheat = null;
    public List<Anticheat> anticheats = new ArrayList<>();
    public Map<UUID, Anticheat> anticheatMap = new ConcurrentHashMap<>();

    public void registerAnticheat() {
        if(Bukkit.getPluginManager().getPlugin("AAC") != null) {
            Anticheat anticheat = new AAC();

            if(!GeneralConfig.testMode) {
                this.anticheat = anticheat;
                return;
            } else {
                anticheats.add(anticheat);
            }
        }
        if(Bukkit.getPluginManager().getPlugin("Iris") != null) {
            Anticheat anticheat = new Iris();

            if(!GeneralConfig.testMode) {
                this.anticheat = anticheat;
                return;
            } else {
                anticheats.add(anticheat);
            }
        }
        if(Bukkit.getPluginManager().getPlugin("KauriLoader") != null) {
            Anticheat anticheat = new Kauri();

            if(!GeneralConfig.testMode) {
                this.anticheat = anticheat;
                return;
            } else {
                anticheats.add(anticheat);
            }
        }
        if(Bukkit.getPluginManager().getPlugin("Reflex") != null) {
            Anticheat anticheat = new Reflex();

            if(!GeneralConfig.testMode) {
                this.anticheat = anticheat;
                return;
            } else {
                anticheats.add(anticheat);
            }
        }
        if(Bukkit.getPluginManager().getPlugin("Spartan") != null) {
            Anticheat anticheat = new Spartan();

            if(!GeneralConfig.testMode) {
                this.anticheat = anticheat;
                return;
            } else {
                anticheats.add(anticheat);
            }
        }
        if(Bukkit.getPluginManager().getPlugin("NoCheatPlus") != null) {
            Anticheat anticheat = Reflections.getClass("cc.funkemunky.scoreboard.anticheat.impl.NoCheatPlus").getConstructor().newInstance();

            if(!GeneralConfig.testMode) {
                this.anticheat = anticheat;
                return;
            } else {
                anticheats.add(anticheat);
            }
        }

        if(anticheat == null) {
            MiscUtils.printToConsole(Color.Red + "No supported anticheat installed. Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(AnticheatScoreboard.INSTANCE);
        }
    }
}
