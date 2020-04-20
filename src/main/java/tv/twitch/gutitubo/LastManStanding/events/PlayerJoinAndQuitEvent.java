package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

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
		//TODO ゲーム中の場合は死亡
	}
}
