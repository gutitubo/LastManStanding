package tv.twitch.gutitubo.LastManStanding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

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
		firework.setGravity(false);

		// 2. Edit Firework meta
		FireworkMeta meta = firework.getFireworkMeta();
		meta.setPower(0);
		meta.addEffect(FireworkEffect.builder().withColor(color).with(Type.BURST).build());

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

	/**
	 * Locationを中心としたランダムな場所に花火を出す
	 * @param l
	 */
	public static void spawnRandomFireworks(Location l) {
		Random rnd = new Random();
		int x = rnd.nextInt(40)-20;
		int z = rnd.nextInt(40)-20;
		Location spawn = new Location(l.getWorld(), l.getX() + x, l.getY(), l.getZ() + z);
		Firework fire = (Firework)spawn.getWorld().spawnEntity(spawn, EntityType.FIREWORK);
		randomEffect(fire);
	}

	/**
	 * お祝いモード用のスポーン地点花火
	 * @param fire
	 */
	public static void spawnRandomFireworks(Location l, int n) {
		Random rnd = new Random();
		int x = rnd.nextInt(20)+n;
		int z = rnd.nextInt(20)+n;
		if (getRandomBool()) x*=-1;
		if (getRandomBool()) z*=-1;

		Location spawn = new Location(l.getWorld(), l.getX() + x, l.getY() - 40, l.getZ() + z);
		Firework fire = (Firework)spawn.getWorld().spawnEntity(spawn, EntityType.FIREWORK);
		randomEffect(fire);
	}


	private static void randomEffect(Firework fire) {
		FireworkMeta meta = fire.getFireworkMeta();
		meta.setPower(new Random().nextInt(4) + 3);
		FireworkEffect eff = FireworkEffect.builder().withColor(getRandomColor()).with(getFireType()).flicker(getRandomBool()).trail(getRandomBool()).build();
		meta.addEffect(eff);
		fire.setFireworkMeta(meta);
	}

	private static boolean getRandomBool() {
		Random rnd = new Random();
		return rnd.nextBoolean();
	}

	private static FireworkEffect.Type getFireType() {
		ArrayList<FireworkEffect.Type> types = new ArrayList<>();
		types.add(Type.BALL);
		types.add(Type.BALL_LARGE);
		types.add(Type.BALL);
		types.add(Type.BALL_LARGE);
		types.add(Type.BALL);
		types.add(Type.BALL_LARGE);
		types.add(Type.CREEPER);
		types.add(Type.STAR);
		Collections.shuffle(types);
		return types.get(0);
	}

	private static Color getRandomColor() {
		int blue = new Random().nextInt(255);
		int green = new Random().nextInt(255);
		int red = new Random().nextInt(255);

		return Color.fromBGR(blue, green, red);
	}

}
