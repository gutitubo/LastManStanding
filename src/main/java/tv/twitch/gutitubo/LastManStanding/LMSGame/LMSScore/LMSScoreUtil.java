package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore;

import org.bukkit.entity.Player;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

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
		int value = ConfigValue.killPoint;
		if (LMSGameUtil.isStreamer(killed.getName())) value = ConfigValue.streamerKillPoint;
		score.addScore(value);
	}

	/**
	 * 順位ポイントをプレイヤーに付与
	 * @param p
	 * @param rank
	 */
	public static void giveRankPoint(Player p, int rank) {
		// 1. LMSScoreを取得
		LMSScore score = getPlayerScore(p);

		// 2. nullだったら終わり
		if (score == null) return;

		// 3. 順位からポイントを取得し、Scoreに加算
		int value = getPointFromRank(rank);
		score.addScore(value);
	}

	/**
	 * 順位からポイントを取得するメソッド
	 * @param rank
	 * @return
	 */
	private static int getPointFromRank(int rank) {
		int value = 0;
		if (rank == 1) value = 100;
		else if (rank == 2) value = 50;
		else if (rank == 3) value = 45;
		else if (rank == 4) value = 40;
		else if (rank == 5) value = 35;
		else if (rank == 6) value = 30;
		else if (rank == 7) value = 25;
		else if (rank == 8) value = 20;
		else if (rank == 9) value = 15;
		else if (rank == 10) value = 10;
		else if (rank >= 15) value = 8;
		else if (rank >= 20) value = 6;
		else if (rank >= 25) value = 4;
		else value = 2;
		return value;
	}

	private static LMSScore getPlayerScore(Player p) {
		// 1. ゲームの存在を確認する
		game = LastManStanding.getGame();
		if (game == null) return null;

		// 2. Playerのスコアを取得
		return game.getLogic().getPlayerScore().get(p);
	}
}
