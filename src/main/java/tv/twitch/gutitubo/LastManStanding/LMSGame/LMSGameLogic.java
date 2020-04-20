package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.ArrayList;

import org.bukkit.entity.Player;

/**
 * ゲームの勝敗等ロジック部分を記述
 * @author gutitubo
 *
 */
public class LMSGameLogic {

	private LMSGame game;
	private ArrayList<Player> alive;

	public LMSGameLogic(LMSGame game) {
		this.game = game;
		alive = game.getPlayers(); //参加者を生存者に追加
	}

	/**
	 * プレイヤーが誰かをキルしたときの処理
	 * @param killer キラー
	 * @param victim 被害者
	 */
	public void killPlayer(Player killer, Player victim) {

	}
}
