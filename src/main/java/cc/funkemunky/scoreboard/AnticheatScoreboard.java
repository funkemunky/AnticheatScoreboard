package cc.funkemunky.scoreboard;

import cc.funkemunky.api.Atlas;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class AnticheatScoreboard extends JavaPlugin {
    public static AnticheatScoreboard INSTANCE;
    public Map<UUID, String> alerts = new HashMap<>();

    public void onEnable() {
        INSTANCE = this;

        Atlas.getInstance().initializeScanner(this, true, true);

        ScoreboardLib.setPluginInstance(this);
    }

    public void onDisable() {

    }
}
