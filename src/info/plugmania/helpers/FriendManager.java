package info.plugmania.helpers;

import info.plugmania.friends.Friends;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendManager {

	private HashMap<String, Friend> friends = new HashMap<String, Friend>();
	private HashMap<String, String> requests = new HashMap<String, String>();

	Friends plugin;
	public FriendManager (Friends instance){
		plugin = instance;
	}

	public Friend getFriend(Player p){
		return friends.get(p.getName());
	}

	public ArrayList<Friend> getAll(){
		ArrayList<Friend> allFriendClasses = new ArrayList<Friend>();
		for(Friend f : friends.values()){
			allFriendClasses.add(f);
		}
		return allFriendClasses;
	}

	public Friend loadFriend(Player p){
		ArrayList<OfflinePlayer> friendList = new ArrayList<OfflinePlayer>();
		//load friends from file and add to list
		//use the ConfigUtil.setPVal to save load array list

		Friend friend = new Friend(p, friendList);

		friends.put(p.getName(), friend);

		return friends.get(p.getName());
	}

	public void unloadFriend(Player p){
		if(!friends.containsKey(p.getName())) return;
		friends.remove(p.getName());
	}
}
