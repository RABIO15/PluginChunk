package fr.rabio.pluginChunck;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerManager implements Listener {


public PluginChunck main;



public PlayerManager(PluginChunck main){

    this.main = main;

}



@EventHandler

    public void OnPlayerFirstJoin(PlayerJoinEvent event){

    Player player =  event.getPlayer();

    player.sendMessage("§6 Hey ton ip c'est : " + player.getAddress());

    if (!player.hasPlayedBefore()) {


        player.sendMessage("§aBienvenue sur le serveur pour la première fois !");

     TeamManager manage_power = new TeamManager(main);

     manage_power.AddPower(player,20);

     player.sendMessage("§9 Votre power est de : " + manage_power.GetPowerPlayer(player));


    }else{

        player.sendMessage("§2 Bon retour par minous  ");

    }





}





}
