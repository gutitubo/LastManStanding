package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import tv.twitch.gutitubo.LastManStanding.LastManStanding;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSGame;

/**
 * 弓がヒットした場合のイベントを定義する
 * @author gutitubo
 *
 */
public class ProjHitEvent implements Listener {

	private LMSGame game;
	/**
	 * プレイヤーの弓がHitした場合の処理
	 * @param e
	 */
	@EventHandler
	public void whenHitProj(ProjectileHitEvent e) {
		game = LastManStanding.getGame();
		if (e.getEntity() instanceof Projectile) {
			Projectile projectile = (Projectile) e.getEntity();
			if ((projectile.getShooter() instanceof Player) && (e.getHitEntity() instanceof Player)) {
				/* ==== shooter, hitEntityがPlayerの場合 ==== */
				//撃った側: Arrow1獲得 Speed獲得 Kill獲得 Point獲得 Title表示
				//撃たれた側: 死亡エフェクト Title表示 観戦者モード
				Player shooter = (Player) projectile.getShooter();
				Player victim = (Player) e.getHitEntity();
				if (shooter.equals(victim)) {
					//TODO 撃った側と撃たれた側が同じ場合: 前Blink

				} else {
					/* あたった側の死亡エフェクト */
					deadPlayerEffect(victim);
					/* あたった側は死亡！ */
					game.getLogic().killPlayer(shooter, victim);
				}
			} else if ((projectile.getShooter() instanceof Player) && (e.getHitEntity() == null)) {
				/* === shooterがPlayer, hitEntityがnullの場合 === */
				missedProjectile(projectile);
			}
		}
	}

	public static void missedProjectile(Projectile projectile) {
		//TODO 着弾地点にエフェクト, ProjectileTypeによっては削除
	}

	public static void deadPlayerEffect(Player player) {
		//TODO 死亡エフェクト
	}
}
