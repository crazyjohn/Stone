package com.i4joy.akka.kok.monster.other;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.other.reward.FirstPayRewardTemplet;
import com.i4joy.akka.kok.monster.other.reward.GrowPlanTemplet;
import com.i4joy.akka.kok.monster.other.reward.OnlineReward;
import com.i4joy.akka.kok.monster.other.reward.OpenReward;
import com.i4joy.akka.kok.monster.other.reward.SignInReward;
import com.i4joy.akka.kok.monster.other.reward.UpGradeReward;
import com.i4joy.akka.kok.monster.other.vip.VipGiftTemplet;
import com.i4joy.akka.kok.monster.other.vip.VipTemplet;
import com.i4joy.util.Tools;

public class OtherTempletManager {

	static OtherTempletManager self;

	private String path;

	Log logger = LogFactory.getLog(getClass());
	private Map<Integer, VipGiftTemplet> amuletDebrisTemplets = new HashMap<Integer, VipGiftTemplet>();
	private Map<Integer, VipTemplet> viptemplets = new HashMap<Integer, VipTemplet>();
	private Map<Integer, GrowPlanTemplet> planTemplets = new HashMap<Integer, GrowPlanTemplet>();
	private Map<Integer, OnlineReward> onlineTemplets = new HashMap<Integer, OnlineReward>();
	private Map<Integer, OpenReward> openTemplets = new HashMap<Integer, OpenReward>();
	private Map<Integer, SignInReward> sginTemplets = new HashMap<Integer, SignInReward>();
	private Map<Integer, UpGradeReward> upGradeTemplets = new HashMap<Integer, UpGradeReward>();
	private Map<Integer, FirstPayRewardTemplet> firstPayTemplets = new HashMap<Integer, FirstPayRewardTemplet>();
	

	public void init() throws Exception {
		long t = System.currentTimeMillis();
		this.path = TextProperties.getText("DESIGN_FILE_PATH");
		this.path = "D:\\WORKSPACE\\SVN\\Game2014\\Design\\数值表\\";
		readVipGiftData();
		readVipData();
		readPlanData();
		readOnlineData();
		readOpenData();
		readSignInData();
		readUpGradeData();
		readFirstPayData();
	}
	
