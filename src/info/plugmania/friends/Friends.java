package info.plugmania.friends;



import info.plugmania.friends.commands.BaseCommand;
import info.plugmania.friends.listeners.PlayerListener;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Friends extends JavaPlugin {
	public YamlConfiguration mainConf;
	
	public boolean debug = false;
	
	public final ConfigUtil configUtil;
	public final Util util;
	
	public Friends() {
        configUtil = new ConfigUtil(this);
        util = new Util(this);
    }
	
	@Override
	public void onDisable() {
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
		
		Util.log("Succesfully loaded.");
	}
	
	private void registerCommands(){
		getCommand("friends").setExecutor(new BaseCommand(this));
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
