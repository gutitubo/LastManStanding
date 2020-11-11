package tv.twitch.gutitubo.LastManStanding.LMSGame;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
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
		// Pre. ゲームが存在しないときにスケジューラをキャンセルする
		if (LastManStanding.getGame() == null) this.cancel();

		// 1. ゲーム開始前準備時間はタイマーが進むたびにカウントダウン
		if (count < waitingTime) {
			String title = "";
			if (count < 5) title = ChatColor.RED.toString() + ChatColor.BOLD.toString() + "LAST MAN STANDING";
			LMSGameUtil.sendTitleToAll(title, ChatColor.RED.toString()+(waitingTime - count), 0, 25, 0);
			LMSGameUtil.sendSoundToAll(Sound.BLOCK_NOTE_BLOCK_SNARE, 0.4F, 0.4F);

		// 2. ゲーム開始と同時に透明化解除とアナウンス
		} else if (count == waitingTime) {
			LMSGameUtil.sendTitleToAll("", ChatColor.RED.toString()+"- START -", 0, 25, 10);
			LMSGameUtil.takePlayerInvis(LastManStanding.getGame().getPlayers());
			LMSGameUtil.sendSoundToAll(Sound.ENTITY_ENDER_DRAGON_GROWL, 0.7F, 1F);
			game.setInGame(true);
			LMSBorder.create();
			LMSBorder.start();

		// 3. ゲーム開始後はボスバーの更新 + ボーダー縮小
		} else {
			Bukkit.getWorld("world");
		}

		// 共通. 5秒ごとに矢を配布する
		if (count%5 == 0) LMSGameUtil.givePlayerArrow(game.getLogic().getAlives());

		// 共通. 60秒ごとに5秒発光させる
		if (count%60 == 0) LMSGameUtil.glowAll(game.getLogic().getAlives());

		// 共通. カウントを1すすめる
		count++;
	}

}