	private void readFirstPayData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.FRISTPAYREWARD);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			FirstPayRewardTemplet edt = new FirstPayRewardTemplet();
			edt = Tools.fillByExcelRow(row, FirstPayRewardTemplet.class);
			firstPayTemplets.put(0, edt);
			index++;
		}
	}
	
	private void readUpGradeData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.UPGRADEREWARD);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			UpGradeReward edt = new UpGradeReward();
			edt = Tools.fillByExcelRow(row, UpGradeReward.class);
			upGradeTemplets.put(edt.getLevel(), edt);
			index++;
		}
	}
	
	private void readSignInData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.SIGNINREWARD);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			SignInReward edt = new SignInReward();
			edt = Tools.fillByExcelRow(row, SignInReward.class);
			sginTemplets.put(edt.getDay(), edt);
			index++;
		}
	}
	
	private void readOpenData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.OPENREWARD);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OpenReward edt = new OpenReward();
			edt = Tools.fillByExcelRow(row, OpenReward.class);
			openTemplets.put(edt.getDay(), edt);
			index++;
		}
	}

	private void readOnlineData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.ONLINEREWARD);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OnlineReward edt = new OnlineReward();
			edt = Tools.fillByExcelRow(row, OnlineReward.class);
			onlineTemplets.put(edt.getMinite(), edt);
			index++;
		}
	}

	private void readPlanData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.GROWPLAN);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			GrowPlanTemplet edt = new GrowPlanTemplet();
			edt = Tools.fillByExcelRow(row, GrowPlanTemplet.class);
			planTemplets.put(edt.getLevel(), edt);
			index++;
		}
	}

	public static void main(String[] args) {
		OtherTempletManager etm = new OtherTempletManager();
		try {
			etm.init();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void readVipData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.VIPINFO);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			VipTemplet edt = new VipTemplet();
			edt = Tools.fillByExcelRow(row, VipTemplet.class);
			viptemplets.put(edt.getLevel(), edt);
			index++;
		}
	}

	private void readVipGiftData() throws Exception {
		FileInputStream fis = new FileInputStream(path + OtherConstants.VIPGIFT);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			VipGiftTemplet edt = new VipGiftTemplet();
			edt = Tools.fillByExcelRow(row, VipGiftTemplet.class);
			amuletDebrisTemplets.put(edt.getId(), edt);
			index++;
		}
	}
	//
	// private void readAmuletUpgradeData() throws Exception {
	// File dir = new File(this.path + OtherConstants.AMULETUPGRADES);
	// File[] fils = dir.listFiles();
	// for (File file : fils) {
	// if (!file.getName().contains("_")) {
	// continue;
	// }
	// String info[] = file.getName().split("_");
	// long equipmentId = Long.parseLong(info[0]);
	// Map<Integer, AmuletUpGradeTemplet> mls = new HashMap<Integer,
	// AmuletUpGradeTemplet>();
	// this.amuletUpGradeTemplets.put(equipmentId, mls);
	//
	// FileInputStream fis = new FileInputStream(file);
	// XSSFWorkbook workbook = new XSSFWorkbook(fis);
	// Sheet sheetLevel = workbook.getSheetAt(0);
	// int index = 1;
	// while (true) {
	// Row row = sheetLevel.getRow(index);
	// if (row == null || row.getCell(0) == null ||
	// row.getCell(0).toString().length() == 0) {
	// break;
	// }
	// AmuletUpGradeTemplet ml = new AmuletUpGradeTemplet();
	// ml = Tools.fillByExcelRow(row, AmuletUpGradeTemplet.class);
	// mls.put(ml.getLevel(), ml);
	// index++;
	//
	// }
	//
	// Map<Integer, AmuletRefineTemplet> ars = new HashMap<Integer,
	// AmuletRefineTemplet>();
	// this.amuletRefineTemplets.put(equipmentId, ars);
	//
	// fis = new FileInputStream(file);
	// workbook = new XSSFWorkbook(fis);
	// sheetLevel = workbook.getSheetAt(1);
	// index = 1;
	// while (true) {
	// Row row = sheetLevel.getRow(index);
	// if (row == null || row.getCell(0) == null ||
	// row.getCell(0).toString().length() == 0) {
	// break;
	// }
	// AmuletRefineTemplet ml = new AmuletRefineTemplet();
	// ml = Tools.fillByExcelRow(row, AmuletRefineTemplet.class);
	// ars.put(ml.getLevel(), ml);
	// index++;
	//
	// }
	// }
	//
	// }
	//
	// private void readAmuletUpGradePay() throws Exception {
	// FileInputStream fis = new FileInputStream(path +
	// OtherConstants.AMULETUPGRADEPAY);
	// XSSFWorkbook workbook = new XSSFWorkbook(fis);
	// int sheetIndex = 0;
	// while (true) {
	// Sheet sheet = null;
	// try {
	// sheet = workbook.getSheetAt(sheetIndex);
	// } catch (Exception e) {
	//
	// }
	// if (sheet == null) {
	// return;
	// }
	// String sheetName = sheet.getSheetName();
	// String[] ss = sheetName.split("_");
	// int upgradeType = Integer.parseInt(ss[0]);
	// Map<Integer, AmuletUpGradePayTemplet> mudts = new HashMap<Integer,
	// AmuletUpGradePayTemplet>();
	// this.amuletUpGradePayTemplets.put(upgradeType, mudts);
	//
	// int index = 1;
	// while (true) {
	// Row row = sheet.getRow(index);
	// if (row == null || row.getCell(0) == null ||
	// row.getCell(0).toString().length() == 0) {
	// break;
	// }
	// AmuletUpGradePayTemplet mudt = Tools.fillByExcelRow(row,
	// AmuletUpGradePayTemplet.class);
	// mudts.put(mudt.getLevel(), mudt);
	// index++;
	// }
	// sheetIndex++;
	// }
	// }

}
