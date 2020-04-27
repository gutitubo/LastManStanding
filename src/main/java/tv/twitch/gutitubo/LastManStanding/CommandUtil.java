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
