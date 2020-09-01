package tv.twitch.gutitubo.LastManStanding.LMSItem;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

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
		String name = ChatColor.GOLD + "一撃弓";
		if (ConfigValue.isOiwai) {
			name = ChatColor.RED.toString() + "祝" + ChatColor.WHITE.toString() + "砲";
		}
		itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 5, true);
		itemMeta.setDisplayName(name);
		itemMeta.setUnbreakable(true);
		itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		itemMeta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		item.setItemMeta(itemMeta);
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
