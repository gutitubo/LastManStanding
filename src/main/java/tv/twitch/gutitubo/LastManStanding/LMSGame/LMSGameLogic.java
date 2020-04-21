package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Score;

/**
 * ゲームの勝敗等ロジック部分を記述
 * @author gutitubo
 *
 */
public class LMSGameLogic {

	private LMSGame game; //このLogicが対応するgame
	private ArrayList<Player> alive; //生存者のリスト
	private HashMap<Player, Score> playerScore; //生存者とスコアのHashMap

	public LMSGameLogic(LMSGame game) {
		this.game = game;
		alive = game.getPlayers(); //参加者を生存者に追加
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
}
