package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

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
					samePlayerShotted(shooter);
				} else {
					/* あたった側の死亡エフェクト */
					deadPlayerEffect(victim);
					/* あたった側は死亡！ */
					game.getLogic().killPlayer(shooter, victim);
					/* キラー側にバフとエフェクトを付与 */
					killedBuff(shooter);
				}
				hittedProjectile(projectile);
			} else if ((projectile.getShooter() instanceof Player) && (e.getHitEntity() == null)) {
				/* === shooterがPlayer, hitEntityがnullの場合 === */
				missedProjectile(projectile);
			}
		}
	}

	private static void missedProjectile(Projectile projectile) {
		//TODO 着弾地点にエフェクト, ProjectileTypeによっては削除
		Location hitted = projectile.getLocation();
		hitted.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, projectile.getLocation(), 1, 0, 0, 0);
		hitted.getWorld().playSound(hitted, Sound.ENTITY_GENERIC_EXPLODE, 0.2F, 1F);
		hitted.getWorld().playSound(hitted, Sound.ENTITY_ARROW_HIT, 0.5F, 1F);
		projectile.remove();
	}

	private static void hittedProjectile(Projectile projectile) {
		//TODO エンティティに着弾したときの絵ヘクト
		Location hitted = projectile.getLocation();
		hitted.getWorld().spawnParticle(Particle.EXPLOSION_LARGE, projectile.getLocation(), 1, 0, 0, 0);
		hitted.getWorld().playSound(hitted, Sound.ENTITY_PLAYER_HURT, 0.5F, 1F);
		projectile.remove();
	}

	/**
	 * プレイヤーキル時のバフ
	 */
	private static void killedBuff(Player p) {
		// 1. 速度上昇を付与
		p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 20 * 5, 1, false, false), true);

		// 2. 音を出す
		p.playSound(p.getLocation(), Sound.ENTITY_ARROW_HIT, 0.8F, 1F);
		p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_GROWL, 0.4F, 1.2F);
		p.playSound(p.getLocation(), Sound.ENTITY_ENDERDRAGON_FIREBALL_EXPLODE, 0.5F, 0.8F);
	}

	private static void samePlayerShotted(Player p) {
		// TODO 前ブリンク
	}

	private static void deadPlayerEffect(Player p) {
		//TODO 死亡エフェクト
		p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 0.4F, 1F);
		p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_HURT, 0.6F, 1F);

		p.getWorld().spawnParticle(Particle.EXPLOSION_HUGE, p.getLocation(), 1, 0, 0, 0);
	}
}
