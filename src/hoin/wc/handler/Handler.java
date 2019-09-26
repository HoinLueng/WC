/**
 * 
 */
package hoin.wc.handler;

import java.io.FileNotFoundException;

import hoin.wc.function.Basic;
import hoin.wc.function.Extended;
import hoin.wc.info.Info;

/**
 * function:根据用户在文件选择器当中选择的是单个源程序文件还是多个源程序文件亦或是文件夹，然后再去执行相对应的处理逻辑
 * 
 * 
 *
 */
public class Handler {

	// 单个源程序文件
	private static final int SINGLE_FILE = 1;
	// 多个源程序文件
	private static final int MULTIPLE_FILE = 2;
	// 文件夹
	private static final int FOLDER = 3;
	// 基本功能
	private Basic baseFunction;
	// 拓展功能
	private Extended extendFunction;

	public Handler() {
		baseFunction = new Basic();
		extendFunction = new Extended();
	}

	/**
	 * function:根据用户的选择分别去执行对应的计算逻辑
	 * 
	 * @param code
	 * @param codeFileInfo
	 * @param filePath
	 * @return
	 * @throws FileNotFoundException
	 */
	public Info handle(int code, Info codeFileInfo, String filePath) {

		switch (code) {
		case SINGLE_FILE:
			codeFileInfo = baseFunction.print(codeFileInfo, filePath);
			codeFileInfo = extendFunction.print(codeFileInfo, filePath);
			break;
		case MULTIPLE_FILE:
			codeFileInfo = baseFunction.printFromFiles(codeFileInfo, filePath);
			codeFileInfo = extendFunction.printFromFiles(codeFileInfo, filePath);
			break;
		case FOLDER:
			codeFileInfo = baseFunction.printFromDir(codeFileInfo, filePath);
			codeFileInfo = extendFunction.printFromDir(codeFileInfo, filePath);
			break;
		default:
			break;
		}

		return codeFileInfo;
	}

}
