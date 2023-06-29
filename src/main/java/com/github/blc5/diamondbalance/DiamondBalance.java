package com.github.blc5.diamondbalance;

import com.github.blc5.diamondbalance.commands.CommandSetItemOwner;
import com.github.blc5.diamondbalance.listeners.InventoryItemClickListener;
import com.github.blc5.diamondbalance.listeners.ItemPickupListener;
import com.github.blc5.diamondbalance.listeners.PlayerBeaconChangeListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Server;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public final class DiamondBalance extends JavaPlugin {

    public final String cmdPrefix = "diabal";
    public static Logger logger;
    public static Economy econ;
    public static Server server;
    public static @NotNull HashMap<String, Integer> materialValueMap = new HashMap<>();


    @Override
    public void onEnable() {
        saveDefaultConfig();
        server = getServer();
        logger = getLogger();
        if (!setupEconomy()) {
            logger.severe("Disabled due to no Vault dependency found!");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        registerListeners();
        registerCommands();
        registerMaterialValues();
    }

    @Override
    public void onDisable() {
        logger.info(String.format("Disabled Version %s", getPluginMeta().getVersion()));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ItemPickupListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryItemClickListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerBeaconChangeListener(), this);
    }

    private void registerCommands() {
        getServer().getCommandMap().register(cmdPrefix, new CommandSetItemOwner(this));
    }

    private void registerMaterialValues() {
        try
        {
            Objects.requireNonNull(getConfig().getConfigurationSection("ItemValues")).getValues(false)
                    .forEach((key, value) ->
                    {
                        if (value instanceof Integer)
                            materialValueMap.put(key, (Integer) value);
                        else
                            logger.severe(String.format("Non-integer value %s for material %s!",
                                    value, key));
                    });
            if (materialValueMap.isEmpty())
                logger.warning("No material values registered! Is config.yml/ItemValues empty?");
        }
        catch (NullPointerException e)
        {
            logger.severe("Could not find material values configuration section.");
        }
    }


    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        logger.info("Vault dependency found!");
        econ = rsp.getProvider();
        return true;
    }
}
