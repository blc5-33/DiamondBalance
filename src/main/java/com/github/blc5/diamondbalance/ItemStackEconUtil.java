package com.github.blc5.diamondbalance;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ItemStackEconUtil
{
    public static boolean depositValuable(OfflinePlayer offlinePlayer, ItemStack itemStack) {
        if (!DiamondBalance.materialValueMap.containsKey(itemStack.getType().toString()))
            return false;

        DiamondBalance.logger.info(String.format(
                "[%s] Player: %s , Amount to be deposited: %d",
                DiamondBalance.server.getName(),
                offlinePlayer.getName(),
                DiamondBalance.materialValueMap.get(itemStack.getType().toString()) * itemStack.getAmount())
        );
        DiamondBalance.econ.depositPlayer(offlinePlayer,
                DiamondBalance.materialValueMap.get(itemStack.getType().toString()) * itemStack.getAmount());
        return true;
    }

    public static boolean withdrawValuable(OfflinePlayer offlinePlayer, ItemStack itemStack) {
        if (!isRegisteredValuable(itemStack))
            return false;

        DiamondBalance.econ.withdrawPlayer(offlinePlayer,
                DiamondBalance.materialValueMap.get(itemStack.getType().toString()) * itemStack.getAmount());
        return true;
    }

    public static void processValuableTransfer(OfflinePlayer offlinePlayer, ItemStack itemStack) {
        if (! isRegisteredValuable(itemStack))
            return;

        List<Component> currLore = itemStack.lore();
        if (currLore == null) {
            depositValuable(offlinePlayer, itemStack);
            generateLore(offlinePlayer, itemStack);
            // Edge case here: if player picks up diamonds on the ground but can't hold all of em, the entire stack's
            // amount is still added to balance, but there's still leftovers on the ground. Despawn/item destruction
            // event handling means that these will be subtracted if they don't pick it up. So it should be alright.
        }
        else if (! ((TextComponent)currLore.get(1)).content().equals(offlinePlayer.getUniqueId().toString())) {
            OfflinePlayer other = DiamondBalance.server.getOfflinePlayer(UUID.fromString(
                    ((TextComponent)currLore.get(1)).content()));
            withdrawValuable(other, itemStack);
            depositValuable(offlinePlayer, itemStack);
            generateLore(offlinePlayer, itemStack);
        }
        else if (! ((TextComponent)currLore.get(0)).content().equals(offlinePlayer.getName())) {
            generateLore(offlinePlayer, itemStack);
        }
    }

    public static boolean isRegisteredValuable(ItemStack itemStack) {
        return DiamondBalance.materialValueMap.containsKey(itemStack.getType().toString());
    }

    public static void generateLore(OfflinePlayer player, ItemStack itemStack) {
        ArrayList<TextComponent> lore = new ArrayList<>();
        lore.add(Component.text(player.getName() != null ? player.getName() : "N/A").style(Style.style(TextDecoration.ITALIC)));
        lore.add(Component.text(player.getUniqueId().toString()).style(Style.style(TextColor.color(0x999999))));
        itemStack.lore(lore);
    }
}
