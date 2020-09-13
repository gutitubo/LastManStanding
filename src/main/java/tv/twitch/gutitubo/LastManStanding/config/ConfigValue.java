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
	 * プレイヤーキル時のポイント
	 */
	public static int killPoint;

	/**
	 * ストリーマーキル時のポイント
	 */
	public static int streamerKillPoint;

	/**
	 * バウンティモードの初期配布額
	 */
	public static int bountyAmount;

	/**
	 * お祝いモードの有効化設定
	 */
	public static boolean isOiwai;

	/**
	 * 登録者奪い合いモードの有効化設定
	 */
	public static boolean isUbaiai;

	/**
	 * Blinkのパワー
	 */
	public static double blinkPower;

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
