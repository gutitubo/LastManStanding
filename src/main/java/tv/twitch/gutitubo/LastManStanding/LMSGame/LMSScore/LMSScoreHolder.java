package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore;

import java.util.HashMap;

import org.bukkit.entity.Player;

/**
 * Scoreを保持しておくHashMapをどうにかするクラス
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
	 * TODO: 死亡時と優勝時に呼び出す。
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
