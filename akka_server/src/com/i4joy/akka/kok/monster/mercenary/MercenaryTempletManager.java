/**
 * 
 */
package com.i4joy.akka.kok.monster.mercenary;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.util.TempFile;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.common.collect.Iterables;
import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.DataCheckListener;
import com.i4joy.akka.kok.monster.DataCheckService;
import com.i4joy.akka.kok.monster.ProtoBuffFileMakeService;
import com.i4joy.akka.kok.monster.ProtoBuffFileMaker;
import com.i4joy.akka.kok.monster.ThingsType;
import com.i4joy.akka.kok.monster.item.IdNumPair;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryBuffTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryFriendlyDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryGhostTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryGiftTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryLevelDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMeridiansTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMinorPropertyTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryModelCorrespondTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryPredestineAssociationTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryPredestineTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryQualityDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenarySkillTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTalentAssociationTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTalentTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryUpgradeDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.TeamTemplet;
import com.i4joy.akka.kok.monster.other.vip.VipGiftTemplet;
import com.i4joy.akka.kok.protobufs.res.KOKRes.BaseClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.BuffClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.GiftClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.MercenaryClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.PredestineClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.SkillClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.TalentClientInfo;
import com.i4joy.util.Tools;

/**
 * 侠客模板管理器
 * 
 * @author Administrator
 * 
 */
public class MercenaryTempletManager implements ProtoBuffFileMaker, DataCheckListener {

	static MercenaryTempletManager self;

	private String path;

	Log logger = LogFactory.getLog(getClass());

	/**
	 * 侠客模板
	 */
	private Map<Long, MercenaryTemplet> mercenaryTemplets = new Hashtable<Long, MercenaryTemplet>();

	/**
	 * 侠客等级数据模板
	 */
	private Map<Long, Map<Integer, MercenaryLevelDataTemplet>> levelDataTemplets = new Hashtable<Long, Map<Integer, MercenaryLevelDataTemplet>>();

	/**
	 * 侠客进化数据模板
	 */
	private Map<Long, Map<Integer, MercenaryQualityDataTemplet>> qualityDataTemplets = new Hashtable<Long, Map<Integer, MercenaryQualityDataTemplet>>();

	/**
	 * 次要属性模板
	 */
	private Map<Integer, MercenaryMinorPropertyTemplet> minorPropertyTemplets = new HashMap<Integer, MercenaryMinorPropertyTemplet>();

	/**
	 * 升级信息模板
	 */
	private Map<Integer, Map<Integer, MercenaryUpgradeDataTemplet>> upgradeDataTemplets = new HashMap<Integer, Map<Integer, MercenaryUpgradeDataTemplet>>();

	private Map<Long, MercenaryModelCorrespondTemplet> modelCorrespondTemplets = new HashMap<Long, MercenaryModelCorrespondTemplet>();

	/**
	 * 情谊数据模板
	 */
	private Map<Integer, Map<Integer, MercenaryFriendlyDataTemplet>> friendlyDataTemplets = new HashMap<Integer, Map<Integer, MercenaryFriendlyDataTemplet>>();

	/**
	 * BUFF数据模板
	 */
	private Map<Integer, MercenaryBuffTemplet> buffTemplets = new HashMap<Integer, MercenaryBuffTemplet>();

	/**
	 * 技能数据模板
	 */
	private Map<Integer, MercenarySkillTemplet> skillTemplets = new HashMap<Integer, MercenarySkillTemplet>();

	/**
	 * 天赋数据模板
	 */
	private Map<Integer, MercenaryTalentTemplet> talentTemplets = new HashMap<Integer, MercenaryTalentTemplet>();

	/**
	 * 天赋侠客关系数据模板
	 */
	private Map<Long, MercenaryTalentAssociationTemplet> talentAssociationTemplets = new HashMap<Long, MercenaryTalentAssociationTemplet>();

	/**
	 * 配缘数据模板
	 */
	private Map<Integer, MercenaryPredestineTemplet> predestineTemplets = new HashMap<Integer, MercenaryPredestineTemplet>();

	/**
	 * 侠客与配缘对应关系模板
	 */
	private Map<Long, MercenaryPredestineAssociationTemplet> predestineAssociationTemplets = new HashMap<Long, MercenaryPredestineAssociationTemplet>();

	/**
	 * 经脉模板
	 */
	private Map<Integer, MercenaryMeridiansTemplet> meridiansTemplets = new HashMap<Integer, MercenaryMeridiansTemplet>();

	/**
	 * 侠客礼品模板
	 */
	private Map<Long, MercenaryGiftTemplet> giftTemplets = new HashMap<Long, MercenaryGiftTemplet>();

	/**
	 * 侠灵模板
	 */
	private Map<Long, MercenaryGhostTemplet> ghostTemplets = new HashMap<Long, MercenaryGhostTemplet>();

	/**
	 * 阵型等级
	 */
	private List<TeamTemplet> teamTemplets = new ArrayList<TeamTemplet>();

	/**
	 * 
	 */
	public MercenaryTempletManager() {

	}

