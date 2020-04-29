package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

/**
 * Scoreを保持しておくHashMapをどうにかするクラス
 * FIXME 冗長的過ぎてウケる
 * @author gutitubo
 *
 */
public class LMSScoreHolder {
	/*
	 * 各スコアを保持するためのHashMap
	 */
	public static HashMap<String, Integer> killHolder = new HashMap<>();
	public static HashMap<String, Integer> scoreHolder = new HashMap<>();
	public static HashMap<String, Integer> rankHolder = new HashMap<>();
	public static HashMap<String, Integer> allPointHolder = new HashMap<>();

	/**
	 * Holderにスコアを登録する
	 * 死亡時と優勝時に呼び出す。
	 * @param score
	 */
	public static void registScore(LMSScore score, int rank) {
		// Pre. Playerを取得
		Player p = score.getPlayer();
		String name = p.getName();

		// 1. 各種ホルダーに値をput
		killHolder.put(name, score.getKill());
		scoreHolder.put(name, score.getScore());
		rankHolder.put(name, rank);

		// 2 allPointHolderにname登録されているか確認 されていれば加算
		if (allPointHolder.containsKey(name)) {
			int point = allPointHolder.get(name);
			allPointHolder.put(name, score.getScore() + point);
		} else {
			allPointHolder.put(name, score.getScore());
		}
	}

	/**
	 * Holderのスコアをソートして表示
	 */
	public static void display(int count, ScoreResultType type) {
		switch (type) {
		case ALL_POINT_RANK:
			displayAllPointRank(count);
			break;
		case KILL_RANK:
			displayKillRank(count);
			break;
		case POINT_RANK:
			displayPointRank(count);
			break;
		case SURVIVE_RANK:
			displayRank(count);
			break;
		default:
			return;
		}
	}

	/**
	 * 全員の累計スコアを表示するクラス
	 * @param count 表示する数
	 */
	private static void displayAllPointRank(int count) {
		// 値を取り出す
		ArrayList<String> players = new ArrayList<>();
		players.addAll(allPointHolder.keySet());

		// 降順にソートする
		Collections.sort(players, (p1, p2) ->
		allPointHolder.get(p1) - allPointHolder.get(p2)
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
			Bukkit.broadcastMessage(ChatColor.GOLD + "#" + (i+1) + " "
					+ ChatColor.RED.toString() + String.format("%-16s", players.get(i))
					+ ChatColor.GRAY.toString() + " - "
					+ ChatColor.RED.toString()
					+ String.format("%3d", allPointHolder.get(players.get(i)))
					+ ChatColor.GRAY + "POINT");
		}
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("======================");
	}

	private static void displayKillRank(int count) {
		// 値を取り出す
		ArrayList<String> players = new ArrayList<>();
		players.addAll(killHolder.keySet());

		// 降順にソートする
		Collections.sort(players, (p1, p2) ->
		killHolder.get(p1) - killHolder.get(p2)
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
			Bukkit.broadcastMessage(ChatColor.DARK_RED + "#" + (i+1) + " "
					+ ChatColor.DARK_RED.toString() + String.format("%-16s", players.get(i))
					+ ChatColor.GRAY.toString() + " - "
					+ ChatColor.DARK_RED.toString()
					+ String.format("%2d", killHolder.get(players.get(i)))
					+ ChatColor.GRAY + "KILL");
		}
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("======================");
	}

	private static void displayRank(int count) {
		// 値を取り出す
		ArrayList<String> players = new ArrayList<>();
		players.addAll(rankHolder.keySet());

		// 昇順にソートする
		Collections.sort(players, (p1, p2) ->
		rankHolder.get(p1) - rankHolder.get(p2));

		// Countがサイズを超えないようにする
		if (count > players.size()) {
			count = players.size();
		}

		// 表示する
		Bukkit.broadcastMessage("======================");
		Bukkit.broadcastMessage("");
		for (int i = 0; i < count; i++) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "#" + (i+1) + " "
					+ ChatColor.RED.toString() + String.format("%-16s", players.get(i))
					+ ChatColor.GRAY.toString() + " - "
					+ ChatColor.RED.toString()
					+ String.format("%2d", killHolder.get(players.get(i)))
					+ ChatColor.GRAY + "KILL");
		}
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("======================");
	}

	private static void displayPointRank(int count) {
		// 値を取り出す
		ArrayList<String> players = new ArrayList<>();
		players.addAll(scoreHolder.keySet());

		// 降順にソートする
		Collections.sort(players, (p1, p2) ->
		scoreHolder.get(p1) - scoreHolder.get(p2));

		// Countがサイズを超えないようにする
		if (count > players.size()) {
			count = players.size();
		}

		// 表示する
		Bukkit.broadcastMessage("======================");
		Bukkit.broadcastMessage("");
		for (int i = 0; i < count; i++) {
			Bukkit.broadcastMessage(ChatColor.GOLD + "#" + (i+1) + " "
					+ ChatColor.RED.toString() + String.format("%-16s", players.get(i))
					+ ChatColor.GRAY.toString() + " - "
					+ ChatColor.RED.toString()
					+ String.format("%3d", allPointHolder.get(players.get(i)))
					+ ChatColor.GRAY + "POINT");
		}
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage("======================");
	}

	/**
	 * スコアホルダーのリセット
	 */
	public static void clear() {
		killHolder.clear();
		scoreHolder.clear();
		rankHolder.clear();
	}

	/**
	 * AllPointHolderを含む全てのスコアホルダーをりせっと
	 */
	public static void allClear() {
		clear();
		allPointHolder.clear();
	}
}
