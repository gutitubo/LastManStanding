package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;

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
		// 1. killer各種ポイントを振り分け,
		killerPointProc(killer, victim);

		// 2. Victimへの後処理
		deadProcess(victim);

		// 3. VictimをKillerのカメラに
		if (killer != null) victim.setSpectatorTarget(killer);

		// 4. VictimにKillerと順位を表示
		if (killer != null)
			victim.sendTitle(ChatColor.DARK_RED + ChatColor.BOLD .toString()+ "#" + (alive.size() + 1)
			, ChatColor.RED + killer.getName() + " に倒された。", 10, 60, 10);

		// 5. KillLog表示
		killLog(killer, victim);

		// 6. Killerが最後の1人になった場合は終了
		if (alive.size() == 1) {
			if (killer != null) {
				winGame(killer);
			} else {
				winGame(alive.get(0));
			}
		}
	}

	/**
	 * Killerに各種ポイントやクールダウン解消等の恩恵を与える
	 */
	public void killerPointProc(Player killer, Player victim) {
		// Pre. killer不在の場合はだめ
		if (killer == null) return;

		// 1. 矢を配布
		LMSGameUtil.givePlayerArrow(killer);

		// 2. クールダウン解消

		// 3. キルを追加
		getPlayerScore().get(killer).addKill(1);

		// 4. ポイントを計算

		// 5. バウンティを計算
	}

	/**
	 * 死亡したプレイヤーへの処理
	 * @param victim 被害者
	 */
	public void deadProcess(Player victim) {
		// 1. 観戦モードに変更
		victim.setGameMode(GameMode.SPECTATOR);

		// 2. インベントリクリア
		victim.getInventory().clear();

		// 3. Aliveリストから削除
		alive.remove(victim);
	}

	/**
	 * 勝者を指定してゲームをたたむメソッド
	 * @param winner 勝者
	 */
	public void winGame(Player winner) {
		// 1. 勝者をアナウンス
		LMSGameUtil.sendTitleToAll(ChatColor.GOLD.toString() + ChatColor.BOLD + winner.getName() + " WON!"
				,ChatColor.DARK_RED.toString() + getPlayerScore().get(winner).getKill() + " kill", 10, 100, 10);

		// 2. 結果をファイル出力 + ゲーム内計算


		// 3. ゲームリセット, ロビー転送
		ArrayList<Player> all = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			all.add(p);
		}
		LastManStanding.resetGame();
	}

	public void killLog(Player killer, Player victim) {
		String r = ChatColor.RESET.toString() + ChatColor.GRAY.toString();
		String victimName = ChatColor.DARK_BLUE.toString() + victim.getName();
		String rank = ChatColor.GOLD.toString() + "#" + (alive.size()+1);
		String kill = ChatColor.RED.toString() + playerScore.get(victim).getKill()
				+ ChatColor.GRAY.toString() + "kill";

		if (killer == null) {
			Bukkit.broadcastMessage(victimName + r + " が死亡した。 - " + rank + " " + kill);
		} else {
			String killerName = ChatColor.GOLD.toString() + killer.getName();
			Bukkit.broadcastMessage(killerName + r + " が " + victimName + r + " を殺した。 - " + rank + " " + kill);
		}
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
