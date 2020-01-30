package cc.funkemunky.scoreboard;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.reflection.CraftReflection;
import cc.funkemunky.api.reflection.MinecraftReflection;
import cc.funkemunky.api.reflections.Reflections;
import cc.funkemunky.api.utils.MathUtils;
import cc.funkemunky.api.utils.ReflectionsUtil;
import cc.funkemunky.api.utils.RunUtils;
import cc.funkemunky.api.utils.TickTimer;
import cc.funkemunky.api.utils.math.RollingAverageDouble;
import cc.funkemunky.api.utils.objects.evicting.EvictingList;
import cc.funkemunky.scoreboard.anticheats.Kauri;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import lombok.val;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
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

    //Lag Information
    public double tps;
    public TickTimer lastTickLag;
    public long lastTick;

    public void onEnable() {
        INSTANCE = this;

        Atlas.getInstance().initializeScanner(this, true, true);

        ScoreboardLib.setPluginInstance(this);

        primaryThread = Reflections.getNMSClass("MinecraftServer").getFieldByName("primaryThread")
                .get(ReflectionsUtil.getMinecraftServer());

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
            Bukkit.getOnlinePlayers().forEach(this::setupSB);
        }
    }

    public void onDisable() {
        HandlerList.unregisterAll(this);
        Bukkit.getScheduler().cancelTasks(this);
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
                        .next("&7Your Ping&8: &f" + MinecraftReflection.getPing(player))
                        .next("&7TPS&8: &f" + MathUtils.round(tps, 3))
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
}
