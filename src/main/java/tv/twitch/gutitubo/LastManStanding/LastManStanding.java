package tv.twitch.gutitubo.LastManStanding;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;
import org.bukkit.scoreboard.Team.Option;
import org.bukkit.scoreboard.Team.OptionStatus;

import net.md_5.bungee.api.ChatColor;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty.LMSBountyManager;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSLastBattle.LMSLastBattle;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.LMSScoreHolder;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.ScoreResultType;
import tv.twitch.gutitubo.LastManStanding.config.ConfigReader;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;
import tv.twitch.gutitubo.LastManStanding.events.InteractItemEvent;
import tv.twitch.gutitubo.LastManStanding.events.LimitedPlayerActivity;
import tv.twitch.gutitubo.LastManStanding.events.OiwaiEvent;
import tv.twitch.gutitubo.LastManStanding.events.PlayerJoinAndQuitEvent;
import tv.twitch.gutitubo.LastManStanding.events.ProjHitEvent;
import tv.twitch.gutitubo.LastManStanding.events.SignTeleportEvent;
import tv.twitch.gutitubo.LastManStanding.events.SneakingJumpEvent;
import tv.twitch.gutitubo.LastManStanding.files.CSVCreator;
import tv.twitch.gutitubo.LastManStanding.files.FileUpdater;

public class LastManStanding extends JavaPlugin {

	/*
	 *  Ver 1.6.2 Update
	 *
	 *  登録者奪い合いモード追加
	 *   - Bountyモードから文字が違うモード
	 *   - [降臨]のタグをつける
	 *
	 */

	Logger logger = Bukkit.getLogger();
	private static String VERSION = "1.6.2";

	public static LastManStanding main;

	private static LMSGame game;

	public static Team team; // 戦闘用チーム

	@Override
	public void onEnable() {
		// TODO 自動生成されたメソッド・スタブ
		super.onEnable();
		logger.info("[LMS] plugin loaded. - ver." + VERSION);
		main = this;
		ConfigReader.init();
		eventRegist(Bukkit.getPluginManager());

		if (ConfigValue.isOiwai) {
			BukkitRunnable oiwaiTimer = new OiwaiTimer();
			oiwaiTimer.runTaskTimer(this, 0, 20);
		}

		registTeam();
	}

	@Override
	public void onDisable() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDisable();
		logger.info("[LMS] plugin unloaded.");
		LMSScoreHolder.allClear();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		String cmd = "";
		/* ---------- */
		/* CommandSenderがプレイヤーであることが必要になった場合に用いる
		/*
		/* boolean isSenderPlayer = false;
		/* if (sender instanceof Player) {
		/*	isSenderPlayer = true;
		/* }
		/* ---------- */

