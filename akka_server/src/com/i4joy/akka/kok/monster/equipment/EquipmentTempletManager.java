package com.i4joy.akka.kok.monster.equipment;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.DataCheckListener;
import com.i4joy.akka.kok.monster.DataCheckService;
import com.i4joy.akka.kok.monster.ProtoBuffFileMakeService;
import com.i4joy.akka.kok.monster.ProtoBuffFileMaker;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentBasePropertyTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentRefineTemplete;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentSuitTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentUpGradePayTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentUpGradeTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentdDebrisTemplet;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;
import com.i4joy.akka.kok.protobufs.res.KOKRes.BaseClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.EquipmentClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.EquipmentDebrisClientInfo;
import com.i4joy.util.Tools;

public class EquipmentTempletManager implements ProtoBuffFileMaker, DataCheckListener {

	static EquipmentTempletManager self;

	private String path;

	Log logger = LogFactory.getLog(getClass());

	/**
	 * 装备碎片数据模板
	 */
	private Map<Long, EquipmentdDebrisTemplet> equipmentDebrisTemplets = new HashMap<Long, EquipmentdDebrisTemplet>();
	private Map<Integer, Map<Integer, EquipmentUpGradePayTemplet>> equipmentGradePaySheetTemples = new HashMap<Integer, Map<Integer, EquipmentUpGradePayTemplet>>();
	private Map<Long, EquipmentBasePropertyTemplet> equipmentBasePropertyTemplets = new HashMap<Long, EquipmentBasePropertyTemplet>();
	private Map<Integer, Map<Integer, EquipmentRefineTemplete>> equipmentRefineTemplets = new HashMap<Integer, Map<Integer, EquipmentRefineTemplete>>();
	private Map<Long, Map<Integer, EquipmentUpGradeTemplet>> equipmentUpGradeTemplets = new HashMap<Long, Map<Integer, EquipmentUpGradeTemplet>>();
	/**
	 * 情谊数据模板
	 */
	private Map<Integer, EquipmentSuitTemplet> equipmentSuitTemplets = new HashMap<Integer, EquipmentSuitTemplet>();

	public void init() throws Exception {
		long t = System.currentTimeMillis();
		this.path = TextProperties.getText("DESIGN_FILE_PATH");
		// this.path = "D:\\WORKSPACE\\SVN\\Game2014\\Design\\数值表\\";
		readEquipmentDebrisData();
		readEquipmentGradePaySheetData();
		readEquipmentBasePropertyData();
		readEquipmentSuitData();
		readEquipmentRefineData();
		readEquipmentUpgradeData();

		DataCheckService.addListener(this);
		ProtoBuffFileMakeService.addMaker(this);
		EquipmentTempletManager.self = this;
		System.out.println("[系统初始化] [完成] [耗时：" + (System.currentTimeMillis() - t) + "ms] [" + this.getClass() + "]");
	}

	public static EquipmentTempletManager getInstance() {
		return EquipmentTempletManager.self;
	}

