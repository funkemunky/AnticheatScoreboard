package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import space.fozzie.iris.api.event.IrisCheatEvent;

public class Iris extends Anticheat implements Listener {

    @EventHandler
    public void onEvent(IrisCheatEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getCheck().getName(), event.getCheck().isCancellable(), vl -> vl.checkName.equals(event.getCheck().getName()), vl -> vl.vlCount = event.getCheck().getVl(), event.getInformation());

        event.setCancelled(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).getAnticheatName().equals(getAnticheatName()));
    }

    @Override
    public String getAnticheatName() {
        return "Iris";
    }
}
