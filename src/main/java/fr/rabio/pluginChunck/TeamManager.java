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




        File file = new File(main.getDataFolder(), "playerinfo.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);


        //en gros j'ai mis nom parce que quand le truc sauvegarde il sauvegarde la entryset donc le nom de la team
        //donc j'ai fait en sorte de faire retourner le truc nom comme ça le truc va dans team puis player puis nom de la team

        return config.getString("team." + player.getUniqueId(), null);



    }

    public void Create_Team(Player player, String name){

        if(!ContainTeam_Other_Version(player)){




                Set_Team_List(name,player);





        }else{


            player.sendMessage("§4Vous devez quitter votre team avant de crée votre team");
            player.sendMessage("§2Vous êtes dans la team :" + getTeamDuJoueur(player));
        }

    }


    public void SetFondateurTeam(Player player){
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // On récupère la liste existante, ou une nouvelle si elle n'existe pas
        List<String> teams = config.getStringList("FondateurTeam");
        String Name_Player = player.getName();

        // On ajoute le nouveau nom si pas déjà présent
        if (!teams.contains(Name_Player)) {
            teams.add(Name_Player);
            config.set("TeamFondateur", teams);



            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{

            player.sendMessage("§4 Ce nom de team est déjà prix !");

        }


    }

    public void RemoveFondateurTeam(Player player){
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // On récupère la liste existante, ou une nouvelle si elle n'existe pas
        List<String> teams = config.getStringList("FondateurTeam");

        String Nom_team = getTeamDuJoueur(player);

        // On ajoute le nouveau nom si pas déjà présent

        teams.remove(Nom_team);
        config.set("TeamFondateur", teams);
        RemovePlayerTeam(player.getUniqueId());

        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public Boolean IsFondateurTeam(Player player){
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // On récupère la liste existante, ou une nouvelle si elle n'existe pas
        List<String> teams = config.getStringList("FondateurTeam");
        String Name_Player = player.getName();

        // On ajoute le nouveau nom si pas déjà présent
        if (teams.contains(Name_Player)) {

            return true;



        }




    return false;
    }








    public boolean SameNameTeam(String name) {
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        List<String> teams = config.getStringList("team");
        return teams.contains(name);
    }


   /* public void Set_Team_List(String name) {
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
*/


    public void Set_Team_List(String name,Player player) {
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // On récupère la liste existante, ou une nouvelle si elle n'existe pas
        List<String> teams = config.getStringList("team");

        // On ajoute le nouveau nom si pas déjà présent
        if (!teams.contains(name)) {
            teams.add(name);
            config.set("team", teams);

            SetFondateurTeam(player);

            SetTeamJoueur(player.getUniqueId(),name);
            player.sendMessage("§2 Felicitation Vous venez de crée votre team la team :" + ChatColor.GOLD +name);

            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{

            player.sendMessage("§4 Ce nom de team est déjà prix !");

        }

        // On remet la liste dans le YAML

    }


    public void Remove_Team_List(Player player) {
        File file = new File(main.getDataFolder(), "TeamList.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        // On récupère la liste existante, ou une nouvelle si elle n'existe pas
        List<String> teams = config.getStringList("team");

        String Nom_team = getTeamDuJoueur(player);

        // On ajoute le nouveau nom si pas déjà présent

            teams.remove(Nom_team);
            config.set("team", teams);
            RemovePlayerTeam(player.getUniqueId());

            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        // On remet la liste dans le YAML

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
