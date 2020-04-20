package tv.twitch.gutitubo.LastManStanding.LMSGame;

import org.bukkit.scheduler.BukkitRunnable;

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
			//TODO ゲーム開始カウントダウン
		} else if (count == waitingTime) {
			//TODO ゲーム開始アナウンス
		}

		count++;
	}

}
