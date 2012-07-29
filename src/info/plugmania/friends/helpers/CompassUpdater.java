package info.plugmania.friends.helpers;

import info.plugmania.friends.Friends;
import info.plugmania.friends.helpers.Friend;

import org.bukkit.entity.Player;

public class CompassUpdater implements Runnable {

	Friends plugin;
	public CompassUpdater(Friends instance){
		plugin = instance;
	}

	@Override
	public void run() {
		for(Friend f: plugin.friendManager.getAll()){
			Player tracker = f.getCurrentLocatorPlayer();
			if(tracker == null) continue;
			if(!f.getPlayer().isOnline()) continue;
			((Player) f.getPlayer()).setCompassTarget(tracker.getLocation());
		}
	}
}
