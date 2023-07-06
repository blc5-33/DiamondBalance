# Diamond Balance Overview
This Minecraft plugin automatically attaches a username/UUID to any valuables defined in `config.yml`, when they are collected by the player.

This plugin depends on Vault to provide economy functionality. When valuables are collected by players, their Vault player banks will be adjusted based on the valuable they collect.

When the player trades their valuables with another person, their own balance will decrease, and the other player's balance will increase to the same amount. This represents a transaction. No `/pay` or `/deposit` or other commands need to be executed. HermitCraft-style diamond economies can stay intact.

When the player loses their valuables, from crafting, using in a beacon, burning them, tossing in the void, etc. their balance will automatically decrease.

To avoid players in Creative mode from unfairly gaining balance from valuables, any time a Creative player interacts with a config-defined valuable, it will automatically be devalued, and the valuable's associated user will have their balance deducted.

---

## `config.yml`
This file is quite simple to understand. The default configuration is:
```yml
ItemValues:
  # Use the Spigot Item identifiers
  DIAMOND: 1
  DIAMOND_BLOCK: 9
```
You assign items from the [Spigot Item Identifier](https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html) list a value, and then it works.

---
## Other Notes

This plugin is built for the Casual-Craft Minecraft server. A lot of useful features such as config reloading, other ways to consume items, and mass item devaluation (economy resets) are missing. Currently, they are not planned on being implemented.
