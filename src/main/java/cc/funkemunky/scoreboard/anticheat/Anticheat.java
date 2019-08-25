package cc.funkemunky.scoreboard.anticheat;

import lombok.NoArgsConstructor;
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

    public void addViolation(UUID uuid, String checkName, boolean cancelled, Predicate<Violation> toFilter, Consumer<Violation> toSet) {
        LinkedList<Violation> vls = violations.getOrDefault(uuid, new LinkedList<>());

        lastViolator = uuid;
        if(containsViolation(uuid, checkName)) {
            vls.stream().filter(toFilter).forEach(toSet);
        } else {
            Violation violation = new Violation(checkName, 0, cancelled);

            Stream.of(violation).filter(toFilter).forEach(toSet);
            vls.add(violation);
        }
        violations.put(uuid, vls);
    }

    public boolean containsViolation(UUID uuid, String checkType) {
        return violations.containsKey(uuid) && violations.get(uuid).stream().anyMatch(vl -> vl.checkName.equals(checkType));
    }
}
