package com.github.blc5.diamondbalance;

//import com.github.blc5.diamondbalance.commands.CommandCheckUUID;
import com.github.blc5.diamondbalance.listeners.ItemPickupListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class DiamondBalance extends JavaPlugin {

    private final Logger log = getServer().getLogger();
    public static Economy econ;

    @Override
    public void onDisable() {
        log.info(String.format("[%s] Disabled Version %s", getName(), getDescription().getVersion()));
    }

    @Override
    public void onEnable() {
        if (!setupEconomy()) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        log.info("Enabled DiamondBalance plugin v1.0-SNAPSHOT");

        getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        log.info("[DiamondBalance] Vault dependency found!");
        econ = rsp.getProvider();
        return true;
    }

}
