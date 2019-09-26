/**
 * 
 */
package hoin.wc;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FileDialog;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import hoin.wc.function.Basic;
import hoin.wc.function.Extended;
import hoin.wc.handler.Handler;
import hoin.wc.info.Info;

/**
 * function:WC程序功能界面
 * 
 * 
 *
 */
public class WcMain extends JFrame implements ActionListener {

	private final JLabel charcount = new JLabel("字符数：");
	private final JLabel wordcount = new JLabel("单词数：");
	private final JLabel linecount = new JLabel("总行数：");
	private final JLabel codeLines = new JLabel("代码行数：");
	private final JLabel commentLines = new JLabel("注释行数：");
	private final JLabel blankLines = new JLabel("空行数：");
	private final JLabel filePath = new JLabel("文件路径");
	private final JTextField filePathText = new JTextField(10);
	private final JButton dqtj = new JButton("统计");
	private final JButton exit = new JButton("退出");
	private final JButton browser = new JButton("浏览");
	private Info codeFileInfo;
	private int code;

	/**
	 * function:构造函数
	 */
	public WcMain() {
		codeFileInfo = new Info();
		// 设置整个内容布局
		getContentPane().setLayout(new BorderLayout());
		// 添加监听事件
		exit.addActionListener(this);
		dqtj.addActionListener(this);
		browser.addActionListener(this);
		// 设置JFrame
		this.setTitle("WordCounter");
		this.setVisible(true);
		this.setSize(400, 300);
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(new GridLayout(2, 2)); // 设置总布局

		JPanel pane1 = new JPanel();
		pane1.setLayout(new GridLayout(4, 1));
		pane1.add(charcount);
		pane1.add(commentLines);
		pane1.add(wordcount);
		getContentPane().add(pane1);
		
		JPanel pane2 = new JPanel();
		pane2.add(filePath);
		pane2.add(filePathText);
		pane2.add(browser);
		getContentPane().add(pane2);

		JPanel pane3 = new JPanel();
		pane3.setLayout(new GridLayout(4, 1));
		pane3.add(codeLines);
		pane3.add(blankLines);
		pane3.add(linecount);
		getContentPane().add(pane3);		
			
		JPanel pane4 = new JPanel();
		pane4.setLayout(new GridLayout(4, 3));
		pane4.add(dqtj);
		pane4.add(exit);
		getContentPane().add(pane4);
	}

	/**
	 * function:程序主入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		WcMain wcMain = new WcMain();
	}

	/**
	 * function:监听事件
	 */
	public void actionPerformed(ActionEvent e) {

		// 监听按钮点击事件（e.getActionCommand()方法依赖于按钮上的字符串）
		String soruceName = e.getActionCommand();

		// 判断触发点击事件的按钮是哪一个
		if (soruceName.equals("浏览")) {
			// 以当前路径创建JFileChooser文件选择器
			JFileChooser jFileChooser = new JFileChooser(".");
			jFileChooser.setDialogTitle("浏览");
			jFileChooser.setMultiSelectionEnabled(true);
			jFileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			int returnval = jFileChooser.showDialog(new JLabel(), "浏览");
			if (returnval == JFileChooser.APPROVE_OPTION) {
				// 用户选择了单个文件或者文件夹
				File file = jFileChooser.getSelectedFile();
				// 用户选择了多个文件
				File[] files = jFileChooser.getSelectedFiles();
				if (files != null && files.length > 1) {
					code = 2;
					ArrayList<String> sourceFilePath = new ArrayList<String>();
					for (int i = 0; i < files.length; i++) {
						sourceFilePath.add(files[i].getAbsolutePath());
					}
					// 多文件绝对路径
					filePathText.setText(arrayListToString(sourceFilePath));
				} else if (file != null && file.isFile()) {
					code = 1;
					// 单文件绝对路径
					filePathText.setText(file.getAbsolutePath());
				} else if (file != null && file.isDirectory()) {
					code = 3;
					// 文件夹绝对路径
					filePathText.setText(file.getAbsolutePath());
				}
			}
		} else if (soruceName.equals("退出")) {
			// 退出程序
			System.exit(0);
		} else if (soruceName.equals("统计")) {
			Handler calculateHandler = new Handler();
			setValue(calculateHandler.handle(code, codeFileInfo, filePathText.getText()));
		} 
	}

	/**
	 * function:将计算数据显示到界面上
	 * 
	 * @param codeFileInfo
	 */
	public void setValue(Info codeFileInfo) {
		linecount.setText("总行数：" + codeFileInfo.getLinecount());
		charcount.setText("字符数：" + codeFileInfo.getCharcount());
		wordcount.setText("单词数：" + codeFileInfo.getWordcount());
		codeLines.setText("代码行数：" + codeFileInfo.getCodeLines());
		commentLines.setText("注释行数：" + codeFileInfo.getCommentLines());
		blankLines.setText("空行数：" + codeFileInfo.getBlankLines());

		initObject(codeFileInfo);

	}

	/**
	 * function:将ArrayList类型转化为String类型
	 * 
	 * @param arrayList
	 * @return
	 */
	private String arrayListToString(ArrayList<String> arrayList) {
		String result = "";
		if (arrayList != null && arrayList.size() > 0) {
			for (String item : arrayList) {
				// 把列表中的每条数据用逗号分割开来，然后拼接成字符串
				result += item + ",";
			}
			// 去掉最后一个逗号
			result = result.substring(0, result.length() - 1);
		}
		return result;
	}

	/**
	 * function:将CodeFileInfo对象的值重新进行初始化
	 * 
	 * @param codeFileInfo
	 */
	private void initObject(Info codeFileInfo) {
		codeFileInfo.setBlankLines(0);
		codeFileInfo.setCharcount(0);
		codeFileInfo.setCodeLines(0);
		codeFileInfo.setCommentLines(0);
		codeFileInfo.setLinecount(0);
		codeFileInfo.setWordcount(0);
	}

}
