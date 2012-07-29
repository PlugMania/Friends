package info.plugmania.friends.commands;


import info.plugmania.friends.Friends;
import info.plugmania.friends.Util;
import info.plugmania.friends.helpers.Friend;

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
				//TODO: Add pagination, no longer necessary since chat scrolls (but looks nicer)
				return true;
			}
			if(args[0].equalsIgnoreCase("request") || args[0].equalsIgnoreCase("req")){
				if(friendPlayer == null) return false;
				if(args.length != 2){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Not enough params!"));
					return true;
				}
				OfflinePlayer newFriend = Bukkit.getOfflinePlayer(args[1]);
				if(newFriend == null){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "Player does not exist."));
					return true;
				}
				if(newFriend.getName().equals(player.getName())){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "You cannot add yourself."));
					return true;
				}
				plugin.friendManager.sendRequest(player, newFriend);
				if(newFriend.isOnline()){
					Player newFriendP = (Player) newFriend;
					newFriendP.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "You have been sent a friend request by " + player.getName() + "."));
					newFriendP.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "To become friends type \"/friends confirm " + player.getName() + "\"."));
				} //TODO Add ignore command for friend requests
				return true;
			} else if(args[0].equalsIgnoreCase("confirm")){
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
				if(!plugin.friendManager.getRequests(player).contains(opFriend.getName())){
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "That player never sent you a request."));
					return true;
				}
				plugin.friendManager.completeRequest(player, opFriend);
				player.sendMessage(Util.formatMessage(ChatColor.DARK_RED + "You are now friends with " + opFriend.getName() + "."));
				if(opFriend.isOnline()){
					((Player) opFriend).sendMessage(Util.formatMessage(ChatColor.DARK_RED + "You are now friends with " + player.getName() + "."));
				}
				return true;
			} else if(args[0].equalsIgnoreCase("remove")){ //TODO To become block (instant removal of friend)
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
