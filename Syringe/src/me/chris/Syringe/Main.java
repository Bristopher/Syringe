package me.chris.Syringe;


import java.util.Arrays;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;

public class Main extends JavaPlugin{
	public final static Logger logger = Logger.getLogger("Minecraft");
	public static Main instance;
	public static boolean startPlugin;
	public static Long setDustDelay;
	public static ItemMeta SyringeMeta;
	public static int syringeDelay;
	public double syringeInitHealth;
	public static double syringeLingerHealth;
	
	public static Main getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		getConfig().options().copyDefaults();
		saveDefaultConfig();
		
		instance = this;
		startPlugin = false;
		syringeDelay = getConfig().getInt("syringeDelay");
		setDustDelay = getConfig().getLong("setDelay");
		syringeInitHealth = getConfig().getDouble("syringeInitHealth");
		syringeLingerHealth = getConfig().getDouble("syringeLingerHealth");
		SyringeHeal.runSyringe = true;
		
		
		Bukkit.addRecipe(getSyringeRecipe());
		getServer().getPluginManager().registerEvents(new SyringeHeal(), this);
		
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " Version " + pdfFile.getVersion()
		+ " Has been enabled!");	

	}
	
	@Override
	public void onDisable() {
		this.saveConfig();
		PluginDescriptionFile pdfFile = this.getDescription();
		logger.info(pdfFile.getName() + " Has been disabled!");
		instance = null;
	}
	
	
	public ShapedRecipe getRecipe() {
		ItemStack item = new ItemStack(Material.NETHER_STAR);
		
		NamespacedKey key = new NamespacedKey(this, "nether_star");
		
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape(" T ", "TET", " T ");
		
		recipe.setIngredient('T', Material.GHAST_TEAR);
		recipe.setIngredient('T', Material.EMERALD_BLOCK);
		
		return recipe;
		
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		player.discoverRecipe(getSyringeRecipe().getKey());
	}
	
	public ShapedRecipe getSyringeRecipe() {
		ItemStack item = new ItemStack(Material.BAMBOO);
		ItemMeta meta = item.getItemMeta();
		SyringeMeta = meta;
		
		
		
		meta.setDisplayName(ChatColor.DARK_GREEN + "Syringe");
		//meta.addEnchant(Enchantment.LOOT_BONUS_BLOCKS, 10, true);
		meta.setLore(Arrays.asList(ChatColor.RED + "Left click to heal or right click to heal others"));
		item.setItemMeta(meta);
		
		NamespacedKey key = new NamespacedKey(this, "syringe");
		
		ShapedRecipe recipe = new ShapedRecipe(key, item);
		
		recipe.shape("WIW", "WCW", "WIW");
		
		recipe.setIngredient('W', Material.WHITE_WOOL);
		recipe.setIngredient('I', Material.IRON_INGOT);
		recipe.setIngredient('C', Material.COAL);
		
		return recipe;
		
	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player player = (Player) sender;
		
		
		
		if(label.equalsIgnoreCase("syringever")) {
			PluginDescriptionFile pdfFile = this.getDescription();
			player.sendMessage(pdfFile.getVersion()); {
				return true;
			}
		}
		
		if(label.equalsIgnoreCase("syringesetdelay")) {
			
			if (player.isOp()) {
				if (isNum(args[0])) {
					// String userInput = label.substring(18);

					syringeDelay = Integer.parseInt(args[0]);
					getConfig().set("syringeDelay", syringeDelay);
					this.saveConfig();
					saveDefaultConfig();

					player.sendMessage("Syringe delay has been set to " + ChatColor.BOLD + "" + ChatColor.BLUE
							+ syringeDelay + ChatColor.RESET + "" + " (default: 2s ) ");
					{
						return true;
					}
				} else {
					player.sendMessage(ChatColor.RED + "Usage: /syringesetdelay <number-value 0-inf>"); {
						return true;
					}
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have permission!"); {
					return true;
				}
			}
		}

		
		return false;	
	}
	
	
	public boolean isNum(String num) {
		try {
			Long.valueOf(num);
		} catch (Exception e) {
			return false;
		}

		return true;
	}
	
	
	
	
}
	

	
