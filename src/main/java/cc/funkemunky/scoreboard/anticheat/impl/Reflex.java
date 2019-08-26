package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import org.bukkit.event.EventHandler;
import rip.reflex.api.event.ReflexCheckEvent;

import java.util.stream.Collectors;

public class Reflex extends Anticheat {

    @EventHandler
    public void onEvent(ReflexCheckEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getCheat().name(), false, vl -> vl.checkName.equals(event.getCheat().name()), vl -> vl.vlCount+= event.getResult().getViolationsMod(), String.join(" ", event.getResult().getTags()));

        event.setCancelled(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).getAnticheatName().equals(getAnticheatName()));
    }

    @Override
    public String getAnticheatName() {
        return "Reflex";
    }
}
