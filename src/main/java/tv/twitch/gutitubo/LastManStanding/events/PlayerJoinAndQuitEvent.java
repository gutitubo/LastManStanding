package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;

/**
 * ゲームへの参加と離脱を制御
 * @author gutitubo
 *
 */
public class PlayerJoinAndQuitEvent implements Listener {

	@EventHandler
	public void whenPlayerJoined(PlayerJoinEvent e) {

	}

	@EventHandler
	public void whenPlayerQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		LMSGame game = LastManStanding.getGame();
		if (game != null) {
			if (game.getLogic().getAlives().contains(p)) {
				game.getLogic().killPlayer(null, p);
			}
		}
	}
}
