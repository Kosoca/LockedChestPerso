package fr.lataverne.commands;

import fr.lataverne.tavernlock.TavernLock;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class GroupsCommands implements CommandExecutor {

    private final TavernLock plugin;
    private final FileConfiguration groupsConfig;

    public GroupsCommands(TavernLock plugin, FileConfiguration groupsConfig) {
        this.plugin = plugin;
        this.groupsConfig = groupsConfig;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSeul les joueurs peuvent executer cette commande :/");
            return true;
        }

        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();

        if (command.getName().equalsIgnoreCase("tavernlock")) {
            if (args.length < 1) {
                player.sendMessage("§c/tavernlock <add|kick> <player>");
                return true;
            }

            String action = args[0];

            if (args.length == 2) {
                Player targetPlayer = Bukkit.getPlayer(args[1]);
                if (targetPlayer == null) {
                    player.sendMessage("§cPlayer not found.");
                    return true;
                }

                UUID targetUUID = targetPlayer.getUniqueId();

                if (action.equalsIgnoreCase("add")) {
                    addPlayerToGroup(playerUUID, targetUUID);
                    player.sendMessage("§a" + targetPlayer.getName() + " a été ajouté à votre groupe.");
                } else if (action.equalsIgnoreCase("kick")) {
                    removePlayerFromGroup(playerUUID, targetUUID);
                    player.sendMessage("§c" + targetPlayer.getName() + " a été supprimé de votre groupe.");
                } else {
                    player.sendMessage("§c/tavernlock <add|kick> <player>");
                }
            }
        }
        return true;
    }

    private void addPlayerToGroup(UUID ownerUUID, UUID memberUUID) {
        List<String> groupMembers = groupsConfig.getStringList("players." + ownerUUID + ".group_members");
        if (!groupMembers.contains(memberUUID.toString())) {
            groupMembers.add(memberUUID.toString());
            groupsConfig.set("players." + ownerUUID + ".group_members", groupMembers);
            plugin.saveGroupsFile();
        }
    }

    private void removePlayerFromGroup(UUID ownerUUID, UUID memberUUID) {
        List<String> groupMembers = groupsConfig.getStringList("players." + ownerUUID + ".group_members");
        if (groupMembers.contains(memberUUID.toString())) {
            groupMembers.remove(memberUUID.toString());
            groupsConfig.set("players." + ownerUUID + ".group_members", groupMembers);
            plugin.saveGroupsFile();
        }
    }
}

