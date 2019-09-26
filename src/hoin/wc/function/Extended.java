/**
 * 
 */
package hoin.wc.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeMap;

import hoin.wc.filter.FileFilter;
import hoin.wc.info.Info;

/**
 * function:拓展功能（计算源程序文件中的代码行行数、空行行数、注释行行数）
 * 
 * 
 *
 */
public class Extended implements Calculate {

	/**
	 * function:计算单个源程序文件
	 * 
	 * @param codeFileInfo
	 * @param sourcefile
	 *            源程序文件绝对路径
	 * @return
	 * @throws FileNotFoundException
	 */
	public Info print(Info codeFileInfo, String sourcefile) {
		File file = new File(sourcefile);
		int commentLines = 0;
		int blankLines = 0;
		int codeLines = 0;
		int fakeCommentLines = 0;
		BufferedReader bufferedReader = null;
		boolean comment = false;

		if (file != null && file.exists()) {
			try {
				bufferedReader = new BufferedReader(new FileReader(file));
				String line = "";
				while ((line = bufferedReader.readLine()) != null) {
					line = line.trim();
					if (line.matches("^[//s&&[^//n]]*$") || line.equals("{") || line.equals("}")) {
						blankLines++;
					}
					else if (line.startsWith("/*") && !line.endsWith("*/")
							|| ((line.startsWith("{/*") || line.startsWith("}/*")) && !line.endsWith("*/"))) {
						commentLines++;
						comment = true;
					} else if (comment == true && !line.endsWith("*/") && !line.startsWith("*/")) {
						fakeCommentLines++;
						commentLines++;
					} else if (comment == true && (line.endsWith("*/") || line.startsWith("*/"))) {
						commentLines++;
						comment = false;
					} else if (line.startsWith("//") || line.startsWith("}//") || line.startsWith("{//")
							|| ((line.startsWith("{/*") || line.startsWith("}/*") || line.startsWith("/*"))
									&& line.endsWith("*/"))) {
						commentLines++;
					} else {
						codeLines++;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bufferedReader != null) {
					try {
						bufferedReader.close();
						bufferedReader = null;
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}
		}

		codeFileInfo.setBlankLines(codeFileInfo.getBlankLines() + blankLines);
		codeFileInfo.setCodeLines(codeFileInfo.getCodeLines() + codeLines + fakeCommentLines);
		codeFileInfo.setCommentLines(codeFileInfo.getCommentLines() + commentLines - fakeCommentLines);

		return codeFileInfo;
	}

	/**
	 * function:计算某个文件夹下的所有源程序文件
	 * 
	 * @param codeFileInfo
	 *            文件夹绝对路径
	 * @param sourceDir
	 * @return
	 * @throws FileNotFoundException
	 */
	public Info printFromDir(Info codeFileInfo, String sourceDir) {
		// 过滤器(将相对应的源程序文件筛选出来)
		FileFilter filter = new FileFilter();
		File file = new File(sourceDir);
		File[] sourceFiles = file.listFiles(filter);
		ArrayList<String> sourceFilePath = new ArrayList<String>();

		for (int i = 0; i < sourceFiles.length; i++) {
			if (sourceFiles[i].isFile()) {
				System.out.println("文件：" + sourceFiles[i]);
				sourceFilePath.add(sourceFiles[i].toString());
			}
			if (sourceFiles[i].isDirectory()) {
				System.out.println("文件夹：" + sourceFiles[i]);
			}
		}

		// 遍历每个源程序文件的绝对路径，利用计算方法对每个源程序文件进行计算，并且合并计算结果
		for (int i = 0; i < sourceFilePath.size(); i++) {
			print(codeFileInfo, sourceFilePath.get(i));
		}

		return codeFileInfo;
	}

	/**
	 * function:计算多个源文件
	 * 
	 * @param codeFileInfo
	 * @param files
	 * @return
	 * @throws FileNotFoundException
	 */
	public Info printFromFiles(Info codeFileInfo, String files) {

		ArrayList<String> sourceFilePath = new ArrayList<String>(Arrays.asList(files.split(",")));

		// 遍历多个源程序文件的绝对路径，利用计算方法对每个源程序文件进行计算，并且合并计算结果
		for (int i = 0; i < sourceFilePath.size(); i++) {
			print(codeFileInfo, sourceFilePath.get(i));
		}

		return codeFileInfo;
	}
}
