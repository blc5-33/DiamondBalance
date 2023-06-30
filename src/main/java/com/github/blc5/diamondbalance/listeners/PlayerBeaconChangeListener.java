package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import io.papermc.paper.event.player.PlayerChangeBeaconEffectEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.BeaconInventory;

public class PlayerBeaconChangeListener implements Listener {
    @EventHandler
    public void onPlayerBeaconEffectChange(PlayerChangeBeaconEffectEvent e) {
        ItemStackEconUtil.processValuableDeduction(
                ((BeaconInventory)e.getPlayer().getOpenInventory().getTopInventory()).getItem());
    }
}
