package cc.funkemunky.scoreboard.anticheats;

import org.bukkit.entity.Player;

import java.util.*;

public abstract class Anticheat {

    public final String name;
    public String pluginName;
    public final List<String> dependantAnticheats = new ArrayList<>();
    public List<Player> inUse = new ArrayList<>();

    public static Set<Anticheat> anticheats = new HashSet<>();
    public static Map<UUID, String> namesinUse = new HashMap<>();

    public Anticheat(String name, String... depends) {
        this.name = name;
        this.pluginName = name;
        dependantAnticheats.addAll(Arrays.asList(depends));

        anticheats.add(this);
    }

    public static Optional<Anticheat> getAnticheatByName(String name) {
        return anticheats.stream().filter(ac -> ac.name.equalsIgnoreCase(name)).findFirst();
    }

    public void enableForPlayer(Player player) {
        namesinUse.put(player.getUniqueId(), name);
        inUse.add(player);

        dependantAnticheats.forEach(name -> Anticheat.getAnticheatByName(name)
                .ifPresent(ac -> ac.enableForPlayer(player)));
    }

    public void disableForPlayer(Player player) {
        namesinUse.remove(player.getUniqueId(), name);
        inUse.remove(player);

        dependantAnticheats.forEach(name -> Anticheat.getAnticheatByName(name)
                .ifPresent(ac -> ac.disableForPlayer(player)));
    }
}
