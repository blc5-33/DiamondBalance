package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class InventoryItemClickListener implements Listener
{
    private final HashSet<InventoryAction> validAsyncActions;
    private final HashSet<InventoryAction> validInstantActions;
//    private final HashMap<InventoryAction, String> debugClickTypeMap;

    public InventoryItemClickListener() {
        validAsyncActions = new HashSet<>();
        validInstantActions = new HashSet<>();

        validInstantActions.add(InventoryAction.HOTBAR_MOVE_AND_READD);
        validInstantActions.add(InventoryAction.HOTBAR_SWAP);
        validInstantActions.add(InventoryAction.MOVE_TO_OTHER_INVENTORY);
        validAsyncActions.add(InventoryAction.PLACE_ONE);
        validAsyncActions.add(InventoryAction.PLACE_SOME);
        validAsyncActions.add(InventoryAction.PLACE_ALL);
        validAsyncActions.add(InventoryAction.SWAP_WITH_CURSOR);

//        debugClickTypeMap = new HashMap<>();
//        debugClickTypeMap.put(InventoryAction.CLONE_STACK, "Clone Stack");
//        debugClickTypeMap.put(InventoryAction.COLLECT_TO_CURSOR, "Collect to Cursor");
//        debugClickTypeMap.put(InventoryAction.DROP_ALL_CURSOR, "Drop All Cursor");
//        debugClickTypeMap.put(InventoryAction.DROP_ALL_SLOT, "Drop All Slot");
//        debugClickTypeMap.put(InventoryAction.DROP_ONE_CURSOR, "Drop One Cursor");
//        debugClickTypeMap.put(InventoryAction.DROP_ONE_SLOT, "Drop One Slot");
//        debugClickTypeMap.put(InventoryAction.HOTBAR_MOVE_AND_READD, "Hotbar Move and Readd");
//        debugClickTypeMap.put(InventoryAction.HOTBAR_SWAP, "Hotbar Swap");
//        debugClickTypeMap.put(InventoryAction.MOVE_TO_OTHER_INVENTORY, "Move to Other Inventory");
//        debugClickTypeMap.put(InventoryAction.NOTHING, "Nothing");
//        debugClickTypeMap.put(InventoryAction.PICKUP_ALL, "Pickup All");
//        debugClickTypeMap.put(InventoryAction.PICKUP_HALF, "Pickup Half");
//        debugClickTypeMap.put(InventoryAction.PICKUP_ONE, "Pickup One");
//        debugClickTypeMap.put(InventoryAction.PICKUP_SOME, "Pickup Some");
//        debugClickTypeMap.put(InventoryAction.PLACE_ALL, "Place All");
//        debugClickTypeMap.put(InventoryAction.PLACE_ONE, "Place One");
//        debugClickTypeMap.put(InventoryAction.PLACE_SOME, "Place Some");
//        debugClickTypeMap.put(InventoryAction.SWAP_WITH_CURSOR, "Swap with Cursor");
//        debugClickTypeMap.put(InventoryAction.UNKNOWN, "Unknown");
    }
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) e.getWhoClicked();

        if (player.getGameMode() == GameMode.CREATIVE) {
            ItemStackEconUtil.processValuableDeduction(e.getCursor());
            return;
        }
        else if (player.getGameMode() == GameMode.SPECTATOR)
            return;

//            DiamondBalance.logger.info(String.format("Click Type: %s", debugClickTypeMap.get(e.getAction())));
        if (validInstantActions.contains(e.getAction()))
            ItemStackEconUtil.processValuableTransfer(player, e.getCurrentItem());
        else if (validAsyncActions.contains(e.getAction()) && e.getClickedInventory() == player.getInventory())
            ItemStackEconUtil.processValuableTransfer(player, e.getCursor());

        // Special inventory processing
        Inventory otherInventory = e.getClickedInventory();
        if (otherInventory instanceof MerchantInventory &&
                e.getSlot() == 2 &&
                otherInventory.getItem(2) != null) {
            ItemStack [] contents = otherInventory.getContents();
            for (ItemStack itemStack : Arrays.stream(contents).limit(2).collect(Collectors.toList())) {
                if (itemStack != null)
                    ItemStackEconUtil.processValuableDeduction(itemStack);
            }

//            MerchantRecipe merchantRecipe = ((MerchantInventory) otherInventory).getSelectedRecipe();
//            if (merchantRecipe == null)
//                return;
//
//            ItemStack recipeIngredient1 = merchantRecipe.getAdjustedIngredient1(),
//                    recipeIngredient2 = null;
//            if (merchantRecipe.getIngredients().get(1) != null)
//                recipeIngredient2 = merchantRecipe.getIngredients().get(1);
//
//            if (!e.isShiftClick()) {
//                if (contents[0] != null && recipeIngredient1 != null)
//                    ItemStackEconUtil.processValuableDeduction(contents[0],
//                            contents[0].getAmount() - recipeIngredient1.getAmount());
//                if (contents[1] != null && recipeIngredient2 != null)
//                    ItemStackEconUtil.processValuableDeduction(contents[1],
//                            contents[1].getAmount() - recipeIngredient2.getAmount());
//            }
//            else {
//                if (contents[0] != null && recipeIngredient1 != null)
//                    ItemStackEconUtil.processValuableDeduction(contents[0],
//                            contents[0].getAmount() -
//                                    (contents[0].getAmount()/recipeIngredient1.getAmount()) * recipeIngredient1.getAmount());
//                if (contents[1] != null && recipeIngredient2 != null)
//                    ItemStackEconUtil.processValuableDeduction(contents[1],
//                            contents[1].getAmount() -
//                                    (contents[1].getAmount()/recipeIngredient2.getAmount()) * recipeIngredient2.getAmount());
        }
        else if (otherInventory instanceof AnvilInventory &&
                e.getSlotType() == InventoryType.SlotType.RESULT) {
            AnvilInventory anvilInventory = (AnvilInventory) otherInventory;
            if (anvilInventory.getResult() == null)
                return;

            ItemStackEconUtil.processValuableDeduction(anvilInventory.getFirstItem());
            if (anvilInventory.getSecondItem() != null)
                ItemStackEconUtil.processValuableDeduction(anvilInventory.getSecondItem());
        }
        else if (otherInventory instanceof SmithingInventory &&
                e.getSlotType() == InventoryType.SlotType.RESULT) {
            SmithingInventory smithingInventory = (SmithingInventory) otherInventory;
            if (smithingInventory.getResult() == null)
                return;

            if (smithingInventory.getInputTemplate() != null)
                ItemStackEconUtil.processValuableDeduction(smithingInventory.getInputTemplate());
            if (smithingInventory.getInputEquipment() != null)
                ItemStackEconUtil.processValuableDeduction(smithingInventory.getInputEquipment());
            if (smithingInventory.getInputMineral() != null)
                ItemStackEconUtil.processValuableDeduction(smithingInventory.getInputMineral());
        }
    }
}
