package KumohTime.Util;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * 사용자 환경에서 브라우저를 실행한다.
 * @author Jeongsam Seo
 *
 */
public class Browser {

	/**
	 * 브라우저를 보여준다
	 * @param uri 
	 */
	public static void open(String uri) {
		try {
			Desktop.getDesktop().browse(new URI(uri));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
	
}
