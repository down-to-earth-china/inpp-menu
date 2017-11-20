package com.huateng.excels;

import com.huateng.entity.User;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 
 * @author Administrator
 * 
 */
public class UserDataExcel {

	/**
	 * 模板相对路径
	 */
	public static String relativePath = "/resources/excel/expRepayInfo.xls";

	/**
	 * 文件名
	 */
	private String fileName;

	public UserDataExcel() {
		super();
		fileName = "repayondate" + System.currentTimeMillis() + ".xls";// 文件名
	}

	/**
	 * 填充excel。2017/1/23;
	 * 
	 * @param template
	 *            填充的excel模板
	 * @param list
	 *            数据list
	 * @param index
	 *            数据填充excel的起始位置
	 * @return int
	 * @throws IOException
	 *             IOException
	 */
	public int fillExcel(ExcelTemplate template, List<User> list,
			int index) throws IOException {
		if (list == null || list.size() <= 0 || template == null || index < 0) {
			return -1;
		}
		for (int i = 0; i < list.size(); i++) {
			User user = list.get(i);
			template.createRow(i);
			template.createCell(i+1);
			template.createCell(user.getName());
			template.createCell(user.getAddress());
		}
		return index;
	}

	/**
	 * 设置返回信息。2016/8/25;
	 * 
	 * @param response
	 * @throws UnsupportedEncodingException
	 */
	public void setExcelResponse(HttpServletResponse response, String fileName)
			throws UnsupportedEncodingException {
		response.setContentType("application/x-download;charset=GBK");
		response.setHeader("Content-Disposition", "attachment;filename="
				+ new String(fileName.getBytes("GBK"), "iso-8859-1") + ".xls");
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
