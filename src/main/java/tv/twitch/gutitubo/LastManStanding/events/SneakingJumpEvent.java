package tv.twitch.gutitubo.LastManStanding.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.util.Vector;

import tv.twitch.gutitubo.LastManStanding.CommonUtil;

/**
 * スニークした際に看板があればぶっ飛んじまうイベント
 * @author gutitubo
 *
 */
public class SneakingJumpEvent implements Listener {

	/**
	 * 看板が下にあるブロック上でスニークしたときのイベントを定義
	 * @param e event
	 */
	@EventHandler
	public void whenSneakingOnBlock(PlayerToggleSneakEvent e) {
		// 1. プレイヤーの1.5マス下の座標を取得
		Player p = e.getPlayer();
		Location loc = p.getLocation();
		loc.setY(loc.getY() - 1.5F);

		// 2. 手順1の座標のブロックをnull check
		Block b = loc.getWorld().getBlockAt(loc);
		if (b == null) return;

		// 3. ブロックが看板だった場合 状態を取得して処理
		if (CommonUtil.isSign(b)) {
			Sign sign =(Sign) b.getState();
			if (sign == null) return;
			giveVelocityFromSign(p, sign);
			p.getWorld().playSound(loc, Sound.ENTITY_PARROT_FLY, 1F, 1F);
		}
	}

	/**
	 * 看板から値を取得して速度としてプレイヤーに与える
	 * @param p
	 * @param sign
	 */
	private static void giveVelocityFromSign(Player p, Sign sign) {
		if (sign.getLine(0).equalsIgnoreCase("jump")) {
			String strValue = sign.getLine(1);
			if (strValue != null) {
				strValue.trim();
				try {
					Double value = Double.parseDouble(strValue);
					Vector vel = new Vector(0, value, 0);
					p.setVelocity(vel);
				} catch (NumberFormatException e) {
					Bukkit.getLogger().warning("[LMS] 看板やばいよException");
				}
			}
		}
	}

	/**
	 * プレイヤーにジャンプパッド用のエフェクトを与える
	 */
	private static void giveJumpPadEffect(Player p) {

	}
}
