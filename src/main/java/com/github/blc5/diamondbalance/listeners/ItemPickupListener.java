package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.DiamondBalance;
import net.kyori.adventure.text.Component;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

public class ItemPickupListener implements Listener
{
    private final DiamondBalance plugin;

    public ItemPickupListener(DiamondBalance plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onValuablePickup(PlayerAttemptPickupItemEvent e) {
        ItemStack itemStack = e.getItem().getItemStack();
        if (! DiamondBalance.materialValueMap.containsKey(itemStack.getType().toString()))
            return;
        Player player = e.getPlayer();
        List<Component> currLore = itemStack.lore();

        if (currLore == null) {
            ItemStackEconUtil.depositValuable(player, itemStack);
            ItemStackEconUtil.generateLore(itemStack, player);
            // Edge case here: if player picks up diamonds on the ground but can't hold all of em, the entire stack's
            // amount is still added to balance, but there's still leftovers on the ground. Despawn/item destruction
            // event handling means that these will be subtracted if they don't pick it up. So it should be alright.
        }
        else if (!currLore.get(1).toString().equals(player.getUniqueId().toString())) {
            OfflinePlayer other = plugin.getServer().getOfflinePlayer(UUID.fromString(currLore.get(1).toString()));
            ItemStackEconUtil.withdrawValuable(other, itemStack);
            ItemStackEconUtil.depositValuable(player, itemStack);
            ItemStackEconUtil.generateLore(itemStack, player);
        }
        else if (!currLore.get(0).toString().equals(player.getName())) {
            ItemStackEconUtil.generateLore(itemStack, player);
        }

    }
}
