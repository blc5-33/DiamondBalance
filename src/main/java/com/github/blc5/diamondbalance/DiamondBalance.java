package com.github.blc5.diamondbalance;

import com.github.blc5.diamondbalance.commands.CommandSetItemOwner;
import com.github.blc5.diamondbalance.listeners.ItemPickupListener;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Logger;

public final class DiamondBalance extends JavaPlugin {

    public final String cmdPrefix = "diabal";
    public final Logger logger = getServer().getLogger();
    public static Economy econ;
    public static @NotNull HashMap<String, Integer> materialValueMap = new HashMap<>();

    @Override
    public void onDisable() {
        logger.info(String.format("[%s] Disabled Version %s", getName(), getPluginMeta().getVersion()));
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        if (!setupEconomy()) {
            logger.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        registerListeners();
        registerCommands();
        registerMaterialValues();
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new ItemPickupListener(this), this);
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
                            logger.severe(String.format("[%s] Non-integer value %s for material %s!",
                                    getName(), value, key));
                    });
            if (materialValueMap.isEmpty())
                logger.warning("[%s] No material values registered! Is config.yml/ItemValues empty?");
        }
        catch (NullPointerException e)
        {
            logger.severe(String.format("[%s] Could not find material values configuration section.", getName()));
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
        logger.info(String.format("[%s] Vault dependency found!", getName()));
        econ = rsp.getProvider();
        return true;
    }
}
