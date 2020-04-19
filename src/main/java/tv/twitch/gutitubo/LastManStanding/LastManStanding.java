package tv.twitch.gutitubo.LastManStanding;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import tv.twitch.gutitubo.LastManStanding.config.ConfigReader;

public class LastManStanding extends JavaPlugin {

	Logger logger = Bukkit.getLogger();
	private static String VERSION = "0.0.2";

	public static LastManStanding main;

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
	}

	@Override
	public void onDisable() {
		// TODO 自動生成されたメソッド・スタブ
		super.onDisable();
		logger.info("[LMS] plugin unloaded.");
	}
}
