package fr.rabio.pluginChunck;

import org.bukkit.Chunk;
import org.bukkit.ChunkSnapshot;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockCanBuildEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.generator.structure.GeneratedStructure;
import org.bukkit.generator.structure.Structure;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class ClaimManager implements Listener {

    PluginChunck main;
    ChunckInfo information_Chunk;

    TeamManager manage_power;

    public ClaimManager(PluginChunck main){

        this.main = main;
        this.information_Chunk = new ChunckInfo(main);
        this.manage_power =  new TeamManager(main);
    }




    public void Claim(Player player){
        Chunk chunk = player.getLocation().getChunk();
        int ChunkX = chunk.getX();
        int ChunkZ = chunk.getZ();
        int power  = manage_power.GetPowerPlayer(player);

        if(power >= 5) {


            if (!isChunkAlreadyClaimed(ChunkX, ChunkZ)) {


                if (!manage_power.ContainTeam(player.getUniqueId())) {

                    information_Chunk.addClaimedChunk(player, ChunkX, ChunkZ);


                    player.sendMessage("Vous venez claim ce chunk !!!");
                    player.sendMessage("Tu es dans le chunk X=" + ChunkX + ", Z=" + ChunkZ);

                    manage_power.RemovePower(player, 5);

                    player.sendMessage("§2 - 5 de power votre power actuel est de " + manage_power.GetPowerPlayer(player));


                } else {


                    player.sendMessage("Vous êtes dans une team !");

                    information_Chunk.addClaimedChunkTeam(player, ChunkX, ChunkZ);
                    player.sendMessage("Votre team à claim ce chunk !!!");
                    player.sendMessage("Tu es dans le chunk X=" + ChunkX + ", Z=" + ChunkZ);

                    manage_power.RemovePower(player, 5);

                    player.sendMessage("§2 - 5 de power votre power actuel est de " + manage_power.GetPowerPlayer(player));


                }

            }else {
                player.sendMessage("Ce chunk est déjà claim  !");

            }

        }else{
            player.sendMessage("§2 Vous n'avez pas assez de power pour pouvoir faire cela votre power est de : "+ "§6"+ power);


        }

    }

    public void UnClaim(Player player){

        Chunk chunk = player.getLocation().getChunk();
        int ChunkX = chunk.getX();
        int ChunkZ = chunk.getZ();

      if(isChunkAlreadyClaimed(ChunkX,ChunkZ)){
          if (!manage_power.ContainTeam(player.getUniqueId())) {
              information_Chunk.RemoveClaimedChunk(player, ChunkX, ChunkZ);

              player.sendMessage("Vous venez de unclaim la zone ! le Chun x est " + ChunkX + "Chunk Z est :" + ChunkZ);

              manage_power.AddPower(player, 5);

              player.sendMessage("§2 +5 de power votre power actuel est de " + manage_power.GetPowerPlayer(player));
          }else {


              player.sendMessage("Vous êtes dans une team !");

              information_Chunk.RemoveClaimedChunkTeam(player, ChunkX, ChunkZ);

              player.sendMessage("Votre team à Unclaim ce chunk !!!");
              player.sendMessage("Tu es dans le chunk X=" + ChunkX + ", Z=" + ChunkZ);

              manage_power.AddPower(player, 5);

              player.sendMessage("§2 + 5 de power votre power actuel est de " + manage_power.GetPowerPlayer(player));


          }



      }else {

          player.sendMessage("Ce chunk n'est pas claim  ");

      }


    }

    //partie TEAM



























    public boolean isChunkAlreadyClaimed( int x, int z) {
        File file = new File(main.getDataFolder(), "InformationChunck.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (!config.contains("players")) return false;  

        ConfigurationSection playersSection = config.getConfigurationSection("players");

        String chunkcore = x+ ";"+ z;

        for (String uuid : playersSection.getKeys(false)) {



            List<String> claimedChunks = config.getStringList("players." + uuid + ".claimed_chunks");


            if (claimedChunks.contains(chunkcore)) {
                return true;
            }
        }

        return false;
    }


    @EventHandler

    public void BlockBreak(BlockBreakEvent event){

        Player player = event.getPlayer();
        Chunk chunk = player.getLocation().getChunk();
        int ChunkX = chunk.getX();
        int ChunkZ = chunk.getZ();
        ChunckInfo infobreak = new ChunckInfo(main);
        TeamManager infoteams = new TeamManager(main);


        if(infobreak.isChunkClaimedParQuelquUn(ChunkX,ChunkZ)) {


            if (infoteams.ContainTeam(player.getUniqueId())) {
                if(!infobreak.OwnChunkTeam(player, ChunkX, ChunkZ)){
                    player.sendMessage("§2Vous ne pouvez pas casser dans ce chunk car il est déjà claim par une team");
                    event.setCancelled(true);

                }


            } else {

                if (!infobreak.OwnChunk(player, ChunkX, ChunkZ)) {


                    player.sendMessage("§4 Vous ne pouvez pas casser dans ce chunk car il est déjà claim par quelq'un");
                    event.setCancelled(true);
                }

            }

        }




    }

    @EventHandler

    public void BlockBuild(BlockPlaceEvent event){

        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();
        int ChunkX = chunk.getX();
        int ChunkZ = chunk.getZ();
        ChunckInfo infobreak = new ChunckInfo(main);
        TeamManager infoteams = new TeamManager(main);

        if(infobreak.isChunkClaimedParQuelquUn(ChunkX,ChunkZ)) {


            if (infoteams.ContainTeam(player.getUniqueId())) {
                if(!infobreak.OwnChunkTeam(player, ChunkX, ChunkZ)){
                    player.sendMessage("§2Vous ne pouvez pas construire dans ce chunk car il est déjà claim par une team");
                    event.setCancelled(true);

                }


            } else {

                if (!infobreak.OwnChunk(player, ChunkX, ChunkZ)) {


                    player.sendMessage("§4 Vous ne pouvez pas construire dans ce chunk car il est déjà claim par quelq'un");
                    event.setCancelled(true);
                }

            }

        }






    }















}