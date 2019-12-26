package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import fr.neatmonster.nocheatplus.actions.ParameterName;
import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import org.bukkit.entity.Player;

public class NoCheatPlus implements NCPHook {

    public NoCheatPlus() {
        NCPHookManager.addHook(CheckType.ALL, this);
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
        AnticheatFlagEvent afe = new AnticheatFlagEvent("NoCheatPlus", player,
                new CheckWrapper(checkType.getName(), iViolationInfo.hasLogAction(), iViolationInfo.willCancel(),
                        checkType.getType().name()),
                (float)iViolationInfo.getTotalVl(), iViolationInfo.getParameter(ParameterName.TAGS));

        Atlas.getInstance().getEventManager().callEvent(afe);
        return !afe.isCancelled();
    }
}
