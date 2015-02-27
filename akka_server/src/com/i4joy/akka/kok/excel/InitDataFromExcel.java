package com.i4joy.akka.kok.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryModelCorrespondTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTalentTemplet;
import com.i4joy.util.Tools;

public class InitDataFromExcel implements Serializable {
	private boolean isTest = true;
	private static InitDataFromExcel instance;
	public static String path = "D:\\WORKSPACE\\SVN\\Game2014\\Design\\数值表\\";

	public static InitDataFromExcel getInstance() {
		if (instance == null) {
			instance = new InitDataFromExcel();
		}
		return instance;
	}

	private ArrayList<MercenaryGift> mercenaryGiftList = new ArrayList<MercenaryGift>();
	private ArrayList<MercenaryCompose> mercenaryComposeList = new ArrayList<MercenaryCompose>();
	private ArrayList<MercenarySoul> mercenarySoulList = new ArrayList<MercenarySoul>();
	private ArrayList<PveEnemyNum> pveEnemyNumList = new ArrayList<PveEnemyNum>();
	private ArrayList<PveGuanQia> pveGuanQiaList = new ArrayList<PveGuanQia>();
	private ArrayList<PveChapter> pveChapterList = new ArrayList<PveChapter>();
	private ArrayList<PveZhenXing> pveZhenXingList = new ArrayList<PveZhenXing>();
	private ArrayList<PvpRewards> pvpRewardslist = new ArrayList<PvpRewards>();
	private ArrayList<PvpBaoWuQiangDuo> pvpBaoWuQiangDuoList = new ArrayList<PvpBaoWuQiangDuo>();
	private ArrayList<AmuletHeCheng> rewardHeChengList = new ArrayList<AmuletHeCheng>();
	private ArrayList<AmuletBaseProperty> rewardBasePropertyList = new ArrayList<AmuletBaseProperty>();
	private ArrayList<AmuletSkill> rewardSkillList = new ArrayList<AmuletSkill>();
	private ArrayList<VipGiftTemplet> vipGiftList = new ArrayList<VipGiftTemplet>();
	private ArrayList<VipShop> vipShopList = new ArrayList<VipShop>();
	private ArrayList<VipPrivilege> vipPrivilegeList = new ArrayList<VipPrivilege>();
	private ArrayList<VipPrivilegeExplain> vipPrivilegeExplainList = new ArrayList<VipPrivilegeExplain>();
	private ArrayList<BoxReward> boxRewardList = new ArrayList<BoxReward>();
	private ArrayList<Item> toolsList = new ArrayList<Item>();
	private ArrayList<ItemAndData> toolsAndDataList = new ArrayList<ItemAndData>();
	private ArrayList<OtherGrow> otherGrowList = new ArrayList<OtherGrow>();
	private ArrayList<OtherOpenServer> otherOpenServerList = new ArrayList<OtherOpenServer>();
	private ArrayList<OtherCheckReward> otherCheckRewardList = new ArrayList<OtherCheckReward>();
	private ArrayList<OtherUpgrade> otherUpgradeList = new ArrayList<OtherUpgrade>();
	private ArrayList<OtherFirstPay> otherFirstPayList = new ArrayList<OtherFirstPay>();
	private ArrayList<OtherOnlineTime> otherOnlineTimeList = new ArrayList<OtherOnlineTime>();
	private ArrayList<OtherShop> otherShopList = new ArrayList<OtherShop>();
	private ArrayList<OtherMercenary> otherMercenaryList = new ArrayList<OtherMercenary>();
	private ArrayList<EquipmentQuality> equipmentQualityList = new ArrayList<EquipmentQuality>();
	private ArrayList<EquipmentHeCheng> equipmentHeChengList = new ArrayList<EquipmentHeCheng>();
	private ArrayList<EquipmentBaseQuality> equipmentBaseQualityList = new ArrayList<EquipmentBaseQuality>();
	private ArrayList<EquipmentIntroduce> equipmentIntroduceList = new ArrayList<EquipmentIntroduce>();
	private ArrayList<BattleDialog> battleDialogList = new ArrayList<BattleDialog>();
	private ArrayList<Dialog> dialogList = new ArrayList<Dialog>();
	private HashMap<Short, Amulet> amuletHM = new HashMap<Short, Amulet>();
	private HashMap<Short, Equipment> equipmentHM = new HashMap<Short, Equipment>();

