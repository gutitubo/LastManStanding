package tv.twitch.gutitubo.LastManStanding.files;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty.LMSBounty;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSBounty.LMSBountyManager;
import tv.twitch.gutitubo.LastManStanding.LMSGame.LMSScore.LMSScoreHolder;

/**
 * CSVファイルエクスポート用のクラス
 * @author gutitubo
 *
 */
public class CSVCreator {

	/**
	 * 実際にファイルを作成するためのクラス
	 */
	public static void createCsv() {
		// 出力先パスを指定
		Path path = Paths.get("exports");

		// 保存用ファイル名を取得
		LocalDateTime time = LocalDateTime.now();
		String fileName = "matchresult"
				+ time.format(DateTimeFormatter.BASIC_ISO_DATE)
				+ time.getHour()
				+ time.getMinute()
				+ ".csv";

		try {
			// 出力先ディレクトリが無ければ作成
			if (!Files.exists(path)) Files.createDirectory(path);

			// 出力先ファイルを作成
			Path file = Paths.get("exports", fileName);
			Files.createFile(file);

			// PrintWriterをPathから作成
			FileWriter f = new FileWriter(file.toString());
			PrintWriter p = new PrintWriter(new BufferedWriter(f));

			// ヘッダーを作成
			p.print("Player,Rank,Kill,Point,TotalPoint,Bounty");
			p.println();

			// 全スコアの書き込み
			for (Object obj : LMSScoreHolder.allPointHolder.keySet().toArray()) {
				if (obj instanceof String) {
					String name = (String) obj;
					Integer rank = LMSScoreHolder.rankHolder.get(name);
					Integer kill = LMSScoreHolder.killHolder.get(name);
					Integer point = LMSScoreHolder.scoreHolder.get(name);
					Integer totalPoint = LMSScoreHolder.allPointHolder.get(name);
					LMSBounty bountyObj = LMSBountyManager.getBounty(name);
					Integer bounty = 0;
					if (bountyObj != null) bountyObj.getBounty();

					if (rank == null) rank = Integer.valueOf(999);
					if (kill == null) kill = Integer.valueOf(0);
					if (point == null) point = Integer.valueOf(0);
					if (totalPoint == null) totalPoint = Integer.valueOf(0);

					p.print(name + ","
							+ rank.toString() + ","
							+ kill.toString() + ","
							+ point.toString() + ","
							+ totalPoint.toString() + ","
							+ bounty.toString());
					p.println();
				}
			}

			// ファイルを閉じる
			p.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
