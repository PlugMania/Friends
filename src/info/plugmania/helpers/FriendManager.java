package info.plugmania.helpers;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class FriendManager {

	private static HashMap<String, Friend> friends = new HashMap<String, Friend>();

	public static Friend getFriend(Player p){
		return friends.get(p.getName());
	}
}
