package com.github.blc5.diamondbalance.listeners;

import com.destroystokyo.paper.event.entity.EntityRemoveFromWorldEvent;
import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class EntityRemoveFromWorldListener implements Listener {
    @EventHandler
    public void onEntityRemoveFromWorld(EntityRemoveFromWorldEvent e) {
        if (e.getEntity() instanceof Item)
            ItemStackEconUtil.processValuableDeduction(((Item) e.getEntity()).getItemStack());
    }
}
