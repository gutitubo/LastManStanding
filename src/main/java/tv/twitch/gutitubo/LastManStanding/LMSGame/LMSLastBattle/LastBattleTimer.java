package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSLastBattle;

import java.util.ArrayList;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;

public class LastBattleTimer extends BukkitRunnable {

	private int count = 0;
	private ArrayList<Player> alive;

	public LastBattleTimer(ArrayList<Player> alive) {
		this.alive = alive;
	}

	@Override
	public void run() {
		// Pre. ゲームが存在しないときにスケジューラをキャンセルする
		if (LastManStanding.getGame() == null) this.cancel();

		// 1. ゲーム開始前準備時間はタイマーが進むたびにカウントダウン
		if (count < 10) {
			String title = "";
			if (count < 5) title = ChatColor.DARK_RED.toString() + ChatColor.BOLD.toString() + "LAST BATTLE";
			LMSGameUtil.sendTitleToAll(title, ChatColor.RED.toString()+(10 - count), 0, 25, 0);
			LMSGameUtil.sendSoundToAll(Sound.BLOCK_NOTE_BLOCK_BELL, 0.4F, 0.4F);

			// 2. ゲーム開始と同時に透明化解除とアナウンス
		} else if (count == 10) {
			LMSGameUtil.sendTitleToAll("", ChatColor.RED.toString()+"- START -", 0, 25, 10);
			LMSGameUtil.takePlayerInvis(LastManStanding.getGame().getPlayers());
			LMSGameUtil.sendSoundToAll(Sound.ENTITY_ENDER_DRAGON_GROWL, 0.7F, 1F);
			LMSGameUtil.takePlayerInvis(alive);
			LMSGameUtil.givePlayerLoadout(alive);
			this.cancel();
		}
		count++;
	}

}
