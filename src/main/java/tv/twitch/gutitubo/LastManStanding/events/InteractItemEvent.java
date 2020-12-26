package tv.twitch.gutitubo.LastManStanding.events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGameUtil;

/**
 * インテ楽と系をどうにかする
 * @author gutitubo
 *
 */
public class InteractItemEvent implements Listener {

	@EventHandler
	public void whenPlayerInteracted(PlayerInteractEvent e) {
		// 事前準備
		Player p = e.getPlayer();
		LMSGame game = LastManStanding.getGame();
		if (game == null) return;

		if (p.getGameMode() == GameMode.SPECTATOR) {
			getPlayerNameOnCursor(p, game);
			return;
		}

		ItemStack item = p.getInventory().getItemInMainHand();
		if (item == null) return;
		if (item.getType() == Material.COMPASS) LMSGameUtil.reloadCompass(p, game.getLogic().getAlives());
	}

	private static void getPlayerNameOnCursor(Player p, LMSGame game) {
		Location loc = p.getLocation();
		Player cursor = null;
		for (int i = 1; i < 100; i++) {
			Vector vector = p.getLocation().getDirection();
			loc.add(vector.multiply(i));
			cursor = getNearByPlayer(p, loc, game.getLogic().getAlives());
			if (cursor != null) {
				String kill = "";
				if (LastManStanding.getGame() != null) {
					kill = ChatColor.DARK_GRAY + " - " + ChatColor.DARK_RED.toString()
							+ LastManStanding.getGame().getLogic().getPlayerScore().get(cursor).getKill()
							+ ChatColor.GRAY.toString() + "Kill";
				}
				String title = ChatColor.RED.toString() + cursor.getName() + kill;
				p.sendTitle("", title, 10, 40, 10);
				for (Player spec: getSpecList(p)) {
					spec.sendTitle("", title, 10, 40, 10);
				}
				return;
			}
			loc.subtract(vector);
		}
	}

	private static List<Player> getSpecList(Player player) {
		ArrayList<Player> list = new ArrayList<>();
		for (Player all : Bukkit.getOnlinePlayers()) {
			if (all.getGameMode().equals(GameMode.SPECTATOR) && all.getSpectatorTarget().equals(player)) {
				list.add(all);
			}
		}
		return list;
	}

	private static Player getNearByPlayer(Player me, Location loc, List<Player> players) {
		for (Player p: players) {
			if ((p.getLocation().distance(loc) < 4) && (me != p)) {
				return p;
			}
		}
		return null;
	}
}
