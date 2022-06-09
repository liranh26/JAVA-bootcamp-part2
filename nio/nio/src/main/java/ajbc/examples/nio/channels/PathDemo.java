package ajbc.examples.nio.channels;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathDemo {

	public static void main(String[] args) {

		// relativize gives the between two destinations
		Path basePath = Paths.get("/data");
		Path filePath = Paths.get("/data/subdata/subsubdata/myfile.txt");

		Path basePathToFile = basePath.relativize(filePath);
		Path fileToBasePath = filePath.relativize(basePath);

		System.out.println(basePathToFile);
		System.out.println(fileToBasePath);

		// normalize path - omits the back and forward cd
		String originalPath = "d:\\data\\projects\\a-project\\..\\another-project";

		Path path1 = Paths.get(originalPath);
		System.out.println("path1 = " + path1);

		Path path2 = path1.normalize();
		System.out.println("path2 = " + path2);

		// is this path real
		Path path3 = Paths.get("c:\\data\\myfile.txt"); //the file not exists
		boolean isReal = false;
		try {
			Path realPath = path3.toRealPath();
			isReal = true;
		} catch (IOException ignore) {
		}

		System.out.println("is it a real path ? " + isReal);

		Path path4 = Paths.get(".\\pom.xml");
		try {
			Path realPath = path4.toRealPath();
			isReal = true;
			System.out.println(realPath);
		} catch (IOException ignore) {
		}

		System.out.println("is it a real path ? " + isReal);

	}

}
