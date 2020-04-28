package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * 設置を踏んだときにテレポートするイベント
 * @author gutitubo, genpyon
 */
public class SignTeleportEvent implements Listener {

	private static String gent = ChatColor.RED + "[GenT]";

	/**
	 * 看板の1行目がgentだった場合, 特殊な看板に変更する
	 * @param e
	 */
	@EventHandler
	public void whenSignPlaced(SignChangeEvent e) {
		// 1. OP以外はだめ
		if (!e.getPlayer().isOp()) return;

		// 2. 看板の1行目を取得
		String line = e.getLine(0);
		if (line == null) return;

		// 3. 1行目がgentの場合は専用の看板に
		if (line.equalsIgnoreCase("gent")) e.setLine(0, gent);
	}

	/**
	 * GenT看板を踏んでいた場合、指定の座標へテレポート
	 * @param e
	 */
	@EventHandler
	public void signTeleport(PlayerMoveEvent e) {
		// 1. プレイヤーの踏んでいるブロックを取得
		Player p = e.getPlayer();
		Block b = p.getWorld().getBlockAt(
				new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() - 0.5, p.getLocation().getZ()));

		// 2. 取得したブロッ九がエンドストーンじゃないとヤダ
		if (b == null || !b.getType().equals(Material.ENDER_STONE)) return;

		// 3. プレイヤーが踏んでるその下のブロックが看板じゃないとヤダ
		Location loc = new Location(p.getWorld(),
				p.getLocation().getX(), p.getLocation().getY() - 1.5F, p.getLocation().getZ());
		b = p.getWorld().getBlockAt(loc);
		if (b == null || !b.getType().equals(Material.SIGN_POST)) return;

		// 4. SIGNの情報を取得してテレポートさせる
		Sign sign = (Sign) b.getState();
		if (sign == null) return;
		signTeleport(p, sign);
	}

	/**
	 * 看板から情報を取得してプレイヤーをTPさせるメソッド
	 */
	private void signTeleport(Player player, Sign sign) {
		// 1. 看板の1行目がgentじゃないとだめ
		String line = sign.getLine(0);
		if (line == null || !line.equalsIgnoreCase(gent)) return;

		// 2. 全部の行に記述があることを確認
		if (sign.getLine(1) == null || sign.getLine(2) == null || sign.getLine(3) == null) return;

		// 3. 各業から数字を取得
		double x = 0, y = 0, z = 0;
		try {
			x = Double.parseDouble(sign.getLine(1).trim());
			y = Double.parseDouble(sign.getLine(2).trim());
			z = Double.parseDouble(sign.getLine(3).trim());
		} catch (NumberFormatException e) {
			Bukkit.getLogger().warning("[LMS] どことなくやばいよException");
			return;
		}

		// 4. テレポート先Locationを作成
		Location teleport = new Location(player.getWorld(), x + 0.5, y + 0.5, z + 0.5);
		teleport.setPitch(player.getLocation().getPitch());
		teleport.setYaw(player.getLocation().getYaw());

		// 5. 作成したLocationにテレポート + エフェクト
		player.teleport(teleport);
		player.setBedSpawnLocation(teleport, true);
		player.playSound(teleport, Sound.ENTITY_ENDERMEN_TELEPORT, 0.2F, 1F);
	}
}