	private void readEquipmentGradePaySheetData() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentConstants.EQUIPMENTUPGRADEPAY);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);

		int sheetIndex = 0;
		while (true) {
			Sheet sheet = null;
			try {
				sheet = workbook.getSheetAt(sheetIndex);
			} catch (Exception e) {

			}
			if (sheet == null) {
				return;
			}
			String sheetName = sheet.getSheetName();
			String[] ss = sheetName.split("_");
			int upgradeType = Integer.parseInt(ss[0]);
			Map<Integer, EquipmentUpGradePayTemplet> mudts = new HashMap<Integer, EquipmentUpGradePayTemplet>();
			this.equipmentGradePaySheetTemples.put(upgradeType, mudts);

			int index = 1;
			while (true) {
				Row row = sheet.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				EquipmentUpGradePayTemplet mudt = new EquipmentUpGradePayTemplet();
				mudt.setLevel(Tools.getCellValue(row.getCell(0), Integer.class));
				mudt.setPay(Tools.getCellValue(row.getCell(1), Integer.class));
				mudt.setPrice(Tools.getCellValue(row.getCell(2), Integer.class));
				mudts.put(mudt.getLevel(), mudt);
				index++;
			}
			sheetIndex++;
		}
	}

	private void readEquipmentBasePropertyData() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentConstants.EQUIPMENTBASEPROPERTY);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentBasePropertyTemplet edt = new EquipmentBasePropertyTemplet();
			edt.setEqipment_id(Tools.getCellValue(row.getCell(0), Long.class));
			edt.setEquipment_name(Tools.getCellValue(row.getCell(1), String.class));
			edt.setStartNum(Tools.getCellValue(row.getCell(2), Byte.class));
			edt.setQuality(Tools.getCellValue(row.getCell(3), Byte.class));
			edt.setPart(Tools.getCellValue(row.getCell(4), Byte.class));
			edt.setSuit_id(Tools.getCellValue(row.getCell(5), Integer.class));
			edt.setIs_xilian(Tools.getCellValue(row.getCell(6), Byte.class) == 1);
			edt.setIs_thaw(Tools.getCellValue(row.getCell(7), Byte.class) == 1);
			edt.setIs_sale(Tools.getCellValue(row.getCell(8), Byte.class) == 1);
			edt.setUpgradPayId(Tools.getCellValue(row.getCell(9), Integer.class));
			edt.setXilian_id(Tools.getCellValue(row.getCell(10), Integer.class));
			String[] debrisStr = Tools.getCellValue(row.getCell(11), String.class).split("&");
			long[][] debirs = new long[debrisStr.length][2];
			for (int i = 0; i < debrisStr.length; i++) {
				String[] temp = debrisStr[i].split("_");
				debirs[i][0] = Long.parseLong(temp[0]);
				debirs[i][1] = Long.parseLong(temp[1]);
			}
			edt.setDebirs(debirs);
			edt.setThaw_stone_num(Tools.getCellValue(row.getCell(8), Integer.class));
			edt.setDesc(Tools.getCellValue(row.getCell(9), String.class));
			equipmentBasePropertyTemplets.put(edt.getEqipment_id(), edt);
			index++;
		}
	}

	private void readEquipmentSuitData() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentConstants.EQUIPMENTSUIT);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentSuitTemplet edt = new EquipmentSuitTemplet();
			edt.setSuit_id(Tools.getCellValue(row.getCell(0), Integer.class));
			edt.setNum(Tools.getCellValue(row.getCell(1), Integer.class));
			edt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(2).getNumericCellValue())));
			edt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(3).getNumericCellValue())));
			edt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(4).getNumericCellValue())));
			edt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(5).getNumericCellValue())));
			edt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(6).getNumericCellValue())));
			edt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(7).getNumericCellValue())));
			edt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(8).getNumericCellValue())));
			edt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(9).getNumericCellValue())));
			equipmentSuitTemplets.put(edt.getSuit_id(), edt);
			index++;
		}
	}

	private void readEquipmentDebrisData() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentConstants.EQUIPMENTDEBRIS);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentdDebrisTemplet edt = new EquipmentdDebrisTemplet();
			edt.setDebris_id(Tools.getCellValue(row.getCell(0), Long.class));
			edt.setEquipment_id(Tools.getCellValue(row.getCell(1), Long.class));
			edt.setPrice(Tools.getCellValue(row.getCell(2), Integer.class));
			edt.setDesc(Tools.getCellValue(row.getCell(3), String.class));
			equipmentDebrisTemplets.put(edt.getDebris_id(), edt);
			index++;
		}
	}

	private void readEquipmentRefineData() throws Exception {
		FileInputStream fis = new FileInputStream(path + EquipmentConstants.EQUIPMENTXILIAN);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		int sheetIndex = 0;
		while (true) {
			Sheet sheet = null;
			try {
				sheet = workbook.getSheetAt(sheetIndex);
			} catch (Exception e) {

			}
			if (sheet == null) {
				return;
			}
			String sheetName = sheet.getSheetName();
			String[] ss = sheetName.split("_");
			int upgradeType = Integer.parseInt(ss[0]);
			Map<Integer, EquipmentRefineTemplete> mudts = new HashMap<Integer, EquipmentRefineTemplete>();
			this.equipmentRefineTemplets.put(upgradeType, mudts);

			int index = 1;
			while (true) {
				Row row = sheet.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				EquipmentRefineTemplete mudt = Tools.fillByExcelRow(row, EquipmentRefineTemplete.class);
				mudts.put(mudt.getLevel(), mudt);
				index++;
			}
			sheetIndex++;
		}
	}

	/**
	 * 读取侠客强化和进化数据模板
	 * 
	 * @throws IOException
	 */
	private void readEquipmentUpgradeData() throws IOException {
		File dir = new File(this.path + EquipmentConstants.EQUIPMENTUPGRADES);
		File[] fils = dir.listFiles();
		for (File file : fils) {
			if (!file.getName().contains("_")) {
				continue;
			}
			String info[] = file.getName().split("_");
			long equipmentId = Long.parseLong(info[0]);
			Map<Integer, EquipmentUpGradeTemplet> mls = new HashMap<Integer, EquipmentUpGradeTemplet>();
			this.equipmentUpGradeTemplets.put(equipmentId, mls);

			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			Sheet sheetLevel = workbook.getSheetAt(0);
			int index = 1;
			while (true) {
				Row row = sheetLevel.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				EquipmentUpGradeTemplet ml = new EquipmentUpGradeTemplet();
				ml.setLevel(Tools.getCellValue(row.getCell(0), Integer.class));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(1).getNumericCellValue())));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(2).getNumericCellValue())));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(3).getNumericCellValue())));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(4).getNumericCellValue())));

				mls.put(ml.getLevel(), ml);
				index++;

			}
		}
	}

	public static void main(String[] args) {
		EquipmentTempletManager etm = new EquipmentTempletManager();
		try {
			etm.init();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void dataCheck() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeFile() throws Exception {
		List<BaseClientInfo> bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Long, EquipmentBasePropertyTemplet>> set = this.equipmentBasePropertyTemplets.entrySet();
		for (Entry<Long, EquipmentBasePropertyTemplet> en : set) {
			EquipmentBasePropertyTemplet ebpt = en.getValue();

			EquipmentClientInfo.Builder eb = EquipmentClientInfo.newBuilder();
			eb.setPartId(ebpt.getPart());
			eb.setSuitId(ebpt.getSuit_id());
			eb.setIsXiLian(ebpt.isIs_xilian());
			eb.setIsThaw(ebpt.isIs_thaw());
			eb.setIsSale(ebpt.isIs_sale());
			eb.setUpgradPayId(ebpt.getUpgradPayId());
			eb.setXilianId(ebpt.getXilian_id());
			eb.setMakeNumsNeed(ebpt.getDebirs()[0][0] + "_" + ebpt.getDebirs()[0][1]);
			eb.setPriceMoneyDebris(0);
			eb.setThawStoneNum(ebpt.getThaw_stone_num());

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId((int) ebpt.getEqipment_id());
			bcib.setName(ebpt.getEquipment_name());
			bcib.setDesc(ebpt.getDesc());
			bcib.setQuality(ebpt.getStartNum());
			bcib.setLevelBase(ebpt.getQuality());
			bcib.setEci(eb);

			bcis.add(bcib.build());
		}

		byte[][] datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "equip.db", datas);

		bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Long, EquipmentdDebrisTemplet>> edSet = this.equipmentDebrisTemplets.entrySet();
		for (Entry<Long, EquipmentdDebrisTemplet> en : edSet) {
			EquipmentdDebrisTemplet edt = en.getValue();

			EquipmentDebrisClientInfo.Builder edci = EquipmentDebrisClientInfo.newBuilder();
			edci.setPrice(edt.getPrice());

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId((int) edt.getDebris_id());
			bcib.setName(edt.getDesc());
			bcib.setEdci(edci);

			bcis.add(bcib.build());
		}
		datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "equip-suipian.db", datas);
	}

	@Override
	public String getFilePath() {
		return "D:\\Projects\\client\\Dev\\GamePrj_KL\\Assets\\StreamingAssets\\db\\";
	}

	public EquipmentBasePropertyTemplet getEquipment(long baseId) {
		return equipmentBasePropertyTemplets.get(baseId);
	}

	/**
	 * 
	 * @param equipmentId
	 * @param level
	 * @return
	 */
	public EquipmentUpGradeTemplet getLevelData(long equipmentId, int level) {
		Map<Integer, EquipmentUpGradeTemplet> euts = this.equipmentUpGradeTemplets.get(equipmentId);
		if (euts == null) {
			Tools.printlnWarn("[装备等级数据不存在] [装备ID：" + equipmentId + "]", logger);
			return null;
		}
		return euts.get(level);
	}

	public EquipmentBasePropertyTemplet getEquipmentBasePropertyTemplet(long id) {
		return this.equipmentBasePropertyTemplets.get(id);
	}

	public EquipmentRefineTemplete getEquipmentRefineTemplete(int refineType, int level) {
		return this.equipmentRefineTemplets.get(refineType).get(level);
	}

	public EquipmentUpGradePayTemplet getEquipmentUpGradePayTemplet(int type, int level) {
		Map<Integer, EquipmentUpGradePayTemplet> map = equipmentGradePaySheetTemples.get(type);
		return map.get(level);
	}
	
	public EquipmentdDebrisTemplet getEquipmentDebirsTemplet(long baseId)
	{
		return equipmentDebrisTemplets.get(baseId);
	}
}
