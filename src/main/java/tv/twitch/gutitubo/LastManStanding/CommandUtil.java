package tv.twitch.gutitubo.LastManStanding;

import org.bukkit.command.CommandSender;
/**
 * コマンド入力の煩雑なアレをアレするクラス
 * @author gutitubo
 *
 */
public class CommandUtil {

	public static void sendCmdAnnounce(CommandSender cmd) {
		cmd.sendMessage("");
		cmd.sendMessage(" == LastManStanding == ");
		cmd.sendMessage("/lms start - ゲーム開始");
		cmd.sendMessage("/lms reset - ゲーム強制終了");
		cmd.sendMessage("");
	}
}
