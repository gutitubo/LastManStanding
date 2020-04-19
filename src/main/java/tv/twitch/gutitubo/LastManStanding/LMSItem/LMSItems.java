package tv.twitch.gutitubo.LastManStanding.LMSItem;

import org.bukkit.inventory.ItemStack;

public enum LMSItems {
	BOW, //戦闘用弓
	ARROW, //戦闘用矢
	COMPASS, //索敵用コンパス
	;

	public ItemStack toItemStack() {
		return LMSEnumToItemUtil.getItemFromEnum(this);
	}
}
