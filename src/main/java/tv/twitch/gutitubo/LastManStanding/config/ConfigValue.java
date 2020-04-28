package tv.twitch.gutitubo.LastManStanding.config;

/**
 * Configからの値はこのクラスに一時格納する
 * 利用する場合はここから取得する
 * @author gutitubo
 *
 */

public class ConfigValue {

	/**
	 * ゲーム開始までの準備時間
	 */
	public static int waitingTime;

	/**
	 * ゲーム終了までの時間
	 */
	public static int gameTime;

	/**
	 * ゲーム開始時間
	 */
	public static int startTime;

	/**
	 * ゲーム開始最少人数
	 */
	public static int minPlayer;

	/**
	 * プレイヤーの足の速さ
	 */
	public static float walkSpeed;

	/**
	 * 最終範囲に選ばれる座標範囲
	 */
	public static double higher_x;
	public static double higher_z;
	public static double lower_x;
	public static double lower_z;

	/**
	 * Borderの初期範囲
	 */
	public static double defaultSize;

}
