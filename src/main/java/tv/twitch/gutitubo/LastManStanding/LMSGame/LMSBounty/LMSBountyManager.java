package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
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
		Bukkit.broadcastMessage(isBountyMode + "");
		if (!isBountyMode) return;
		Bukkit.broadcastMessage("killer:"+killer);
		Bukkit.broadcastMessage("victim:"+victim);
		if (killer == null || victim == null) return;

		// 名前を取得
		String killerName = killer.getName();
		String victimName = victim.getName();
		Bukkit.broadcastMessage("killerName:"+killerName);
		Bukkit.broadcastMessage("victimName:"+victimName);
		// 各バウンティを定義
		LMSBounty killerBounty = null;
		LMSBounty victimBounty = null;

		// 各バウンティを取得
		for (Object obj : LMSBountyHolder.bountyHolder.stream().toArray()) {
			LMSBounty bounty = (LMSBounty) obj;
			if (bounty.getName().equals(killerName)) killerBounty = bounty;
			if (bounty.getName().equals(victimName)) victimBounty = bounty;
		}
		Bukkit.broadcastMessage("killerBounty:"+killerBounty);
		Bukkit.broadcastMessage("victimBounty:"+victimBounty);
		// Bountyがないとだめ
		if (killerBounty == null || victimBounty == null) return;
		// Bountyを計算する
		int value = killerBounty.robBounty(victimBounty);
		Bukkit.broadcastMessage("value:"+value);

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

	/**
	 * BountyTopの表示
	 */
	public static void displayBountyTop(int count) {
		// Bounty格納用 ArrayListの作成
		ArrayList<LMSBounty> players = new ArrayList<>();
		for (Object obj : LMSBountyHolder.bountyHolder.toArray()) {
			players.add((LMSBounty) obj);
		}

		// 降順にソートする
		Collections.sort(players, (p1, p2) ->
		p1.getBounty() - p2.getBounty()
				);
		Collections.reverse(players);

		// Countがサイズを超えないようにする
		if (count > players.size()) {
			count = players.size();
		}

		// 表示する
		Bukkit.broadcastMessage("======================");
		Bukkit.broadcastMessage("");
		for (int i = 0; i < count; i++) {
			Bukkit.broadcastMessage(ChatColor.RED.toString() + String.format("%-16s", players.get(i).getName())
					+ ChatColor.GRAY.toString() + " - "
					+ ChatColor.GOLD.toString()
					+ "$"
					+ String.format("%5d", players.get(i).getBounty()));
		}
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("======================");
	}
}
