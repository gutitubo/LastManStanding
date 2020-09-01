package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Arrow;
import org.bukkit.scheduler.BukkitRunnable;

import tv.twitch.gutitubo.LastManStanding.FireworkUtil;

public class OiwaiEventTimer extends BukkitRunnable {

	private Arrow arrow;
	private int count = 0;

	public OiwaiEventTimer(Arrow arrow) {
		this.arrow = arrow;
	}

	@Override
	public void run() {
		if (arrow == null) this.cancel();

		Location loc = arrow.getLocation();
		if (count % 2 == 0) {
			FireworkUtil.spawnArrowFirework(loc, Color.RED);
		} else {
			FireworkUtil.spawnArrowFirework(loc, Color.WHITE);
		}
		count++;
	}

}
