package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.DiamondBalance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class ItemPickupListener implements Listener
{
    private final DiamondBalance plugin;

    public ItemPickupListener(DiamondBalance plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onDiamondPickup(PlayerAttemptPickupItemEvent e) {
        ItemStack itemStack = e.getItem().getItemStack();
        if (itemStack.getType() != Material.DIAMOND && itemStack.getType() != Material.DIAMOND_BLOCK)
            return;
        Player player = e.getPlayer();
        PersistentDataContainer container = itemStack.getItemMeta().getPersistentDataContainer();

        // Diamonds being picked up do NOT have an associated UUID, most likely freshly mined, needs to have
        // the player UUID added
        if (!container.has(plugin.diamondUserKey, PersistentDataType.STRING)) {
            container.set(plugin.diamondUserKey, PersistentDataType.STRING, player.getUniqueId().toString());

            // Debugging, just to make sure it works
            player.sendMessage(Component.text(container.get(plugin.diamondUserKey, PersistentDataType.STRING)));

            ArrayList<TextComponent> lore = new ArrayList<>();
            lore.add(Component.text(player.getName()).color(TextColor.color(0x80A8BE)).style(Style.style(TextDecoration.ITALIC)));
            itemStack.lore(lore);
        }

    }
}
