package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

/**
 * ゲーム進行用のStaticメソッドを集めたクラス
 * @author gutitubo
 *
 */
public class LMSGameUtil {

	/**
	 * 全プレイヤーに初期処理を行うメソッド
	 */
	public static void playerInitProc (List<Player> players) {
		givePlayerLoadout(players);
		givePlayerStatus(players);
	}

	/**
	 * プレイヤーに初期ステータス処理を行うメソッド
	 */
	public static void givePlayerStatus (Player p) {
		p.setWalkSpeed(0.25F);
	}

	/**
	 * 全プレイヤーに初期ステータス処理を行うメソッド
	 */
	public static void givePlayerStatus (List<Player> players) {
		for (Player p: players) {
			givePlayerStatus(p);
		}
	}

	/**
	 * プレイヤーのステータスをデフォルトにリセットする
	 */
	public static void resetPlayerStatus (Player p) {
		p.setWalkSpeed(0.2F);
	}

	/**
	 * 全プレイヤーのステータスをデフォルトにリセットする
	 */
	public static void resetPlayerStatus (List<Player> players) {
		for (Player p: players) {
			givePlayerStatus(p);
		}
	}

	/**
	 * プレイヤーに戦闘用装備を配るためのメソッド
	 */
	public static void givePlayerLoadout (Player p) {
		Inventory inv = p.getInventory();
		//TODO LMSItemの作成
	}

	/**
	 * 全プレイヤーに戦闘用装備を配るためのメソッド
	 */
	public static void givePlayerLoadout (List<Player> players) {
		for (Player p: players) {
			givePlayerLoadout(p);
		}
	}
}
