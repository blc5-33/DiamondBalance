//package com.github.blc5.diamondbalance.listeners;
//
//import com.github.blc5.diamondbalance.DiamondBalance;
//import com.github.blc5.diamondbalance.ItemStackEconUtil;
//import io.papermc.paper.event.player.PlayerPurchaseEvent;
//import org.bukkit.event.EventHandler;
//import org.bukkit.event.Listener;
//import org.bukkit.inventory.ItemStack;
//
//// For villagers
//public class PlayerPurchaseListener implements Listener {
//    @EventHandler
//    public void onPlayerPurchase(PlayerPurchaseEvent e) {
//        ItemStack ingredient1 = e.getTrade().getAdjustedIngredient1(),
//                ingredient2 = null;
//        if (e.getTrade().getIngredients().size() > 1)
//            ingredient2 = e.getTrade().getIngredients().get(1);
//
//        DiamondBalance.logger.info("Ingredient 1: " + ingredient1);
//        if (ingredient2 != null)
//            DiamondBalance.logger.info("Ingredient 2: " + ingredient2);
//
//        ItemStackEconUtil.processValuableDeduction(ingredient1);
//        if (ingredient2 != null)
//            ItemStackEconUtil.processValuableDeduction(ingredient2);
//    }
//}
