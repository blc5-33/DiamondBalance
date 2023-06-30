package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemDespawnEvent;

public class ItemDespawnListener implements Listener {
    @EventHandler
    public void onItemDespawn(ItemDespawnEvent e) {
        ItemStackEconUtil.processValuableDeduction(e.getEntity().getItemStack());
    }
}
