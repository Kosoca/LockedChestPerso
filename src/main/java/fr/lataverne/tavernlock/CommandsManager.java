package fr.lataverne.tavernlock;

import fr.lataverne.commands.GroupsCommands;

import java.util.Objects;

public class CommandsManager {

    private final TavernLock plugin;

    public CommandsManager(TavernLock plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("tavernlock")).setExecutor(new GroupsCommands(plugin, TavernLock.getGroupsConfig()));
    }
    public TavernLock getPlugin() {return this.plugin;}
}

