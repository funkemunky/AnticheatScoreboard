package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.anticheat.Anticheat;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.fozzie.iris.api.event.IrisCheatEvent;

public class Iris extends Anticheat implements Listener {

    @EventHandler
    public void onEvent(IrisCheatEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getCheck().getName(), event.getCheck().isCancellable(), vl -> vl.checkName.equals(event.getCheck().getName()), vl -> vl.vlCount = event.getCheck().getVl());
    }

    @Override
    public String getAnticheatName() {
        return "Iris";
    }
}
