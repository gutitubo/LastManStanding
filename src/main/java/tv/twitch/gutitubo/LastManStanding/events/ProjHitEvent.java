package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * 弓がヒットした場合のイベントを定義する
 * @author gutitubo
 *
 */
public class ProjHitEvent implements Listener {

	/**
	 * プレイヤーの弓がHitした場合の処理
	 * @param e
	 */
	@EventHandler
	public void whenHitProj(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Projectile) {
			Projectile projectile = (Projectile) e.getEntity();
			if ((projectile.getShooter() instanceof Player) && (e.getHitEntity() instanceof Player)) {
				/* ==== shooter, hitEntityがPlayerの場合 ==== */
				//TODO 撃った側と撃たれた側が同じ場合: 前Blink
				//撃った側: Arrow1獲得 Speed獲得 Kill獲得 Point獲得 Title表示
				//撃たれた側: 死亡エフェクト Title表示 観戦者モード
			} else if ((projectile.getShooter() instanceof Player) && (e.getHitEntity() == null)) {
				/* === shooterがPlayer, hitEntityがnullの場合 === */
				//TODO 着弾地点にエフェクト, ProjectileTypeによっては削除
			}
		}
	}

}
