package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;


public class CraftItemListener implements Listener {
    @EventHandler
    public void onItemCraft(CraftItemEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;
        Player player = (Player) e.getWhoClicked();
        ItemStack result = e.getInventory().getResult();

        int amountCrafts = Integer.MAX_VALUE;
        for (ItemStack itemStack : e.getInventory().getMatrix()) {
            if (itemStack != null)
                if (itemStack.getAmount() < amountCrafts)
                    amountCrafts = itemStack.getAmount();
        }

        for (ItemStack itemStack : e.getInventory().getMatrix()) {
            if (itemStack != null)
                ItemStackEconUtil.processValuableDeduction(itemStack, itemStack.getAmount() - amountCrafts);
        }

        ItemStackEconUtil.processValuableTransfer(player, result);
    }
}
