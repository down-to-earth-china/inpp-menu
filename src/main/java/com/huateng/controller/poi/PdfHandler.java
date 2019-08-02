package com.huateng.controller.poi;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.BaseFont;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.apache.poi.poifs.filesystem.DocumentEntry;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.xhtmlrenderer.pdf.ITextRenderer;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
/*
* 1、ftl生成html、pdf、word
*
* 2、PdfHandler.class.getResourceAsStream(string path)
* path：以"/"开头是在java项目根路径下【源码根路径/classes下】查找文件
*    不以"/开头的是在当前类的类路径下加载文件"
*    如加载源码位于"src/main/resources/contract/pdfDemo.html"
*    则去java项目根路径下 /contract/pdfDemo.html
* 注意：如果生成文件"pdfDemo.html"位于""src/main/resources/contract/"下
* 则 File file = new File(""src/main/resources/contract/pdfDemo.html");
* 路径path则为源码文件所指定的路径下
*
*
* 3、ServletContext.getResourceAsStream(String path)：
* 默认从WebAPP根目录下取资源【javaweb项目的根路径】，
* Tomcat下path是否以’/'开头无所谓，当然这和具体的容器实现有关。
 *
 *
 * 所以由上可知：源码webapp则是javaweb项目的根路径。
 * src/main【src/main/java和src/main/resources】下的源码包则发布到WEB-INF下
 *
 * PdfHandler.class.getClassLoader().getResourceAsStream(String path)
 * path不需要以"/"开头，默认在根路径下加载文件
 *
* */
@Controller
@RequestMapping("/baobiao")
public class PdfHandler {

	private static final String CONTRACT = "src/main/resources/contract/";// 合同文件存储路径
	private static final String TEMPLATE = "src/main/resources/templates/";// 模板存储路径

	private static final String PDFNAME = "pdfDemo";// pdf文件名
	private static final String HTMLNAME = "pdfDemo";// html文件名

	public static void contractHandler(String templateName, Map<String, Object> paramMap) throws Exception {
		// 获取本地模板存储路径、合同文件存储路径
		String templatePath = TEMPLATE;
		String contractPath = CONTRACT;
		// 组装html和pdf合同的全路径URL
		String localHtmlUrl = contractPath + HTMLNAME + ".html";
		String localPdfPath = contractPath + "/";
		// 判断本地路径是否存在如果不存在则创建
		File localFile = new File(localPdfPath);
		if (!localFile.exists()) {
			localFile.mkdirs();
		}
		String localPdfUrl = localFile + "/" + PDFNAME + ".pdf";
		templateName = templateName + ".ftl";
		htmHandler(templatePath, templateName, localHtmlUrl, paramMap);// 生成html合同
		pdfHandler(localHtmlUrl, localPdfUrl);// 根据html合同生成pdf合同
		deleteFile(localHtmlUrl);// 删除html格式合同

		System.out.println("PDF生成成功");
	}

