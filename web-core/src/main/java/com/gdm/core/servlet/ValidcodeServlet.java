/* 
 * 
 *
 * 
 */
package com.gdm.core.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import com.gdm.core.utils.Strings;

/**
 * 生成随机验证码.
 * 随机验证码存储在当前Session中.
 * 当前Session只能有一个有效的随机验证码.
 * 
 * @author YHY
 * @version 2015-01-14
 * @----------------------------------------------------------------------------------------
 * @updated 修改描述.
 * @updated by YHY
 * @updated at 2015-01-14
 */
@SuppressWarnings("serial")
public class ValidcodeServlet extends HttpServlet {

	/**
	 * 性能测试专用：是否进行图片验证码验证
	 * true：验证并返回正确结果
	 * false：不验证始终返回验证成功
	 */
	private final static boolean IS_VALIDATE_CODE = true;

	/** 验证码request参数名称 */
	private static final String VALIDATE_NAME = "validcode";
	/** 验证码session的名称 */
	private static final String VALIDATE_CODE = "_session_validate_code_";
	/** 验证码session的校验失败次数 */
	private static final String VALIDATE_COUNT = "_session_validate_count_";

	private int w = 70;
	private int h = 30;

	public ValidcodeServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	/**
	 * 验证随机验证码是否有效.
	 * 
	 * @param request
	 * @param validateCode 被验证的随机验证码
	 * @return
	 * @author YHY
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-14
	 */
	public static boolean validate(HttpServletRequest request, String validcode) {
		if (!IS_VALIDATE_CODE) { // 不验证始终返回验证成功
			return true;
		}
		Object countObj = request.getSession().getAttribute(VALIDATE_COUNT); // 验证的失败次数
		int count = 0;
		if (countObj != null && Strings.isNotBlank(countObj.toString())) {
			count = Integer.parseInt(countObj.toString());
		}
		count++;
		if (count > 5) { // 如果失败次数大于5次：直接返回图片验证码错误
			return false;
		} else {
			request.getSession().setAttribute(VALIDATE_COUNT, count);
		}
		String code = (String) request.getSession().getAttribute(VALIDATE_CODE);
		boolean result = validcode.toUpperCase().equals(code);
		if (result) { // 验证码只能验证一次
			request.getSession().removeAttribute(VALIDATE_CODE);
		}
		return result;
	}

	/**
	 * 发送请求在页面显示随机验证码图片
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String validateCode = request.getParameter(VALIDATE_NAME); // AJAX验证，成功返回true
		if (StringUtils.isNotBlank(validateCode)) {
			response.getOutputStream().print(validate(request, validateCode) ? "true" : "false");
		} else {
			this.doPost(request, response);
		}
	}

	/**
	 * 发送请求在页面显示随机验证码图片
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		createImage(request, response);
	}

	/**
	 * 创建随机验证码图片.
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @author YHY
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-14
	 */
	private void createImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		/*
		 * 得到参数高，宽，都为数字时，则使用设置高宽，否则使用默认值
		 */
		String width = request.getParameter("width");
		String height = request.getParameter("height");
		if (StringUtils.isNumeric(width) && StringUtils.isNumeric(height)) {
			w = NumberUtils.toInt(width);
			h = NumberUtils.toInt(height);
		}

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();

		/*
		 * 生成背景
		 */
		createBackground(g);

		/*
		 * 生成字符
		 */
		String s = createCharacter(g);
		request.getSession().setAttribute(VALIDATE_CODE, s);
		request.getSession().setAttribute(VALIDATE_COUNT, 0);

		g.dispose();
		OutputStream out = response.getOutputStream();
		ImageIO.write(image, "JPEG", out);
		out.close();

	}

	/**
	 * 获取背景色.
	 * 
	 * @param fc
	 * @param bc
	 * @return
	 * @author YHY
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-14
	 */
	private Color getRandColor(int fc, int bc) {
		int f = fc;
		int b = bc;
		Random random = new Random();
		if (f > 255) {
			f = 255;
		}
		if (b > 255) {
			b = 255;
		}
		return new Color(f + random.nextInt(b - f), f + random.nextInt(b - f), f + random.nextInt(b - f));
	}

	/**
	 * 创建背景图.
	 * 
	 * @param g
	 * @author YHY
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-14
	 */
	private void createBackground(Graphics g) {
		// 填充背景
		g.setColor(getRandColor(220, 250));
		g.fillRect(0, 0, w, h);
		// 加入干扰线条
		for (int i = 0; i < 10; i++) {
			g.setColor(getRandColor(40, 150));
			Random random = new Random();
			int x = random.nextInt(w);
			int y = random.nextInt(h);
			int x1 = random.nextInt(w);
			int y1 = random.nextInt(h);
			g.drawLine(x, y, x1, y1);
		}
	}

	/**
	 * 生成随机验证码字符.
	 * 
	 * @param g
	 * @return
	 * @author YHY
	 * @version 2015-01-14
	 * @----------------------------------------------------------------------------------------
	 * @updated 修改描述.
	 * @updated by YHY
	 * @updated at 2015-01-14
	 */
	private String createCharacter(Graphics g) {
		// char[] codeSeq = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		char[] codeSeq = { '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		String[] fontTypes = { "\u5b8b\u4f53", "\u65b0\u5b8b\u4f53", "\u9ed1\u4f53", "\u6977\u4f53", "\u96b6\u4e66" };
		Random random = new Random();
		StringBuilder s = new StringBuilder();
		for (int i = 0; i < 4; i++) {
			String r = String.valueOf(codeSeq[random.nextInt(codeSeq.length)]);// random.nextInt(10));
			g.setColor(new Color(50 + random.nextInt(100), 50 + random.nextInt(100), 50 + random.nextInt(100)));
			g.setFont(new Font(fontTypes[random.nextInt(fontTypes.length)], Font.BOLD, 26));
			g.drawString(r, 15 * i + 5, 19 + random.nextInt(8));
			// g.drawString(r, i*w/4, h-5);
			s.append(r);
		}
		return s.toString();
	}

}
