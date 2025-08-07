package fr.rabio.pluginChunck;



import java.io.File;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
public class TeamManager  {

    PluginChunck main;

    SaveManager save;
  


    public TeamManager(PluginChunck main){

        this.main = main;

        this.save = new SaveManager(main);
    }

    final  HashMap<UUID,String> Team = new HashMap<>();





    //faire une  hashmap qui va stoker uuid et nom de l'equipe


    //methode pour recupérer l'equipe du joueur

    public String getTeamDuJoueur(Player player) {


        //Load_HashMapTeam();

        File file = new File(main.getDataFolder(), "playerinfo.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


        //en gros j'ai mis nom parce que quand le truc sauvegarde il sauvegarde la entryset donc le nom de la team
        //donc j'ai fait en sorte de faire retourner le truc nom comme ça le truc va dans team puis player puis nom de la team

        return config.getString("team." + player.getUniqueId(), null);



    }

    public void Create_Team(Player player, String name){

        if(!ContainTeam_Other_Version(player)){
            if(!SameNameTeam(name)){

                SetTeamJoueur(player.getUniqueId(),name);

                Set_Team_List(name);
                player.sendMessage("§2 Felicitation Vous venez de crée votre team la team :" + ChatColor.GOLD +name);

            }else{
                player.sendMessage("§4Une team à déjà le même nom changer de nom");

            }


        }else{


            player.sendMessage("§4Vous devez quitter votre team avant de crée votre team");
            player.sendMessage("§2Vous êtes dans la team :" + getTeamDuJoueur(player));
        }

    }







    public boolean SameNameTeam(String name) {
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);



        ConfigurationSection playersSection = config.getConfigurationSection("team");



        for (String uuid : playersSection.getKeys(false)) {



            List<String> claimedChunks = config.getStringList("team");


            if (claimedChunks.contains(name)) {
                return true;
            }
        }

        return false;
    }


    public void Set_Team_List(String name) {
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        ConfigurationSection TeamSection = config.getConfigurationSection("team");

       config.set("team",name);

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }















    public boolean ContainTeam_Other_Version(Player player){


        if(getTeamDuJoueur(player) != null){
            player.sendMessage("Vous êtes dans une team bravo !");
            return true;

        }else {

            player.sendMessage("§4Vous n'êtes dans aucune  team  GET OUTT!");

                return false;
            }








    }






    public int Team_invite(Player player){



        File file = new File(main.getDataFolder(), "teams.yml");

        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        String key = "players." + player.getUniqueId() + "." + "invite";
        int invite_value = config.getInt(key ,0);

        return invite_value;
    }


    public void Team_invite_set(Player player,int invite){

        save.set_Sauvegarde_Int(player,"team.yml","invite",invite);

    }


    public HashMap<UUID,String> Load_HashMapTeam(){
        File file = new File(main.getDataFolder(), "teams.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);



        if (config.contains("team")) {
            for (String uuidStr : config.getConfigurationSection("team").getKeys(false)) {
                try {
                    UUID uuid = UUID.fromString(uuidStr);
                    String teamName = config.getString("team." + uuidStr);
                    Team.put(uuid, teamName);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace(); // UUID mal formé, au cas où
                }
            }
        }

        return Team;



    }




    //methode pour ajouter  le joueur dans une équipe
    public void SetTeamJoueur(UUID joueuruuid,String nom){

        Team.put(joueuruuid,nom);

        save.Save_HashMapTeam(Team);

  
    }


  

    //methode pour enlever un joueur de l'équipe

    public void RemovePlayerTeam(UUID joueuruuid){

        Team.remove(joueuruuid);
        save.Save_HashMapTeam(Team);


    }





    //methode pour voir le power du joueur

    public int GetPowerPlayer(Player player){





        // Lire la valeur et retourner soit la vraie, soit la valeur par défaut
        return  save.get_Sauvegarde_Int(player,"Power.yml","power");



    }

    public void RemovePower(Player player,int remove){


        int unite = GetPowerPlayer(player);

        unite -= remove;

       save.set_Sauvegarde_Int(player,"Power.yml","power",unite);



    }




    public void AddPower(Player player,int add){

        int unite = GetPowerPlayer(player);

        unite += add;
  
        save.set_Sauvegarde_Int(player,"Power.yml","power",unite);




    }

















}
