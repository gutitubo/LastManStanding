package tv.twitch.gutitubo.LastManStanding.files;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class FileUpdater {

	public static void UpdateFile(String urls,String filename){

		try {

			URL url = new URL(urls);

			HttpURLConnection conn =
					(HttpURLConnection) url.openConnection();
			conn.setAllowUserInteraction(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestMethod("GET");
			conn.connect();

			int httpStatusCode = conn.getResponseCode();

			if(httpStatusCode != HttpURLConnection.HTTP_OK){
				throw new Exception();
			}

			// Input Stream
			DataInputStream dataInStream
			= new DataInputStream(
					conn.getInputStream());

			// Output Stream
			DataOutputStream dataOutStream
			= new DataOutputStream(
					new BufferedOutputStream(
							new FileOutputStream("plugins/" + filename)));

			// Read Data
			byte[] b = new byte[4096];
			int readByte = 0;

			while(-1 != (readByte = dataInStream.read(b))){
				dataOutStream.write(b, 0, readByte);
			}

			// Close Stream
			dataInStream.close();
			dataOutStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
