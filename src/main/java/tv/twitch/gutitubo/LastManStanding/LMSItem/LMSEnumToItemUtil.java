package tv.twitch.gutitubo.LastManStanding.LMSItem;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
		itemMeta.setUnbreakable(true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
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
