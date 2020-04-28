package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;

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

			// 3. ゲーム中かつ1000ダメージ以上受けていた場合の処理
			if (LastManStanding.getGame() == null) return;
			if (LastManStanding.getGame().getLogic().getAlives().size() <= 1) return;
			if (e.getDamage() < 950) return;
			LastManStanding.getGame().getLogic().killPlayer(null, (Player) entity);
		}
	}

	/**
	 * プレイヤーの飢餓を防ぐ
	 */
	@EventHandler
	public void whenPlayerFoodLevelChanged(FoodLevelChangeEvent e) {
		e.setCancelled(true);
	}

	/**
	 * inGame中じゃないと撃てないの
	 */
	@EventHandler
	public void whenPlayerShooted(EntityShootBowEvent e) {
		LMSGame game = LastManStanding.getGame();
		if (game == null || !game.isInGame()) {
			e.setCancelled(true);
		}
	}

	/**
	 * 矢を持てないように
	 */
	@EventHandler
	public void whenPlayerClickedArrow(InventoryClickEvent e) {
		ItemStack item = e.getCurrentItem();
		if (e.getWhoClicked().getGameMode().equals(GameMode.CREATIVE)) return;
		if (item.getType() == Material.ARROW) {
			e.setCancelled(true);
		}
	}

}
