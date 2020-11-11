package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSLastBattle;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBorder;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;

/**
 * LastBattleの挙動を定義
 */
public class LMSLastBattle {

	private static Location lastBattleLocation;
	private static double borderSize = 11;

	public static void start() {
		lastBattleLocation = new Location(Bukkit.getWorld("world"), 452.5, 120, 630.5);
		// 既存のボーダーをオフに
		LMSBorder.off();
		ArrayList<Player> players = LastManStanding.getGame().getLogic().getAlives();

		// 透明化
		LMSGameUtil.givePlayerInvis(players);
		for (Player p: players) {
			p.getInventory().clear();
		}
		for (Player all : Bukkit.getOnlinePlayers()) {
			all.teleport(lastBattleLocation);
			all.playSound(all.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 0.6F, 1F);
		}

		BukkitRunnable timer = new LastBattleTimer(players);
		timer.runTaskTimer(LastManStanding.main, 20, 20);
		LMSBorder.createLastBattle(lastBattleLocation, borderSize);
	}

}
