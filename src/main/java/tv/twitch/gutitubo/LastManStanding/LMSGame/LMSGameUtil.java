package tv.twitch.gutitubo.LastManStanding.LMSGame;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSItem.LMSItems;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

/**
 * ゲーム進行用のStaticメソッドを集めたクラス
 * @author gutitubo
 *
 */
public class LMSGameUtil {

	private static final float defaultSpeed = 0.2F;
	private static float walkSpeed = 0.3F;

	public static void reloadValue() {
		walkSpeed = ConfigValue.walkSpeed;
	}

	/**
	 * 全プレイヤーに初期処理を行うメソッド
	 */
	public static void playerInitProc (List<Player> players) {
		reloadValue();
		resetPlayerStatus(players);
		givePlayerLoadout(players);
		givePlayerInvis(players);
		givePlayerStatus(players);
		teleportToSpawn(players);
	}

	/**
	 * 全プレイヤーにリセット処理を行うメソッド
	 */
	public static void playerResetProc (List<Player> players) {
		resetPlayerStatus(players);
		for (Player p: players) {
			/* 各プレイヤーへの処理 */

			// 1. ロビー転送
			teleportToLobby(p);

			// 2. ゲームモード変更
			p.setGameMode(GameMode.ADVENTURE);

			// 3. インベントリクリア
			p.getInventory().clear();

		}
	}

	/**
	 * プレイヤーに初期ステータス処理を行うメソッド
	 */
	public static void givePlayerStatus (Player p) {
		p.setWalkSpeed(walkSpeed);
	}

	/**
	 * 全プレイヤーに初期ステータス処理を行うメソッド
	 */
	public static void givePlayerStatus (List<Player> players) {
		for (Player p: players) {
			givePlayerStatus(p);
		}
	}

	/**
	 * プレイヤーのステータスをデフォルトにリセットする
	 */
	public static void resetPlayerStatus (Player p) {
		p.setWalkSpeed(defaultSpeed);
	}

	/**
	 * 全プレイヤーのステータスをデフォルトにリセットする
	 */
	public static void resetPlayerStatus (List<Player> players) {
		for (Player p: players) {
			givePlayerStatus(p);
		}
	}

	/**
	 * プレイヤーに戦闘用装備を配るためのメソッド
	 */
	public static void givePlayerLoadout (Player p) {
		Inventory inv = p.getInventory();
		inv.setItem(0, LMSItems.BOW.toItemStack());
		inv.setItem(8, LMSItems.COMPASS.toItemStack());
	}

	/**
	 * 全プレイヤーに戦闘用装備を配るためのメソッド
	 */
	public static void givePlayerLoadout (List<Player> players) {
		for (Player p: players) {
			givePlayerLoadout(p);
		}
	}

	/**
	 * プレイヤーに矢を配るためのメソッド
	 */
	public static void givePlayerArrow (Player p) {
		Inventory inv = p.getInventory();
		if (!inv.contains(Material.ARROW)) {
			inv.addItem(new ItemStack(Material.ARROW));
		}
	}

	/**
	 * 全プレイヤーに矢を配るためのメソッド
	 */
	public static void givePlayerArrow (List<Player> players) {
		for (Player p : players) {
			givePlayerArrow(p);
		}
	}

	/**
	 * プレイヤーに初期の透明化を配るためのメソッド
	 */
	private static void givePlayerInvis (Player p) {
		p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20*60*10, 0));
	}

	/**
	 * 全プレイヤーに初期の透明化を配るためのメソッド
	 */
	public static void givePlayerInvis (List<Player> players) {
		for (Player p : players) {
			givePlayerInvis(p);
			for (Player other: players) {
				if (!p.equals(other)) p.hidePlayer(other);
			}
		}
	}

	/**
	 * プレイヤーの透明化を解除するためのメソッド
	 */
	private static void takePlayerInvis (Player p) {
		p.removePotionEffect(PotionEffectType.INVISIBILITY);
	}

	/**
	 * 全プレイヤーの透明化を解除するためのメソッド
	 */
	public static void takePlayerInvis (List<Player> players) {
		for (Player p : players) {
			takePlayerInvis(p);
			for (Player other: players) {
				if (!p.equals(other)) p.showPlayer(other);
			}
		}
	}

	/**
	 * プレイヤーリストにタイトルを送信
	 */
	public static void sendTitleToPlayerList (List<Player> players, String title, String subtitle,
			int fadeIn, int stay, int fadeOut) {
		for (Player p: players) {
			p.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
		}
	}

	/**
	 * 全員にタイトルを送信
	 */
	public static void sendTitleToAll(String title, String subtitle,
			int fadeIn, int stay, int fadeOut) {
		for (Player p: Bukkit.getOnlinePlayers()) {
			p.sendTitle(title, subtitle, fadeIn, stay, fadeOut);
		}
	}

	/**
	 * ロビーにテレポートさせる
	 */
	public static void teleportToLobby(Player p) {
		World w = Bukkit.getWorld("world");
		double x = LastManStanding.main.getConfig().getInt("Lobby.x") + 0.5;
		double y = LastManStanding.main.getConfig().getInt("Lobby.y") + 0.5;
		double z = LastManStanding.main.getConfig().getInt("Lobby.z") + 0.5;
		Location location = new Location(w, x, y, z);
		p.teleport(location);
	}

	/**
	 * スポーン地点にテレポートさせる　
	 */
	public static void teleportToSpawn(Player p, int point) {
		World w = Bukkit.getWorld("world");
		double x = LastManStanding.main.getConfig().getInt("Spawn.Point" + point + ".x") + 0.5;
		double y = LastManStanding.main.getConfig().getInt("Spawn.Point" + point + ".y") + 0.5;
		double z = LastManStanding.main.getConfig().getInt("Spawn.Point" + point + ".z") + 0.5;
		Location location = new Location(w, x, y, z);
		p.teleport(location);
	}

	/**
	 * 全員をロビーにテレポートさせる
	 */
	public static void teleportAllLobby() {
		for (Player p: Bukkit.getOnlinePlayers()) {
			teleportToLobby(p);
		}
	}

	/**
	 * 全員を各スポーン地点にテレポートさせる
	 */
	public static void teleportToSpawn(List<Player> players) {
		int count = 0;
		int point = 1;
		for (Player p: players) {
			point = count % 10;
			teleportToSpawn(p, point);
			count++;
		}
	}
}
