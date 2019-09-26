/**
 * 
 */
package hoin.wc.function;

import java.io.FileNotFoundException;

import hoin.wc.info.Info;

/**
 * function:源程序文件的代码统计器程序的功能接口
 * 
 * 
 *
 */
public interface Calculate {

	/**
	 * function:计算单个源程序文件
	 * 
	 * @param codeFileInfo
	 * @param sourcefile
	 * @return
	 */
	public Info print(Info codeFileInfo, String sourcefile);

	/**
	 * function:计算某个文件夹下的所有源程序文件
	 * 
	 * @param codeFileInfo
	 * @param sourceDir
	 * @return
	 * @throws FileNotFoundException
	 */
	public Info printFromDir(Info codeFileInfo, String sourceDir);

	/**
	 * function:计算多个源文件
	 * 
	 * @param codeFileInfo
	 * @param files
	 * @return
	 * @throws FileNotFoundException
	 */
	public Info printFromFiles(Info codeFileInfo, String files);
}
