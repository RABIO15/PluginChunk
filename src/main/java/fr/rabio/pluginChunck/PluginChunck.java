package fr.rabio.pluginChunck;

import org.bukkit.plugin.java.JavaPlugin;

public final class PluginChunck extends JavaPlugin {

    @Override
    public void onEnable() {


        SaveManager saveManager = new SaveManager(this); // ici "this" est bien le main

        // Puis passe-le aux autres classes correctement
        ChunckInfo chunckInfo = new ChunckInfo(this);
        ClaimManager claim = new ClaimManager(this);
        PlayerManager playermanager = new PlayerManager(this);
        TeamManager team = new TeamManager(this);

        team.Load_HashMapTeam();

        getServer().getPluginManager().registerEvents(new ClaimManager(this), this);
        getServer().getPluginManager().registerEvents(new PlayerManager(this), this);


        getCommand("invite").setExecutor(new CommandManager(this));
        getCommand("team").setExecutor(new CommandManager(this));
        getCommand("claim").setExecutor(new CommandManager(this));
        getCommand("unclaim").setExecutor(new CommandManager(this));
        getCommand("power").setExecutor(new CommandManager(this));




    }

    @Override
    public void onDisable() {



    }
}