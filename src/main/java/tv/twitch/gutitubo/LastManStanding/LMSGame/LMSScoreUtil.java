package tv.twitch.gutitubo.LastManStanding.LMSGame;

import org.bukkit.entity.Player;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;

/**
 * Score処理関連をどうにかしてくれる
 * @author gutitubo
 *
 */
public class LMSScoreUtil {
	private static LMSGame game;

	/**
	 * Killポイントをプレイヤーに付与
	 * @param p
	 * @param value
	 */
	public static void giveKillPoint(Player p, Player killed) {
		// 1. LMSScoreを取得
		LMSScore score = getPlayerScore(p);

		// 2. nullだったら終わり
		if (score == null) return;

		// 3. 渡すkillPointを設定
		int value = 0;
	}

	/**
	 * 順位ポイントをプレイヤーに付与
	 * @param p
	 * @param rank
	 */
	public static void giveRankPoint(Player p, int rank) {

	}

	private static LMSScore getPlayerScore(Player p) {
		// 1. ゲームの存在を確認する
		game = LastManStanding.getGame();
		if (game == null) return null;

		// 2. Playerのスコアを取得
		return game.getLogic().getPlayerScore().get(p);
	}
}
