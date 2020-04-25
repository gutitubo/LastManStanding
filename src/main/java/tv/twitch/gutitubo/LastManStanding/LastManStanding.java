package tv.twitch.gutitubo.LastManStanding;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import net.md_5.bungee.api.ChatColor;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;
import tv.twitch.gutitubo.LastManStanding.config.ConfigReader;
import tv.twitch.gutitubo.LastManStanding.events.PlayerJoinAndQuitEvent;
import tv.twitch.gutitubo.LastManStanding.events.ProjHitEvent;
import tv.twitch.gutitubo.LastManStanding.events.SignTeleportEvent;
import tv.twitch.gutitubo.LastManStanding.events.SneakingJumpEvent;

public class LastManStanding extends JavaPlugin {

	Logger logger = Bukkit.getLogger();
	private static String VERSION = "0.0.6";

	public static LastManStanding main;

	private static LMSGame game;

	// 最寄りのプレイヤーコンパス更新
	// 弓で前ブリンク
	// 発光クールダウン

	@Override
	public void onEnable() {
		// TODO 自動生成されたメソッド・スタブ
		super.onEnable();
		logger.info("[LMS] plugin loaded. - ver." + VERSION);
		main = this;
		ConfigReader.init();
		eventRegist(Bukkit.getPluginManager());
	}

	@Override
	public void onDisable() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDisable();
		logger.info("[LMS] plugin unloaded.");
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
							game = new LMSGame();
							game.startTimer();
						} else {
							CommandUtil.cantStartAnnounce(sender);
						}
						/* -- ---- --- --- -- */
					} else if (cmd.equalsIgnoreCase("reset")) {
						/* -- ゲームリセット分岐 -- */
						if (getGame() != null) {
							game.reset();
							game = null;
						} else {
							CommandUtil.cantResetAnnounce(sender);
						}
						/* -- ---- ---- ---- -- */
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
	}

	public static LMSGame getGame() {
		return game;
	}
}
