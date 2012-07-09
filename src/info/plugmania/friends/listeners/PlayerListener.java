package info.plugmania.friends.listeners;


import info.plugmania.friends.Friends;

import info.plugmania.helpers.Friend;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PlayerListener implements Listener {

	Friends plugin;
	public PlayerListener(Friends instance) {
		plugin = instance;
	}

	@EventHandler
	public void onPlayerChat(PlayerChatEvent event){
		if(event.isCancelled()) return;

		Player player = event.getPlayer();
		Friend friendPlayer = plugin.friendManager.getFriend(player);
		if(!friendPlayer.isInFriendChat()) return;

		//formatting, hardcoded for now
		String msg = event.getMessage();
		String format = ChatColor.DARK_AQUA + "[FriendChat] " + ChatColor.WHITE + "<" + player.getName() + "> : " + msg;

		//send to each friend if they are online
		for(OfflinePlayer op : friendPlayer.getFriends()){
			if(!op.isOnline()) return;
			Player p = (Player) op;
			p.sendMessage(format);
		}
		//dont forget to send it to the sender
		player.sendMessage(format);

		//set cancelled so we dont send the message twice, and to stop other plugins interferring
		event.setCancelled(true);
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event){
		plugin.friendManager.loadFriend(event.getPlayer());
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event){
		plugin.friendManager.unloadFriend(event.getPlayer());
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent event){
		plugin.friendManager.unloadFriend(event.getPlayer());
	}
}
