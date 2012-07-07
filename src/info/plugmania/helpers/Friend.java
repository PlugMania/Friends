package info.plugmania.helpers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Friend {

	private String playerName;
	private List<Friend> friends = new ArrayList<Friend>();

	public Friend(Player p) {
		playerName = p.getName();
	}

	public List<Friend> getFriends(){
		return friends;
	}

	public List<Friend> addFriend(Friend f){
		friends.add(f);
		return friends;
	}

	public OfflinePlayer getPlayer(){
		return Bukkit.getOfflinePlayer(playerName);
	}
}
