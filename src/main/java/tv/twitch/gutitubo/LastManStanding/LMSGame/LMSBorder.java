package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;

import tv.twitch.gutitubo.LastManStanding.config.ConfigReader;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

/**
 * ゲーム中に縮小していくWorldBorderを司るクラス
 * @author gutitubo
 *
 */
public class LMSBorder {
	private static WorldBorder wb = Bukkit.getWorld("world").getWorldBorder();

	/* higherとlowerをコンフィグから取得する */
	private static double higher_x = ConfigValue.higher_x;
	private static double higher_z = ConfigValue.higher_z;
	private static double lower_x = ConfigValue.lower_x;
	private static double lower_z = ConfigValue.lower_z;

	private static double defaultSize = ConfigValue.defaultSize;
	private static int gameTime = ConfigValue.gameTime;

	public static void create() {
		// Pre. Configリロード
		ConfigReader.reload();

		// 1. WorldBorderの中央を設定
		wb.setCenter(randomX(), randomZ());

		// 2. WorldBorderの初期範囲を設定
		wb.setSize(defaultSize);

		// 3. ダメージバッファを0に設定
		wb.setDamageBuffer(0);

		// 4. ダメージを1000に設定
		wb.setDamageAmount(1000);
	}

	public static void createLastBattle(Location location, double borderSize) {
		// Worldの中心を設定
		wb.setCenter(location.getX(), location.getZ());

		wb.setSize(borderSize);

		// 3. ダメージバッファを0に設定
		wb.setDamageBuffer(0);

		// 4. ダメージを1000に設定
		wb.setDamageAmount(1000);
	}

	public static void start() {
		// GameTime時間で最大まで縮小する
		wb.setSize(0.1, gameTime);
	}

	private static double randomX() {
		double range = higher_x - lower_x;
		Random rnd = new Random();
		double d_rnd = rnd.nextDouble() * range;
		return lower_x + d_rnd;
	}

	private static double randomZ() {
		double range = higher_z - lower_z;
		Random rnd = new Random();
		double d_rnd = rnd.nextDouble() * range;
		return lower_z + d_rnd;
	}

	public static void off() {
		wb.reset();
	}
}
