package Model;

import java.util.*;
import java.io.*;

public class ScoreHistoryFile {

	private static String SCOREHISTORY_DAT = "../SCOREHISTORY.DAT";

	public static void addScore(String nick, String date, String score) throws IOException, FileNotFoundException {
		String data = nick + "\t" + date + "\t" + score + "\n";
		RandomAccessFile out = new RandomAccessFile(SCOREHISTORY_DAT, "rw");
		out.skipBytes((int) out.length());
		out.writeBytes(data);
		out.close();
	}

	public static Vector getScores(String nick)
		throws IOException, FileNotFoundException {
		Vector scores = new Vector();
		BufferedReader in =
			new BufferedReader(new FileReader(SCOREHISTORY_DAT));
		String data;
		while ((data = in.readLine()) != null) {
			String[] scoredata = data.split("\t");
			if (nick.equals(scoredata[0])) {
				scores.add(new Score(scoredata[0], scoredata[1], scoredata[2]));
			}
		}
		return scores;
	}
}
