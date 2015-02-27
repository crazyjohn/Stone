package com.i4joy.akka.kok.monster.amulet;

import java.io.File;
import java.io.FileInputStream;
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
import com.i4joy.akka.kok.monster.amulet.templet.AmuletBasePropertyTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletDebirsTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletRefineTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradePayTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradeTemplet;
import com.i4joy.akka.kok.protobufs.res.KOKRes.AmuletClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.AmuletDebrisClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.BaseClientInfo;
import com.i4joy.util.Tools;

public class AmuletTempletManager implements ProtoBuffFileMaker, DataCheckListener {

	static AmuletTempletManager self;

	private String path;

	Log logger = LogFactory.getLog(getClass());
	private Map<Long, AmuletDebirsTemplet> amuletDebrisTemplets = new HashMap<Long, AmuletDebirsTemplet>();
	private Map<Integer, Map<Integer, AmuletUpGradePayTemplet>> amuletUpGradePayTemplets = new HashMap<Integer, Map<Integer, AmuletUpGradePayTemplet>>();
	private Map<Long, Map<Integer, AmuletUpGradeTemplet>> amuletUpGradeTemplets = new HashMap<Long, Map<Integer, AmuletUpGradeTemplet>>();
	private Map<Long, Map<Integer, AmuletRefineTemplet>> amuletRefineTemplets = new HashMap<Long, Map<Integer, AmuletRefineTemplet>>();
	private Map<Long, AmuletBasePropertyTemplet> amuletBasePropertyTemplets = new HashMap<Long, AmuletBasePropertyTemplet>();

	public void init() throws Exception {
		long t = System.currentTimeMillis();
		this.path = TextProperties.getText("DESIGN_FILE_PATH");
		// this.path = "D:\\WORKSPACE\\SVN\\Game2014\\Design\\数值表\\";
		readAmuletDebrisData();
		readAmuletUpgradeData();
		readAmuletUpGradePay();
		readAmuletBasePropertyData();

		DataCheckService.addListener(this);
		ProtoBuffFileMakeService.addMaker(this);

		AmuletTempletManager.self = this;
		System.out.println("[系统初始化] [完成] [耗时：" + (System.currentTimeMillis() - t) + "ms] [" + this.getClass() + "]");
	}

	public static AmuletTempletManager getInstance() {
		return AmuletTempletManager.self;
	}

	public static void main(String[] args) {
		AmuletTempletManager etm = new AmuletTempletManager();
		try {
			etm.init();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readAmuletBasePropertyData() throws Exception {
		FileInputStream fis = new FileInputStream(path + AmuletConstants.AMULETBASEPROPERTY);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			AmuletBasePropertyTemplet edt = new AmuletBasePropertyTemplet();
			edt = Tools.fillByExcelRow(row, AmuletBasePropertyTemplet.class);
			amuletBasePropertyTemplets.put(edt.getId(), edt);
			index++;
		}
	}

	private void readAmuletDebrisData() throws Exception {
		FileInputStream fis = new FileInputStream(path + AmuletConstants.AMULETDEBRIS);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			AmuletDebirsTemplet edt = new AmuletDebirsTemplet();
			edt = Tools.fillByExcelRow(row, AmuletDebirsTemplet.class);
			amuletDebrisTemplets.put(edt.getId(), edt);
			index++;
		}
	}

	private void readAmuletUpgradeData() throws Exception {
		File dir = new File(this.path + AmuletConstants.AMULETUPGRADES);
		File[] fils = dir.listFiles();
		for (File file : fils) {
			if (!file.getName().contains("_")) {
				continue;
			}
			String info[] = file.getName().split("_");
			long equipmentId = Long.parseLong(info[0]);
			Map<Integer, AmuletUpGradeTemplet> mls = new HashMap<Integer, AmuletUpGradeTemplet>();
			this.amuletUpGradeTemplets.put(equipmentId, mls);

			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			Sheet sheetLevel = workbook.getSheetAt(0);
			int index = 1;
			while (true) {
				Row row = sheetLevel.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				AmuletUpGradeTemplet ml = new AmuletUpGradeTemplet();
				ml = Tools.fillByExcelRow(row, AmuletUpGradeTemplet.class);
				mls.put(ml.getLevel(), ml);
				index++;

			}

			Map<Integer, AmuletRefineTemplet> ars = new HashMap<Integer, AmuletRefineTemplet>();
			this.amuletRefineTemplets.put(equipmentId, ars);

			fis = new FileInputStream(file);
			workbook = new XSSFWorkbook(fis);
			sheetLevel = workbook.getSheetAt(1);
			index = 1;
			while (true) {
				Row row = sheetLevel.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				AmuletRefineTemplet ml = new AmuletRefineTemplet();
				ml = Tools.fillByExcelRow(row, AmuletRefineTemplet.class);
				ars.put(ml.getLevel(), ml);
				index++;

			}
		}

	}

