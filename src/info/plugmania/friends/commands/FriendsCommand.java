package info.plugmania.friends.commands;


import info.plugmania.friends.Friends;
import info.plugmania.friends.Util;

import info.plugmania.helpers.Friend;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;


public class FriendsCommand implements CommandExecutor {

	Friends plugin;
	public FriendsCommand(Friends instance) {
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
				sender.sendMessage(Util.formatMessage(".-.-.-.-.-.-.-.-.-.-.-. " + Util.pdfFile.getName() + ".-.-.-.-.-.-.-.-.-.-.-."));
				sender.sendMessage(Util.formatMessage(plugin.getName() + " developed by " + Util.pdfFile.getAuthors().get(0)));
				return true;
			}

			//Make sure this isnt null!!! small chance but it could happen
			Friend friendPlayer = plugin.friendManager.getFriend(player);
			
			if(args.length == 0){
				if(friendPlayer == null) return false;
				sender.sendMessage(Util.formatMessage(ChatColor.GOLD + ".-.-.-.-.-.-.-.-.-.-.-. " + ChatColor.DARK_AQUA + "Friends" + ChatColor.GOLD +  ".-.-.-.-.-.-.-.-.-.-.-."));
				ArrayList<String> onlinePlayers = new ArrayList<String>();
				ArrayList<String> offlinePlayers = new ArrayList<String>();
				for(OfflinePlayer op : friendPlayer.getFriends()){
					if(op.isOnline())
						onlinePlayers.add(op.getName());
					else
						offlinePlayers.add(op.getName());
				}
				for(String s : onlinePlayers){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_PURPLE + s));
				}
				for(String s : offlinePlayers){
					sender.sendMessage(Util.formatMessage(ChatColor.BLUE + s));
				}
				//TODO: Add pagination
				return true;
			}
			if(args[0].equalsIgnoreCase("add")){ //TODO Must become request (both users must accept)
				if(friendPlayer == null) return false;
				if(args.length != 2){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Not enough params!"));
					return true;
				}
				OfflinePlayer newFriend = Bukkit.getOfflinePlayer(args[1]);
				if(newFriend.getName().equals(player.getName())){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "You cannot add yourself."));
					return true;
				}
				if(newFriend == null){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Player does not exist."));
					return true;
				}
				friendPlayer.addFriend(newFriend);
				return true;
			} else if(args[0].equalsIgnoreCase("remove")){ //TODO To become ignore/block
				if(friendPlayer == null) return false;
				if(args.length != 2){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Not enough params."));
					return true;
				}
				OfflinePlayer opFriend = Bukkit.getOfflinePlayer(args[1]);
				if(opFriend == null){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Player does not exist."));
					return true;
				}
				if(!friendPlayer.getFriends().contains(opFriend)){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Player is not your friend."));
					return true;
				}
				friendPlayer.removeFriend(opFriend);
				return true;
			} else if(args[0].equalsIgnoreCase("help")){
				sender.sendMessage(Util.formatMessage(ChatColor.GOLD + ".-.-.-.-.-.-.-.-.-.-.-. " + ChatColor.DARK_AQUA + Util.pdfFile.getName() + ChatColor.GOLD +  ".-.-.-.-.-.-.-.-.-.-.-."));
				//show the rest of help
				return true;
			}

			return false;
		}
		return false;
	}

}
