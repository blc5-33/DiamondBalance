package com.github.blc5.diamondbalance.listeners;

import com.github.blc5.diamondbalance.ItemStackEconUtil;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.HashSet;

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
    public void onInventoryItemMove(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player))
            return;

        Player player = (Player) e.getWhoClicked();

        // Creative player click events are ALWAYS PLACE_ALL
        if (player.getGameMode() == GameMode.CREATIVE ||
            player.getGameMode() == GameMode.SPECTATOR)
            return;

//            DiamondBalance.logger.info(String.format("Click Type: %s", debugClickTypeMap.get(e.getAction())));
        if (validInstantActions.contains(e.getAction()))
            ItemStackEconUtil.processValuableTransfer(player, e.getCurrentItem());
        else if (validAsyncActions.contains(e.getAction()) && e.getClickedInventory() == player.getInventory())
            ItemStackEconUtil.processValuableTransfer(player, e.getCursor());

    }
}
