package info.plugmania.helpers;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class FriendManager {

	private HashMap<String, Friend> friends = new HashMap<String, Friend>();

	public FriendManager (){
		//load all friend classes!!
	}

	public Friend getFriend(Player p){
		return friends.get(p.getName());
	}
}
