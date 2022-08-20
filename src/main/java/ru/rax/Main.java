package ru.rax;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    @Override
    public void onEnable() {
        getServer().getLogger().info("This is out plugin");
        getServer().getPluginManager().registerEvents(this, this);

        config.options().copyDefaults(true);
        saveConfig();
        Bukkit.getLogger().info(config.getString("name"));
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
        return new MainGenerator(Integer.parseInt(config.getString("height")), Integer.parseInt(config.getString("width")));
    }

    //public FileConfiguration getConfigFile() {
    //    return config;
    //}
}
