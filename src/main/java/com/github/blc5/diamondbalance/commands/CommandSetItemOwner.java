package com.github.blc5.diamondbalance.commands;

import com.github.blc5.diamondbalance.DiamondBalance;
import com.github.blc5.diamondbalance.ItemStackEconUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

public class CommandSetItemOwner extends Command
{
    private final DiamondBalance plugin;
    public CommandSetItemOwner(DiamondBalance plugin) {
        super("dbsetitemowner", "Sets the held item's owner to the specified player/UUID"
                , "name: player name", Collections.emptyList());
        this.plugin = plugin;
    }

    public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {

        if (sender instanceof Player)
        {
            if (args.length == 0)
            {
                sender.sendMessage(Component.text(String.format("[%s] No player name provided!",
                        plugin.getName())).color(NamedTextColor.YELLOW));
                return false;
            }
            else if (args.length > 1)
            {
                sender.sendMessage(Component.text(String.format("[%s] Too many arguments!",
                        plugin.getName())).color(NamedTextColor.YELLOW));
                return false;
            }

            Player player = (Player) sender;
            OfflinePlayer specifiedPlayer = plugin.getServer().getOfflinePlayer(args[0]);
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            if (ItemStackEconUtil.isRegisteredValuable(itemStack))
            {
                if (!DiamondBalance.econ.hasAccount(specifiedPlayer))
                {
                    player.sendMessage(Component.text(String.format("[%s] Player does not have player bank account.",
                            plugin.getName())).color(NamedTextColor.YELLOW));

                    if (DiamondBalance.econ.createPlayerAccount(specifiedPlayer))
                    {
                        player.sendMessage(Component.text(String.format("[%s] Player account successfully created.",
                                plugin.getName())).color(NamedTextColor.GREEN));
                    }
                    else
                        player.sendMessage(Component.text(String.format(
                                "[%s] Could not create player account! Action failed.",
                                plugin.getName())).color(NamedTextColor.RED));
                }

                if (DiamondBalance.econ.hasAccount(specifiedPlayer))
                {
                    player.sendMessage(Component.text(String.format("[%s] Item lore owner updated.",
                            plugin.getName())).color(NamedTextColor.GREEN));
                    ItemStackEconUtil.processValuableTransfer(specifiedPlayer, itemStack);
                }
            }
            else
                player.sendMessage(Component.text(String.format("[%s] This item does not have an assigned value!",
                        plugin.getName())).color(NamedTextColor.YELLOW));
        }
        else
            sender.sendMessage(Component.text(String.format(
                    "[%s] Only players can execute this command!",
                    plugin.getName())).color(NamedTextColor.YELLOW));
        return true;
    }

}
