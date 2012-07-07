package info.plugmania.helpers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Friend {

	private String playerName;
	private ArrayList<OfflinePlayer> friends = new ArrayList<OfflinePlayer>();
	private ArrayList<OfflinePlayer> friendRequests = new ArrayList<OfflinePlayer>();

	public Friend(Player p) {
		playerName = p.getName();
	}

	public ArrayList<OfflinePlayer> getFriends(){
		return friends;
	}

	public ArrayList<OfflinePlayer> addFriend(OfflinePlayer friend){
		friends.add(friend);
		return friends;
	}

	public ArrayList<OfflinePlayer> removeFriend(OfflinePlayer friend){
		friends.remove(friend);
		return friends;
	}

	public ArrayList<OfflinePlayer> getFriendRequests(){
		return friendRequests;
	}

	public OfflinePlayer getPlayer(){
		return Bukkit.getOfflinePlayer(playerName);
	}
}
