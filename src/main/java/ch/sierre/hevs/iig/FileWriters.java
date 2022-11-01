package ch.sierre.hevs.iig;

import java.io.BufferedWriter;	
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriters {

	public static void writeMeSHPaths(String fileName, String str) {
	
		try (FileWriter fw = new FileWriter(fileName, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter out = new PrintWriter(bw)) {
			out.println(str);
//			out.println("\n");
		} catch (IOException e) {
			// exception handling left as an exercise for the reader
		}
	}
}