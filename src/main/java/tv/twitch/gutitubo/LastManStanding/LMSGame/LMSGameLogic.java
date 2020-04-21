package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;

/**
 * ゲームの勝敗等ロジック部分を記述
 * @author gutitubo
 *
 */
public class LMSGameLogic {

	private LMSGame game; //このLogicが対応するgame
	private ArrayList<Player> alive; //生存者のリスト
	private HashMap<Player, LMSScore> playerScore; //生存者とスコアのHashMap

	public LMSGameLogic(LMSGame game) {
		this.game = game;
		alive = game.getPlayers(); //参加者を生存者に追加
		playerScore = new HashMap<>();
		for (Player p: alive) {
			playerScore.put(p, new LMSScore(p));
		}
	}

	/**
	 * プレイヤーが誰かをキルしたときの処理
	 * キラーがいない場合はkillPlayerをnullとする
	 * @param killer キラー
	 * @param victim 被害者
	 */
	public void killPlayer(Player killer, Player victim) {

	}

	/**
	 * 死亡したプレイヤーへの処理
	 * @param victim 被害者
	 */
	public void deadProcess(Player victim) {

	}

	/**
	 * 試合結果を表示するためのテキスト
	 */
	public void displayResult() {

	}

	/**
	 * プレイヤースコア管理Mapを取得するmethod
	 */
	public HashMap<Player, LMSScore> getPlayerScore() {
		return playerScore;
	}

	/**
	 * 生存者取得用メソッド
	 */
	public ArrayList<Player> getAlives() {
		return alive;
	}
}
