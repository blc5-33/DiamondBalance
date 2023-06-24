package com.github.blc5.diamondbalance;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStackEconUtil
{
    public static boolean depositValuable(OfflinePlayer offlinePlayer, ItemStack itemStack) {
        if (!DiamondBalance.materialValueMap.containsKey(itemStack.getType().toString()))
            return false;

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

    public static boolean isRegisteredValuable(ItemStack itemStack) {
        return DiamondBalance.materialValueMap.containsKey(itemStack.getType().toString());
    }

    public static void generateLore(ItemStack itemStack, OfflinePlayer player) {
        ArrayList<TextComponent> lore = new ArrayList<>();
        lore.add(Component.text(player.getName() != null ? player.getName() : "N/A").style(Style.style(TextDecoration.ITALIC)));
        lore.add(Component.text(player.getUniqueId().toString()).style(Style.style(TextColor.color(0x999999))));
        itemStack.lore(lore);
    }
}
