package cc.funkemunky.scoreboard.anticheats.impl;

import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import org.bukkit.entity.Player;

@Init
public class Vanilla extends Anticheat {

    public Vanilla() {
        super("Vanilla");
    }

    @Override
    public void enableForPlayer(Player player) {
        Anticheat.anticheats.forEach(ac -> ac.disableForPlayer(player));

        super.enableForPlayer(player);
    }
}
