/**
 * 
 */
package hoin.wc.function;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
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
 * function:基本功能（计算源程序文件中的字符数、单词数和总行数）
 * 
 * 
 *
 */
public class Basic implements Calculate {

	/**
	 * function:计算单个源程序文件
	 * 
	 * @param codeFileInfo
	 * @param sourcefile
	 * @return
	 */
	public Info print(Info codeFileInfo, String sourcefile) {
		int linecount = 0;
		int charcount = 0;
		int wordcount = 0;

		File file = new File(sourcefile);

		if (file.exists()) {
			try {
				FileInputStream inputStream = new FileInputStream(file);
				InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
				BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
				String line = new String("");
				StringBuffer stringBuffer = new StringBuffer();
				TreeMap<String, Integer> map = new TreeMap<>();

				while ((line = bufferedReader.readLine()) != null) {
					linecount++;
					stringBuffer.append(line);
					charcount += line.length();
					String[] split = line
							.split("\\s++|\\.|,|\\;|\\(|\\)|\\[|\\]|\\<|\\>|\\=|\\-|\\+|\\*|\\/|\\{|\\}|\\_");
					for (int i = 0; i < split.length; i++) {
						Integer integer = map.get(split[i]);
						if (null == integer) {
							map.put(split[i], 1);
						} else {
							map.put(split[i], ++integer);
						}
					}
				}
				// 遍历，根据key获取所对应的value
				Set<String> keySet = map.keySet();
				for (String string : keySet)
					if (!(string.equals("")))
						wordcount += map.get(string);
				bufferedReader.close();
				inputStreamReader.close();
				inputStream.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		codeFileInfo.setLinecount(codeFileInfo.getLinecount() + linecount);
		codeFileInfo.setCharcount(codeFileInfo.getCharcount() + charcount);
		codeFileInfo.setWordcount(codeFileInfo.getWordcount() + wordcount);

		return codeFileInfo;
	}

	/**
	 * function:计算某个文件夹下的所有源程序文件
	 * 
	 * @param codeFileInfo
	 *            文件夹绝对路径
	 * @param sourceDir
	 * @return
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
