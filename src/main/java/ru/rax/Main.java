package ru.rax;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.http.WebSocket;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getLogger().info("This is out plugin");
        getServer().getPluginManager().registerEvents(this, this);
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
        return new MainGenerator();
    }
}
