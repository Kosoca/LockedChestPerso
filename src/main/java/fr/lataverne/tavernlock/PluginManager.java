package fr.lataverne.tavernlock;

import fr.lataverne.actions.ChestEvent;

public class PluginManager {

    private final TavernLock plugin;

    public PluginManager(TavernLock plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(new ChestEvent(), plugin);
    }

    public TavernLock getPlugin() {return this.plugin;}

}
