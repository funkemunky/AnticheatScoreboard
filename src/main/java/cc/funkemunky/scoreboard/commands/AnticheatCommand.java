package cc.funkemunky.scoreboard.commands;

import cc.funkemunky.api.commands.ancmd.Command;
import cc.funkemunky.api.commands.ancmd.CommandAdapter;
import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.Init;
import cc.funkemunky.api.utils.ItemBuilder;
import cc.funkemunky.api.utils.MiscUtils;
import cc.funkemunky.scoreboard.AnticheatScoreboard;
import cc.funkemunky.scoreboard.anticheats.Anticheat;
import cc.funkemunky.scoreboard.utils.menu.button.Button;
import cc.funkemunky.scoreboard.utils.menu.preset.button.FillerButton;
import cc.funkemunky.scoreboard.utils.menu.type.impl.ChestMenu;
import dev.brighten.api.KauriAPI;
import dev.brighten.api.KauriVersion;
import lombok.val;
import lombok.var;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Init(commands = true)
public class AnticheatCommand {

    @Command(name = "as.anticheat", description = "Choose an anticheat to use.",
            aliases = {"anticheat", "ac", "pac", "asac", "pickac"},
            playerOnly = true)
    public void onCommand(CommandAdapter cmd) {
        cmd.getSender().sendMessage(Color.Gray + "Opening menu...");
        val menu = getACMenu(cmd.getPlayer());

        menu.showMenu(cmd.getPlayer());
    }

    private static ChestMenu getACMenu(Player player) {
        ChestMenu menu = new ChestMenu("Select an Anticheat", 3);

        updateButtons(player, menu);

        menu.buildInventory(true);

        return menu;
    }

    private static void updateButtons(Player player, ChestMenu menu) {
        for (int i = 0; i < menu.contents.length; i++) {
            menu.contents = new Button[menu.getMenuDimension().getSize()];
        }
        Anticheat.anticheats.forEach(anticheat -> {
            val plugin = Bukkit.getPluginManager().getPlugin(anticheat.pluginName);
            boolean enabled = anticheat.pluginName.equals("Vanilla") || (plugin != null && plugin.isEnabled());
            boolean selected = anticheat.inUse.contains(player);

            var item = new ItemBuilder(Material.getMaterial(enabled ? 340 : 339))  //Book = enabled, Paper = disabled.
                    .amount(1).name((selected ? "&a" : "&6") + anticheat.name);

            if(plugin != null && plugin.getDescription() != null) {
                List<String> depends = new ArrayList<>();

                plugin.getDescription().getDepend().forEach(depend -> depends.add("&8- &f" + depend));
                plugin.getDescription().getSoftDepend().forEach(depend -> depends.add("&8- &f&o" + depend));

                List<String> lore = new ArrayList<>(Arrays.asList("",
                        "&eBy&8: &f" + String.join("&7, &f", plugin.getDescription().getAuthors()),
                        "&7Version&8: &f" + (anticheat.name.equals("Kauri") ? KauriVersion.getVersion()
                                : plugin.getDescription().getVersion()), "&7Description&8:"));

                if(plugin.getDescription().getDescription() != null && plugin.getDescription()
                        .getDescription().length() > 0) {
                    lore.addAll(Arrays.asList(MiscUtils.splitIntoLine(plugin.getDescription().getDescription(),
                            30)));
                } else lore.add("&fNone");
                lore.add("&7Depends&8:");
                lore.addAll(depends);
                item = item
                        .lore(lore.stream().map(Color::translate).toArray(String[]::new));
            }

            if(enabled) {
                Button button = new Button(false, item.build(), (pl, info) -> {

                    Anticheat.anticheats.forEach(ac -> ac.disableForPlayer(pl));
                    anticheat.enableForPlayer(pl);

                    updateButtons(pl, (ChestMenu)info.getMenu());

                    menu.buildInventory(false);
                });

                menu.addItem(button);
            }
        });
        menu.fill(new FillerButton());
    }
}
