package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

/**
 * お祝いモード有効状態でのイベント
 * @author gutitubo
 *
 */
public class OiwaiEvent implements Listener {

	@EventHandler
	public void onShoot(EntityShootBowEvent e) {
		// 1. OiwaiMode確認
		if (!ConfigValue.isOiwai) return;

		// 2. 撃ったのがPlayerじゃないとやばい
		if (e.getEntityType() != EntityType.PLAYER) return;

		// 3. 撃ったものがArrowじゃないとやばい
		if (e.getProjectile() instanceof Arrow) {
			// 4. Arrowをタイマーに登録する
			Arrow arrow = (Arrow) e.getProjectile();
			OiwaiEventTimer timer = new OiwaiEventTimer(arrow);
			timer.runTaskTimer(LastManStanding.main, 0, 1);
		}
	}
}
