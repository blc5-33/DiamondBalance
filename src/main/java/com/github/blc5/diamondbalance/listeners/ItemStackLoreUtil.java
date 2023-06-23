package com.github.blc5.diamondbalance.listeners;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class ItemStackLoreUtil
{
    public static void generateLore(ItemStack itemStack, Player player) {
        ArrayList<TextComponent> lore = new ArrayList<>();
        lore.add(Component.text(player.getName()).style(Style.style(TextDecoration.ITALIC)));
        lore.add(Component.text(player.getUniqueId().toString()).style(Style.style(TextColor.color(0x999999))));
        itemStack.lore(lore);
    }
}
