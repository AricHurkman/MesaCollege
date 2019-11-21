package MusicMain;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class BuildMusicData {

	Thread thread;

	private byte[] musicBytes;

	void runBuildData(InputStream stream) {

		thread = new Thread(() -> {
			musicBytes = inputStreamToByteArray(stream);
			for (int i = 0; i < musicBytes.length; i++) {
				System.out.println(musicBytes[i]);
			}
		});
		thread.start();

	}


	private byte[] inputStreamToByteArray(InputStream inStream) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int bytesRead = 0;

		try {
			while ((bytesRead = inStream.read(buffer)) > 0) {
				baos.write(buffer, 0, bytesRead);
			}
			inStream.close();
		} catch (IOException io) {
			//io.printStackTrace();

		}

		return baos.toByteArray();
	}
}
