package tv.twitch.gutitubo.LastManStanding.LMSItem;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * Enumで指定されたアイテムスタックを返すスタティックメソッドを集めたクラス
 * @author gutitubo
 *
 */
public class LMSEnumToItemUtil {
	public static ItemStack getItemFromEnum(LMSItems type) {
		switch(type) {
		case BOW:
			return LMSBow();
		case ARROW:
			return LMSArrow();
		case COMPASS:
			return LMSCompass();
		default:
			return null;
		}
	}

	private static ItemStack LMSBow() {
		ItemStack item = new ItemStack(Material.BOW);
		return item;
	}

	private static ItemStack LMSArrow() {
		ItemStack item = new ItemStack(Material.ARROW);
		return item;
	}

	private static ItemStack LMSCompass() {
		ItemStack item = new ItemStack(Material.COMPASS);
		return item;
	}
}
