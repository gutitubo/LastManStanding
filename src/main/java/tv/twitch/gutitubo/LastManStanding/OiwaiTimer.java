package tv.twitch.gutitubo.LastManStanding;

import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;

public class OiwaiTimer extends BukkitRunnable {

	@Override
	public void run() {
		if (LastManStanding.getGame() == null) {
			Location spawn = LMSGameUtil.getLobby();
			FireworkUtil.spawnRandomFireworks(spawn, 20);
			FireworkUtil.spawnRandomFireworks(spawn, 20);
			FireworkUtil.spawnRandomFireworks(spawn, 20);
			FireworkUtil.spawnRandomFireworks(spawn, 20);
			FireworkUtil.spawnRandomFireworks(spawn, 20);
		}
	}

}
