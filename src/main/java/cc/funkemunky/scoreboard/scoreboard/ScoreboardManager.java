package cc.funkemunky.scoreboard.scoreboard;

import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.ConfigSetting;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import lombok.val;
import me.tigerhix.lib.scoreboard.ScoreboardLib;
import me.tigerhix.lib.scoreboard.common.EntryBuilder;
import me.tigerhix.lib.scoreboard.type.Entry;
import me.tigerhix.lib.scoreboard.type.Scoreboard;
import me.tigerhix.lib.scoreboard.type.ScoreboardHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

@Init
public class ScoreboardManager {

    @ConfigSetting(path = "scoreboard", name = "title")
    private static String scoreboardtitle = "&6&lAnticheatScoreboard";

    @ConfigSetting(path = "scoreboard", name = "entries")
    private static List<String> scoreboardEntries = Arrays.asList("%blank%", "&e&lAnticheat", "&f%anticheat%", "%blank%", "&e&lLast Violator", "%lastViolator%", "%blank%", "&e&lTop Violators", "%topViolators%");

    @ConfigSetting(path = "scoreboard.topViolators", name = "field")
    private static String violatorField = "&8- &f%player% (&c%checks%&7)";

    public Map<Player, Scoreboard> scoreboards = new WeakHashMap<>();

    public Scoreboard addScoreboard(Player player) {
        val scoreboard = ScoreboardLib.createScoreboard(player).setHandler(new ScoreboardHandler() {
            @Override
            public String getTitle(Player player) {
                return Color.translate(scoreboardtitle);
            }

            @Override
            public List<Entry> getEntries(Player player) {
                EntryBuilder builder = new EntryBuilder();
                for (String scoreboardEntry : scoreboardEntries) {
                    builder = formatString(builder, scoreboardEntry);
                }
                return builder.build();
            }
        }).setUpdateInterval(5);

        scoreboards.put(player, scoreboard);
        return scoreboard;
    }

    private EntryBuilder formatString(EntryBuilder builder, String string) {
        String formatted = Color.translate(string);

        if(formatted.equals("%blank%")) {
            return builder.blank();
        } else if(formatted.equals("%topViolators%")) {
            val instance = new LinkedHashMap<>(AnticheatScoreboard.INSTANCE.anticheatManager.anticheat.violations);
            List<UUID> violators = instance.keySet().stream().sorted(Comparator.comparing(key -> instance.get(key).size(), Comparator.reverseOrder())).limit(5).collect(Collectors.toList());

            if(violators.size() > 0) {
                EntryBuilder newBuilder = builder;
                for (UUID violator : violators) {
                    newBuilder = newBuilder.next(violatorField.replace("%player%", Bukkit.getOfflinePlayer(violator).getName()).replace("%checks%", instance.get(violator).size() + ""));
                }
                instance.clear();
                return newBuilder;
            } else {
                instance.clear();
                return builder.next(Color.Red + "No violators.");
            }
        }

        if(formatted.contains("%anticheat%")) {
            formatted = formatted.replace("%anticheat%", AnticheatScoreboard.INSTANCE.anticheatManager.anticheat.getAnticheatName());
        }
        if(formatted.contains("%lastViolator%")) {
            return builder.next(formatted.replace("%lastViolator%", AnticheatScoreboard.INSTANCE.anticheatManager.anticheat.lastViolator != null ? Bukkit.getOfflinePlayer(AnticheatScoreboard.INSTANCE.anticheatManager.anticheat.lastViolator).getName() : "None"));
        }

        return builder.next(formatted);
    }
}
