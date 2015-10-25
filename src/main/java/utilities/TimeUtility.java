package utilities;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.net.ntp.NTPUDPClient;
import org.apache.commons.net.ntp.TimeInfo;

public class TimeUtility {

	public static String generateDateMethod()
	{
		String correct_time = "";
		long returnTime;  
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		String TIME_SERVER = "time-a.nist.gov";   
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		TimeInfo timeInfo = null;
		try {
			timeInfo = timeClient.getTime(inetAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date(returnTime);
		correct_time = dateFormat.format(time).toString();
		return correct_time;

	}
	
	public static Date generateDate()
	{
		long returnTime;  
		String TIME_SERVER = "time-a.nist.gov";   
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		TimeInfo timeInfo = null;
		try {
			timeInfo = timeClient.getTime(inetAddress);
		} catch (IOException e) {
			e.printStackTrace();
		}
		returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date(returnTime);
		return time;
	}
	
	public static String generateSysDateMethod()
	{
		String system_time = "";
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		system_time = dateFormat.format(date).toString();
		return system_time;
	}

	public static int generateYearWebMethod()
	{
		int web_year = 0;
		long returnYear;
		DateFormat dateFormat_sec = new SimpleDateFormat("yyyy");
		String TIME_SERVER = "time-a.nist.gov";   
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeInfo timeInfo = null;
		try {
			timeInfo = timeClient.getTime(inetAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnYear = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date(returnYear);
		web_year = Integer.parseInt(dateFormat_sec.format(time));
		return web_year;
	}

	public static int generateSysYearMethod()
	{
		String system_year = "";
		int int_sys_year = 0;
		DateFormat dateFormat = new SimpleDateFormat("yyyy");
		Date date = new Date();
		system_year = dateFormat.format(date).toString();
		int_sys_year = Integer.parseInt(system_year);
		return int_sys_year;
	}

	public static int generateMonthWebMethod()
	{
		int web_month = 0;
		long returnTime;
		DateFormat dateFormat_sec = new SimpleDateFormat("MM");
		String TIME_SERVER = "time-a.nist.gov";   
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeInfo timeInfo = null;
		try {
			timeInfo = timeClient.getTime(inetAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date(returnTime);
		web_month = Integer.parseInt(dateFormat_sec.format(time));
		return web_month;
	}

	public static int generateSysMonthMethod()
	{
		String system_month = "";
		int int_sys_month = 0;
		DateFormat dateFormat_sec = new SimpleDateFormat("MM");
		Date date = new Date();
		system_month = dateFormat_sec.format(date).toString();
		int_sys_month =  Integer.parseInt(system_month);
		return int_sys_month;
	}

	public static int generateSecondsMethod()
	{
		int web_sec = 0;
		long returnTime;
		DateFormat dateFormat_sec = new SimpleDateFormat("ss");
		String TIME_SERVER = "time-a.nist.gov";   
		NTPUDPClient timeClient = new NTPUDPClient();
		InetAddress inetAddress = null;
		try {
			inetAddress = InetAddress.getByName(TIME_SERVER);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TimeInfo timeInfo = null;
		try {
			timeInfo = timeClient.getTime(inetAddress);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		returnTime = timeInfo.getMessage().getTransmitTimeStamp().getTime();
		Date time = new Date(returnTime);
		web_sec = Integer.parseInt(dateFormat_sec.format(time));
		return web_sec;
	}
	public static int generateSysSecondsMethod()
	{
		String system_time = "";
		int int_sys_time = 0;
		DateFormat dateFormat_sec = new SimpleDateFormat("ss");
		Date date = new Date();
		system_time = dateFormat_sec.format(date).toString();
		int_sys_time =  Integer.parseInt(system_time);
		return int_sys_time;
	}

}