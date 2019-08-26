package cc.funkemunky.scoreboard.config;

import cc.funkemunky.api.utils.ConfigSetting;
import cc.funkemunky.api.utils.Init;

@Init
public class GeneralConfig {

    @ConfigSetting(name = "testmode")
    public static boolean testMode = false;

    @ConfigSetting(path = "testmode", name = "alerts")
    public static String alertMessage = "&8[&c%anticheat%&8] &f%player% &7failed &f%check% &8[&etags=&f%tags%&8] &9(%vl%)";

}
