package tv.twitch.gutitubo.LastManStanding;

import org.bukkit.Material;
import org.bukkit.block.Block;

public class CommonUtil {

	public static boolean isSign (Block b) {
		boolean ret = false;
		Material mat = b.getType();
		if (mat == Material.ACACIA_SIGN) ret = true;
		if (mat == Material.ACACIA_WALL_SIGN) ret = true;
		if (mat == Material.BIRCH_SIGN) ret = true;
		if (mat == Material.BIRCH_WALL_SIGN) ret = true;
		if (mat == Material.CRIMSON_SIGN) ret = true;
		if (mat == Material.CRIMSON_WALL_SIGN) ret = true;
		if (mat == Material.DARK_OAK_SIGN) ret = true;
		if (mat == Material.DARK_OAK_WALL_SIGN) ret = true;
		if (mat == Material.JUNGLE_SIGN) ret = true;
		if (mat == Material.JUNGLE_WALL_SIGN) ret = true;
		if (mat == Material.OAK_SIGN) ret = true;
		if (mat == Material.OAK_WALL_SIGN) ret = true;
		if (mat == Material.SPRUCE_SIGN) ret = true;
		if (mat == Material.SPRUCE_WALL_SIGN) ret = true;
		if (mat == Material.WARPED_SIGN) ret = true;
		if (mat == Material.WARPED_WALL_SIGN) ret = true;
		return ret;
	}

}
