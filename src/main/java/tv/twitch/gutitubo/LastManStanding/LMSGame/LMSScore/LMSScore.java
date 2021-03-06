package tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore;

import org.bukkit.entity.Player;

public class LMSScore {
	private Player player;
	private int kill;
	private int score;

	public LMSScore (Player player) {
		this.player = player;
		kill = 0;
		score = 0;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public int getKill() {
		return kill;
	}

	public void setKill(int kill) {
		this.kill = kill;
	}

	public void addKill(int kill) {
		setKill(getKill() + kill);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void addScore(int score) {
		setScore(getScore() + score);
	}
}
