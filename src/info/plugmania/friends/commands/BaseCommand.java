package info.plugmania.friends.commands;


import info.plugmania.friends.Friends;
import info.plugmania.friends.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class BaseCommand implements CommandExecutor {

	Friends plugin;
	public BaseCommand(Friends instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(command.getName().equalsIgnoreCase("friends")){
			Player player = null;
			if(sender instanceof Player)
				player = (Player) sender;
			
			if(player != null){
				if(!plugin.hasPermission(player, "default")){
					Util.sendMessageNoPerms(sender);
					return true;
				}
			}
			
			if(player == null){
				sender.sendMessage(Util.formatMessage("---------------------- " + Util.pdfFile.getName() + " ----------------------"));
				sender.sendMessage(Util.formatMessage(plugin.getName() + " developed by " + Util.pdfFile.getAuthors().get(0)));
				sender.sendMessage(Util.formatMessage("Visit my site: http://www.that-ryan.com/"));
				return true;
			}
			
			if(args.length == 0){
				sender.sendMessage(Util.formatMessage("---------------------- " + Util.pdfFile.getName() + " ----------------------"));
				sender.sendMessage(Util.formatMessage(plugin.getName() + " developed by " + Util.pdfFile.getAuthors().get(0)));
				sender.sendMessage(Util.formatMessage("Visit my site: http://www.that-ryan.com/ (<-- Click it!)"));
			}
			return true;
		}
		return false;
	}

}
