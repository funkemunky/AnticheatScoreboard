package cc.funkemunky.scoreboard;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.reflections.Reflections;
import cc.funkemunky.api.utils.ReflectionsUtil;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class AnticheatScoreboard extends JavaPlugin {
    public static AnticheatScoreboard INSTANCE;
    public Map<UUID, String> alerts = new HashMap<>();
    public Thread primaryThread;

    public void onEnable() {
        INSTANCE = this;

        Atlas.getInstance().initializeScanner(this, true, true);

        ScoreboardLib.setPluginInstance(this);

        primaryThread = Reflections.getNMSClass("MinecraftServer").getFieldByName("primaryThread")
                .get(ReflectionsUtil.getMinecraftServer());
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);

    }
}
