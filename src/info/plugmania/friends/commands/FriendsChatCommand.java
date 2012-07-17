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

public class FriendsChatCommand implements CommandExecutor {

	Friends plugin;
	public FriendsChatCommand(Friends instance) {
		plugin = instance;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
		if(command.getName().equalsIgnoreCase("fchat")){
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
				Util.sendMessageNotPlayer(sender);
				return true;
			}

			//Make sure this isnt null!!! small chance but it could happen
			Friend friendPlayer = plugin.friendManager.getFriend(player);

			if(args.length == 0){
				if(friendPlayer == null) return false;
				if(friendPlayer.isInFriendChat())
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_AQUA + "You no longer in friends chat."));
				else
					sender.sendMessage(Util.formatMessage(ChatColor.DARK_AQUA + "You are now in friends chat."));

				friendPlayer.setInFriendChat(!friendPlayer.isInFriendChat());
				return true;
			}

			return false;
		}
		return false;
	}
}
