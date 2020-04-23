package tv.twitch.gutitubo.LastManStanding.LMSGame;

import org.bukkit.Bukkit;
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
	private LMSGame game = LastManStanding.getGame();

	@Override
	public void run() {

		// 1. ゲーム開始前準備時間はタイマーが進むたびにカウントダウン
		if (count < waitingTime) {
			LMSGameUtil.sendTitleToAll("", ChatColor.RED.toString()+(waitingTime - count), 0, 25, 0);

		// 2. ゲーム開始と同時に透明化解除とアナウンス
		} else if (count == waitingTime) {
			LMSGameUtil.sendTitleToAll("", ChatColor.RED.toString()+"- START -", 0, 25, 10);
			LMSGameUtil.takePlayerInvis(LastManStanding.getGame().getPlayers());

		// 3. ゲーム開始後はボスバーの更新 + ボーダー縮小
		} else {
			Bukkit.getWorld("world");
		}

		// 共通. 5秒ごとに矢を配布する
		if (count%5 == 0) LMSGameUtil.givePlayerArrow(game.getLogic().getAlives());

		// 共通. カウントを1すすめる
		count++;
	}

}
