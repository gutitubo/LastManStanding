package tv.twitch.gutitubo.LastManStanding.LMSGame;

import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

/**
 * ゲーム進行用タイマークラス
 * @author gutitubo
 *
 */
public class LMSGameTimer extends BukkitRunnable {

	private int count = 0;
	private int waitingTime = ConfigValue.waitingTime;

	@Override
	public void run() {
		//if (count%5 == 0) LMSGameUtil.givePlayerArrow();
		if (count < waitingTime) {
			LMSGameUtil.sendTitleToAll("", ChatColor.RED.toString()+(waitingTime - count), 0, 25, 0);
		} else if (count == waitingTime) {
			LMSGameUtil.sendTitleToAll("", ChatColor.RED.toString()+"- START -", 0, 25, 10);
			LMSGameUtil.playerInitProc(LastManStanding.getGame().getPlayers());
			LMSGameUtil.takePlayerInvis(LastManStanding.getGame().getPlayers());
		}

		count++;
	}

}
