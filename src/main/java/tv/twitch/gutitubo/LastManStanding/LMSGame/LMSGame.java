package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.config.ConfigReader;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

public class LMSGame {

	/* ------------- */
	/* ゲーム設定用変数 */
	/* ------------- */
	protected int waitingTime; //待機時間
	protected int gameTime; //ゲーム全体の時間
	protected int startTime; //ゲーム開始の時間
	protected int minPlayer; //ゲーム開始最少人数

	/* ----------------- */
	/* ゲーム内部処理用変数 */
	/*------------------ */
	protected boolean canJoin = false; //ゲーム参加可能フラグ
	protected ArrayList<Player> players; //参加者リスト
	private LMSGameLogic logic; //ゲームロジック処理用オブジェクト

	public LMSGame() {
		init();
	}

	/**
	 * ゲーム開始前の処理を行うメソッド
	 */
	private void init() {
		loadConfig();
		registPlayer();
		logic = new LMSGameLogic(this);
	}

	/**
	 * Configから各種値を読み込むメソッド
	 */
	private void loadConfig() {
		//TODO configからDefault値を読み込む
		ConfigReader.reload();
		waitingTime = ConfigValue.waitingTime;
		gameTime = ConfigValue.gameTime;
		startTime = ConfigValue.startTime;
		minPlayer = ConfigValue.minPlayer;
	}

	/**
	 * サーバー内のプレイヤーを参加者登録する
	 */
	private void registPlayer() {
		players = new ArrayList<>();
		for (Player p: Bukkit.getOnlinePlayers()) {
			if (!p.getGameMode().equals(GameMode.CREATIVE)) players.add(p);
		}
	}

	/**
	 * ゲームタイマーの開始
	 * このメソッドを読み込むことでゲームを開始する
	 */
	public void startTimer() {
		// 1. 開始可能か判定
		if (players.size() < minPlayer) {
			Bukkit.broadcastMessage(ChatColor.RED + "ゲーム開始には最低 " + minPlayer +"人 必要です。");
			return;
		}

		// 2. 参加プレイヤーに処理
		LMSGameUtil.playerInitProc(players);

		// 3. タイマー開始
		BukkitRunnable timer = new LMSGameTimer();
		timer.runTaskTimer(LastManStanding.main, 20, 20);

		// 4. 参加者テレポート


		// 5. ワールドボーダー初期処理
	}

	/**
	 * ゲーム終了時処理のメソッド
	 */
	public void reset() {
		LMSGameUtil.playerResetProc(players);
		players = null;
		canJoin = false;

		//TODO ロビーテレポート
	}

	/**
	 * 参加者リストを取得
	 */
	public ArrayList<Player> getPlayers() {
		return this.players;
	}

	/**
	 * ゲームロジックへアクセス
	 */
	public LMSGameLogic getLogic() {
		return this.logic;
	}
}
