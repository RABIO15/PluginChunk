package fr.rabio.pluginChunck;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SaveManager {

    PluginChunck main;

    public SaveManager(PluginChunck main){
        this.main = main;

    }


    public void set_Sauvegarde_Int(Player player,String fileyml,String informationyml,int variableinformations){

        File file = new File(main.getDataFolder(), fileyml);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String key = "players." + player.getUniqueId() + "." + informationyml;



        config.set(key ,variableinformations);

        try {
            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();
        }





    }










    public void set_Add_Sauvegarde_Int(Player player,String fileyml,String informationyml,int variableinformations){

        File file = new File(main.getDataFolder(), fileyml);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String key = "players." + player.getUniqueId() + "." + informationyml;;
        int add = config.getInt(key ,0);
        // ne comprend pas son utiliter je pensef que c'est pour le classement mais je trouve rien int pointC = configC.getInt(keyC + ".point",0);

        add +=  variableinformations;



        config.set(key + informationyml,add);

        try {
            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();
        }







    }

    public void set_Remove_Sauvegarde_Int(Player player,String fileyml,String informationyml,int variableinformations){

        File file = new File(main.getDataFolder(), fileyml);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String key = "players." + player.getUniqueId() + "." + informationyml;
        int remove = config.getInt(key ,0);


        remove -=  variableinformations;



        config.set(key ,remove);

        try {
            config.save(file);

        } catch (IOException e) {

            e.printStackTrace();
        }







    }











    public int get_Sauvegarde_Int(Player player, String fileyml, String informationyml) {

        File file = new File(main.getDataFolder(), fileyml);

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String key = "players." + player.getUniqueId() + "." + informationyml;


        // Lire la valeur et retourner soit la vraie, soit la valeur par défaut
        return config.getInt(key , 0);
    }



    public void set_Sauvegarde_String(Player player, String fileyml, String informationyml, String valeur) {
        File file = new File(main.getDataFolder(), fileyml);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + "." + informationyml;

        config.set(key, valeur);

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










    public String get_Sauvegarde_String(Player player, String fileyml, String informationyml) {
        File file = new File(main.getDataFolder(), fileyml);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + "." + informationyml;

        return config.getString(key, "");
    }



    public void set_Sauvegarde_UUID(Player player, String fileyml, String informationyml, UUID valeur) {
        File file = new File(main.getDataFolder(), fileyml);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + "." + informationyml;

        config.set(key, valeur.toString());

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    public UUID get_Sauvegarde_UUID(Player player, String fileyml, String informationyml) {
        File file = new File(main.getDataFolder(), fileyml);
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        String key = "players." + player.getUniqueId() + "." + informationyml;

        String uuidString = config.getString(key);

        if (uuidString != null) {
            try {
                return UUID.fromString(uuidString);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            }
        }

        return null; // Rien trouvé, on renvoie null
    }


  



    public void Save_HashMapTeam(HashMap<UUID, String> teams) {
        File file = new File(main.getDataFolder(), "playerinfo.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        //ici on charge le dosseier Team.yml

        // Supprimer juste les entrées obsolètes
        ConfigurationSection teamSection = config.getConfigurationSection("team");
        //ici on veut que ça retourn la section team de teams.yml

        if (teamSection != null) {
            //le if team section sert a voir  y a un truc dans la liste

            for (String key : teamSection.getKeys(false)) {
                //la boucle va recupérer la clée Teams et le false permet juste de recupére les clée de
                //Team et pas c'est sous c'est


                UUID uuid = UUID.fromString(key);
                //la on récupée le uuid en transformant ce que on trouve dans la boucle en uuid

                if (!teams.containsKey(uuid)) {
                    //on vérifie si le uuid c'est fait GETOUT du de la team si oui la supprimer
                    config.set("team." + key, null); // Supprime uniquement ceux absents de la HashMap
                }
            }
        }

        // Ajout / update des valeurs actuelles
        for (Map.Entry<UUID, String> entry : teams.entrySet()) {

            //la la boucle va recupérer tout les truc que on a mis a parametre dans cette boucle
            //et va lajouter dans le fichier teams
            config.set("team." + entry.getKey().toString(), entry.getValue());
        }

        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

































}
