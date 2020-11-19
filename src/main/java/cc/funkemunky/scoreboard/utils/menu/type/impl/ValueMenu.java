package cc.funkemunky.scoreboard.utils.menu.type.impl;

import cc.funkemunky.api.utils.ItemBuilder;
import cc.funkemunky.scoreboard.utils.menu.MenuListener;
import cc.funkemunky.scoreboard.utils.menu.button.Button;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;

import java.util.function.BiConsumer;

public class ValueMenu<T> extends ChestMenu {

    public BiConsumer<Player, T> consumer;

    public ValueMenu(BiConsumer<Player, T> consumer) {
        super("&7Value Menu", 1);

        Button valButton = new Button(false,
                new ItemBuilder(Material.BOOK).name("&eEnter Value").amount(1).build(),
                (player, info) -> {
                    AnvilInventory anvil = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL, "Enter Value");

                    anvil.setItem(0, new ItemBuilder(Material.STAINED_GLASS_PANE).amount(1).name("  ").build());

                    player.openInventory(anvil);
                    MenuListener.anvils.put(anvil, this);
                });

        this.consumer = consumer;

        setItem(4, valButton);
    }
}