		if (command.getName().equalsIgnoreCase("lms")) {
			if (sender.isOp()) {
				if (args[0] != null) {
					cmd = args[0];
					if (cmd.equalsIgnoreCase("start")) {
						/* -- ゲーム開始分岐 -- */
						if (getGame() == null) {
							LMSScoreHolder.clear();
							game = new LMSGame();
							game.startTimer();
						} else {
							CommandUtil.cantStartAnnounce(sender);
						}
						/* -- ---- --- --- -- */
					} else if (cmd.equalsIgnoreCase("reset")) {
						/* -- ゲームリセット分岐 -- */
						if (getGame() != null) {
							resetGame();
						} else {
							CommandUtil.cantResetAnnounce(sender);
						}
						/* -- ---- ---- ---- -- */
					} else if (cmd.equalsIgnoreCase("reload")) {
						ConfigReader.reload();
						CommandUtil.sendConfigReloadAnnounce(sender);
					} else if (cmd.equalsIgnoreCase("setspawnpoint")) {
						/* -------------------- */
						// Pre. senderがPlayerじゃないとだめです
						if (!(sender instanceof Player)) return true;

						// 1. args[1]がない場合 nullで返す
						if (args[1] == null) {
							sender.sendMessage("Usage: /lms setspawnpoint <1~9>");
							return true;
						}

						// 2. args[1]から数字を取得
						int point = 0;
						try {
							point = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {
							Bukkit.getLogger().warning("[LMS] だめですエクセプション");
						}

						// 3. Pointが1~9じゃないとだめ
						if (!(1 <= point && point <= 9)) {
							sender.sendMessage(ChatColor.RED + "SpawnPointは1~9で設定してください");
							return true;
						}

						// 4. 座標をConfigに設定
						CommandUtil.setSpawnPointToConfig(point, ((Player)sender).getLocation());

						// 5. 成功の旨をメッセージ表示
						sender.sendMessage(ChatColor.YELLOW + "Spawn" + point + "を設定しました。");

						saveConfig();

						/* -------------------- */
					} else if (cmd.equalsIgnoreCase("setlobby")) {
						// Pre. senderがPlayerじゃないとだめです
						if (!(sender instanceof Player)) return true;

						// 1. senderのロケーションをロビーとして登録
						CommandUtil.setLobbyToConfig(((Player)sender).getLocation());

						// 2. 成功の旨をメッセージ表示
						sender.sendMessage(ChatColor.YELLOW + "Lobby座標を設定しました。");

						saveConfig();
					} else if (cmd.equalsIgnoreCase("suvrank")) {
						int count = 10;
						if (args.length != 1 && args[1] != null) {
							count = Integer.parseInt(args[1]);
						}
						LMSScoreHolder.display(count, ScoreResultType.SURVIVE_RANK);
					} else if (cmd.equalsIgnoreCase("killrank")) {
						int count = 10;
						if (args.length != 1 && args[1] != null) {
							count = Integer.parseInt(args[1]);
						}
						LMSScoreHolder.display(count, ScoreResultType.KILL_RANK);
					} else if (cmd.equalsIgnoreCase("pointrank")) {
						int count = 10;
						if (args.length != 1 && args[1] != null) {
							count = Integer.parseInt(args[1]);
						}
						LMSScoreHolder.display(count, ScoreResultType.POINT_RANK);
					} else if (cmd.equalsIgnoreCase("rank")) {
						int count = 10;
						if (args.length != 1 && args[1] != null) {
							count = Integer.parseInt(args[1]);
						}
						LMSScoreHolder.display(count, ScoreResultType.ALL_POINT_RANK);
					} else if (cmd.equalsIgnoreCase("csv")) {
						CSVCreator.createCsv();
						sender.sendMessage("CSVファイルを出力しました。");
					} else if (cmd.equalsIgnoreCase("bountyon")) {
						// PlayerListを生成
						ArrayList<Player> players = new ArrayList<>();
						for (Player p: Bukkit.getOnlinePlayers()) {
							if (!p.getGameMode().equals(GameMode.CREATIVE)) players.add(p);
						}

						// Bountyを登録
						LMSBountyManager.enableBounty(players);

						// アナウンス用のメセージを作る
						int bounty = ConfigValue.bountyAmount;
						String title_main = ChatColor.DARK_RED + ChatColor.BOLD.toString() + "BOUNTY MODE";
						String title_sub = ChatColor.GOLD + "$" + bounty + " が配布された";
						String msg = ChatColor.DARK_RED + "BountyModeが有効化されました。";

						if (ConfigValue.isUbaiai) {
							title_main = ChatColor.DARK_RED + ChatColor.BOLD.toString() + "登録者奪い合いモード";
							title_sub = ChatColor.GOLD + "あなたの登録者は" + bounty + " 人です。";
							msg = ChatColor.DARK_RED + "登録者奪い合いモードが有効化されました。";
						}

						// 全員にアナウンスする
						LMSGameUtil.sendTitleToAll(title_main, title_sub, 15, 100, 15);
						Bukkit.broadcastMessage(msg);
					} else if (cmd.equalsIgnoreCase("bountyrank")) {
						int count = 10;
						if (args.length != 1 && args[1] != null) {
							count = Integer.parseInt(args[1]);
						}
						LMSBountyManager.displayBountyTop(count);
					} else if (cmd.equalsIgnoreCase("update")) {
						if (args.length != 3) return true;
						String url = args[1];
						String fileName = args[2];
						FileUpdater.UpdateFile(url, fileName);
						sender.sendMessage("ファイルを指定のURLからダウンロードしました。");
					} else if (cmd.equalsIgnoreCase("lastbattle")) {
						if (getGame() == null) {
							sender.sendMessage("ゲームは開始されていません");
							return true;
						}
						LMSLastBattle.start();
					} else {
						CommandUtil.sendCmdAnnounce(sender);
					}
				} else {
					CommandUtil.sendCmdAnnounce(sender);
				}
			} else {
				/* - CommandはOPのみに許可する - */
				sender.sendMessage(ChatColor.DARK_RED + "この操作は許可されていません。");
			}
		}
		return true;
	}

	private void eventRegist(PluginManager pm) {
		pm.registerEvents(new PlayerJoinAndQuitEvent(), this);
		pm.registerEvents(new ProjHitEvent(), this);
		pm.registerEvents(new SignTeleportEvent(), this);
		pm.registerEvents(new SneakingJumpEvent(), this);
		pm.registerEvents(new LimitedPlayerActivity(), this);
		pm.registerEvents(new InteractItemEvent(), this);
		pm.registerEvents(new OiwaiEvent(), this);
	}

	private static void registTeam() {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getMainScoreboard();
		team = board.getTeam("LMSPlayer");

		if (team != null) {
			team.unregister();
		}

		team = board.registerNewTeam("LMSPlayer");
		team.setPrefix(ChatColor.GOLD.toString());
		team.setSuffix(ChatColor.RESET.toString());
		team.setOption(Option.COLLISION_RULE, OptionStatus.NEVER);
		team.setOption(Option.NAME_TAG_VISIBILITY, OptionStatus.NEVER);
		team.setAllowFriendlyFire(true);

		for (Player p : Bukkit.getOnlinePlayers()) {
			LMSGameUtil.joinTeam(p);
		}
	}

	public static LMSGame getGame() {
		return game;
	}

	public static void resetGame() {
		game.reset();
		game = null;
	}
}