	public void init(String path) {
		try {
			InitDataFromExcel.path = path;
//			readMercenaryBaseProperty();
//			readMercenarySkillBuffer();
//			readMercenarySkill();
			readMercenaryGift();
//			readMercenaryFellingUp();
			readMercenaryCompose();
			readMercenarySoul();
//			readMercenaryTalent();
//			readMercenaryTalentCorrespond();
//			readMercenaryPeiYuan();
//			readMercenaryPeiYuanCorrespond();
//			readMercenaryModel();
//			readMercenaryModelCorrespond();
//			readMercenaryTianMing();
			readPveEnemyNum();
			readPveGuanQia();
			readPveChapter();
			readPveZhenXing();
			readPvpRewards();
			readPvpBaoWuQiangDuo();
			readRewardHeCheng();
			readRewardBaseProperty();
			readRewardSkill();
			readVipGift();
			readVipShop();
			readVipPrivilege();
			readVipPrivilegeExplain();
			readBoxReward();
			readTools();
			readToolsAndData();
			readOtherGrow();
			readOtherOpenServer();
			readOtherCheckReward();
			readOtherUpgrade();
			readOtherFirstPay();
			readOtherOnlineTime();
			readOtherShop();
			readOtherMercenary();
			readEquipmentQuality();
			readEquipmentHeCheng();
			readEquipmentBaseQuality();
			readEquipmentIntroduce();
			readBattleDialog();
			readDialog();
//			readMercenaryHM();
			readAmuletHM();
			readEquipmentHM();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		InitDataFromExcel idfe = InitDataFromExcel.getInstance();
		idfe.init("D:\\WORKSPACE\\SVN\\Game2014\\Design\\数值表\\");
		System.out.println(Tools.serialize(idfe).length);
		// File dir = new
		// File("D:\\WORKSPACE\\SVN\\Game2014\\Design\\数值表\\宝物系统\\宝物升级数值表");
		// File[] fils = dir.listFiles();
		// for (File file : fils) {
		// if (!file.isHidden()) {
		// String name = file.getName();
		// if (name.contains("_")) {
		// String[] header = name.split("[.]");
		// String[] names = header[0].split("_");
		// file.renameTo(new File(dir.getPath() + "\\" + names[1] + "_" +
		// names[0] + ".xlsx"));
		// System.out.println(file.getName());
		// }
		// }
		//
		// }
	}

	public void readEquipmentHM() throws IOException {
		File dir = new File(path + Equipment.Path);
		File[] fils = dir.listFiles();
		for (File file : fils) {
			if (file.getName().contains("_")) {
				String info[] = file.getName().split("_");
				Equipment equipment = new Equipment();
				equipment.setId(Short.parseShort(info[0]));
				equipmentHM.put(equipment.getId(), equipment);
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
					EquipmentLevel el = new EquipmentLevel();
					el.setLevel((byte) (row.getCell(0).getNumericCellValue()));
					el.setPay_money((int) (row.getCell(1).getNumericCellValue()));
					el.setMoney((int) (row.getCell(2).getNumericCellValue()));
					el.setStone((int) (row.getCell(3).getNumericCellValue()));
					el.setHp((int) (row.getCell(4).getNumericCellValue()));
					el.setAtk((int) (row.getCell(5).getNumericCellValue()));
					el.setW_def((int) (row.getCell(6).getNumericCellValue()));
					el.setN_def((int) (row.getCell(7).getNumericCellValue()));
					el.setDouble_hit((int) (row.getCell(8).getNumericCellValue()));
					el.setRenxing((int) (row.getCell(9).getNumericCellValue()));
					el.setHit((int) (row.getCell(10).getNumericCellValue()));
					el.setMiss((int) (row.getCell(11).getNumericCellValue()));
					el.setGedang((int) (row.getCell(12).getNumericCellValue()));
					el.setChuanchi((int) (row.getCell(13).getNumericCellValue()));
					el.setHp_limit((int) (row.getCell(14).getNumericCellValue()));
					el.setAtk_limit((int) (row.getCell(15).getNumericCellValue()));
					el.setW_def_limit((int) (row.getCell(16).getNumericCellValue()));
					el.setN_def_limit((int) (row.getCell(17).getNumericCellValue()));
					el.setDouble_hit_limit((int) (row.getCell(18).getNumericCellValue()));
					el.setRenxing_limit((int) (row.getCell(19).getNumericCellValue()));
					el.setHit_limit((int) (row.getCell(20).getNumericCellValue()));
					el.setMiss_limit((int) (row.getCell(21).getNumericCellValue()));
					el.setGedang_limit((int) (row.getCell(22).getNumericCellValue()));
					el.setChuanchi_limit((int) (row.getCell(23).getNumericCellValue()));
					equipment.getLevelList().add(el);
					index++;
					if (isTest) {
						break;
					}
				}
				index = 1;
				while (true) {
					Row row = sheetQuality.getRow(index);
					if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
						break;
					}
					EQuality aq = new EQuality();
					aq.setPropertyName(row.getCell(0).getStringCellValue());
					aq.setInitValue((int) (row.getCell(1).getNumericCellValue()));
					aq.setNormalMin((int) (row.getCell(2).getNumericCellValue()));
					aq.setNormalMax((int) (row.getCell(3).getNumericCellValue()));
					aq.setMiddleMin((int) (row.getCell(4).getNumericCellValue()));
					aq.setMiddleMax((int) (row.getCell(5).getNumericCellValue()));
					aq.setBigMin((int) (row.getCell(6).getNumericCellValue()));
					aq.setBigMax((int) (row.getCell(7).getNumericCellValue()));
					equipment.getQualityList().add(aq);
					index++;
					if (isTest) {
						break;
					}
				}
			}
		}
	}

	public void readAmuletHM() throws IOException {
		File dir = new File(path + Amulet.Path);
		File[] fils = dir.listFiles();
		for (File file : fils) {
			if (file.getName().contains("_")) {

				try {
					String info[] = file.getName().split("_");
					Amulet amulet = new Amulet();
					amulet.setId(Short.parseShort(info[0]));
					amuletHM.put(amulet.getId(), amulet);
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
						AmuletLevel al = new AmuletLevel();
						al.setLevel((byte) (row.getCell(0).getNumericCellValue()));
						al.setPay_money((int) (row.getCell(1).getNumericCellValue()));
						al.setPay_exp((int) (row.getCell(2).getNumericCellValue()));
						al.setExp((int) (row.getCell(3).getNumericCellValue()));
						al.setHp((int) (row.getCell(4).getNumericCellValue()));
						al.setAtk((int) (row.getCell(5).getNumericCellValue()));
						al.setW_def((int) (row.getCell(6).getNumericCellValue()));
						al.setN_def((int) (row.getCell(7).getNumericCellValue()));
						al.setDouble_hit((int) (row.getCell(8).getNumericCellValue()));
						al.setRenxing((int) (row.getCell(9).getNumericCellValue()));
						al.setHit((int) (row.getCell(10).getNumericCellValue()));
						al.setMiss((int) (row.getCell(11).getNumericCellValue()));
						al.setGedang((int) (row.getCell(12).getNumericCellValue()));
						al.setChuanchi((int) (row.getCell(13).getNumericCellValue()));
						al.setHp_bili((float) (row.getCell(14).getNumericCellValue()));
						al.setAtk_bili((float) (row.getCell(15).getNumericCellValue()));
						al.setW_def_bili((float) (row.getCell(16).getNumericCellValue()));
						al.setN_def_bili((float) (row.getCell(17).getNumericCellValue()));
						al.setDouble_hit_bili((float) (row.getCell(18).getNumericCellValue()));
						al.setRenxing_bili((float) (row.getCell(19).getNumericCellValue()));
						al.setHit_bili((float) (row.getCell(20).getNumericCellValue()));
						al.setMiss_bili((float) (row.getCell(21).getNumericCellValue()));
						al.setGedang_bili((float) (row.getCell(22).getNumericCellValue()));
						al.setChuanchi_bili((float) (row.getCell(23).getNumericCellValue()));
						amulet.getLevelList().add(al);
						index++;
						if (isTest) {
							break;
						}
					}
					index = 1;
					while (true) {
						Row row = sheetQuality.getRow(index);
						if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
							break;
						}
						AmuletQuality aq = new AmuletQuality();
						aq.setLevel((byte) (row.getCell(0).getNumericCellValue()));
						aq.setPay_money((int) (row.getCell(1).getNumericCellValue()));
						aq.setPay_items(row.getCell(2).getStringCellValue());
						aq.setHp((int) (row.getCell(3).getNumericCellValue()));
						aq.setAtk((int) (row.getCell(4).getNumericCellValue()));
						aq.setW_def((int) (row.getCell(5).getNumericCellValue()));
						aq.setN_def((int) (row.getCell(6).getNumericCellValue()));
						aq.setDouble_hit((int) (row.getCell(7).getNumericCellValue()));
						aq.setRenxing((int) (row.getCell(8).getNumericCellValue()));
						aq.setHit((int) (row.getCell(9).getNumericCellValue()));
						aq.setMiss((int) (row.getCell(10).getNumericCellValue()));
						aq.setGedang((int) (row.getCell(11).getNumericCellValue()));
						aq.setChuanchi((int) (row.getCell(12).getNumericCellValue()));
						aq.setHp_bili((float) (row.getCell(13).getNumericCellValue()));
						aq.setAtk_bili((float) (row.getCell(14).getNumericCellValue()));
						aq.setW_def_bili((float) (row.getCell(15).getNumericCellValue()));
						aq.setN_def_bili((float) (row.getCell(16).getNumericCellValue()));
						aq.setDouble_hit_bili((float) (row.getCell(17).getNumericCellValue()));
						aq.setRenxing_bili((float) (row.getCell(18).getNumericCellValue()));
						aq.setHit_bili((float) (row.getCell(19).getNumericCellValue()));
						aq.setMiss_bili((float) (row.getCell(20).getNumericCellValue()));
						aq.setGedang_bili((float) (row.getCell(21).getNumericCellValue()));
						aq.setChuanchi_bili((float) (row.getCell(22).getNumericCellValue()));
						amulet.getAualityList().add(aq);
						index++;
						if (isTest) {
							break;
						}
					}

				} catch (Exception e) {
					e.printStackTrace();
					System.out.println(file.getName());
				}
			}
		}
	}

	

	public void readDialog() throws IOException {
		FileInputStream fis = new FileInputStream(path + Dialog.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			Dialog mbp = new Dialog();
			mbp.setDialogId((int) (row.getCell(0).getNumericCellValue()));
			mbp.setContext(row.getCell(1).getStringCellValue());
			dialogList.add(mbp);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readBattleDialog() throws IOException {
		FileInputStream fis = new FileInputStream(path + BattleDialog.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			BattleDialog mbp = new BattleDialog();
			mbp.setBattleId((int) (row.getCell(0).getNumericCellValue()));
			mbp.setBattleStartDialog(row.getCell(1).getStringCellValue());
			mbp.setBattleIngDialog(row.getCell(2).getStringCellValue());
			mbp.setBattleEndDialog(row.getCell(3).getStringCellValue());
			battleDialogList.add(mbp);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readMercenaryGift() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryGift.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryGift mg = new MercenaryGift();
			mg.setId((int) (row.getCell(0).getNumericCellValue()));
			mg.setName(row.getCell(1).getStringCellValue());
			mg.setStarLevel((int) (row.getCell(2).getNumericCellValue()));
			mg.setExperience((int) (row.getCell(3).getNumericCellValue()));
			mg.setShengJiProbability((int) (row.getCell(4).getNumericCellValue()));
			mercenaryGiftList.add(mg);
			index++;
			if (isTest) {
				break;
			}
		}

	}

	public void readMercenaryCompose() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenaryCompose.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenaryCompose mc = new MercenaryCompose();
			mc.setMercenaryId((int) (row.getCell(0).getNumericCellValue()));
			mc.setXiaLingId((int) (row.getCell(1).getNumericCellValue()));
			mc.setComposeNum((int) (row.getCell(2).getNumericCellValue()));
			mercenaryComposeList.add(mc);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readMercenarySoul() throws IOException {
		FileInputStream fis = new FileInputStream(path + MercenarySoul.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			MercenarySoul ms = new MercenarySoul();
			ms.setMercenarySoulId((int) (row.getCell(0).getNumericCellValue()));
			ms.setMercenarySoulName(row.getCell(1).getStringCellValue());
			ms.setSellPrice((int) (row.getCell(2).getNumericCellValue()));
			ms.setHunYuPrice((int) (row.getCell(3).getNumericCellValue()));
			mercenarySoulList.add(ms);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readPveEnemyNum() throws IOException {
		FileInputStream fis = new FileInputStream(path + PveEnemyNum.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			PveEnemyNum pen = new PveEnemyNum();
			pen.setEnemtyId((int) (row.getCell(0).getNumericCellValue()));
			pen.setMercenaryId((int) (row.getCell(1).getNumericCellValue()));
			pen.setMercenaryName(row.getCell(2).getStringCellValue());
			pen.setLevel((int) (row.getCell(3).getNumericCellValue()));
			pen.setJinJieLevel((int) (row.getCell(4).getNumericCellValue()));
			pen.setEquipId((int) (row.getCell(5).getNumericCellValue()));
			pen.setEquipQiangHuaLevel((int) (row.getCell(6).getNumericCellValue()));
			pen.setValuedId((int) (row.getCell(7).getNumericCellValue()));
			pen.setValuedQiangHuaLevel((int) (row.getCell(8).getNumericCellValue()));
			pen.setValuedJingLianLevel((int) (row.getCell(9).getNumericCellValue()));
			pen.setLife((int) (row.getCell(10).getNumericCellValue()));
			pen.setAttack((int) (row.getCell(11).getNumericCellValue()));
			pen.setOutside((int) (row.getCell(12).getNumericCellValue()));
			pen.setInside((int) (row.getCell(13).getNumericCellValue()));
			pen.setBaoJi((int) (row.getCell(14).getNumericCellValue()));
			pen.setRenXing((int) (row.getCell(15).getNumericCellValue()));
			pen.setMingZhong((int) (row.getCell(16).getNumericCellValue()));
			pen.setShanBi((int) (row.getCell(17).getNumericCellValue()));
			pen.setGeDang((int) (row.getCell(18).getNumericCellValue()));
			pen.setChuanCi((int) (row.getCell(19).getNumericCellValue()));
			pveEnemyNumList.add(pen);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readPveGuanQia() throws IOException {
		FileInputStream fis = new FileInputStream(path + PveGuanQia.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			PveGuanQia pgq = new PveGuanQia();
			pgq.setGuanQiaId((int) (row.getCell(0).getNumericCellValue()));
			pgq.setChapterId((int) (row.getCell(1).getNumericCellValue()));
			pgq.setTiaoZhanNum((int) (row.getCell(2).getNumericCellValue()));
			pgq.setGuanQiaHard((int) (row.getCell(3).getNumericCellValue()));
			pgq.setTongQianReward((int) (row.getCell(4).getNumericCellValue()));
			pgq.setExperienceReward((int) (row.getCell(5).getNumericCellValue()));
			pgq.setZhenXingId((int) (row.getCell(6).getNumericCellValue()));
			pgq.setSpecialJuQingId((int) (row.getCell(7).getNumericCellValue()));
			pgq.setToolsReward(row.getCell(8).getStringCellValue());
			pgq.setTianJiangBaoWu(row.getCell(9).getStringCellValue());
			pgq.setDescribe(row.getCell(10).getStringCellValue());
			pgq.setOnceReward(row.getCell(11).getStringCellValue());
			pveGuanQiaList.add(pgq);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readPveChapter() throws IOException {
		FileInputStream fis = new FileInputStream(path + PveChapter.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			PveChapter pc = new PveChapter();
			pc.setChapterId((int) (row.getCell(0).getNumericCellValue()));
			pc.setGuanQiaId((int) (row.getCell(1).getNumericCellValue()));
			pc.setChapterTitle(row.getCell(2).getStringCellValue());
			pc.setChapterDescribe(row.getCell(3).getStringCellValue());
			pveChapterList.add(pc);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readPveZhenXing() throws IOException {
		FileInputStream fis = new FileInputStream(path + PveZhenXing.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			PveZhenXing pzx = new PveZhenXing();
			pzx.setZhenXingId((int) (row.getCell(0).getNumericCellValue()));
			pzx.setPart_1((int) (row.getCell(1).getNumericCellValue()));
			pzx.setPart_2((int) (row.getCell(2).getNumericCellValue()));
			pzx.setPart_3((int) (row.getCell(3).getNumericCellValue()));
			pzx.setPart_4((int) (row.getCell(4).getNumericCellValue()));
			pzx.setPart_5((int) (row.getCell(5).getNumericCellValue()));
			pzx.setPart_6((int) (row.getCell(6).getNumericCellValue()));
			pzx.setPart_7((int) (row.getCell(7).getNumericCellValue()));
			pzx.setPart_8((int) (row.getCell(8).getNumericCellValue()));
			pveZhenXingList.add(pzx);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readPvpRewards() throws IOException {
		FileInputStream fis = new FileInputStream(path + PvpRewards.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			PvpRewards pr = new PvpRewards();
			pr.setRoleLevel((int) (row.getCell(0).getNumericCellValue()));
			pr.setToolsReward(row.getCell(1).getStringCellValue());
			pr.setGaiLv((int) (row.getCell(2).getNumericCellValue()));
			pvpRewardslist.add(pr);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readPvpBaoWuQiangDuo() throws IOException {
		FileInputStream fis = new FileInputStream(path + PvpBaoWuQiangDuo.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			PvpBaoWuQiangDuo pbq = new PvpBaoWuQiangDuo();
			pbq.setBaoWuId((int) (row.getCell(0).getNumericCellValue()));
			pbq.setBaoWuName(row.getCell(1).getStringCellValue());
			pbq.setGetBaoWu((int) (row.getCell(2).getNumericCellValue()));
			pvpBaoWuQiangDuoList.add(pbq);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readRewardHeCheng() throws IOException {
		FileInputStream fis = new FileInputStream(path + AmuletHeCheng.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			AmuletHeCheng rhc = new AmuletHeCheng();
			rhc.setRewardId((int) (row.getCell(0).getNumericCellValue()));
			rhc.setRewardPatch_1((int) (row.getCell(1).getNumericCellValue()));
			rhc.setRewardPatch_2((int) (row.getCell(2).getNumericCellValue()));
			rhc.setRewardPatch_3((int) (row.getCell(3).getNumericCellValue()));
			rhc.setRewardPatch_4((int) (row.getCell(4).getNumericCellValue()));
			rhc.setRewardPatch_5((int) (row.getCell(5).getNumericCellValue()));
			rhc.setRewardPatch_6((int) (row.getCell(6).getNumericCellValue()));
			rhc.setRewardName(row.getCell(7).getStringCellValue());
			rhc.setPatch_1Name(row.getCell(8).getStringCellValue());
			rhc.setPatch_2Name(row.getCell(9).getStringCellValue());
			rhc.setPatch_3Name(row.getCell(10).getStringCellValue());
			rhc.setPatch_4Name(row.getCell(11).getStringCellValue());
			rhc.setPatch_5Name(row.getCell(12).getStringCellValue());
			rhc.setPatch_6Name(row.getCell(13).getStringCellValue());
			rewardHeChengList.add(rhc);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readRewardBaseProperty() throws IOException {
		FileInputStream fis = new FileInputStream(path + AmuletBaseProperty.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			AmuletBaseProperty rbp = new AmuletBaseProperty();
			rbp.setRewardId((int) (row.getCell(0).getNumericCellValue()));
			rbp.setRewardName(row.getCell(1).getStringCellValue());
			rbp.setRewardType((int) (row.getCell(2).getNumericCellValue()));
			rbp.setStarLevel((int) (row.getCell(3).getNumericCellValue()));
			rbp.setPinLevel((int) (row.getCell(4).getNumericCellValue()));
			rbp.setIsJingLian((byte) (row.getCell(5).getNumericCellValue()));
			rbp.setDescribe(row.getCell(6).getStringCellValue());
			rewardBasePropertyList.add(rbp);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readRewardSkill() throws IOException {
		FileInputStream fis = new FileInputStream(path + AmuletSkill.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			AmuletSkill rs = new AmuletSkill();
			rs.setRewardSkillId((int) (row.getCell(0).getNumericCellValue()));
			rs.setRewardName(row.getCell(1).getStringCellValue());
			rs.setJieSuoLevel((int) (row.getCell(2).getNumericCellValue()));
			rs.setLife((int) (row.getCell(3).getNumericCellValue()));
			rs.setAttack((int) (row.getCell(4).getNumericCellValue()));
			rs.setOutside((int) (row.getCell(5).getNumericCellValue()));
			rs.setInside((int) (row.getCell(6).getNumericCellValue()));
			rs.setBaoJi((int) (row.getCell(7).getNumericCellValue()));
			rs.setRenXing((int) (row.getCell(8).getNumericCellValue()));
			rs.setMingZhong((int) (row.getCell(9).getNumericCellValue()));
			rs.setShanBi((int) (row.getCell(10).getNumericCellValue()));
			rs.setGeDang((int) (row.getCell(11).getNumericCellValue()));
			rs.setChuanCi((int) (row.getCell(12).getNumericCellValue()));
			rs.setDescribe(row.getCell(13).getStringCellValue());
			rewardSkillList.add(rs);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readVipGift() throws IOException {
		FileInputStream fis = new FileInputStream(path + VipGiftTemplet.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			VipGiftTemplet vg = new VipGiftTemplet();
			vg.setVipGiftId((int) (row.getCell(0).getNumericCellValue()));
			vg.setGiftName(row.getCell(1).getStringCellValue());
			vg.setPrimeCost((int) (row.getCell(2).getNumericCellValue()));
			vg.setNowPrice((int) (row.getCell(3).getNumericCellValue()));
			vg.setBuyNum(row.getCell(4).getStringCellValue());
			vg.setDescribe(row.getCell(5).getStringCellValue());
			vipGiftList.add(vg);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readVipShop() throws IOException {
		FileInputStream fis = new FileInputStream(path + VipShop.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			VipShop vs = new VipShop();
			vs.setVipLevel((int) (row.getCell(0).getNumericCellValue()));
			vs.setBuyNum(row.getCell(1).getStringCellValue());
			vipShopList.add(vs);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readVipPrivilege() throws IOException {
		FileInputStream fis = new FileInputStream(path + VipPrivilege.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			VipPrivilege vp = new VipPrivilege();
			vp.setVipLevel((int) (row.getCell(0).getNumericCellValue()));
			vp.setVipName(row.getCell(1).getStringCellValue());
			vp.setMoney((int) (row.getCell(2).getNumericCellValue()));
			vp.setYuanBao((int) (row.getCell(3).getNumericCellValue()));
			vp.setVipGiftId((int) (row.getCell(4).getNumericCellValue()));
			vp.setShopNum((int) (row.getCell(5).getNumericCellValue()));
			vp.setTiaoZhanNum((int) (row.getCell(6).getNumericCellValue()));
			vp.setLeast((int) (row.getCell(7).getNumericCellValue()));
			vp.setBiggest((int) (row.getCell(8).getNumericCellValue()));
			vp.setIsAttack((byte) (row.getCell(9).getNumericCellValue()));
			vp.setDescribe(row.getCell(10).getStringCellValue());
			vipPrivilegeList.add(vp);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readVipPrivilegeExplain() throws IOException {
		FileInputStream fis = new FileInputStream(path + VipPrivilegeExplain.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			VipPrivilegeExplain vpe = new VipPrivilegeExplain();
			vpe.setVipLevel(row.getCell(0).getStringCellValue());
			vpe.setMoney((int) (row.getCell(1).getNumericCellValue()));
			vpe.setVipPrivilege(row.getCell(2).getStringCellValue());
			vpe.setVipGift(row.getCell(3).getStringCellValue());
			vpe.setOldVipGift((int) (row.getCell(4).getNumericCellValue()));
			vpe.setNewVipGift((int) (row.getCell(5).getNumericCellValue()));
			vipPrivilegeExplainList.add(vpe);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readBoxReward() throws IOException {
		FileInputStream fis = new FileInputStream(path + BoxReward.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			BoxReward br = new BoxReward();
			br.setToolsId((int) (row.getCell(0).getNumericCellValue()));
			br.setBoxID((int) (row.getCell(1).getNumericCellValue()));
			br.setRewardType((int) (row.getCell(2).getNumericCellValue()));
			br.setReward(row.getCell(3).getStringCellValue());
			boxRewardList.add(br);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readTools() throws IOException {
		FileInputStream fis = new FileInputStream(path + Item.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			Item tools = new Item();
			tools.setToolsId((int) (row.getCell(0).getNumericCellValue()));
			tools.setToolsName(row.getCell(1).getStringCellValue());
			tools.setToolsType((int) (row.getCell(2).getNumericCellValue()));
			tools.setUseType((int) (row.getCell(3).getNumericCellValue()));
			tools.setSellPrice((int) (row.getCell(4).getNumericCellValue()));
			tools.setToolsDescribe(row.getCell(5).getStringCellValue());
			toolsList.add(tools);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readToolsAndData() throws IOException {
		FileInputStream fis = new FileInputStream(path + ItemAndData.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			ItemAndData tad = new ItemAndData();
			tad.setToolsType((int) (row.getCell(0).getNumericCellValue()));
			tad.setDataName(row.getCell(1).getStringCellValue());
			toolsAndDataList.add(tad);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherGrow() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherGrow.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherGrow og = new OtherGrow();
			og.setLevel((int) (row.getCell(0).getNumericCellValue()));
			og.setReward(row.getCell(1).getStringCellValue());
			otherGrowList.add(og);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherOpenServer() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherOpenServer.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherOpenServer oos = new OtherOpenServer();
			oos.setDays((int) (row.getCell(0).getNumericCellValue()));
			oos.setReward(row.getCell(1).getStringCellValue());
			otherOpenServerList.add(oos);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherCheckReward() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherCheckReward.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherCheckReward ocr = new OtherCheckReward();
			ocr.setDays((int) (row.getCell(0).getNumericCellValue()));
			ocr.setReward(row.getCell(1).getStringCellValue());
			otherCheckRewardList.add(ocr);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherUpgrade() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherUpgrade.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherUpgrade ou = new OtherUpgrade();
			ou.setLevel((int) (row.getCell(0).getNumericCellValue()));
			ou.setReward(row.getCell(1).getStringCellValue());
			otherUpgradeList.add(ou);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherFirstPay() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherFirstPay.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherFirstPay ofp = new OtherFirstPay();
			ofp.setLevel((int) (row.getCell(0).getNumericCellValue()));
			ofp.setReward(row.getCell(1).getStringCellValue());
			otherFirstPayList.add(ofp);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherOnlineTime() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherOnlineTime.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherOnlineTime oot = new OtherOnlineTime();
			oot.setOnlineTime((int) (row.getCell(0).getNumericCellValue()));
			oot.setReward(row.getCell(1).getStringCellValue());
			otherOnlineTimeList.add(oot);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherShop() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherShop.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherShop os = new OtherShop();
			os.setGoodsId((int) (row.getCell(0).getNumericCellValue()));
			os.setGoodsType((int) (row.getCell(1).getNumericCellValue()));
			os.setToolsName(row.getCell(2).getStringCellValue());
			os.setPrice((int) (row.getCell(3).getNumericCellValue()));
			os.setGailv((int) (row.getCell(4).getNumericCellValue()));
			otherShopList.add(os);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readOtherMercenary() throws IOException {
		FileInputStream fis = new FileInputStream(path + OtherMercenary.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			OtherMercenary om = new OtherMercenary();
			om.setMercenaryId((int) (row.getCell(0).getNumericCellValue()));
			om.setGailv((int) (row.getCell(1).getNumericCellValue()));
			otherMercenaryList.add(om);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readEquipmentQuality() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentQuality.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentQuality eq = new EquipmentQuality();
			eq.setSuitId((int) (row.getCell(0).getNumericCellValue()));
			eq.setSuitNum((int) (row.getCell(1).getNumericCellValue()));
			eq.setLife((int) (row.getCell(2).getNumericCellValue()));
			eq.setAttack((int) (row.getCell(3).getNumericCellValue()));
			eq.setOutside((int) (row.getCell(4).getNumericCellValue()));
			eq.setInside((int) (row.getCell(5).getNumericCellValue()));
			eq.setLifeSum((int) (row.getCell(6).getNumericCellValue()));
			eq.setAttackSum((int) (row.getCell(7).getNumericCellValue()));
			eq.setOutsideSum((int) (row.getCell(8).getNumericCellValue()));
			eq.setInsideSum((int) (row.getCell(9).getNumericCellValue()));
			equipmentQualityList.add(eq);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readEquipmentHeCheng() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentHeCheng.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentHeCheng ehc = new EquipmentHeCheng();
			ehc.setEquipmentId((int) (row.getCell(0).getNumericCellValue()));
			ehc.setEquipmentName(row.getCell(1).getStringCellValue());
			ehc.setEquipmentPatchId((int) (row.getCell(2).getNumericCellValue()));
			ehc.setEquipmentPatchName(row.getCell(3).getStringCellValue());
			ehc.setEquipmentPatchNum((int) (row.getCell(4).getNumericCellValue()));
			equipmentHeChengList.add(ehc);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readEquipmentBaseQuality() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentBaseQuality.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentBaseQuality ebq = new EquipmentBaseQuality();
			ebq.setEquipmentId((int) (row.getCell(0).getNumericCellValue()));
			ebq.setEquipmentName(row.getCell(1).getStringCellValue());
			ebq.setStarLevel((int) (row.getCell(2).getNumericCellValue()));
			ebq.setPinLevel((int) (row.getCell(3).getNumericCellValue()));
			ebq.setPart((int) (row.getCell(4).getNumericCellValue()));
			ebq.setSuitId((int) (row.getCell(5).getNumericCellValue()));
			ebq.setIsXiLian((byte) (row.getCell(6).getNumericCellValue()));
			ebq.setIsRongLian((byte) (row.getCell(7).getNumericCellValue()));
			ebq.setLife((int) (row.getCell(8).getNumericCellValue()));
			ebq.setAttack((int) (row.getCell(9).getNumericCellValue()));
			ebq.setOutside((int) (row.getCell(10).getNumericCellValue()));
			ebq.setInside((int) (row.getCell(11).getNumericCellValue()));
			ebq.setLifeAdd((int) (row.getCell(12).getNumericCellValue()));
			ebq.setAttackAdd((int) (row.getCell(13).getNumericCellValue()));
			ebq.setOutsideAdd((int) (row.getCell(14).getNumericCellValue()));
			ebq.setInsideAdd((int) (row.getCell(15).getNumericCellValue()));
			equipmentBaseQualityList.add(ebq);
			index++;
			if (isTest) {
				break;
			}
		}
	}

	public void readEquipmentIntroduce() throws IOException {
		FileInputStream fis = new FileInputStream(path + EquipmentIntroduce.Path);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			EquipmentIntroduce ei = new EquipmentIntroduce();
			ei.setEquipmentId((int) (row.getCell(0).getNumericCellValue()));
			ei.setEquipmentIntroduce(row.getCell(1).getStringCellValue());
			equipmentIntroduceList.add(ei);
			index++;
			if (isTest) {
				break;
			}
		}
	}

}
