package info.plugmania.friends;

import info.plugmania.friends.commands.FriendsChatCommand;
import info.plugmania.friends.commands.FriendsCommand;
import info.plugmania.friends.listeners.PlayerListener;

import info.plugmania.helpers.CompassUpdater;
import info.plugmania.helpers.FriendManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * TODO
 * Ignore/Request command instead of add/remove
 * Trading between friends
 * Find players with compass (cycle through with shift)
 */
public class Friends extends JavaPlugin {
	public YamlConfiguration mainConf;
	
	public boolean debug = false;
	
	public final ConfigUtil configUtil;
	public final Util util;

	public final FriendManager friendManager;
	
	public Friends() {
        configUtil = new ConfigUtil(this);
        util = new Util(this);
		friendManager = new FriendManager(this);
    }
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);
		Util.log(Util.pdfFile.getName() + " has been disabled");
	}
	
	@Override
	public void onEnable() { //enable
		Util.pdfFile = getDescription();
		Util.log("----------- " + Util.pdfFile.getName() + " has been enabled" + " -----------");
		Util.log(Util.pdfFile.getName() + " Version " + Util.pdfFile.getVersion());
		Util.log(Util.pdfFile.getName() + " By " + Util.pdfFile.getAuthors().get(0));
		
		ConfigUtil.loadConfig("config", "config");
		mainConf = ConfigUtil.getConfig("config");
		
		debug = mainConf.getBoolean("debug", false);
		
		PluginManager pm = getServer().getPluginManager();
		pm.registerEvents(new PlayerListener(this), this);
		
		registerCommands();

		//Start compass updator task
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, new CompassUpdater(this), 20L, 10 * 20L);
		
		Util.log("Succesfully loaded.");
	}
	
	private void registerCommands(){
		getCommand("friends").setExecutor(new FriendsCommand(this));
		getCommand("fchat").setExecutor(new FriendsChatCommand(this));
	}

	private String basePerm = "friends";
	
	public boolean hasPermission(String name, String perm) {
		Player player = Bukkit.getPlayer(name);
		return hasPermission(player, perm);
	}
	public boolean hasPermission(Player player, String perm) {
		if(player.hasPermission(basePerm + "." + perm) || player.hasPermission(basePerm + ".*")){
			Util.debug("Has permission for player: " + player.getName() + " and perm: " + basePerm + "." + perm);
			return true;
		}
		return false;
	}
}

