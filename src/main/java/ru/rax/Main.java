package ru.rax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.http.WebSocket;

public class Main extends JavaPlugin implements Listener {

    FileConfiguration config = getConfig();
    MainGenerator mainGenerator = new MainGenerator(Integer.parseInt(config.getString("height")), Integer.parseInt(config.getString("width")));;

    @Override
    public void onEnable() {
        getServer().getLogger().info("This is out plugin");
        getServer().getPluginManager().registerEvents(this, this);

        config.options().copyDefaults(true);
        saveConfig();
        //Bukkit.getLogger().info(config.getString("name"));

        getCommand("info").setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
                commandSender.sendMessage("Max height: " + mainGenerator.getMax_height() +
                        " countIteration: " + mainGenerator.countIteration + " piece count: " + mainGenerator.peaceMap.size());
                for (int i = 0; i < mainGenerator.peaceMap.size(); i++) {
                    commandSender.sendMessage("Piece: " + mainGenerator.peaceMap.get(i).getNumberIsland()  +
                            " status: " + mainGenerator.peaceMap.get(i).getStatus() +
                            " ChunkX: " + mainGenerator.peaceMap.get(i).getChunkX() +
                            " ChunkZ: " + mainGenerator.peaceMap.get(i).getChunkZ());
                }

                for (int i = 0; i < mainGenerator.island.size(); i++) {
                    commandSender.sendMessage("Found island: " + mainGenerator.island.get(i));
                }
                return true;
            }
        });
    }

    @Override
    public void onDisable() {

    }

    @EventHandler
    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.YELLOW + "Welcome " + ChatColor.BLUE + player.getName());
    }

    public ChunkGenerator getDefaultWorldGenerator(String worldName, String uid) {
        return mainGenerator;
    }

    //public FileConfiguration getConfigFile() {
    //    return config;
    //}
}
