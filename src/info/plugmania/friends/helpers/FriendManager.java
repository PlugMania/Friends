package info.plugmania.friends.helpers;

import info.plugmania.friends.ConfigUtil;
import info.plugmania.friends.Friends;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FriendManager {

	private HashMap<String, Friend> friends = new HashMap<String, Friend>();
	private HashMap<String, ArrayList<String>> requests = new HashMap<String, ArrayList<String>>();

	Friends plugin;
	public FriendManager (Friends instance){
		plugin = instance;
	}

	public Friend getFriend(Player p){
		return friends.get(p.getName());
	}
	
	public ArrayList<String> getRequests(Player p){
		if(requests.containsKey(p.getName())) return requests.get(p.getName());
		return null;
	}

	public void sendRequest(Player p, OfflinePlayer otherP){//p is the one sending
		if(!requests.containsKey(otherP.getName())) requests.put(otherP.getName(), new ArrayList<String>());
		requests.get(otherP.getName()).add(p.getName());
	}
	
	public void voidRequest(Player p, OfflinePlayer otherP){//p is the one that got sent the request
		if(!requests.containsKey(p.getName())) requests.put(p.getName(), new ArrayList<String>());
		if(requests.get(p.getName()).contains(otherP.getName())) requests.get(p.getName()).remove(otherP.getName());
	}
	
	public void completeRequest(Player p, OfflinePlayer otherP){ //p is the one that got sent the request
		if(!requests.containsKey(p.getName())) requests.put(p.getName(), new ArrayList<String>());
		if(requests.get(p.getName()).contains(otherP.getName())){
			requests.get(p.getName()).remove(otherP.getName());
			Friend pF = friends.get(p.getName());
			loadFriend(otherP);
			Friend pO = friends.get(otherP.getName());
			if(pF == null || pO == null) return;
			pF.addFriend(otherP);
			pO.addFriend(p);
			if(!otherP.isOnline()) unloadFriend(otherP);
		}
	}
	
	public ArrayList<Friend> getAll(){
		ArrayList<Friend> allFriendClasses = new ArrayList<Friend>();
		for(Friend f : friends.values()){
			allFriendClasses.add(f);
		}
		return allFriendClasses;
	}

	public Friend loadFriend(OfflinePlayer p){
		ArrayList<OfflinePlayer> friendList = new ArrayList<OfflinePlayer>();

		if(ConfigUtil.getPConfExists(p)){
			//Load friends from file
			List<String> frens = ConfigUtil.getPValStringList(p, "friends");
			if(frens != null){
				for(String s : frens){
					OfflinePlayer op = Bukkit.getOfflinePlayer(s);
					if(op != null) friendList.add(op);
				}
			}
		}

		Friend friend = new Friend(p, friendList);

		friends.put(p.getName(), friend);

		return friends.get(p.getName());
	}

	public void unloadFriend(OfflinePlayer p){
		if(!friends.containsKey(p.getName())) return;
		Friend f = friends.get(p.getName());
		
		//Save
		File pconffold = new File(plugin.getDataFolder() + File.separator + "userdata");
		File pconfl = new File(plugin.getDataFolder() + File.separator + "userdata" + File.separator + p.getName().toLowerCase() + ".yml");
		if(!pconffold.exists() || !pconfl.exists()){
			pconffold.mkdirs();
			try {
				pconfl.createNewFile();
			} catch (IOException e) {
			}
		}
		List<String> frens = new ArrayList<String>();
		if(f.getFriends() != null){
			for(OfflinePlayer op : f.getFriends()){
				frens.add(op.getName());
			}
		}
		ConfigUtil.setPValStringList(p, frens, "friends");
		
		friends.remove(p.getName());
	}
}
