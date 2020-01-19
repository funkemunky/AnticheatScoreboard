package cc.funkemunky.scoreboard.anticheats;

import cc.funkemunky.api.events.AtlasListener;
import cc.funkemunky.api.events.Listen;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.api.utils.RunUtils;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.listeners.custom.AnticheatFlagEvent;
import cc.funkemunky.scoreboard.wrapper.CheckWrapper;
import dev.brighten.api.listener.KauriFlagEvent;
import org.bukkit.Bukkit;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Init(requirePlugins = {"KauriLoader", "Kauri"})
public class Kauri implements AtlasListener {

    @Listen
    public void onCheat(KauriFlagEvent event) {
        FutureTask<Boolean> future = new FutureTask<>(() -> {
            AnticheatFlagEvent afe = new AnticheatFlagEvent("Kauri", event.player,
                    new CheckWrapper(event.check.getName(),
                            event.check.isEnabled(),
                            false,
                            event.check.getCheckType().name()),
                    event.check.getVl(),
                    event.information);

            Bukkit.getPluginManager().callEvent(afe);
            return afe.isCancelled();
        });

        if(Thread.currentThread() == AnticheatScoreboard.INSTANCE.primaryThread) {
            future.run();
        } else {
            RunUtils.task(future, AnticheatScoreboard.INSTANCE);
        }

        try {
            event.setCancelled(future.get(60L, TimeUnit.MILLISECONDS));
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            //Empty catch
        }
    }
}
