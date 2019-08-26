package cc.funkemunky.scoreboard;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.scoreboard.anticheat.AnticheatManager;
import cc.funkemunky.scoreboard.scoreboard.ScoreboardManager;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class AnticheatScoreboard extends JavaPlugin {
    public static AnticheatScoreboard INSTANCE;
    public AnticheatManager anticheatManager;
    public ScoreboardManager scoreboardManager;
    public List<Player> alerts = new ArrayList<>();

    public void onEnable() {
        INSTANCE = this;

        anticheatManager = new AnticheatManager();
        anticheatManager.registerAnticheat();

        ScoreboardLib.setPluginInstance(this);

        if(anticheatManager.anticheat != null) {
            Bukkit.getPluginManager().registerEvents(anticheatManager.anticheat, this);
        }

        scoreboardManager = new ScoreboardManager();

        loadScoreboards();
    }

    public void onDisable() {

    }

    public void loadScoreboards() {
        Atlas.getInstance().initializeScanner(getClass(), this, true, true);
        Bukkit.getOnlinePlayers().stream().filter(player -> player.hasPermission("as.scoreboard")).forEach(player -> scoreboardManager.addScoreboard(player));
    }
}
