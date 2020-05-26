package me.chris.Syringe;


import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
//import java.util.HashMap;
//import java.util.logging.Logger;
//import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
//import org.bukkit.command.Command;
//import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.event.player.PlayerJoinEvent;
//import org.bukkit.plugin.java.JavaPlugin;
//import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.md_5.bungee.api.ChatColor;



//import net.md_5.bungee.api.ChatColor;

public class SyringeHeal implements Listener {
	private Plugin plugin = Main.getPlugin(Main.class);
	public HashMap<String, Long> cooldowns = new HashMap<String, Long>();
	public static boolean runSyringe;
	//TODO
	//Add wait time before each use
	//Use healthing potions so heart animation works or make custom potion?
	//make noise for everyone
	//figure out when item in hand is not empty but something else and you try to interact with block nullpointerexception occurs
	//make config

	
	
	@EventHandler
	public void onPlayerClicks(PlayerInteractEvent event) {
	    Player player = event.getPlayer();
	    Action action = event.getAction();
	    ItemStack item = event.getItem();
	    Location loc = player.getLocation();
	    Location soundLoc = player.getLocation().add(0,3,0);
	    //ItemMeta meta = item.getItemMeta();

	    if (event != null && item != null && (player.getInventory().getItemInMainHand().getItemMeta() != null || player.getInventory().getItemInOffHand().getItemMeta() != null) && (player.getInventory().getItemInMainHand().getType() == Material.BAMBOO || player.getInventory().getItemInOffHand().getType() == Material.BAMBOO) && (player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta) || player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta))) {
	    	int cooldownTime = Main.syringeDelay; // Get number of seconds from wherever you want
		    
	        if(cooldowns.containsKey(player.getName())) {
	        	long secondsLeft = ((cooldowns.get(player.getName())/1000)+cooldownTime) - (System.currentTimeMillis()/1000);
	            
	            if(secondsLeft>0) {
	                // Still cooling down
	                player.sendMessage("You cant use it for another " + ChatColor.DARK_GREEN + "" + ChatColor.BOLD  + (int) secondsLeft + ChatColor.RESET + "" + " second(s)!");
	                runSyringe = false;
	            } else {
	            	// No cooldown found or cooldown has expired, save new cooldown
	                cooldowns.put(player.getName(), System.currentTimeMillis());
	                runSyringe = true;
	                // Do Command Here
	            }
	        }
	    }
	    
        
        if(runSyringe) {
    
	     
	     if ( action.equals( Action.LEFT_CLICK_AIR ) || action.equals( Action.LEFT_CLICK_AIR ) ) {
	         if (item != null && player.getInventory().getItemInMainHand().getItemMeta() != null && (player.getInventory().getItemInMainHand().getType() == Material.BAMBOO || player.getInventory().getItemInOffHand().getType() == Material.BAMBOO) && player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta) || player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta)) {
	        	 //if(player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta) || player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta)) {
	        		 
	        		 player.getWorld().playSound(soundLoc, Sound.valueOf("ENTITY_GENERIC_SPLASH"), 1.0f, 1.0f);
		        	 player.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(),loc.getY()+2, loc.getY(), 1, .1, .1, .1, new Particle.DustOptions(Color.RED, 1.0f));
		        	 //player.getWorld().playEffect(player.getLocation(), ENTITY_GENERIC_SPLASH", 200, 15);
		        	 //player.setWalkSpeed(.1f);
		        	 player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 3));
		        	 
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
						public void run() {
							Player playerNow = event.getPlayer();

							setHealth(playerNow, Main.syringeLingerHealth);
						}
		             }, 40L);
		        	 //player.setWalkSpeed(.2f);
		        	 
		        	 if(player.getInventory().getItemInMainHand().getAmount() != 0 && item != null && player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta)) {
	        			 player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);

		        	 }
		        	 
		        	 if(player.getInventory().getItemInMainHand().getAmount() != 0 && item != null && player.getInventory().getItemInOffHand().getItemMeta() != null && player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta)) {
		        			 player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
		        	 }
		        	 
		        	 //player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();
		                	 
		                	 setHealth(playerNow);
		                 }
		             }, 60L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();
		                	 
		                	 setHealth(playerNow);
		                 }
		             }, 80L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();
		                	 
		                	 setHealth(playerNow);
		                 }
		             }, 100L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();
		                	 
		                	 setHealth(playerNow);
		                 }
		             }, 120L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();

		                	 setHealth(playerNow);
		                 }
		             }, 140L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();

		                	 setHealth(playerNow);
		                 }
		             }, 160L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();

		                	 setHealth(playerNow);
		    	        	 }
		                 
		             }, 180L);
		        	 
		        	 Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
		                 public void run() {
		                	 Player playerNow = event.getPlayer();
		                	 
		                	 setHealth(playerNow);
		                 }
		             }, 200L);
	        	// }
	         } 
	     }
        }
	}
	
	/*
	@EventHandler
	public void craftSyringe(CraftItemEvent event) {
		
		if(event.getCurrentItem().getItemMeta() != null && event.getCurrentItem().getItemMeta().equals(Main.SyringeMeta)) {
			int syringeStack = event.getCurrentItem().getAmount() - 2;
			event.getCurrentItem().setAmount(2);
			
			ItemStack item = new ItemStack(Material.BAMBOO);
			item.setItemMeta(Main.SyringeMeta);
			
			for( )
			event.getInventory().addItem(item.setAmount(amount););
		}
		//for 2 stack size
		
		
	}
	*/
	
	@EventHandler
	public void onInventory(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
	    ItemStack item = player.getInventory().getItemInOffHand();
	    Location loc = player.getLocation();
	    Location soundLoc = player.getLocation().add(0,3,0);

	    if(item.getType() != null && item.getType().isBlock() == false) {
		if (event.getRightClicked().getType().equals(EntityType.PLAYER) && player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta)) {
			String name = event.getRightClicked().getName();
			Player healing = Bukkit.getPlayerExact(name);

			if (item != null && player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta) || player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta)) {
				player.getWorld().playSound(soundLoc, Sound.valueOf("ENTITY_GENERIC_SPLASH"), 1.0f, 1.0f);
	        	player.getWorld().spawnParticle(Particle.REDSTONE, loc.getX(),loc.getY()+2, loc.getY(), 10, 1, 1, 1);
	        	//player.getWorld().playEffect(player.getLocation(), ENTITY_GENERIC_SPLASH", 200, 15);
	        	//player.setWalkSpeed(.1f);
	        	player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 40, 4));
				
				
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
	                 public void run() {
	                	 event.getPlayer().setHealth((double)event.getPlayer().getHealth() + (double) 3);
	    	        	 
	    	        	 if(event.getPlayer().getHealth() > 20.0) {
	    	        		 event.getPlayer().setHealth((double) 20.0);
	    	        	 }
	                 }
	             }, 40L);
				
				if(item != null && player.getInventory().getItemInMainHand().getItemMeta() != null && player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta)) {
	        		 if(player.getInventory().getItemInMainHand().getAmount() != 0) {
	        			 player.getInventory().getItemInMainHand().setAmount(player.getInventory().getItemInMainHand().getAmount() - 1);
	        		 }
	        	 }
	        	 
	        	 if(item != null && player.getInventory().getItemInOffHand().getItemMeta() != null && player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta)) {
	        		 if(player.getInventory().getItemInOffHand().getAmount() != 0) {
	        			 player.getInventory().getItemInOffHand().setAmount(player.getInventory().getItemInOffHand().getAmount() - 1);
	        		 }
	        	 }
				

				//player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 1));

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 60L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
			        	 
					}
				}, 80L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 100L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 120L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 140L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 160L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 180L);

				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new Runnable() {
					public void run() {
						Player playerNow = event.getPlayer();
	                	 
	                	setHealth(playerNow);
					}
				}, 200L);
			}
		}
	    }
	}
	
	@EventHandler
	public void questActionBlock(PlayerInteractEvent event) {
		Player player = event.getPlayer();
	    ItemStack item = event.getItem();
	    
	    
		if((event != null && player.getInventory().getItemInMainHand().getItemMeta() != null || player.getInventory().getItemInOffHand().getItemMeta() != null) && (player.getInventory().getItemInMainHand().getType() == Material.BAMBOO || player.getInventory().getItemInOffHand().getType() == Material.BAMBOO) && (player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta) || player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta))) {
			//if(player.getInventory().getItemInMainHand().getItemMeta().equals(Main.SyringeMeta) || player.getInventory().getItemInOffHand().getItemMeta().equals(Main.SyringeMeta)) {
				event.setCancelled(true);
			//}
		}
	}
	
	@EventHandler
	public void playerJoins(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		cooldowns.put(player.getName(), System.currentTimeMillis());
	}
	
	public void setHealth(Player player) {
		if (Main.syringeLingerHealth + player.getHealth() > 20.0 ) {
			player.setHealth(20.0);
		} else {
			player.setHealth(player.getHealth() + Main.syringeLingerHealth);
		}
	}
	
	public void setHealth(Player player, double syringeInitHealth) {
		if (syringeInitHealth + player.getHealth() > 20.0 ) {
			player.setHealth(20.0);
		} else {
			player.setHealth(player.getHealth() + syringeInitHealth);
		}
	}
	
	public static void dustCircle(Player player) {		
		new BukkitRunnable() {
			@Override
			public void run() {
				int degree = 0;
				if (degree < 360) {

					degree = degree + 5;
					double radian1 = Math.toRadians(degree);
					double radian2 = Math.toRadians(degree + 120);
					double radian3 = Math.toRadians(degree + 240);

					Location loc = player.getLocation();
					loc.add(Math.cos(radian1) * 2, 0, Math.sin(radian1) * 2);
					player.getWorld().playEffect(loc, Effect.SMOKE, 20);
					loc.subtract(Math.cos(radian1) * 2, 0, Math.sin(radian1) * 2);
					loc.add(Math.cos(radian2) * 2, 0, Math.sin(radian2) * 2);
					player.getWorld().playEffect(loc, Effect.SMOKE, 20);
					loc.subtract(Math.cos(radian2) * 2, 0, Math.sin(radian2) * 2);
					loc.add(Math.cos(radian3) * 2, 0, Math.sin(radian3) * 2);
					player.getWorld().playEffect(loc, Effect.SMOKE, 20);
					loc.subtract(Math.cos(radian3) * 2, 0, Math.sin(radian3) * 2);
				} else {
				degree = 0;
				}
			}
		}.runTaskTimer(Main.getInstance(), 0, Main.setDustDelay);
	}
}