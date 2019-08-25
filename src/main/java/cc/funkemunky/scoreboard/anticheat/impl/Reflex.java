package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.anticheat.Anticheat;
import org.bukkit.event.EventHandler;
import rip.reflex.api.event.ReflexCheckEvent;

public class Reflex extends Anticheat {

    @EventHandler
    public void onEvent(ReflexCheckEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getCheat().name(), false, vl -> vl.checkName.equals(event.getCheat().name()), vl -> vl.vlCount+= event.getResult().getViolationsMod());
    }

    @Override
    public String getAnticheatName() {
        return "Reflex";
    }
}
