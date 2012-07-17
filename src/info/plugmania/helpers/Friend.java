package info.plugmania.helpers;

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

	public Friend(Player p, ArrayList<OfflinePlayer> friendsPlayers) {
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
		if(playerLocator == null) return getNextLocatorPlayer();
		if(Bukkit.getPlayerExact(playerLocator) == null){
			return getNextLocatorPlayer();
		} else {
			return Bukkit.getPlayerExact(playerLocator);
		}
	}

	public Player getNextLocatorPlayer(){
		ArrayList<Player> onlineFriends = getOnlineFriends();
		if(onlineFriends.size() == 0) return null;
		boolean isNext = false;
		for(Player p : onlineFriends){
			if(isNext){
				playerLocator = p.getName();
				return p;
			} else {
				if(playerLocator == p.getName()) isNext = true;
			}
		}
		return null;
	}
}
