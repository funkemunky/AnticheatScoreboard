package cc.funkemunky.scoreboard.anticheat;

import cc.funkemunky.api.utils.Color;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import java.util.LinkedList;
import java.util.TreeMap;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Stream;

@NoArgsConstructor
public abstract class Anticheat implements Listener {
    public TreeMap<UUID, LinkedList<Violation>> violations = new TreeMap<>();
    public UUID lastViolator;

    public abstract String getAnticheatName();

    public void addViolation(UUID uuid, String checkName, boolean cancelled, Predicate<Violation> toFilter, Consumer<Violation> toSet, String tags) {
        if(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(Bukkit.getPlayer(uuid).getUniqueId()).getAnticheatName().equals(getAnticheatName())) return;

        LinkedList<Violation> vls = violations.getOrDefault(uuid, new LinkedList<>());

        lastViolator = uuid;
        if(containsViolation(uuid, checkName)) {
            vls.stream().filter(toFilter).forEach(toSet);
        } else {
            Violation violation = new Violation(checkName, 0, cancelled);

            Stream.of(violation).filter(toFilter).forEach(toSet);
            vls.add(violation);
        }

        String message = Color.translate(GeneralConfig.alertMessage.replace("%anticheat%", getAnticheatName()).replace("%player%", Bukkit.getPlayer(uuid).getName()).replace("%check%", checkName).replace("%vl%", String.valueOf(vls.stream().filter(toFilter).findFirst().orElse(new Violation("", 0, false)).vlCount)).replace("%tags%", tags));
        if(GeneralConfig.testMode) {
            AnticheatScoreboard.INSTANCE.alerts.stream().filter(pl -> AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(pl.getUniqueId()).getAnticheatName().equals(getAnticheatName())).forEach(pl -> pl.sendMessage(message));
        } else {
            AnticheatScoreboard.INSTANCE.alerts.forEach(pl -> pl.sendMessage(message));
        }
        violations.put(uuid, vls);
    }

    public boolean containsViolation(UUID uuid, String checkType) {
        return violations.containsKey(uuid) && violations.get(uuid).stream().anyMatch(vl -> vl.checkName.equals(checkType));
    }
}