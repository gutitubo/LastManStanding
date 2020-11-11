package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty;

import tv.twitch.gutitubo.LastManStanding.config.ConfigValue;

/**
 * プレイヤーに配られるBountyを定義したクラス
 * Bountyモードの開始はコマンドにて制御する
 * @author gutitubo
 *
 */
public class LMSBounty {

	private String name;
	private int bounty;

	public LMSBounty(String name) {
		this.name = name;
		bounty = ConfigValue.bountyAmount;
	}

	public String getName() {
		return name;
	}

	public int getBounty() {
		return bounty;
	}

	public void setBounty(int bounty) {
		this.bounty = bounty;
	}

	public void addBounty(int bounty) {
		setBounty(getBounty() + bounty);
	}

	/**
	 * 相手のバウンティを奪い取る
	 * @param enemyBounty 相手のバウンティ
	 * @return 奪い取った / 取られた額
	 */
	public int robBounty(LMSBounty enemyBounty) {
		int half = enemyBounty.getBounty() / 2;
		enemyBounty.setBounty(half);
		addBounty(half);
		return half;
	}
}
