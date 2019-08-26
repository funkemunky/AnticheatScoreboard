package cc.funkemunky.scoreboard.anticheat.impl;

import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheat.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import org.bukkit.entity.Player;

public class NoCheatPlus extends Anticheat implements NCPHook {

    public NoCheatPlus() {
        NCPHookManager.addHook(CheckType.ALL, this);
    }

    @Override
    public String getAnticheatName() {
        return "NoCheatPlus";
    }

    @Override
    public String getHookName() {
        return "NCPScoreboard";
    }

    @Override
    public String getHookVersion() {
        return "1.0";
    }

    @Override
    public boolean onCheckFailure(CheckType checkType, Player player, IViolationInfo iViolationInfo) {
        addViolation(player.getUniqueId(), checkType.getName(), iViolationInfo.willCancel(), vl -> vl.checkName.equals(checkType.getName()), vl -> vl.vlCount = iViolationInfo.getTotalVl(), "none");

        return GeneralConfig.testMode && !AnticheatScoreboard.INSTANCE.anticheatManager.anticheatMap.get(player.getUniqueId()).getAnticheatName().equals(getAnticheatName());
    }
}
