package info.plugmania.friends.helpers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Friend {

	private String playerName;
	private String playerLocator;

	private boolean inFriendChat = false;

	private ArrayList<OfflinePlayer> friends = new ArrayList<OfflinePlayer>();

	public Friend(OfflinePlayer p, ArrayList<OfflinePlayer> friendsPlayers) {
		playerName = p.getName();
		friends = friendsPlayers;
	}

	public ArrayList<OfflinePlayer> getFriends(){
		return friends;
	}

	public ArrayList<Player> getOnlineFriends(){
		ArrayList<Player> fArray = new ArrayList<Player>();
		for(OfflinePlayer op : friends){
			if(op.isOnline()) fArray.add((Player) op);
		}
		return fArray;
	}

	public ArrayList<OfflinePlayer> addFriend(OfflinePlayer player){
		friends.add(player);
		return friends;
	}

	public ArrayList<OfflinePlayer> removeFriend(OfflinePlayer player){
		friends.remove(player);
		return friends;
	}

	public OfflinePlayer getPlayer(){
		return Bukkit.getPlayer(playerName);
	}

	public boolean isInFriendChat(){
		return inFriendChat;
	}

	public boolean setInFriendChat(boolean inChat){
		return inFriendChat = inChat;
	}

	public Player getCurrentLocatorPlayer(){
		if(playerLocator == null) return null;
		return Bukkit.getPlayerExact(playerLocator);
	}

	public Player getNextLocatorPlayer(){
		ArrayList<Player> onlineFriends = getOnlineFriends();
		if(onlineFriends.size() == 0) return null;
		int index = 0;
		if(playerLocator != null){
			Player currLocator = Bukkit.getPlayerExact(playerLocator);
			System.out.print("1");
			if(currLocator != null && onlineFriends.contains(currLocator)){
				index = onlineFriends.indexOf(currLocator) + 1;
				if(index >= onlineFriends.size()) index = 0;
			}
		}
		System.out.print(index);
		return onlineFriends.get(index);
	}
}
