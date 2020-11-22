package cc.funkemunky.scoreboard;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.commands.ancmd.CommandManager;
import cc.funkemunky.api.reflections.Reflections;
import cc.funkemunky.api.utils.*;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import lombok.val;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AnticheatScoreboard extends JavaPlugin {
    public static AnticheatScoreboard INSTANCE;
    public Map<UUID, String> alerts = new HashMap<>();
    public Map<UUID, Scoreboard> boards = new HashMap<>();
    public Thread primaryThread;
    public Permission permission;

    //Lag Information
    public double tps;
    public TickTimer lastTickLag;
    public long lastTick;

    public void onEnable() {
        INSTANCE = this;

        Plugin kauri = Bukkit.getPluginManager().getPlugin("Kauri");
        if(kauri != null) {
            MiscUtils.printToConsole("Getting rid of Kauri /anticheat...");
            Atlas.getInstance().getCommandManager(kauri).unregisterCommand("anticheat");
        }

        MiscUtils.printToConsole("Running scanner...");
        Atlas.getInstance().initializeScanner(this, true, true);

        MiscUtils.printToConsole("Starting Scoreboard API...");
        ScoreboardLib.setPluginInstance(this);

        primaryThread = Reflections.getNMSClass("MinecraftServer").getFieldByName("primaryThread")
                .get(ReflectionsUtil.getMinecraftServer());

        MiscUtils.printToConsole("Setting up Vault hook...");
        setupPermissions();

        MiscUtils.printToConsole("Starting services...");
        lastTickLag = new TickTimer(6);
        AtomicInteger ticks = new AtomicInteger();
        AtomicLong lastTimeStamp = new AtomicLong(0);
        RunUtils.taskTimer(() -> {
            ticks.getAndIncrement();
            long currentTime = System.currentTimeMillis();

            if(currentTime - lastTick > 120) {
                lastTickLag.reset();
            }
            if(ticks.get() >= 10) {
                ticks.set(0);
                tps = 500D / (currentTime - lastTimeStamp.get()) * 20;
                lastTimeStamp.set(currentTime);
            }
            lastTick = currentTime;
        }, this, 1L, 1L);

        if(GeneralConfig.testMode) {
            MiscUtils.printToConsole("Setting up scoreboard for online players...");
            Bukkit.getOnlinePlayers().forEach(this::setupSB);
        }
        MiscUtils.printToConsole(Color.Green + "Completed startup!");
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        permission = rsp.getProvider();
        return permission != null;
    }

    public Scoreboard setupSB(Player player) {
        val board = ScoreboardLib.createScoreboard(player).setHandler(new ScoreboardHandler() {

            @Override
            public String getTitle(Player player) {
                return "&6&lAnticheat Test Server";
            }

            @Override
            public List<Entry> getEntries(Player player) {
                return new EntryBuilder()
                        .next("&8&m--------------------------")
                        .next("&e&lAnticheat")
                        .next("&f" + alerts.getOrDefault(player.getUniqueId(), "Vanilla"))
                        .blank()
                        .next("&e&lLag")
                        .next("&7Your Ping&8: &f" + player.spigot().getPing())
                        .next("&7TPS&8: &f" + MathUtils.round(Bukkit.getServer().spigot().getTPS()[0], 1))
                        .next("&f&8&m--------------------------").build();
            }

        }).setUpdateInterval(5L);

        board.activate();

        if(boards.containsKey(player.getUniqueId())) {
            val board2 = boards.get(player.getUniqueId());

            board2.deactivate();

            boards.remove(player.getUniqueId());
        }
        boards.put(player.getUniqueId(), board);
        return board;
    }

    public CommandManager getCommandManager() {
        return Atlas.getInstance().getCommandManager(this);
    }
}
