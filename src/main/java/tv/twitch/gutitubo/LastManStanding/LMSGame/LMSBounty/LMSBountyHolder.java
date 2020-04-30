package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty;

import java.util.HashSet;
import java.util.List;

import org.bukkit.entity.Player;

/**
 * Bountyを保持し、各種表示等を行うためのクラス
 * @author gutitubo
 *
 */
public class LMSBountyHolder {

	/* Bounty保管用HashSet */
	public static HashSet<LMSBounty> bountyHolder = new HashSet<>();

	/**
	 * 参加プレイヤーをBountyに登録する
	 * @param players
	 */
	public static void registBounty(List<Player> players) {
		for (Player p : players) {
			bountyHolder.add(new LMSBounty(p.getName()));
		}
	}

}
