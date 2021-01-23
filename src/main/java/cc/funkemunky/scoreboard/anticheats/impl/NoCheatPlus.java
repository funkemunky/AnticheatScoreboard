package cc.funkemunky.scoreboard.anticheats.impl;

import cc.funkemunky.api.Atlas;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.config.GeneralConfig;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import fr.neatmonster.nocheatplus.actions.ParameterName;
import fr.neatmonster.nocheatplus.checks.CheckType;
import fr.neatmonster.nocheatplus.checks.access.IViolationInfo;
import fr.neatmonster.nocheatplus.hooks.NCPHook;
import fr.neatmonster.nocheatplus.hooks.NCPHookManager;
import org.bukkit.entity.Player;

@Init(requirePlugins = {"NoCheatPlus"})
public class NoCheatPlus extends Anticheat implements NCPHook {

    public NoCheatPlus() {
        super("NoCheatPlus");
        
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
        AnticheatFlagEvent afe = new AnticheatFlagEvent(this, player,
                new CheckWrapper(checkType.getName(), iViolationInfo.hasLogAction(), iViolationInfo.willCancel(),
                        checkType.getParent().getName()),
                (float)iViolationInfo.getTotalVl(), iViolationInfo.getParameter(ParameterName.TAGS));

        Atlas.getInstance().getEventManager().callEvent(afe);


        return afe.isCancelled();
    }


    @Override
    public void enableForPlayer(Player player) {
        if(!GeneralConfig.testMode) return;
        AnticheatScoreboard.INSTANCE.permission.playerAdd(player, "nocheatplus.shortcut.bypass");
        AnticheatScoreboard.INSTANCE.permission.playerAdd(player, "nocheatplus.shortcut.bypass.*");

        super.enableForPlayer(player);
    }

    @Override
    public void disableForPlayer(Player player) {
        if(!GeneralConfig.testMode) return;
        AnticheatScoreboard.INSTANCE.permission.playerRemove(player, "nocheatplus.shortcut.bypass");
        AnticheatScoreboard.INSTANCE.permission.playerRemove(player, "nocheatplus.shortcut.bypass.*");

        super.disableForPlayer(player);
    }
}
