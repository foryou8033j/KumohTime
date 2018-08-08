package KumohTime.Util;

/**
 * 현재 사용자의 OS 환경을 검사한다.
 * @author Jeongsam Seo
 * @since 2018-08-01
 */
public class OSCheck {

	private static String OS = System.getProperty("os.name").toLowerCase();
	
	public static boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
	
	public static boolean isMac() {
		return (OS.indexOf("mac") >= 0 || OS.indexOf("osx") >= 0);
	}
	
	public static boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") >=0); 
	}
	
	public static boolean isSolaris() {
		return (OS.indexOf("sunos") >= 0);
	}
	
}