	private void readAmuletUpGradePay() throws Exception {
		FileInputStream fis = new FileInputStream(path + AmuletConstants.AMULETUPGRADEPAY);
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
			Map<Integer, AmuletUpGradePayTemplet> mudts = new HashMap<Integer, AmuletUpGradePayTemplet>();
			this.amuletUpGradePayTemplets.put(upgradeType, mudts);

			int index = 1;
			while (true) {
				Row row = sheet.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				AmuletUpGradePayTemplet mudt = Tools.fillByExcelRow(row, AmuletUpGradePayTemplet.class);
				mudts.put(mudt.getLevel(), mudt);
				index++;
			}
			sheetIndex++;
		}
	}

	@Override
	public void dataCheck() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeFile() throws Exception {
		List<BaseClientInfo> bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Long, AmuletBasePropertyTemplet>> set = this.amuletBasePropertyTemplets.entrySet();
		for (Entry<Long, AmuletBasePropertyTemplet> en : set) {
			AmuletBasePropertyTemplet abpt = en.getValue();

			AmuletClientInfo.Builder ab = AmuletClientInfo.newBuilder();
			ab.setType(abpt.getType());
			ab.setRefine(abpt.isRefine());
			//TODO 客户端需要修改数据模板类
//			long[][] unlock = abpt.getUnlock();
			StringBuffer sb = new StringBuffer();
//			for (long[] ls : unlock) {
//				sb.append(ls[0]);
//				sb.append("_");
//				sb.append(ls[1]);
//				sb.append("&");
//			}
//			ab.setBuffList(sb.toString());
			ab.setConsumables(abpt.isConsumables());
			ab.setUpGradePayId(abpt.getUpGradePayId());

			long[][] debris = abpt.getDebris_ids();
			sb = new StringBuffer();
			for (long[] ls : debris) {
				sb.append(ls[0]);
				sb.append("_");
				sb.append(ls[1]);
				sb.append("&");
			}
			ab.setMakeItemNeed(sb.toString());
			ab.setUpGrade(abpt.isUpGrade());
			ab.setDecomposing(abpt.isDecomposing());

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId((int) abpt.getId());
			bcib.setName(abpt.getName());
			bcib.setDesc(abpt.getDesc());
			bcib.setQuality(abpt.getStart());
			bcib.setLevelBase(abpt.getQuality());
			bcib.setAci(ab);

			bcis.add(bcib.build());
		}
		byte[][] datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "baowu.db", datas);

		bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Long, AmuletDebirsTemplet>> dSet = this.amuletDebrisTemplets.entrySet();
		for (Entry<Long, AmuletDebirsTemplet> en : dSet) {
			AmuletDebirsTemplet adt = en.getValue();

			AmuletDebrisClientInfo.Builder adci = AmuletDebrisClientInfo.newBuilder();
			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId((int) adt.getId());
			bcib.setName(adt.getName());
			bcib.setDesc(adt.getDesc());
			bcib.setQuality(adt.getQuality());
			bcib.setAdci(adci);

			bcis.add(bcib.build());
		}
		datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "baowu-suipian.db", datas);
	}

	@Override
	public String getFilePath() {
		return "D:\\Projects\\client\\Dev\\GamePrj_KL\\Assets\\StreamingAssets\\db\\";
	}

	public AmuletBasePropertyTemplet getAmuletBasePropertyTemplet(long id) {
		return this.amuletBasePropertyTemplets.get(id);
	}

	public AmuletUpGradeTemplet getAmuletUpGradeTemplet(long id, int level) {
		return this.amuletUpGradeTemplets.get(id).get(level);
	}

	public AmuletUpGradePayTemplet getAmuletUpGradePayTemplet(long id, int level) {
		return this.amuletUpGradePayTemplets.get((int) id).get(level);
	}

	/**
	 * 获取精炼等级数据
	 * 
	 * @param id
	 * @param level
	 * @return
	 */
	public AmuletRefineTemplet getAmuletRefineTemplet(long id, int level) {
		Map<Integer, AmuletRefineTemplet> arts = this.amuletRefineTemplets.get(id);
		if (arts != null) {
			return arts.get(level);
		}
		return null;
	}

	public AmuletDebirsTemplet getAmuletDebirsTemplet(long baseId) {
		return amuletDebrisTemplets.get(baseId);
	}

}
