/**
 * 
 */
package com.zhangliping.baseUtil;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author zhangliping
 *
 */
public class JosnLogUtil {
	public static final String twoTabs = "        ";
	public static final String newLine = "\n";
	public static String formatEx(Throwable e){
		
		if(null == e){
			return null;
		}
		String exName = e.toString() + ": " + e.getMessage();
		int maxLine = 20;
		StackTraceElement[] stackTraceElements = e.getStackTrace();
	    int errLength = stackTraceElements.length;
		int printLength = maxLine>errLength?errLength:maxLine;
	    StringBuffer sb = new StringBuffer();
	    sb.append(exName);
	    for(int i = 0; i < printLength; i++){
	    	sb.append(twoTabs).append(stackTraceElements[i]).append(newLine);
	    }
		return sb.toString();
	}

	/**
	 * 获取异常的全栈信息
	 * @param e
	 * @return
     */
	public static String causeByEx(Throwable e){
		if(null == e){
			return null;
		}

		int maxLine = 5;

		StringBuilder builder = new StringBuilder();

		StringWriter sw = null;
		PrintWriter pw = null;
		try {
			sw = new StringWriter();
			pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			String exStack = sw.toString();
			String[] stacks = exStack.split("Caused by");
			if (stacks.length <= 1){
                maxLine = 20;
            }
			for(String info : stacks){
                builder.append(twoTabs).append("Caused by");
                String[] lines = info.split("\n");
                for(int i=0;i<maxLine&&i<lines.length;i++){
                    builder.append(lines[i]).append(newLine);
                }
            }
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				if(pw != null){
                    pw.close();
                }
				if(sw != null){
                    sw.close();
                }
			} catch (IOException e1) {
				pw = null;
				sw = null;
			}

		}

		return builder.toString();
	}
}

