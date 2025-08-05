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
    private ClaimManager claim_manager;


    public ChunckInfo(PluginChunck main) {

        this.main = main;
        this.savemanager = new SaveManager(main); // ici main est bien initialisé
        this.teams = new TeamManager(main);



        // idem
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

        String key = "team." + teams.getTeamDuJoueur(player) + ".claimed_chunks";
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


    public void addClaimedChunkPower(Player player , int chunkX, int chunkZ,int power){



                if(!teams.ContainTeam_Other_Version(player)){
                    player.sendMessage("Vous n'êtes dans aucune team et vous faites un claim");
                    addClaimedChunk(player, chunkX, chunkZ);

                }

            File file = new File(main.getDataFolder(), "InformationChunck.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            String chunkCoord = chunkX + ";" + chunkZ;
            String uuidPath = "players." ;
            String chunkListPath = uuidPath + ".claimed_chunks";


            String chunkPowerPath = uuidPath + ".chunk_power"+ "." + chunkCoord;
            player.sendMessage("test 1");
            // Ajouter à la liste s'il n'est pas déjà là
            List<String> claimedChunks = config.getStringList(chunkListPath);



            if (!claimedChunks.contains(chunkCoord)) {
                player.sendMessage("test 2");
                claimedChunks.add(chunkCoord);
                config.set(chunkListPath, claimedChunks);
                // Enregistrer le power pour ce chunk

            }
              config.set(chunkPowerPath, power);


            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }


        player.sendMessage("test 3");

    }



    public void removeClaimedChunk_power( int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String chunkCoord = chunkX + ";" + chunkZ;
        String uuidPath = "players.";
        String chunkListPath = uuidPath + ".claimed_chunks";
        String chunkPowerPath = uuidPath + ".chunk_power." + chunkCoord;

        // 1. Retirer de la liste des chunks claim
        List<String> claimedChunks = config.getStringList(chunkListPath);
        if (claimedChunks.contains(chunkCoord)) {
            claimedChunks.remove(chunkCoord);
            config.set(chunkListPath, claimedChunks); // Mets à jour la liste
        }

        // 2. Supprimer le power du chunk
        if (config.contains(chunkPowerPath)) {
            config.set(chunkPowerPath, null);
        }

        // 3. Sauvegarder le fichier
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    public int getChunkPower( int chunkX, int chunkZ) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String chunkCoord = chunkX + ";" + chunkZ; //attention a ne
        // pas mettree player car il faut celui qui la fauit pas le joueurn qui execute la commande
        String path = "players." + ".chunk_power" +"."+ chunkCoord;


        return config.getInt(path, 0); // 0 si non trouvé
    }



    public boolean Is_Power_Chunk(Player player){

        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String chunkCoord = chunkX + ";" + chunkZ;
        String uuidPath = "players.";

        String chunkPowerPath = uuidPath + ".chunk_power." + chunkCoord;

        // 1. Retirer de la liste des chunks claim


        // 2. Supprimer le power du chunk
        if (config.contains(chunkPowerPath)) {
            return true;
        }

        // 3. Sauvegarder le fichier


        return false;
    }














    public boolean OwnChunk(Player player , int chunkX, int chunkZ){

        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + ".claimed_chunks";
        List<String> chunks = config.getStringList(key);

        String chunkCoord = chunkX + ";" + chunkZ;
        if (chunks.contains(chunkCoord)) {

          player.sendMessage("happy happy");
            return true;

        }








        return false;
    }



    public boolean OwnChunkTeam(Player player , int chunkX, int chunkZ){

        File file = new File(main.getDataFolder(), "Teams.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "team." + teams.getTeamDuJoueur(player) + ".claimed_chunks";
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



    public boolean isChunkClaimedParQuelquUn_Team(int chunkX, int chunkZ ) {
        File file = new File(main.getDataFolder(), "Teams.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.contains("team")) return false;

        ConfigurationSection TeamSection = config.getConfigurationSection("team");
        if (TeamSection == null) return false;

        String chunkCoord = chunkX + ";" + chunkZ;

        for (String uuidStr : TeamSection.getKeys(true)) {
            List<String> claimedChunks = config.getStringList("team." + uuidStr  + ".claimed_chunks");

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
        File file = new File(main.getDataFolder(), "Teams.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "team." + teams.getTeamDuJoueur(player) + ".claimed_chunks";
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



















