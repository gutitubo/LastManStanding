package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.GameMode;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

/**
 * プレイヤーの各種行動を制限するためのクラス
 * @author gutitubo
 *
 */
public class LimitedPlayerActivity implements Listener {

	/**
	 * プレイヤーがアイテムを捨てることを防ぐ
	 */
	@EventHandler
	public void whenPlayerDroppedItems(PlayerDropItemEvent e) {

		// 1. プレイヤー取得
		Player p = e.getPlayer();

		// 2. プレイヤーのゲームモードを取得
		GameMode gm = p.getGameMode();

		// 3. ゲームモードがCreativeじゃない場合キャンソォ
		if (!gm.equals(GameMode.CREATIVE)) e.setCancelled(true);
	}

	/**
	 * プレイヤーのダメージを防ぐ
	 */
	@EventHandler
	public void whenPlayerGotDamaged(EntityDamageEvent e) {
		// 1. ダメージを受けたえんちちーがPlayerであることを確認する
		Entity entity = e.getEntity();
		if (entity instanceof Player) {

			// 2. ダメージをキャンセル
			e.setCancelled(true);
		}
	}
}
