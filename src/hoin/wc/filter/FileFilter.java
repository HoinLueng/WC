/**
 * 
 */
package hoin.wc.filter;

import java.io.File;
import java.io.FilenameFilter;

/**
 * function:筛选某个目录下的源程序文件
 * 
 * 
 *
 */
public class FileFilter implements FilenameFilter {

	// 以下后缀的文件将会被筛选出来
	private static final String cFile = ".c";
	private static final String javaFile = ".java";

	@Override
	public boolean accept(File dir, String name) {
		if (name.endsWith(cFile) || name.endsWith(javaFile)) {
			return true;
		}
		return false;
	}
}
