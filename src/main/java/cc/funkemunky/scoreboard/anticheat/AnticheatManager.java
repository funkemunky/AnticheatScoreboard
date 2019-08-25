package cc.funkemunky.scoreboard.anticheat;

import cc.funkemunky.api.tinyprotocol.api.packets.reflections.Reflections;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.MiscUtils;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.impl.*;
import org.bukkit.Bukkit;

public class AnticheatManager {
    public Anticheat anticheat = null;

    public void registerAnticheat() {
        if(Bukkit.getPluginManager().getPlugin("AAC") != null) {
            anticheat = new AAC();
        } else if(Bukkit.getPluginManager().getPlugin("Iris") != null) {
            anticheat = new Iris();
        } else if(Bukkit.getPluginManager().getPlugin("KauriLoader") != null) {
            anticheat = new Kauri();
        } else if(Bukkit.getPluginManager().getPlugin("Reflex") != null) {
            anticheat = new Reflex();
        } else if(Bukkit.getPluginManager().getPlugin("Spartan") != null) {
            anticheat = new Spartan();;
        } else if(Bukkit.getPluginManager().getPlugin("NoCheatPlus") != null) {
            anticheat = Reflections.getClass("cc.funkemunky.scoreboard.anticheat.impl.NoCheatPlus").getConstructor().newInstance();
        }

        if(anticheat == null) {
            MiscUtils.printToConsole(Color.Red + "No supported anticheat installed. Disabling plugin...");
            Bukkit.getPluginManager().disablePlugin(AnticheatScoreboard.INSTANCE);
        }
    }
}
