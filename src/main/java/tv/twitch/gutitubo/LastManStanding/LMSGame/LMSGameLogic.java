package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import tv.twitch.gutitubo.LastManStanding.FireworkUtil;
import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty.LMSBountyManager;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.LMSScore;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.LMSScoreHolder;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.LMSScoreUtil;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.ScoreResultType;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;
import tv.twitch.gutitubo.LastManStanding.files.CSVCreator;

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
		// EX. お祝いモードの花火
		FireworkUtil.spawnDeadFirework(victim.getLocation(), getPlayerScore().get(victim).getKill());

		// 1. killer各種ポイントを振り分け,
		killerPointProc(killer, victim);

		// 2. バウンティを計算
		LMSBountyManager.calcBounty(killer, victim);

		// 3. Victimへの後処理
		deadProcess(victim);
		LMSScoreUtil.giveRankPoint(victim, alive.size() + 1);

		// 4. VictimをKillerのカメラに
		if (killer != null) swapSpecCamera(killer, victim);

		// 5. VictimにKillerと順位を表示
		if (killer != null)
			victim.sendTitle(ChatColor.DARK_RED + ChatColor.BOLD .toString()+ "#" + (alive.size() + 1)
					, ChatColor.RED + killer.getName() + " に倒された。", 10, 60, 10);

		// 6. Killerにサウンドを追加
		if (killer != null)
		killer.playSound(killer.getLocation(), Sound.ENTITY_GHAST_SHOOT, 1F, 1F);

		// 7. KillLog表示
		killLog(killer, victim);

		// 8. Killerが最後の1人になった場合は終了
		if (alive.size() == 1) {
			winGame(alive.get(0));
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
		LMSScoreUtil.giveKillPoint(killer, victim);
	}

	/**
	 * 死亡したプレイヤーへの処理
	 * @param victim 被害者
	 */
	public void deadProcess(Player victim) {
		// Pre. スコア登録
		LMSScoreHolder.registScore(playerScore.get(victim), alive.size());

		// 1. 観戦モードに変更
		victim.setGameMode(GameMode.SPECTATOR);

		// 2. インベントリクリア
		victim.getInventory().clear();

		// 3. Aliveリストから削除
		alive.remove(victim);

		// 4. 死んだ場所に雷
		victim.getWorld().strikeLightningEffect(victim.getLocation());

		// 5. 全員に音を奏でる
		LMSGameUtil.sendSoundToAll(Sound.ENTITY_ARROW_HIT_PLAYER, 1F, 1F);

		// 6. タイトル表示
		victim.sendTitle(ChatColor.DARK_RED + ChatColor.BOLD .toString()+ "#" + (alive.size() + 1)
				, "", 10, 60, 10);
	}

	/**
	 * 勝者を指定してゲームをたたむメソッド
	 * @param winner 勝者
	 */
	public void winGame(Player winner) {
		// Pre. スコア登録
		LMSScoreHolder.registScore(playerScore.get(winner), 1);

		// 1. 勝者をアナウンス
		LMSGameUtil.sendTitleToAll(ChatColor.GOLD.toString() + ChatColor.BOLD + winner.getName() + " WON!"
				,ChatColor.DARK_RED.toString() + getPlayerScore().get(winner).getKill() + " kill", 10, 100, 10);
		Bukkit.broadcastMessage("");
		Bukkit.broadcastMessage(ChatColor.GOLD.toString() + ChatColor.BOLD.toString() +
				winner.getName() + " がゲームに勝利しました!");
		Bukkit.broadcastMessage("");

		// 2. 結果をファイル出力 + ゲーム内計算
		CSVCreator.createCsv();
		LMSScoreHolder.display(10, ScoreResultType.SURVIVE_RANK);

		// 3. ゲームリセット, ロビー転送
		ArrayList<Player> all = new ArrayList<>();
		for (Player p : Bukkit.getOnlinePlayers()) {
			all.add(p);
		}
		LastManStanding.resetGame();
	}

	public void killLog(Player killer, Player victim) {
		String r = ChatColor.RESET.toString() + ChatColor.GRAY.toString();
		String victimName = ChatColor.BLUE.toString() + victim.getName();
		String rank = ChatColor.GOLD.toString() + "#" + (alive.size()+1);
		String kill = ChatColor.RED.toString() + playerScore.get(victim).getKill()
				+ ChatColor.GRAY.toString() + "kill";

		String sibou = " が死亡した。 - ";
		String korosita = " を殺した。 - ";

		/* お祝いモードログ */
		if (ConfigValue.isOiwai) sibou = " が祝われた。 - ";
		if (ConfigValue.isOiwai) korosita = " をお祝いした。 - ";
		if (ConfigValue.isOiwai) victimName = ChatColor.WHITE.toString() + victim.getName();

		if (killer == null) {
			Bukkit.broadcastMessage(victimName + r + " が死亡した。 - " + rank + " " + kill);
		} else {
			String killerName = ChatColor.GOLD.toString() + killer.getName();
			if (ConfigValue.isOiwai) killerName = ChatColor.RED.toString() + killer.getName();
			Bukkit.broadcastMessage(killerName + r + " が " + victimName + r + " を殺した。 - " + rank + " " + kill);
		}
	}

	/**
	 * キラー視点のカメラに移動する
	 */
	public void swapSpecCamera(Player killer, Player victim) {
		for (Player p : Bukkit.getOnlinePlayers()) {
			Entity entity = p.getSpectatorTarget();
			if (entity != null && entity instanceof Player) {
				Player targ = (Player) entity;
				if (targ.equals(victim)) p.setSpectatorTarget(killer);
			}
		}
		victim.setSpectatorTarget(killer);
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

	/**
	 * Game取得メソッド
	 */
	public LMSGame getGame() {
		return game;
	}
}