	/**
	 * 使用poi html文件转换成word
	 * 
	 * @throws Exception
	 */
	private static void htmlToWord(String templateName, Map<String, Object> paramMap) throws Exception {
		String templatePath = TEMPLATE;
		String contractPath = CONTRACT;
		// 组装html和pdf合同的全路径URL
		String localHtmlUrl = contractPath + HTMLNAME + ".html";
		String localPdfUrl = contractPath + "/" + PDFNAME + ".doc";
		File htmlfile = new File(localHtmlUrl);
		String url = htmlfile.toURI().toURL().toString();
		templateName = templateName + ".ftl";
		htmHandler(templatePath, templateName, localHtmlUrl, paramMap);// 生成html合同

		POIFSFileSystem poifs = new POIFSFileSystem();
		DirectoryEntry directory = poifs.getRoot();
		OutputStream out = new FileOutputStream(localPdfUrl);

		InputStream in = new FileInputStream(url.substring(6));
		try {
			DocumentEntry document = directory.createDocument("hello", in);
			// 创建文档,1.格式,2.HTML文件输入流
			// directory.createDocument("WordDocument", getInputStream(url));
			// 写入
			poifs.writeFilesystem(out);
			// 释放资源
			out.close();
			System.out.println("success");
			deleteFile(localHtmlUrl);// 删除html格式合同
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 生成pdf格式合同
	 */
	private static void pdfHandler(String htmUrl, String pdfUrl) throws DocumentException, IOException {
		File htmFile = new File(htmUrl);
		File pdfFile = new File(pdfUrl);

		String url = htmFile.toURI().toURL().toString();

		OutputStream os = new FileOutputStream(pdfFile);

		org.xhtmlrenderer.pdf.ITextRenderer renderer = new ITextRenderer();
		renderer.setDocument(url);

		org.xhtmlrenderer.pdf.ITextFontResolver fontResolver = renderer.getFontResolver();
		// 解决中文支持问题
		fontResolver.addFont(TEMPLATE + "simsun.ttc", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);

		renderer.layout();
		renderer.createPDF(os);
		os.close();
	}
/*
* pdf文件直接输出 在客户端 使用firebug更改不了 浏览器端选择默认flash打卡pdf
*浏览器端可以随意下载
* */
	@RequestMapping("topdf")
	public void topdf(HttpServletRequest request,HttpServletResponse response) throws DocumentException, IOException {
		/*文件新建在tomcat/bin下
		*   File file = new File("hello1234567");
		*	System.out.println("file.mkdir()="+file.mkdir());
		* */

		String path = request.getSession().getServletContext().getRealPath("/");
		System.out.println("path-->"+path);

		//String htmUrl ="pdfDemo.html";
		String htmUrl ="/Users/shuai/develop/workspace_ht/inpp-menu/target/classes/templates/pdfDemo2.pdf";
		File htmFile = new File(htmUrl);
		FileInputStream fileInputStream = new FileInputStream(htmFile);
		byte[] bytes = new byte[1024];
		OutputStream printWriter = response.getOutputStream();
		while (fileInputStream.read(bytes)!=-1){
			printWriter.write(bytes);
		}
		printWriter.close();
	}
	/**
	 * html生成图片
	 * 
	 * @throws Exception
	 */
	private static void htmlToImg(String templateName, Map<String, Object> paramMap) throws Exception {
		String templatePath = TEMPLATE;
		String contractPath = CONTRACT;
		// 组装html和pdf合同的全路径URL
		String localHtmlUrl = contractPath + HTMLNAME + ".html";
		String imgUrl = contractPath + "/" + PDFNAME + ".jpg";
		File htmlfile = new File(localHtmlUrl);
		String url = htmlfile.toURI().toURL().toString();
		templateName = templateName + ".ftl";
		htmHandler(templatePath, templateName, localHtmlUrl, paramMap);// 生成html合同

		Desktop.getDesktop().browse(new URL(url).toURI());
		Robot robot = new Robot();
		robot.delay(10000);
		Dimension d = new Dimension(Toolkit.getDefaultToolkit().getScreenSize());
		int width = (int) d.getWidth();
		int height = (int) d.getHeight();
		// 最大化浏览器
		robot.keyRelease(KeyEvent.VK_F11);
		robot.delay(2000);
		Image image = robot.createScreenCapture(new Rectangle(0, 0, width, height));
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics g = bi.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		// 保存图片
		ImageIO.write(bi, "jpg", new File(imgUrl));
		deleteFile(localHtmlUrl);
	}

	/**
	 * 删除文件
	 */
	private static void deleteFile(String fileUrl) {
		File file = new File(fileUrl);
		file.delete();
	}

	public static void tohtml() throws Exception {
		String templateName = "201";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ZJHKZH", "271003********279975");
		paramMap.put("KYYE", "79244.95");
		paramMap.put("LXFS", "配置web.xml中LXFS属性，例如(张小凡，123,4567,8909)");
		paramMap.put("KHWD", "123");
		paramMap.put("CSKSRQ", "2016年10月31日00时00分");
		paramMap.put("KSRQ", "2017-03-14");
		paramMap.put("YE", "94444.95");
		paramMap.put("KHZH", "271**********07279975");
		paramMap.put("AH", "(2015)****字第0***0号");
		paramMap.put("CKH", "(2017)法YH****9控字第*号");
		paramMap.put("YDJAH", "(2015)***执字第00020号");
		paramMap.put("KZCS", "01");
		paramMap.put("XM", "張三豐");
		paramMap.put("FYMC", "****人民法院");
		paramMap.put("JSRQ", "2017-06-14");
		paramMap.put("KZZT", "1");
		paramMap.put("SE", "100");
		paramMap.put("LCZH", "987234234");
		paramMap.put("DATE", "2017年03月24日09时39分");
		paramMap.put("CKWH", "(2015)*****字第0**20-1**0号裁定书");
		paramMap.put("SKSE", "100");
		paramMap.put("CSJSRQ", "2016年10月31日 00时00分");

		String templatePath = TEMPLATE;
		String htmUrl =CONTRACT+HTMLNAME+"1.html";
		htmHandler(TEMPLATE, templateName+".ftl", htmUrl, paramMap);
	}

	public static void topdf() throws Exception {
		String templateName = "201";
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ZJHKZH", "271003********279975");
		paramMap.put("KYYE", "79244.95");
		paramMap.put("LXFS", "配置web.xml中LXFS属性，例如(张小凡，123,4567,8909)");
		paramMap.put("KHWD", "123");
		paramMap.put("CSKSRQ", "2016年10月31日00时00分");
		paramMap.put("KSRQ", "2017-03-14");
		paramMap.put("YE", "94444.95");
		paramMap.put("KHZH", "271**********07279975");
		paramMap.put("AH", "(2015)****字第0***0号");
		paramMap.put("CKH", "(2017)法YH****9控字第*号");
		paramMap.put("YDJAH", "(2015)***执字第00020号");
		paramMap.put("KZCS", "01");
		paramMap.put("XM", "張三豐");
		paramMap.put("FYMC", "****人民法院");
		paramMap.put("JSRQ", "2017-06-14");
		paramMap.put("KZZT", "1");
		paramMap.put("SE", "100");
		paramMap.put("LCZH", "987234234");
		paramMap.put("DATE", "2017年03月24日09时39分");
		paramMap.put("CKWH", "(2015)*****字第0**20-1**0号裁定书");
		paramMap.put("SKSE", "100");
		paramMap.put("CSJSRQ", "2016年10月31日 00时00分");


		final String PDFNAME = "pdfDemo.pdf";// pdf文件名
//		private static final String HTMLNAME = "pdfDemo";// html文件名
		//定义templatePath
		String htmUrl =CONTRACT+HTMLNAME+".html";
		pdfHandler(htmUrl, TEMPLATE+PDFNAME);
	}

	/**
	 * 生成html格式合同
	 */
	private static void htmHandler(String templatePath, String templateName, String htmUrl,
			Map<String, Object> paramMap) throws Exception {
		Configuration cfg = new Configuration();
		cfg.setDefaultEncoding("UTF-8");
		cfg.setDirectoryForTemplateLoading(new File(templatePath));

		Template template = cfg.getTemplate(templateName);

		File outHtmFile = new File(htmUrl);

		Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outHtmFile)));
		template.process(paramMap, out);

		out.close();
	}

	public static void main(String[] args) throws Exception {
		//tohtml();
		//topdf();
		/**
		 * getResourceAsStream(String path)
		 * path 以/开头是在项目根路径下找资源[java项目根路径为classes文件夹下]
		 */

		/*返回ture
		* InputStream inputStream =  PdfHandler.class.getResourceAsStream("/contract/pdfDemo.html");
		 * */

		/**返回falst
		 * 		InputStream inputStream =  PdfHandler.class.getClassLoader().getResourceAsStream("/contract/pdfDemo.html");
		 */

		//InputStream inputStream = PdfHandler.class.getResourceAsStream("/contract/pdfDemo4.html");
		InputStream inputStream = PdfHandler.class.getClassLoader().getResourceAsStream("/contract/pdfDemo4.html");
		if (inputStream==null){
			System.out.println("false");
		}else {
			System.out.println("true");
		}

	}
}
