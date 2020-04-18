package tv.twitch.gutitubo.LastManStanding.config;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;

/**
 * configから値を読み込むためのクラス
 * 読み込んだ値を保存するには
 * ConfigValueクラスを使用する
 *
 * @author gutitubo
 *
 */
public class ConfigReader {

	/**
	 *	Configの値が登録されていない場合に新しく値を登録する
	 */
	public static void init() {
		LastManStanding.main.saveDefaultConfig();
	}

	/**
	 * configから値を読み込んでConfigValueに格納する
	 */
	public static void reload() {
		ConfigValue.waitingTime = LastManStanding.main.getConfig().getInt("WaitingTime");
		ConfigValue.gameTime = LastManStanding.main.getConfig().getInt("GameTime");
		ConfigValue.startTime = LastManStanding.main.getConfig().getInt("StartTime");
		ConfigValue.minPlayer = LastManStanding.main.getConfig().getInt("MinPlayer");
	}

}
