package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinListener implements Listener {
    @EventHandler
    public void onInventoryOpen(PlayerJoinEvent e) {
        Player player = e.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null)
                    ItemStackEconUtil.processValuableDeduction(itemStack);
            }
        }

    }
}
