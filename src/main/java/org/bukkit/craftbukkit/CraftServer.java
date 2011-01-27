package org.bukkit.craftbukkit;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.minecraft.server.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PropertyManager;
import net.minecraft.server.ServerConfigurationManager;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.java.JavaPluginLoader;

public final class CraftServer implements Server {
    private final String serverName = "Craftbukkit";
    private final String serverVersion = "1.2_01";
    private final PluginManager pluginManager = new SimplePluginManager(this);
    private final CommandMap commandMap = new SimpleCommandMap(this);
    protected final MinecraftServer console;
    protected final ServerConfigurationManager server;

    public CraftServer(MinecraftServer console, ServerConfigurationManager server) {
        this.console = console;
        this.server = server;

        pluginManager.RegisterInterface(JavaPluginLoader.class);
    }

    public void loadPlugins() {
        File pluginFolder = (File)console.options.valueOf("plugins");

        if (pluginFolder.exists()) {
            try {
                Plugin[] plugins = pluginManager.loadPlugins(pluginFolder);
                for (Plugin plugin : plugins) {
                    loadPlugin(plugin);
                }
            } catch (Throwable ex) {
                Logger.getLogger(CraftServer.class.getName()).log(Level.SEVERE, ex.getMessage() + " (Is it up to date?)", ex);
            }
        } else {
            pluginFolder.mkdir();
        }
    }

    public void disablePlugins() {
        pluginManager.disablePlugins();
    }

    private void loadPlugin(Plugin plugin) {
        List<Command> pluginCommands = PluginCommandYamlParser.parse(plugin);
        if (!pluginCommands.isEmpty()) {
            commandMap.registerAll(plugin.getDescription().getName(), pluginCommands);
        }
        pluginManager.enablePlugin(plugin);
    }

    public String getName() {
        return serverName;
    }

    public String getVersion() {
        return serverVersion;
    }

    public Player[] getOnlinePlayers() {
        List<EntityPlayerMP> online = server.b;
        Player[] players = new Player[online.size()];

        for (int i = 0; i < players.length; i++) {
            players[i] = online.get(i).a.getPlayer();
        }

        return players;
    }

    public Player getPlayer(final String name) {
        Player[] players = getOnlinePlayers();

        Player found = null;
        String lowerName = name.toLowerCase();
        int delta = Integer.MAX_VALUE;
        for (Player player : players) {
            if (player.getName().toLowerCase().startsWith(lowerName)) {
                int curDelta = player.getName().length() - lowerName.length();
                if (curDelta < delta) {
                    found = player;
                    delta = curDelta;
                }
                if(curDelta == 0) break;
            }
        }
        return found;
    }

    public int broadcastMessage(String message) {
        Player[] players = getOnlinePlayers();

        for (Player player : players) {
            player.sendMessage(message);
        }

        return players.length;
    }

    public Player getPlayer(final EntityPlayerMP entity) {
        return entity.a.getPlayer();
    }

    public List<Player> matchPlayer(String partialName) {
        List<Player> matchedPlayers = new ArrayList<Player>();

        for (Player iterPlayer : this.getOnlinePlayers()) {
            String iterPlayerName = iterPlayer.getName();

            if (partialName.equalsIgnoreCase(iterPlayerName)) {
                // Exact match
                matchedPlayers.clear();
                matchedPlayers.add(iterPlayer);
                break;
            }
            if (iterPlayerName.toLowerCase().indexOf(partialName.toLowerCase()) != -1) {
                // Partial match
                matchedPlayers.add(iterPlayer);
            }
        }

        return matchedPlayers;
    }

    public PluginManager getPluginManager() {
        return pluginManager;
    }

    public World[] getWorlds() {
        return new World[]{console.e.getWorld()};
    }

    public long getTime() {
        return console.e.e;
    }

    public void setTime(long time) {
        console.e.e = time;
    }

    public ServerConfigurationManager getHandle() {
        return server;
    }

    public boolean dispatchCommand(CommandSender sender, String commandLine) {
        return commandMap.dispatch(sender, commandLine);
    }

    public void reload() {
        PropertyManager config = new PropertyManager(console.options);

        console.d = config;

        boolean animals = config.a("spawn-monsters", console.m);
        boolean monsters = config.a("spawn-monsters", console.e.k > 0);

        console.l = config.a("online-mode", console.l);
        console.m = config.a("spawn-animals", console.m);
        console.n = config.a("pvp", console.n);

        console.e.k = monsters ? 1 : 0;
        console.e.a(monsters, animals);
    }
}
