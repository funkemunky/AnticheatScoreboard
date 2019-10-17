package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.api.utils.Color;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import me.konsolas.aac.api.PlayerViolationCommandEvent;
import me.konsolas.aac.api.PlayerViolationEvent;
import org.bukkit.event.EventHandler;

public class AAC extends Anticheat {

    @EventHandler
    public void onEvent(PlayerViolationEvent event) {
        addViolation(event.getPlayer().getUniqueId(), event.getHackType().getName(), event.isCancelled(), vl -> vl.checkName.equals(event.getHackType().getName()), vl -> vl.vlCount = event.getViolations(), event.getMessage());


        event.setViolations(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).equals(getAnticheatName()) ? 0 : event.getViolations());
        event.setCancelled(GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(event.getPlayer().getUniqueId()).equals(getAnticheatName()));
    }

    @EventHandler
    public void onEvent(PlayerViolationCommandEvent event) {
        event.getPlayer().sendMessage(Color.translate("&7You would have been kicked for &e" + event.getHackType().name() + "&7."));
        event.setCancelled(true);
    }

    @Override
    public String getAnticheatName() {
        return "AAC";
    }
}
