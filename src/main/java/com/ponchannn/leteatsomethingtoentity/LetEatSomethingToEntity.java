package com.ponchannn.leteatsomethingtoentity;

import org.bukkit.plugin.java.JavaPlugin;

public final class LetEatSomethingToEntity extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        getServer().getPluginManager().registerEvents(new Listeners(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
