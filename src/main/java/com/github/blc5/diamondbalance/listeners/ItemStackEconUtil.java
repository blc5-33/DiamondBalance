package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.DiamondBalance;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static com.github.blc5.diamondbalance.DiamondBalance.materialValueMap;

public class ItemStackEconUtil
{
    public static boolean depositValuable(OfflinePlayer offlinePlayer, ItemStack itemStack) {
        if (!materialValueMap.containsKey(itemStack.getType().toString()))
            return false;

        DiamondBalance.econ.depositPlayer(offlinePlayer,
                materialValueMap.get(itemStack.getType().toString()) * itemStack.getAmount());
        return true;
    }

    public static boolean withdrawValuable(OfflinePlayer offlinePlayer, ItemStack itemStack) {
        if (!materialValueMap.containsKey(itemStack.getType().toString()))
            return false;

        DiamondBalance.econ.withdrawPlayer(offlinePlayer,
                materialValueMap.get(itemStack.getType().toString()) * itemStack.getAmount());
        return true;
    }

    public static void generateLore(ItemStack itemStack, Player player) {
        ArrayList<TextComponent> lore = new ArrayList<>();
        lore.add(Component.text(player.getName()).style(Style.style(TextDecoration.ITALIC)));
        lore.add(Component.text(player.getUniqueId().toString()).style(Style.style(TextColor.color(0x999999))));
        itemStack.lore(lore);
    }
}
