package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import net.md_5.bungee.api.ChatColor;
import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;

/**
 * ゲームへの参加と離脱を制御
 * @author gutitubo
 *
 */
public class PlayerJoinAndQuitEvent implements Listener {

	@EventHandler
	public void whenPlayerJoined(PlayerJoinEvent e) {
		/* ゲーム開始後に参加した場合の処理 */
		Player p = e.getPlayer();
		LMSGame game = LastManStanding.getGame();
		LMSGameUtil.teleportToLobby(p);
		LMSGameUtil.joinTeam(p);
		if (game != null) {
			p.setGameMode(GameMode.SPECTATOR);
			p.sendMessage(ChatColor.YELLOW + "すでにゲームが開始されているため観戦者として参加します。");
		}
	}

	@EventHandler
	public void whenPlayerQuit(PlayerQuitEvent e) {
		/* ゲーム開始後に退出した場合の処理 */
		Player p = e.getPlayer();
		LMSGame game = LastManStanding.getGame();
		LMSGameUtil.leaveTeam(p);
		if (game != null) {
			if (game.getLogic().getAlives().contains(p)) {
				game.getLogic().killPlayer(null, p);
			}
		}
	}
}
