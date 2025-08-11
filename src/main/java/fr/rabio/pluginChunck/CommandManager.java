package fr.rabio.pluginChunck;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class CommandManager  implements CommandExecutor {

    public PluginChunck main;
    int power_add = 0;



    public CommandManager(PluginChunck main){
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player) {


            Player target;
            Player player = (Player) sender;
            TeamManager manage_power = new TeamManager(main);
            TeamManager teams = new TeamManager(main);
            ChunckInfo infobreak = new ChunckInfo(main);


        if(label.equals("invite")) {




            target = Bukkit.getPlayer(args[0]);


                if (args.length < 2) {
                    sender.sendMessage("§cUsage : /invite [player]");
                    return true;
                } else if (args.length > 3) {



                    if (target == null) {
                        sender.sendMessage("§cLe joueur spécifié est introuvable ou hors ligne.");
                        return true;
                    }



                    if(teams.ContainTeam_Other_Version(target)){
                        sender.sendMessage("§c Ce joueur est déjà dans une teams !");
                        return true;

                    }  if(!teams.ContainTeam_Other_Version(player)){

                        sender.sendMessage("§c Vous ne pouvez inviter personne car vous n'avez pas de team !");
                        return true;
                    }



                    else{


                        target.sendMessage("Le joueur " + player.getName() + "vous a envoyer une invitation dans ça team" );

                        target.sendMessage("Pour accepter fait /team accept ou /team refuse");
                        teams.Team_invite_set(target,1);
                        return true;
                    }



                }

                // Récupérer le joueur


            return true;



            }
            if(label.equals("team")) {

                Player Other_player = (Player) sender;

                if (args.length >= 1) {

                if (args[0].equalsIgnoreCase("refuse")) {

                    player.sendMessage("Le joueur a refuser la propositions  !");
                    return true;
                }


                if (args[0].equalsIgnoreCase("create")) {
                    int power  = manage_power.GetPowerPlayer(player);


                    if (args.length >= 2) {


                        if (power >= 35) {

                            manage_power.RemovePower(player, 35);


                            manage_power.Create_Team(player,args[1]);



                            return true;
                        } else {


                            player.sendMessage("Pour pouvoir crée une team vous devez avoir 35 de power minimum vous en avez que :" + power);

                        }

                    }
                    return true;
                }


                if (args[0].equalsIgnoreCase("accept")) {


                    player.sendMessage("Le joueur a accepter  la propositions !");
                    String teamplayer = teams.getTeamDuJoueur(player);


                    teams.SetTeamJoueur(Other_player.getUniqueId(), teamplayer);

                    Other_player.sendMessage("Vous avez accepter la propositions");

                    return true;
                }


              /*  } else {

                    Other_player.sendMessage("Vous n'avez pas été inviter !");
                    return true;
                }
*/

                if (args[0].equalsIgnoreCase("add")) {

                    Other_player.sendMessage("§2 Vous venez de vous ajoutez dans une team!");

                    teams.SetTeamJoueur(Other_player.getUniqueId(), args[1]);
                    Other_player.sendMessage("§3 Vous êtes desormais dans la teams : " + args[1]);


                    return true;

                }

                if (args[0].equalsIgnoreCase("info")) {

                    Other_player.sendMessage("Votre team est : " + teams.getTeamDuJoueur(Other_player));


                    return true;

                }


                if (args[0].equalsIgnoreCase("verif")) {


                    if (teams.ContainTeam_Other_Version(player)) {

                        Other_player.sendMessage("§2Felicitation vous êtes dans une Team");

                    } else {
                        Other_player.sendMessage("§4Vous n'êtes dans aucune team :(");


                    }


                    return true;

                }


                if (args[0].equalsIgnoreCase("leave")) {

                    Other_player.sendMessage("§2 Vous venez de vous retirer d'une team!");

                    Other_player.sendMessage("§3 Vous avez quittez la team : " + teams.getTeamDuJoueur(Other_player));

                    teams.RemovePlayerTeam(Other_player.getUniqueId(),player);


                    return true;

                }
                return true;

            }

                return true;

            }

            if(label.equals("claim")) {
                player.sendMessage("§5 Vous venez de exucuter la comande Claim !");
                ClaimManager claim = new ClaimManager(main);



                if (args.length >= 1) {




                    try {






                        power_add += Integer.parseInt(args[0]);
                        player.sendMessage("§4 L'ajout est de : "+  power_add);

                        player.sendMessage("§2 Vous venez de   ajoutez  : " + power_add + " §2 power au chunk ");
                        player.sendMessage("§7 Il ne vous restes que : " + manage_power.GetPowerPlayer(player) + " §7 power");




                        claim.Claim(player,power_add);

                        power_add = 0;


                    } catch (NumberFormatException e) {

                        player.sendMessage("§4 TEST4");
                        player.sendMessage(" TU DOIS METTRE UN CHIFFRE !");
                    }



                    return true;
                }else {

                    player.sendMessage("§4 TEST5");
                    player.sendMessage("§8 On essaye un truc");
                    power_add = 0;

                    claim.Claim(player,power_add);
                    player.sendMessage("§8 cela a  marcher ?");





                }

                player.sendMessage("§4 TEST6");
                return true;
            }



            if(label.equals("unclaim")) {


                player.sendMessage("§2 Vous venez de exucuter la comande Unclaim !");
                ClaimManager claim = new ClaimManager(main);

                claim.UnClaim(player);




                return true;

            }


            if(label.equals("power")) {
                player.sendMessage("§7 Vous venez de exucuter la comande Power !");
                

                
               int power =  manage_power.GetPowerPlayer(player);
                player.sendMessage("Votre est de :  " + power);

                if (args.length >= 2 && args[0].equalsIgnoreCase("add")) {

                    if(args[1] != null){

                        try{
                            int power_add = Integer.parseInt(args[1]);

                            manage_power.AddPower(player, power_add);
                            player.sendMessage("§2 Vous venez de vous ajoutez : " + power_add +" §2 power" );
                            player.sendMessage("§7 Il ne vous restes que : " + manage_power.GetPowerPlayer(player)+" §7 power");


                        }catch(NumberFormatException e){

                            player.sendMessage(" TU DOIS METTRE UN CHIFFRE !");
                        }





                    }




                    return true;
                }




                if (args.length >= 2 && args[0].equalsIgnoreCase("remove")) {

                    if(args[1] != null){

                        try{
                            int power_remove = Integer.parseInt(args[1]);

                            manage_power.RemovePower(player, power_remove);
                            player.sendMessage("§4 Vous venez de vous retirer : " + power_remove +" §4 power" );
                            player.sendMessage("§7 Il ne vous restes que : " + manage_power.GetPowerPlayer(player)+" §7 power");

                        }catch(NumberFormatException e){

                            player.sendMessage(" TU DOIS METTRE UN CHIFFRE !");
                        }





                    }




                    return true;
                }




                return true;

            }










        }












        return true;
    }
}
