package tv.twitch.gutitubo.LastManStanding;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;

import net.md_5.bungee.api.ChatColor;
/**
 * コマンド入力の煩雑なアレをアレするクラス
 * @author gutitubo
 *
 */
public class CommandUtil {

	public static void sendCmdAnnounce(CommandSender cmd) {
		cmd.sendMessage("");
		cmd.sendMessage(" == LastManStanding == ");
		cmd.sendMessage("/lms start - ゲーム開始");
		cmd.sendMessage("/lms reset - ゲーム強制終了");
		cmd.sendMessage("/lms reload - Configリロード");
		cmd.sendMessage("/lms setlobby - ロビー座標設定");
		cmd.sendMessage("/lms setspawnpoint <1~9> - スポーン座標を設定");

		cmd.sendMessage("/lms suvrank [count] - ランキング表示");
		cmd.sendMessage("/lms killrank [count] - キルランク表示");
		cmd.sendMessage("/lms pointrank [count] - 獲得ポイントランキング表示");
		cmd.sendMessage("/lms rank [count] - 総合ポイントランキング");
		cmd.sendMessage("/lms bountyrank [count] - バウンティランキング");
		cmd.sendMessage("/lms bountyon - バウンティ有効化(参加者が揃ってからコマンド使用)");
		cmd.sendMessage("/lms csv - フェイルをエクスポート");
		cmd.sendMessage("/lms update <FileName> <URL> - Pluginフォルダにファイルをインポート");
		cmd.sendMessage("");
	}

	public static void cantStartAnnounce(CommandSender cmd) {
		cmd.sendMessage(ChatColor.DARK_RED + "ゲームはすでに開始されています。");
	}

	public static void cantResetAnnounce(CommandSender cmd) {
		cmd.sendMessage(ChatColor.DARK_RED + "ゲームは開始されていません。");
	}

	public static void sendConfigReloadAnnounce(CommandSender cmd) {
		cmd.sendMessage(ChatColor.GOLD + "[LastManStanding] Configをリロードしました。");
	}

	public static void setSpawnPointToConfig(int point, Location location) {
		LastManStanding.main.getConfig().set("Spawn.Point" + point + ".x", location.getBlockX());
		LastManStanding.main.getConfig().set("Spawn.Point" + point + ".y", location.getBlockY());
		LastManStanding.main.getConfig().set("Spawn.Point" + point + ".z", location.getBlockZ());
	}

	public static void setLobbyToConfig(Location location) {
		LastManStanding.main.getConfig().set("Lobby.x", location.getBlockX());
		LastManStanding.main.getConfig().set("Lobby.y", location.getBlockY());
		LastManStanding.main.getConfig().set("Lobby.z", location.getBlockZ());
	}
}
