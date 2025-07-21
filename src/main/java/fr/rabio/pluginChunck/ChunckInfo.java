package fr.rabio.pluginChunck;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class ChunckInfo {
    private int chunkX;
    private int chunkZ;
    private UUID joueur;

    private String nomMonde;
    PluginChunck main;
    private SaveManager savemanager;
    private TeamManager teams;



    public ChunckInfo(PluginChunck main) {

        this.main = main;
        this.savemanager = new SaveManager(main); // ici main est bien initialisé
        this.teams = new TeamManager(main);       // idem
    }







    public void addClaimedChunk(Player player, int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;


        if (!chunks.contains(chunkCoord)) {
            chunks.add(chunkCoord);
            config.set(key, chunks);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public void addClaimedChunkTeam(Player player, int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "Teams.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + teams.GetPowerPlayer(player) + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;
        if (!chunks.contains(chunkCoord)) {
            chunks.add(chunkCoord);
            config.set(key, chunks);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }











    public boolean OwnChunk(Player player , int chunkX, int chunkZ){

        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;
        if (chunks.contains(chunkCoord)) {


            return true;

        }








        return false;
    }



    public boolean OwnChunkTeam(Player player , int chunkX, int chunkZ){

        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + teams.getTeamDuJoueur(player) + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;
        if (chunks.contains(chunkCoord)) {


            return true;

        }








        return false;
    }









    public boolean isChunkClaimedParQuelquUn(int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.contains("players")) return false;

        ConfigurationSection playersSection = config.getConfigurationSection("players");
        if (playersSection == null) return false;

        String chunkCoord = chunkX + ";" + chunkZ;

        for (String uuidStr : playersSection.getKeys(false)) {
            List<String> claimedChunks = config.getStringList("players." + uuidStr + ".claimed_chunks");

            if (claimedChunks.contains(chunkCoord)) {
                return true; // trouvé => quelqu'un a claim
            }
        }

        return false; // personne n’a claim ce chunk
    }




    public void RemoveClaimedChunk(Player player, int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;
        if (chunks.contains(chunkCoord)) {
            chunks.remove(chunkCoord);

            config.set(key, chunks);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    public void RemoveClaimedChunkTeam(Player player, int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + teams.getTeamDuJoueur(player) + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;
        if (chunks.contains(chunkCoord)) {
            chunks.remove(chunkCoord);

            config.set(key, chunks);

            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



























}



















