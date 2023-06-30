package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerGameModeChangeListener implements Listener {
    @EventHandler
    public void onPlayerGameModeChange(PlayerGameModeChangeEvent e) {
        if (!e.getPlayer().isOnline())
            return;
        Player player = e.getPlayer();
        if (e.getNewGameMode() == GameMode.CREATIVE) {
            for (ItemStack itemStack : player.getInventory().getContents()) {
                if (itemStack != null)
                    ItemStackEconUtil.processValuableDeduction(itemStack);
            }
        }
    }
}
