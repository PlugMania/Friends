package info.plugmania.helpers;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Friend {

	private String playerName;

	private boolean inFriendChat = false;

	private ArrayList<OfflinePlayer> friends = new ArrayList<OfflinePlayer>();

	public Friend(Player p, ArrayList<OfflinePlayer> friendsPlayers) {
		playerName = p.getName();
		friends = friendsPlayers;
	}

	public ArrayList<OfflinePlayer> getFriends(){
		return friends;
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
}