	public void init() throws Exception {
		long t = System.currentTimeMillis();
		this.path = TextProperties.getText("DESIGN_FILE_PATH");
		this.readMercenaryUpgradeData();
		this.readMinorPropertyData();
		this.readUpgradeData();
		this.readMercenaryData();
		this.readMercenaryModelCorrespond();
		this.readMercenaryFriendlyData();
		this.readBuffData();
		this.readSkillData();
		this.readTalent();
		this.readTalentAssociationData();
		this.readPredestineData();
		this.readPredestineAssociationData();
		this.readMeridiansData();
		this.readTeam();
		this.readGiftData();
		this.readGhostData();

		DataCheckService.addListener(this);
		ProtoBuffFileMakeService.addMaker(this);
		MercenaryTempletManager.self = this;
		System.out.println("[系统初始化] [完成] [耗时：" + (System.currentTimeMillis() - t) + "ms] [" + this.getClass() + "]");
	}

	public static MercenaryTempletManager getInstance() {
		return MercenaryTempletManager.self;
	}

	public void readTeam() throws Exception {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.TEAM_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			TeamTemplet edt = new TeamTemplet();
			edt = Tools.fillByExcelRow(row, TeamTemplet.class);
			teamTemplets.add(edt);

			index++;
		}
	}

	/**
	 * 读取侠客强化和进化数据模板
	 * 
	 * @throws IOException
	 */
	private void readMercenaryUpgradeData() throws IOException {
		File dir = new File(this.path + MercenaryConstants.LEVEL_FILE_PATH);
		File[] fils = dir.listFiles();
		for (File file : fils) {
			if (!file.getName().contains("_")) {
				continue;
			}
			String info[] = file.getName().split("_");
			long mercenaryId = Long.parseLong(info[0]);
			Map<Integer, MercenaryLevelDataTemplet> mls = new HashMap<Integer, MercenaryLevelDataTemplet>();
			this.levelDataTemplets.put(mercenaryId, mls);
			Map<Integer, MercenaryQualityDataTemplet> mqs = new HashMap<Integer, MercenaryQualityDataTemplet>();
			this.qualityDataTemplets.put(mercenaryId, mqs);

			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			Sheet sheetLevel = workbook.getSheetAt(0);
			Sheet sheetQuality = workbook.getSheetAt(1);
			int index = 1;
			while (true) {
				Row row = sheetLevel.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				MercenaryLevelDataTemplet ml = new MercenaryLevelDataTemplet();
				ml.setLevel((int) (row.getCell(0).getNumericCellValue()));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(1).getNumericCellValue())));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(2).getNumericCellValue())));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(3).getNumericCellValue())));
				ml.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(4).getNumericCellValue())));

				mls.put(ml.getLevel(), ml);
				index++;

			}
			index = 1;
			while (true) {
				Row row = sheetQuality.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				MercenaryQualityDataTemplet mq = new MercenaryQualityDataTemplet();
				mq.setLevel((int) (row.getCell(0).getNumericCellValue()));
				mq.setFee((int) (row.getCell(1).getNumericCellValue()));
				String materials = row.getCell(2).getStringCellValue();
				String[] ss = materials.split("&");
				for (String s : ss) {
					String[] material = s.split("_");
					mq.addMaterial(new IdNumPair(Long.parseLong(material[0]), Integer.parseInt(material[1])));
				}
				mq.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(3).getNumericCellValue())));
				mq.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(4).getNumericCellValue())));
				mq.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(5).getNumericCellValue())));
				mq.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(6).getNumericCellValue())));

				mqs.put(mq.getLevel(), mq);
				index++;
			}
		}
	}

	/**
	 * 读取侠客次要属性数据
	 * 
	 * @throws IOException
	 */
	private void readMinorPropertyData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.MINOR_PROPERTY_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryMinorPropertyTemplet mmpt = new MercenaryMinorPropertyTemplet();
			mmpt.setId((int) (row.getCell(0).getNumericCellValue()));
			mmpt.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.CRITICAL, (int) (row.getCell(1).getNumericCellValue())));
			mmpt.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.RESILIENCE, (int) (row.getCell(2).getNumericCellValue())));
			mmpt.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HIT_RATING, (int) (row.getCell(3).getNumericCellValue())));
			mmpt.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.DODGE, (int) (row.getCell(4).getNumericCellValue())));
			mmpt.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.BLOCK, (int) (row.getCell(5).getNumericCellValue())));
			mmpt.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.PENETRATE, (int) (row.getCell(6).getNumericCellValue())));

			this.minorPropertyTemplets.put(mmpt.getId(), mmpt);

			index++;
		}
	}

	/**
	 * 读取侠客模板数据
	 * 
	 * @throws IOException
	 */
	private void readMercenaryData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.MERCENARY_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryTemplet mt = new MercenaryTemplet();
			mt.setId((long) (row.getCell(0).getNumericCellValue()));
			mt.setName(row.getCell(1).getStringCellValue());
			mt.setStar((int) (row.getCell(2).getNumericCellValue()));
			mt.setQuality((int) (row.getCell(3).getNumericCellValue()));
			mt.setMercenaryType((int) (row.getCell(4).getNumericCellValue()));
			mt.setFriendshipId((int) (row.getCell(5).getNumericCellValue()));
			mt.setCamp((int) (row.getCell(6).getNumericCellValue()));
			mt.setFaction(row.getCell(7).getStringCellValue());
			mt.setSex((int) (row.getCell(8).getNumericCellValue()));
			mt.setJadePrice((int) (row.getCell(9).getNumericCellValue()));
			mt.setJadeFromSacrifice((int) (row.getCell(10).getNumericCellValue()));
			String s = Tools.getCellValue(row.getCell(11), String.class);
			String[] ss = s.split("_");
			mt.setComposeNeededGhosts(new IdNumPair(Long.parseLong(ss[0]), Integer.parseInt(ss[1])));
			mt.setNormalAbilityId((int) (row.getCell(12).getNumericCellValue()));
			mt.setRageAbilityId((int) (row.getCell(13).getNumericCellValue()));
			mt.setIsSoar((byte) (row.getCell(14).getNumericCellValue()));
			mt.setIsSacrifice((byte) (row.getCell(15).getNumericCellValue()));
			if (row.getCell(16).getCellType() == Cell.CELL_TYPE_NUMERIC) {
				mt.setIcon("" + (int) (row.getCell(16).getNumericCellValue()));
			} else {
				mt.setIcon(row.getCell(16).getStringCellValue());
			}
			mt.setUpgradeType((int) (row.getCell(17).getNumericCellValue()));
			int minorProp = (int) (row.getCell(18).getNumericCellValue());
			mt.setMinorPropType(minorProp);
			mt.setDesc(row.getCell(19).getStringCellValue());

			// 读取初始等级属性数据
			Map<Integer, MercenaryLevelDataTemplet> mldts = this.levelDataTemplets.get(mt.getId());
			MercenaryLevelDataTemplet mldt = mldts.get(1);
			mt.getPropsValue().putAll(mldt.getPropsValue());
			// 读取次要属性数据
			MercenaryMinorPropertyTemplet mmpt = this.minorPropertyTemplets.get(minorProp);
			mt.getPropsValue().putAll(mmpt.getPropsValue());

			this.mercenaryTemplets.put(mt.getId(), mt);
			index++;
		}
	}

	/**
	 * 升级数据读取
	 * 
	 * @throws IOException
	 */
	private void readUpgradeData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.UPGRADE_FILE_PATH);
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
			Map<Integer, MercenaryUpgradeDataTemplet> mudts = new HashMap<Integer, MercenaryUpgradeDataTemplet>();
			this.upgradeDataTemplets.put(upgradeType, mudts);

			int index = 1;
			while (true) {
				Row row = sheet.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				MercenaryUpgradeDataTemplet mudt = new MercenaryUpgradeDataTemplet();
				mudt.setLevel((int) (row.getCell(0).getNumericCellValue()));
				mudt.setFee((int) (row.getCell(1).getNumericCellValue()));
				mudt.setExpNeeded((int) (row.getCell(2).getNumericCellValue()));
				mudt.setExpFromStrengthen((int) (row.getCell(3).getNumericCellValue()));
				mudt.setMoneyFromSacrifice((int) (row.getCell(4).getNumericCellValue()));
				mudt.setExpFromSacrifice((int) (row.getCell(5).getNumericCellValue()));

				mudts.put(mudt.getLevel(), mudt);

				index++;
			}

			sheetIndex++;
		}

	}

	private void readMercenaryModelCorrespond() throws Exception {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.MODEL_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			// MercenaryModelCorrespondTemplet mmct = new
			// MercenaryModelCorrespondTemplet();
			// mmct.setMercenaryId((long)
			// (row.getCell(0).getNumericCellValue()));
			// mmct.setSex(row.getCell(1).getStringCellValue().equals("m") ?
			// (byte) 1 : (byte) 0);
			// mmct.setSize((byte) (row.getCell(2).getNumericCellValue()));
			// mmct.setBodyTexture((int)
			// (row.getCell(3).getNumericCellValue()));
			// mmct.setHeadId((int) (row.getCell(4).getNumericCellValue()));
			// mmct.setHeadTexture((int)
			// (row.getCell(5).getNumericCellValue()));
			// mmct.setFaceId((int) (row.getCell(6).getNumericCellValue()));
			// mmct.setFaceTexture((int)
			// (row.getCell(7).getNumericCellValue()));
			// mmct.setClothesId((int) (row.getCell(8).getNumericCellValue()));
			// mmct.setClothesTexture((int)
			// (row.getCell(9).getNumericCellValue()));
			// mmct.setWeaponId((int) (row.getCell(10).getNumericCellValue()));
			// mmct.setWeaponTexture((int)
			// (row.getCell(11).getNumericCellValue()));
			// mmct.setAniIdle((int) (row.getCell(12).getNumericCellValue()));
			// mmct.setAniIdleAtt((int)
			// (row.getCell(13).getNumericCellValue()));
			// mmct.setAniIdleAttack1((int)
			// (row.getCell(14).getNumericCellValue()));
			// mmct.setAniIdleAttack2((int)
			// (row.getCell(15).getNumericCellValue()));
			// mmct.setAniDamage((int) (row.getCell(16).getNumericCellValue()));
			// mmct.setAniDie((int) (row.getCell(17).getNumericCellValue()));
			// mmct.setAniWin((int) (row.getCell(18).getNumericCellValue()));
			// mmct.setAniLose((int) (row.getCell(19).getNumericCellValue()));
			// mmct.setAniContend((int)
			// (row.getCell(20).getNumericCellValue()));
			// mmct.setAniParry((int) (row.getCell(21).getNumericCellValue()));
			// mmct.setAniReady((int) (row.getCell(22).getNumericCellValue()));

			MercenaryModelCorrespondTemplet mmct = Tools.fillByExcelRow(row, MercenaryModelCorrespondTemplet.class);

			this.modelCorrespondTemplets.put(mmct.getMercenaryId(), mmct);
			index++;
		}
	}

	/**
	 * 情谊数据读取
	 * 
	 * @throws IOException
	 */
	private void readMercenaryFriendlyData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.FRIENDLY_FILE_PATH);
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
			int friendshipId = Integer.parseInt(sheetName);
			Map<Integer, MercenaryFriendlyDataTemplet> mfdts = new HashMap<Integer, MercenaryFriendlyDataTemplet>();
			this.friendlyDataTemplets.put(friendshipId, mfdts);

			int index = 1;
			while (true) {
				Row row = sheet.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}

				MercenaryFriendlyDataTemplet mfdt = new MercenaryFriendlyDataTemplet();
				mfdt.setLevel((int) (row.getCell(0).getNumericCellValue()));
				mfdt.setExpNeeded((int) (row.getCell(1).getNumericCellValue()));
				mfdt.setCriticalPercent((int) (row.getCell(2).getNumericCellValue()));
				mfdt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(3).getNumericCellValue())));
				mfdt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(4).getNumericCellValue())));
				mfdt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(5).getNumericCellValue())));
				mfdt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(6).getNumericCellValue())));
				mfdt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(7).getNumericCellValue())));
				mfdt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(8).getNumericCellValue())));
				mfdt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(9).getNumericCellValue())));
				mfdt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(10).getNumericCellValue())));

				mfdts.put(mfdt.getLevel(), mfdt);

				index++;
			}

			sheetIndex++;
		}
	}

	private void readBuffData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.BUFF_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryBuffTemplet mbt = new MercenaryBuffTemplet();
			mbt.setId((int) (row.getCell(0).getNumericCellValue()));
			mbt.setDesc(row.getCell(1).getStringCellValue());
			mbt.setBuffType((int) (row.getCell(2).getNumericCellValue()));
			mbt.setValue((float) (row.getCell(3).getNumericCellValue()));
			mbt.setEffect((int) (row.getCell(4).getNumericCellValue()));

			this.buffTemplets.put(mbt.getId(), mbt);

			index++;
		}
	}

	private void readSkillData() throws Exception {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.SKILL_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
//			MercenarySkillTemplet mst = new MercenarySkillTemplet();
//			mst.setId((int) (row.getCell(0).getNumericCellValue()));
//			mst.setName(row.getCell(1).getStringCellValue());
//			mst.setSkillType((int) (row.getCell(2).getNumericCellValue()));
//			mst.setDamageType((int) (row.getCell(3).getNumericCellValue()));
//			mst.setRageCost((int) (row.getCell(4).getNumericCellValue()));
//			mst.setDesc(Tools.getCellValue(row.getCell(5), String.class));
//			mst.setMinDamagePercent((int) (row.getCell(6).getNumericCellValue()));
//			mst.setMaxDamagePercent((int) (row.getCell(7).getNumericCellValue()));
//			mst.setTargetType((int) (row.getCell(8).getNumericCellValue()));
//			mst.setTargetNum((int) (row.getCell(9).getNumericCellValue()));
//			mst.setBuffInfo(Tools.getCellValue(row.getCell(10), String.class));
//			mst.setInfo(Tools.getCellValue(row.getCell(11), String.class));
			
			MercenarySkillTemplet mst = Tools.fillByExcelRow(row, MercenarySkillTemplet.class);

			this.skillTemplets.put(mst.getId(), mst);

			index++;
		}
	}

	/**
	 * 读取天赋
	 * 
	 * @throws IOException
	 */
	private void readTalent() throws Exception {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.TALENT_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryTalentTemplet mt = Tools.fillByExcelRow(row, MercenaryTalentTemplet.class);

			this.talentTemplets.put(mt.getTalentId(), mt);

			index++;
		}
	}

	/**
	 * 天赋侠客对应表
	 * 
	 * @throws IOException
	 */
	private void readTalentAssociationData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.TALENT_ASSOCIATION_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryTalentAssociationTemplet mtat = new MercenaryTalentAssociationTemplet();
			mtat.setMercenaryId((long) (row.getCell(0).getNumericCellValue()));
			String s = row.getCell(1).getStringCellValue();
			String[] ss = s.split("_");
			mtat.addAssociation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));

			s = row.getCell(2).getStringCellValue();
			ss = s.split("_");
			mtat.addAssociation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));

			s = row.getCell(3).getStringCellValue();
			ss = s.split("_");
			mtat.addAssociation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));

			s = row.getCell(4).getStringCellValue();
			ss = s.split("_");
			mtat.addAssociation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));

			s = row.getCell(5).getStringCellValue();
			ss = s.split("_");
			mtat.addAssociation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));

			s = row.getCell(6).getStringCellValue();
			ss = s.split("_");
			mtat.addAssociation(Integer.parseInt(ss[0]), Integer.parseInt(ss[1]));

			this.talentAssociationTemplets.put(mtat.getMercenaryId(), mtat);

			index++;
		}
	}

	/**
	 * 读取配缘数据
	 * 
	 * @throws IOException
	 */
	private void readPredestineData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.PREDESTINE_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}

			MercenaryPredestineTemplet mpt = new MercenaryPredestineTemplet();
			mpt.setId((int) (row.getCell(0).getNumericCellValue()));
			mpt.setName(row.getCell(1).getStringCellValue());
			String s = Tools.getCellValue(row.getCell(2), String.class);
			String[] ss = s.split("_");
			for (String id : ss) {
				mpt.addElement(Long.parseLong(id));
			}
			
			String propsString=Tools.getCellValue(row.getCell(3), String.class);
			mpt.setPropsString(propsString);
			ss = propsString.split("&");
			float[][] props=new float[ss.length][3];
			for (int i=0;i<ss.length;i++) {
				if (ss[i].length() > 0) {
					String[] ps=ss[i].split("_");
					props[i][0]=Float.parseFloat(ps[0]);
					props[i][1]=Float.parseFloat(ps[1]);
					props[i][2]=Float.parseFloat(ps[2]);
				}
			}
			mpt.setProps(props);

			mpt.setDesc(row.getCell(4).getStringCellValue());

			this.predestineTemplets.put(mpt.getId(), mpt);

			index++;
		}
	}

	/**
	 * 读取侠客配缘对应关系数据
	 * 
	 * @throws IOException
	 */
	private void readPredestineAssociationData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.PREDESTINE_ASSOCIATION_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryPredestineAssociationTemplet mpat = new MercenaryPredestineAssociationTemplet();
			mpat.setMercenaryId((long) (row.getCell(0).getNumericCellValue()));
			mpat.addPredestine((int) (row.getCell(1).getNumericCellValue()));
			mpat.addPredestine((int) (row.getCell(2).getNumericCellValue()));
			mpat.addPredestine((int) (row.getCell(3).getNumericCellValue()));
			mpat.addPredestine((int) (row.getCell(4).getNumericCellValue()));
			mpat.addPredestine((int) (row.getCell(5).getNumericCellValue()));
			mpat.addPredestine((int) (row.getCell(6).getNumericCellValue()));

			this.predestineAssociationTemplets.put(mpat.getMercenaryId(), mpat);

			index++;
		}
	}

	/**
	 * 读取经脉模板
	 * 
	 * @throws IOException
	 */
	private void readMeridiansData() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.MERIDIANS_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}

			MercenaryMeridiansTemplet mmt = new MercenaryMeridiansTemplet();
			mmt.setLevel((int) (row.getCell(0).getNumericCellValue()));
			mmt.setName(Tools.getCellValue(row.getCell(1), String.class));
			mmt.setStarsNeeded((int) (row.getCell(2).getNumericCellValue()));
			mmt.setFee((int) (row.getCell(3).getNumericCellValue()));
			mmt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(4).getNumericCellValue())));
			mmt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(5).getNumericCellValue())));
			mmt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(6).getNumericCellValue())));
			mmt.addCurrentLevelProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(7).getNumericCellValue())));
			mmt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, (int) (row.getCell(8).getNumericCellValue())));
			mmt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, (int) (row.getCell(9).getNumericCellValue())));
			mmt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, (int) (row.getCell(10).getNumericCellValue())));
			mmt.addTotalProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, (int) (row.getCell(11).getNumericCellValue())));

			this.meridiansTemplets.put(mmt.getLevel(), mmt);

			index++;
		}
	}

	public MercenaryUpgradeDataTemplet getUpgradeDataTemplet(MercenaryTemplet templet, int level) {
		if (templet == null)
			return null;
		Map<Integer, MercenaryUpgradeDataTemplet> map = upgradeDataTemplets.get(templet.getUpgradeType());
		if (map == null)
			return null;
		return map.get(level);
	}

	public MercenaryTemplet getMercenaryTemplet(long baseId) {
		return mercenaryTemplets.get(baseId);
	}

	public MercenaryUpgradeDataTemplet getUpgradeDataTemplet(int type, int level) {
		return upgradeDataTemplets.get(type).get(level);
	}

	public TeamTemplet getTeamTemplet(int level) {
		for (int i = 0; i < teamTemplets.size(); i++) {
			if (teamTemplets.get(i).getLevel() > level) {
				return teamTemplets.get(i - 1);
			}
		}
		return null;
	}

	private void readGiftData() throws Exception {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.GIFT_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}

			MercenaryGiftTemplet mgt = Tools.fillByExcelRow(row, MercenaryGiftTemplet.class);
			this.giftTemplets.put(mgt.getId(), mgt);

			index++;
		}
	}

	private void readGhostData() throws Exception {
		FileInputStream fis = new FileInputStream(path + MercenaryConstants.GHOST_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}

			MercenaryGhostTemplet mgt = Tools.fillByExcelRow(row, MercenaryGhostTemplet.class);
			if (!this.mercenaryTemplets.containsKey(mgt.getMercenaryId())) {
				throw new Exception("[侠灵数据错误] [侠灵ID：" + mgt.getId() + "] [侠客ID：" + mgt.getMercenaryId() + "]");
			}
			this.ghostTemplets.put(mgt.getId(), mgt);

			index++;
		}
	}

	public static void main(String[] args) {

	}

	@Override
	public void makeFile() throws Exception {
		List<BaseClientInfo> bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Long, MercenaryGiftTemplet>> giftSet = this.giftTemplets.entrySet();
		for (Entry<Long, MercenaryGiftTemplet> en : giftSet) {
			MercenaryGiftTemplet mgt = en.getValue();
			GiftClientInfo.Builder giftBuilder = GiftClientInfo.newBuilder();
			giftBuilder.setFriendlyValue(mgt.getFriendlyValue());
			giftBuilder.setPrice(0);

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId((int) mgt.getId());
			bcib.setName(mgt.getName());
			bcib.setDesc(mgt.getDesc());
			bcib.setQuality(mgt.getStars());
			bcib.setGci(giftBuilder);

			bcis.add(bcib.build());
		}
		byte[][] datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "item-lipin.db", datas);

		// TODO 数据不全，临时注释掉
		// bcis=new ArrayList<BaseClientInfo>();
		// Set<Entry<Long,MercenaryTemplet>> mercenarySet =
		// this.mercenaryTemplets.entrySet();
		// for(Entry<Long,MercenaryTemplet> en:mercenarySet){
		// MercenaryTemplet mt=en.getValue();
		// MercenaryClientInfo.Builder
		// mercenaryBuilder=MercenaryClientInfo.newBuilder();
		// mercenaryBuilder.setCamp(mt.getCamp());
		// mercenaryBuilder.setMakeNumsNeed(mt.getComposeNeededGhosts().getId()+"_"+mt.getComposeNeededGhosts().getNum());
		// mercenaryBuilder.setUpgradeType(mt.getUpgradeType());
		// mercenaryBuilder.setNormalAbilityId((int)mt.getNormalAbilityId());
		// mercenaryBuilder.setRageAbilityId((int)mt.getRageAbilityId());
		//
		// MercenaryTalentAssociationTemplet
		// mtat=this.talentAssociationTemplets.get(mt.getId());
		// Integer[] talents=mtat.getTalentActivated().values().toArray(new
		// Integer[0]);
		// for(Integer talent:talents){
		// mercenaryBuilder.addTalentIds(talent);
		// }
		//
		// Integer[] levels=mtat.getTalentActivated().keySet().toArray(new
		// Integer[0]);
		// for(Integer level:levels){
		// mercenaryBuilder.addTalentLevels(level);
		// }
		//
		// MercenaryPredestineAssociationTemplet
		// mpat=this.predestineAssociationTemplets.get(mt.getId());
		// Integer[] predestines=mpat.getPredestines().toArray(new Integer[0]);
		// for(Integer predestine:predestines){
		// mercenaryBuilder.addPredestineIds(predestine);
		// }
		//
		// MercenaryModelCorrespondTemplet
		// mmct=this.modelCorrespondTemplets.get(mt.getId());
		// mercenaryBuilder.addFbxID(mmct.getHeadId());
		// mercenaryBuilder.addFbxID(mmct.getFaceId());
		// mercenaryBuilder.addFbxID(mmct.getClothesId());
		// mercenaryBuilder.addFbxID(mmct.getWeaponId());
		// mercenaryBuilder.addFbxTexID(mmct.getHeadTexture());
		// mercenaryBuilder.addFbxTexID(mmct.getFaceTexture());
		// mercenaryBuilder.addFbxTexID(mmct.getClothesTexture());
		// mercenaryBuilder.addFbxTexID(mmct.getWeaponId());
		// mercenaryBuilder.addAnimationID(mmct.getAniIdle());
		// mercenaryBuilder.addAnimationID(mmct.getAniIdleAtt());
		// mercenaryBuilder.addAnimationID(mmct.getAniIdleAttack1());
		// mercenaryBuilder.addAnimationID(mmct.getAniIdleAttack2());
		// mercenaryBuilder.addAnimationID(mmct.getAniDamage());
		// mercenaryBuilder.addAnimationID(mmct.getAniDie());
		// mercenaryBuilder.addAnimationID(mmct.getAniWin());
		// mercenaryBuilder.addAnimationID(mmct.getAniLose());
		// mercenaryBuilder.addAnimationID(mmct.getAniContend());
		// mercenaryBuilder.addAnimationID(mmct.getAniParry());
		// mercenaryBuilder.addAnimationID(mmct.getAniReady());
		// String[] ss=mmct.getKeyFrame1().split(";");
		// for(String s:ss){
		// mercenaryBuilder.addFrameAtk1ID(Integer.parseInt(s));
		// }
		// ss=mmct.getKeyFrame2().split(";");
		// for(String s:ss){
		// mercenaryBuilder.addFrameAtk2ID(Integer.parseInt(s));
		// }
		// mercenaryBuilder.setSex(mt.getSex());
		// mercenaryBuilder.setSize(mmct.getSize());
		// mercenaryBuilder.setBodyTexID(mmct.getBodyTexture());
		// mercenaryBuilder.setJadeFromSacrifice(mt.getJadeFromSacrifice());
		//
		//
		// BaseClientInfo.Builder bcib=BaseClientInfo.newBuilder();
		// bcib.setId((int)mt.getId());
		// bcib.setName(mt.getName());
		// bcib.setDesc(mt.getDesc());
		// bcib.setQuality(mt.getStar());
		// bcib.setLevelBase(mt.getQuality());
		// bcib.setMci(mercenaryBuilder);
		//
		// bcis.add(bcib.build());
		// }
		// datas=new byte[bcis.size()][];
		// for(int i=0;i<datas.length;i++){
		// datas[i]=bcis.get(i).toByteArray();
		// }
		// Tools.makeFile(this.getFilePath()+"xiake.db", datas);

		bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Integer, MercenaryBuffTemplet>> buffSet = this.buffTemplets.entrySet();
		for (Entry<Integer, MercenaryBuffTemplet> en : buffSet) {
			MercenaryBuffTemplet mbt = en.getValue();

			BuffClientInfo.Builder buffBuilder = BuffClientInfo.newBuilder();
			buffBuilder.setEffectId(mbt.getEffect());

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId(mbt.getId());
			bcib.setName(mbt.getDesc());
			bcib.setBci(buffBuilder);

			bcis.add(bcib.build());
		}

		datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "buff.db", datas);

		bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Integer, MercenarySkillTemplet>> skillSet = this.skillTemplets.entrySet();
		for (Entry<Integer, MercenarySkillTemplet> en : skillSet) {
			MercenarySkillTemplet mst = en.getValue();

			SkillClientInfo.Builder skillBuilder = SkillClientInfo.newBuilder();
			skillBuilder.setDamageType(mst.getDamageType());

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId(mst.getId());
			bcib.setName(mst.getName());
			bcib.setDesc(mst.getDesc());
			bcib.setSci(skillBuilder);

			bcis.add(bcib.build());
		}
		datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "skill.db", datas);

		bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Integer, MercenaryTalentTemplet>> talentSet = this.talentTemplets.entrySet();
		for (Entry<Integer, MercenaryTalentTemplet> en : talentSet) {
			MercenaryTalentTemplet mtt = en.getValue();

			TalentClientInfo.Builder tcib = TalentClientInfo.newBuilder();
			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId(mtt.getTalentId());
			bcib.setName(mtt.getTalentName());
			bcib.setDesc(mtt.getDescribe());
			bcib.setTci(tcib);

			bcis.add(bcib.build());
		}
		datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "tianfu.db", datas);

		bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Integer, MercenaryPredestineTemplet>> pSet = this.predestineTemplets.entrySet();
		for (Entry<Integer, MercenaryPredestineTemplet> en : pSet) {
			MercenaryPredestineTemplet mpt = en.getValue();

			PredestineClientInfo.Builder pb = PredestineClientInfo.newBuilder();
			pb.setPropsString(mpt.getPropsString());

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId(mpt.getId());
			bcib.setName(mpt.getName());
			bcib.setDesc(mpt.getDesc());
			bcib.setPci(pb);

			bcis.add(bcib.build());
		}
		datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "peiyuan.db", datas);

	}

	@Override
	public String getFilePath() {
		return "D:\\Projects\\client\\Dev\\GamePrj_KL\\Assets\\StreamingAssets\\db\\";
	}

	@Override
	public void dataCheck() throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * 获取强化等级属性
	 * 
	 * @param mercenaryId
	 * @param level
	 */
	public MercenaryLevelDataTemplet getLevelData(long mercenaryId, int level) {
		Map<Integer, MercenaryLevelDataTemplet> mlds = this.levelDataTemplets.get(mercenaryId);
		if (mlds == null) {
			Tools.printlnWarn("[强化等级属性不存在] [侠客ID：" + mercenaryId + "]", logger);
			return null;
		}
		return mlds.get(level);
	}

	/**
	 * 获取进化等级属性
	 * 
	 * @param mercenaryId
	 * @param quality
	 * @return
	 */
	public MercenaryQualityDataTemplet getQualityData(long mercenaryId, int quality) {
		Map<Integer, MercenaryQualityDataTemplet> mqds = this.qualityDataTemplets.get(mercenaryId);
		if (mqds == null) {
			Tools.printlnWarn("[进化等级属性不存在] [侠客ID：" + mercenaryId + "]", logger);
			return null;
		}
		return mqds.get(quality);
	}

	/**
	 * 获取情义等级数据
	 * 
	 * @param type
	 * @param friendlyLevel
	 * @return
	 */
	public MercenaryFriendlyDataTemplet getFriendlyData(int type, int friendlyLevel) {
		Map<Integer, MercenaryFriendlyDataTemplet> mfdts=this.friendlyDataTemplets.get(type);
		if(mfdts!=null){
			return mfdts.get(friendlyLevel);
		}
		return null;
	}

	/**
	 * 获取经脉等级数据
	 * 
	 * @param meridiansLevel
	 * @return
	 */
	public MercenaryMeridiansTemplet getMeridiansData(int meridiansLevel) {
		return this.meridiansTemplets.get(meridiansLevel);
	}

	public MercenaryBuffTemplet getBuff(int buffId) {
		return this.buffTemplets.get(buffId);
	}

	/**
	 * 根据进阶等级，获取所有开启的天赋
	 * 
	 * @param mercenaryId
	 * @param quality
	 * @return
	 */
	public List<MercenaryTalentTemplet> getMercenaryTalentTemplets(long mercenaryId, int quality) {
		List<MercenaryTalentTemplet> mtts = new ArrayList<MercenaryTalentTemplet>();
		MercenaryTalentAssociationTemplet mtat = this.talentAssociationTemplets.get(mercenaryId);
		for (int i = 0; i <= quality; i++) {
			Integer tid = mtat.getTalentActivated().get(i);
			if (tid != null) {
				mtts.add(this.talentTemplets.get(tid));
			}
		}

		return mtts;
	}

	public MercenaryPredestineAssociationTemplet getMercenaryPredestineAssociationTemplet(long mercenaryId) {
		return this.predestineAssociationTemplets.get(mercenaryId);
	}

	/**
	 * 获取所有配缘
	 * 
	 * @param mercenaryId
	 * @return
	 */
	public List<MercenaryPredestineTemplet> getMercenaryPredestineTemplet(long mercenaryId) {
		List<MercenaryPredestineTemplet> mpts = new ArrayList<MercenaryPredestineTemplet>();
		MercenaryPredestineAssociationTemplet mpat = this.getMercenaryPredestineAssociationTemplet(mercenaryId);
		for (int id : mpat.getPredestines()) {
			MercenaryPredestineTemplet mpt = this.predestineTemplets.get(id);
			if (mpt != null) {
				mpts.add(mpt);
			}
		}
		return mpts;
	}

	/**
	 * 根据阵容、装备、宝物信息，获取当前激活的配缘
	 * 
	 * @param mpat
	 * @param equipmentIds
	 * @param amuletIds
	 * @param mercenaryTempletIds
	 * @return
	 */
	public List<MercenaryPredestineTemplet> getActiveMercenaryPredestineTemplet(long mercenaryTempletId, long[] equipmentIds, long[] amuletIds, long[] mercenaryTempletIds) {
		List<MercenaryPredestineTemplet> mpts = new ArrayList<MercenaryPredestineTemplet>();
		MercenaryPredestineAssociationTemplet mpat = this.getMercenaryPredestineAssociationTemplet(mercenaryTempletId);
		for (int pid : mpat.getPredestines()) {
			MercenaryPredestineTemplet mpt = this.predestineTemplets.get(pid);
			ThingsType tt = mpt.getType();
			if (tt == ThingsType.EQUIPMENT) {
				if (mpt.isActive(equipmentIds)) {
					mpts.add(mpt);
				}
			} else if (tt == ThingsType.AMULET) {
				if (mpt.isActive(amuletIds)) {
					mpts.add(mpt);
				}
			} else if (tt == ThingsType.MERCENARY) {
				if (mpt.isActive(mercenaryTempletIds)) {
					mpts.add(mpt);
				}
			}
		}
		return mpts;
	}
	
	public MercenaryMinorPropertyTemplet getMercenaryMinorPropertyTemplet(int type){
		return this.minorPropertyTemplets.get(type);
	}
	
	public MercenaryGiftTemplet getMercenaryGiftTemplet(long baseId)
	{
		return giftTemplets.get(baseId);
	}

	public MercenaryGhostTemplet getMercenaryGhostTemplet(long baseId) {
		return ghostTemplets.get(baseId);
	}
	
	public List<MercenaryQualityDataTemplet> getLessThenQualityDataList(long mercenaryId, int quality) {
		List<MercenaryQualityDataTemplet> list = new ArrayList<MercenaryQualityDataTemplet>();
		Map<Integer, MercenaryQualityDataTemplet> mqds = this.qualityDataTemplets.get(mercenaryId);
		MercenaryQualityDataTemplet[] mqdts = new MercenaryQualityDataTemplet[mqds.size()];
		mqds.values().toArray(mqdts);
		for (MercenaryQualityDataTemplet mercenaryQualityDataTemplet : mqdts) {
			if(mercenaryQualityDataTemplet.getLevel() < quality)
			{
				list.add(mercenaryQualityDataTemplet);
			}
		}
		return list;
	}
	
	public MercenarySkillTemplet getMercenarySkillTemplet(int id){
		return this.skillTemplets.get(id);
	}

}
