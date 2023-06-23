package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.DiamondBalance;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.UUID;

import static com.github.blc5.diamondbalance.listeners.ItemStackLoreUtil.generateLore;

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
        List<Component> currLore = itemStack.lore();

        if (currLore == null) {
            DiamondBalance.econ.depositPlayer(player, itemStack.getAmount());
            generateLore(itemStack, player);
            // Edge case here: if player picks up diamonds on the ground but can't hold all of em, the entire stack's
            // amount is still added to balance, but there's still leftovers on the ground. Despawn/item destruction
            // event handling means that these will be subtracted if they don't pick it up. So it should be alright.
        }
        else if (!currLore.get(1).toString().equals(player.getUniqueId().toString())) {
            OfflinePlayer other = plugin.getServer().getOfflinePlayer(UUID.fromString(currLore.get(1).toString()));
            DiamondBalance.econ.withdrawPlayer(other, itemStack.getAmount());
            DiamondBalance.econ.depositPlayer(player, itemStack.getAmount());
            generateLore(itemStack, player);
        }
        else if (!currLore.get(0).toString().equals(player.getName())) {
            generateLore(itemStack, player);
        }

    }
}
