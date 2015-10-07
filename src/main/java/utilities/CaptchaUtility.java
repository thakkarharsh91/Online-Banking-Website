package utilities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CaptchaUtility {

	public static final String FILE_TYPE = "jpeg";

	public void generateCaptcha(HttpServletRequest request,HttpServletResponse response) throws IOException{
		OutputStream outputStream = null;
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Max-Age", 0);
		response.setContentType("image/jpeg");
		
		String captchaStr=generateCaptchaTextMethod(6);
		int width=175;     	int height=30;

		Color bg = new Color(100,100,100);
		Color fg = new Color(0,0,0);

		Font font = new Font("Arial", Font.BOLD, 20);
		BufferedImage cpimg =new BufferedImage(width,height,BufferedImage.OPAQUE);
		Graphics g = cpimg.createGraphics();

		g.setFont(font);
		g.setColor(bg);
		g.fillRect(0, 0, width, height);
		g.setColor(fg);
		g.drawString(captchaStr,45,25);   
		
		HttpSession session = request.getSession(true);
		session.setAttribute("CAPTCHA", captchaStr);

		try {
			outputStream = response.getOutputStream();
			ImageIO.write(cpimg, FILE_TYPE, outputStream);
			if(outputStream!=null){
				outputStream.flush();
				outputStream.close();
				response.sendRedirect("login.jsp");
				//response.flushBuffer();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*finally{
			if(outputStream!=null){
				outputStream.flush();
				outputStream.close();
				response.flushBuffer();
			}
		}*/

	}

	public static String generateCaptchaTextMethod(int captchaLength) 	 {

		String saltChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuffer captchaStrBuffer = new StringBuffer();
		java.util.Random rnd = new java.util.Random();

		// build a random captchaLength chars salt        
		while (captchaStrBuffer.length() < captchaLength)
		{
			int index = (int) (rnd.nextFloat() * saltChars.length());
			captchaStrBuffer.append(saltChars.substring(index, index+1));
		}

		return captchaStrBuffer.toString();

	}

}
