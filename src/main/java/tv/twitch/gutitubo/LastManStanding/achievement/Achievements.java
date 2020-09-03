package tv.twitch.gutitubo.LastManStanding.achievement;

import org.bukkit.ChatColor;

public class Achievements {

	/**
	 * どうせここほとんど増えないし原始的にやる
	 * @param name
	 * @return
	 */
	public static String getAchievement(String name) {
		String achievement = "";

		/* 第一回超LMS sponsored by galleria 優勝 */
		if (name.equalsIgnoreCase("tarakoTBTB")) {
			achievement = ChatColor.GOLD.toString() + "[初代王者]" + ChatColor.RESET.toString();
		}

		/* 超LMS ぐちつぼ降臨2020杯優勝 */
		if (name.equalsIgnoreCase("Toxic_Kindred")) {
			achievement = ChatColor.GOLD.toString() + "[降臨]" + ChatColor.RESET.toString();
		}

		return achievement;
	}

}
