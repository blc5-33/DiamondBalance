package com.github.blc5.diamondbalance.commands;

//import com.github.blc5.diamondbalance.DiamondBalance;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
//import org.bukkit.entity.Player;
//import org.bukkit.inventory.meta.ItemMeta;
//import org.bukkit.persistence.PersistentDataContainer;
//import org.bukkit.persistence.PersistentDataType;
//import org.jetbrains.annotations.NotNull;

//public class CommandCheckUUID extends Command
//{
//    private final DiamondBalance plugin;
//
//    public CommandCheckUUID (DiamondBalance p){
//        super("checkUUID");
//        this.plugin = p;
//    }
//
//    @Override
//    public boolean execute(@NotNull CommandSender sender,@NotNull String label, String[] args) {
//        if (sender instanceof Player) {
//            Player player = (Player) sender;
//            ItemMeta ism = player.getInventory().getItemInMainHand().getItemMeta();
//            if (ism != null) {
//                PersistentDataContainer pdc = ism.getPersistentDataContainer();
//                if (pdc.has(plugin.diamondUserKey, PersistentDataType.STRING))
//                    player.sendMessage("[DiamondBalance] " + pdc.get(plugin.diamondUserKey, PersistentDataType.STRING));
//                else
//                    player.sendMessage("[DiamondBalance] No player UUID associated with this diamond.");
//            }
//            else
//                player.sendMessage("[DiamondBalance] You're not holding anything");
//        }
//        else {
//            plugin.getLogger().warning("[DiamondBalance] Sorry, console cannot run this command!");
//        }
//        return true;
//    }
//}
