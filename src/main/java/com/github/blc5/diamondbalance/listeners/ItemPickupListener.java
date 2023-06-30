package com.github.blc5.diamondbalance.listeners;


import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;

public class ItemPickupListener implements Listener
{
    @EventHandler
    public void onValuablePickup(PlayerAttemptPickupItemEvent e) {
        if (e.getPlayer().getGameMode() == GameMode.CREATIVE)
            return;
        ItemStackEconUtil.processValuableTransfer(e.getPlayer(), e.getItem().getItemStack());
    }
}
