package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty;

import java.util.List;

import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class LMSBountyManager {

	/* BountyMode判別用boolean */
	public static boolean isBountyMode = false;

	/**
	 * Bountyモードを有効化する
	 */
	public static void enableBounty(List<Player> players) {
		// Bountyモードを有効化する
		isBountyMode = true;

		// プレイヤーリストをBountyに登録
		LMSBountyHolder.registBounty(players);
	}

	public static void disableBounty() {
		isBountyMode = false;
	}

	/**
	 * バウンティを計算する
	 */
	public static void calcBounty(Player killer, Player victim) {
		// Bountyモードが有効じゃないとだめ
		if (!isBountyMode) return;
		if (killer == null || victim == null) return;

		// 名前を取得
		String killerName = killer.getName();
		String victimName = victim.getName();

		// 各バウンティを定義
		LMSBounty killerBounty = null;
		LMSBounty victimBounty = null;

		// 各バウンティを取得
		for (Object obj : LMSBountyHolder.bountyHolder.stream().toArray()) {
			LMSBounty bounty = (LMSBounty) obj;
			if (bounty.getName().contains(killerName)) killerBounty = bounty;
			if (bounty.getName().contains(victimName)) victimBounty = bounty;
		}

		// Bountyがないとだめ
		if (killerBounty == null || victimBounty == null) return;

		// Bountyを計算する
		int value = killerBounty.robBounty(victimBounty);

		// 両者にバウンティのメッセージを送信
		killer.sendMessage(ChatColor.GOLD + "$" + value + " を獲得！");
		victim.sendMessage(ChatColor.RED + "$" + value + " を失った…");
	}

	/**
	 * StringからBountyを取得
	 */
	public static LMSBounty getBounty(String name) {
		LMSBounty bounty = null;
		for (Object obj : LMSBountyHolder.bountyHolder.stream().toArray()) {
			LMSBounty temp = (LMSBounty) obj;
			if (temp.getName().equalsIgnoreCase(name)) bounty = temp;
		}
		return bounty;
	}
}
