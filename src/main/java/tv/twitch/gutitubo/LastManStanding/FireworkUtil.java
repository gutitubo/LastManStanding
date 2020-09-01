package tv.twitch.gutitubo.LastManStanding;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.inventory.meta.FireworkMeta;

public class FireworkUtil {

	/**
	 * 弓矢を撃ったときのめでたい感じのエフェクト
	 * @param loc
	 * @param color
	 */
	public static void spawnArrowFirework(Location loc, Color color) {
		// 1. Spawn Firework
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

		// 2. Edit Firework meta
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(0);
		meta.addEffect(FireworkEffect.builder().withColor(color).with(Type.STAR).build());

		// 3. Set Firework meta
		firework.setFireworkMeta(meta);
	}

	/**
	 * KILLされたときのめでたい感じのエフェクト
	 * @param loc 死んだ場所
	 * @param kill 被害者のキル数
	 */
	public static void spawnDeadFirework(Location loc, int kill) {
		kill++;

		// 1. Spawn Firework
		Firework firework = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);

		// 2. Edit Firework meta
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(getPower(kill));
		meta.addEffect(FireworkEffect.builder().withColor(Color.RED).with(getType(kill)).build());

		// 3. Set Firework meta
		firework.setFireworkMeta(meta);
	}

	private static Type getType(int kill) {
		Type type = Type.BALL;
		if (kill >= 3) type = Type.BALL_LARGE;
		return type;
	}

	private static int getPower(int kill) {
		int power = 2 + kill;
		return power;
	}

}
