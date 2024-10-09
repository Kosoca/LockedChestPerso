package fr.lataverne.tavernlock;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class TavernLock extends JavaPlugin implements Listener {

    private static TavernLock TAVERNLOCK;
    private static File dataFile;
    private static File groupsFile;

    private static FileConfiguration dataConfig;
    private static FileConfiguration groupsConfig;

    private CommandsManager commandsManager;
    private PluginManager pluginManager;

    @Override
    public void onEnable() {
        TAVERNLOCK = this;
        loadDefaultConfig();

        dataFile = new File(getDataFolder(), "data.yml");
        dataConfig = loadYAMLFile(dataFile);

        groupsFile = new File(getDataFolder(), "groups.yml");
        groupsConfig = loadYAMLFile(groupsFile);

        this.pluginManager = new PluginManager(TAVERNLOCK);
        this.commandsManager = new CommandsManager(TAVERNLOCK);
    }

    private FileConfiguration loadYAMLFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return YamlConfiguration.loadConfiguration(file);
    }

    public static void saveDataFile() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveGroupsFile() {
        try {
            groupsConfig.save(groupsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static FileConfiguration getDataConfig() {
        return dataConfig;
    }

    public static FileConfiguration getGroupsConfig() {
        return groupsConfig;
    }

    private void loadDefaultConfig() {
        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
    }


    public CommandsManager getCommandsManager() {return commandsManager;}

    public PluginManager getPluginManager() {return pluginManager;}

    public static TavernLock getTAVERNLOCK(){return TAVERNLOCK;}
}