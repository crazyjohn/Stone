/**
 * 
 */
package com.i4joy.akka.kok.calculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import scala.Array;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.calculator.MercenaryDisignData.AmuletData;
import com.i4joy.akka.kok.calculator.MercenaryDisignData.EquipmentData;
import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletBasePropertyTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletRefineTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradeTemplet;
import com.i4joy.akka.kok.monster.battle.BattleCmdBuilder;
import com.i4joy.akka.kok.monster.battle.BattleManager;
import com.i4joy.akka.kok.monster.battle.CalculatorProps;
import com.i4joy.akka.kok.monster.battle.CmdElementData;
import com.i4joy.akka.kok.monster.battle.RoleElementData;
import com.i4joy.akka.kok.monster.equipment.EquipmentTempletManager;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentBasePropertyTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentRefineTemplete;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentUpGradeTemplet;
import com.i4joy.akka.kok.monster.item.ItemTempletManager;
import com.i4joy.akka.kok.monster.mercenary.BuffTypeEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyPercent;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;
import com.i4joy.akka.kok.monster.mercenary.MercenaryTempletManager;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryBuffTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryFriendlyDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryLevelDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMeridiansTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMinorPropertyTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryPredestineTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryQualityDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTalentTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTemplet;
import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.util.Tools;
import com.ump.model.Amulet;
import com.ump.model.Equipment;
import com.ump.model.Mercenary;
import com.ump.model.Team;

/**
 * 战斗数据计算工具
 * @author Administrator
 *
 */
public class BattleCalculator {
	
	private List<MercenaryDisignData> attacker=new ArrayList<MercenaryDisignData>();
	
	private List<MercenaryDisignData> defender=new ArrayList<MercenaryDisignData>();
	
	private List<CalculatorProps> attackerCps=new ArrayList<CalculatorProps>();
	
	private List<CalculatorProps> defenderCps=new ArrayList<CalculatorProps>();
	
	/**
	 * 装备洗炼百分比
	 */
	static final int EQUIPMENT_REFINE_PERCENT=5;
	
	private XSSFWorkbook workbook;
	
	public static long ID=0;
	
	Hashtable<String,RoleElementData> reds=new Hashtable<String, RoleElementData>();
	
	List<CmdElementData> aCmdList=new ArrayList<CmdElementData>();

	/**
	 * 
	 */
	public BattleCalculator() {
		
	}
	
	public void initTempletManager() throws Exception{
		MercenaryTempletManager mtm = new MercenaryTempletManager();
		mtm.init();
		ItemTempletManager itm=new ItemTempletManager();
		itm.init();
		EquipmentTempletManager etm=new EquipmentTempletManager();
		etm.init();
		AmuletTempletManager atm=new AmuletTempletManager();
		atm.init();
		BattleManager bm=new BattleManager();
		bm.init();
	}
	
	public void readMercenaryData(String file) throws Exception{
		FileInputStream fis = new FileInputStream(file);
		this.workbook = new XSSFWorkbook(fis);
		Sheet sheet = this.workbook.getSheetAt(0);
		int index = 2;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			MercenaryDisignData mdd=new MercenaryDisignData();
			mdd.setEntityId(ID++);
			mdd.setMercenaryId(Tools.getCellValue(row.getCell(0), Long.class));
			mdd.setLevel(Tools.getCellValue(row.getCell(1), Integer.class));
			mdd.setQuality(Tools.getCellValue(row.getCell(2), Integer.class));
			mdd.setFriendlyLevel(Tools.getCellValue(row.getCell(3), Integer.class));
			mdd.setMeridiansLevel(Tools.getCellValue(row.getCell(4), Integer.class));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(5), Long.class), Tools.getCellValue(row.getCell(6), Integer.class),ID++));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(7), Long.class), Tools.getCellValue(row.getCell(8), Integer.class),ID++));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(9), Long.class), Tools.getCellValue(row.getCell(10), Integer.class),ID++));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(11), Long.class), Tools.getCellValue(row.getCell(12), Integer.class),ID++));
			mdd.addAmulet(new AmuletData(Tools.getCellValue(row.getCell(13), Long.class), Tools.getCellValue(row.getCell(14), Integer.class), Tools.getCellValue(row.getCell(15), Integer.class),ID++));
			mdd.addAmulet(new AmuletData(Tools.getCellValue(row.getCell(16), Long.class), Tools.getCellValue(row.getCell(17), Integer.class), Tools.getCellValue(row.getCell(18), Integer.class),ID++));
			
			this.attacker.add(mdd);
			index++;
		}
		
		sheet = this.workbook.getSheetAt(1);
		index = 2;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			MercenaryDisignData mdd=new MercenaryDisignData();
			mdd.setEntityId(ID++);
			mdd.setMercenaryId(Tools.getCellValue(row.getCell(0), Long.class));
			mdd.setLevel(Tools.getCellValue(row.getCell(1), Integer.class));
			mdd.setQuality(Tools.getCellValue(row.getCell(2), Integer.class));
			mdd.setFriendlyLevel(Tools.getCellValue(row.getCell(3), Integer.class));
			mdd.setMeridiansLevel(Tools.getCellValue(row.getCell(4), Integer.class));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(5), Long.class), Tools.getCellValue(row.getCell(6), Integer.class),ID++));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(7), Long.class), Tools.getCellValue(row.getCell(8), Integer.class),ID++));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(9), Long.class), Tools.getCellValue(row.getCell(10), Integer.class),ID++));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(11), Long.class), Tools.getCellValue(row.getCell(12), Integer.class),ID++));
			mdd.addAmulet(new AmuletData(Tools.getCellValue(row.getCell(13), Long.class), Tools.getCellValue(row.getCell(14), Integer.class), Tools.getCellValue(row.getCell(15), Integer.class),ID++));
			mdd.addAmulet(new AmuletData(Tools.getCellValue(row.getCell(16), Long.class), Tools.getCellValue(row.getCell(17), Integer.class), Tools.getCellValue(row.getCell(18), Integer.class),ID++));
			
			this.defender.add(mdd);
			index++;
		}
	}
	
	public void calculateProps(){
		BattleManager bm=BattleManager.getInstance();
		
		for(int i=0;i<2;i++){
			List<MercenaryDisignData> mdds;
			List<CalculatorProps> cps;
			if(i==0){
				mdds=this.attacker;
				cps=this.attackerCps;
			}else{
				mdds=this.defender;
				cps=this.defenderCps;
			}
			
			PlayerEntity pe=new PlayerEntity();
			List<Team> ts=new ArrayList<Team>();
			List<Mercenary> ms=new ArrayList<Mercenary>();
			List<Equipment> es=new ArrayList<Equipment>();
			List<Amulet> as=new ArrayList<Amulet>();
			List<Long> attendants=new ArrayList<Long>();
			
			int index=0;
			for(MercenaryDisignData mdd:mdds){
				if(index<8){
					ts.add(mdd.createTeam(index));
				}else{
					attendants.add(mdd.getMercenaryId());
				}
				ms.add(mdd.createMercenary());
				es.addAll(mdd.getEquipments());
				as.addAll(mdd.getAmulets());
				
				index++;
			}
			pe.setMercenaryList(ms);
			pe.setAmuletList(as);
			pe.setEquipmentList(es);
			long[] attendantIds=new long[attendants.size()];
			for(int j=0;j<attendantIds.length;j++){
				attendantIds[j]=attendants.get(j);
			}
			
			this.reds.putAll(bm.convertRoleElementData(pe, ts, attendantIds, i==0));
			
		}
	}
	
	public void fight(){
		BattleCmdBuilder.BuildCmd(this.reds, this.aCmdList);
	}
	
	public void saveFile() throws Exception{
		XSSFSheet sheet = this.workbook.createSheet("属性");
		XSSFRow row=sheet.createRow(0);
		XSSFCell cell=row.createCell(0);
		cell.setCellValue("侠客ID");
		
		cell=row.createCell(1);
		cell.setCellValue("生命");
		
		cell=row.createCell(2);
		cell.setCellValue("攻击");
		
		cell=row.createCell(3);
		cell.setCellValue("物防");
		
		cell=row.createCell(4);
		cell.setCellValue("法防");
		
		cell=row.createCell(5);
		cell.setCellValue("暴击");
		
		cell=row.createCell(6);
		cell.setCellValue("韧性");
		
		cell=row.createCell(7);
		cell.setCellValue("命中");
		
		cell=row.createCell(8);
		cell.setCellValue("闪避");
		
		cell=row.createCell(9);
		cell.setCellValue("格挡");
		
		cell=row.createCell(10);
		cell.setCellValue("穿刺");
		
		cell=row.createCell(11);
		cell.setCellValue("BUFF_ID");
		
		int rowIndex=1;
		for(CalculatorProps cp:this.attackerCps){
			row=sheet.createRow(rowIndex);
			
			cell=row.createCell(0);
			cell.setCellValue(cp.getMercenaryId());
			
			cell=row.createCell(1);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.HP));
			
			cell=row.createCell(2);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.AD));
			
			cell=row.createCell(3);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.AD_DEF));
			
			cell=row.createCell(4);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.AP_DEF));
			
			cell=row.createCell(5);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.CRITICAL));
			
			cell=row.createCell(6);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.RESILIENCE));
			
			cell=row.createCell(7);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.HIT_RATING));
			
			cell=row.createCell(8);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.DODGE));
			
			cell=row.createCell(9);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.BLOCK));
			
			cell=row.createCell(10);
			cell.setCellValue(cp.getProps(MercenaryPropertyEnum.PENETRATE));
			
			cell=row.createCell(11);
			cell.setCellValue(cp.getBuffString());
			
			rowIndex++;
		}
		
		FileOutputStream fos = new FileOutputStream("mercenary_props.xlsx");
		workbook.write(fos);
		fos.flush();
		fos.close();
		System.out.println("生成文件：mercenary_props.xlsx");
	}
	
	private void addBuffProps(MercenaryBuffTemplet mbt,CalculatorProps cp){
		BuffTypeEnum bte=BuffTypeEnum.getBuffTypeEnum(mbt.getBuffType());
		if(bte==null){
			return;
		}
		MercenaryProperty mp=bte.getMercenaryProperty(mbt.getValue());
		if(mp instanceof MercenaryPropertyValue){
			cp.addValue((MercenaryPropertyValue)mp);
			cp.addBuffId(mbt.getId());
		}else if(mp instanceof MercenaryPropertyPercent){
			cp.addPercent((MercenaryPropertyPercent)mp);
			cp.addBuffId(mbt.getId());
		}
	}
	
	private void addEquipmentRefineProps(EquipmentRefineTemplete ert,CalculatorProps cp){
		int value;
		if(ert.getHp_refine_limit()>0){
			value=ert.getHp_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.HP, value);
			}
		}
		
		if(ert.getAd_refine_limit()>0){
			value=ert.getAd_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.AD, value);
			}
		}
		
		if(ert.getAdDef_refine_limit()>0){
			value=ert.getAdDef_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.AD_DEF, value);
			}
		}
		
		if(ert.getPdDef_refine_limit()>0){
			value=ert.getPdDef_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.AP_DEF, value);
			}
		}
		
		if(ert.getCritical_refine_limit()>0){
			value=ert.getCritical_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.CRITICAL, value);
			}
		}
		
		if(ert.getResilience_refine_limit()>0){
			value=ert.getResilience_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.RESILIENCE, value);
			}
		}
		
		if(ert.getHit_rating_refine_limit()>0){
			value=ert.getHit_rating_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.HIT_RATING, value);
			}
		}
		
		if(ert.getDodge_refine_limit()>0){
			value=ert.getDodge_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.DODGE, value);
			}
		}
		
		if(ert.getPenetrate_refine_limit()>0){
			value=ert.getPenetrate_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.PENETRATE, value);
			}
		}
		
		if(ert.getBlock_refine_limit()>0){
			value=ert.getBlock_refine_limit()*EQUIPMENT_REFINE_PERCENT/100;
			if(value>0){
				cp.addValue(MercenaryPropertyEnum.BLOCK, value);
			}
		}
	}
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			String path=args[1];
			TextProperties.setProperty("DESIGN_FILE_PATH",path);
			BattleCalculator mpc=new BattleCalculator();
			mpc.initTempletManager();
			mpc.readMercenaryData(args[0]);
			mpc.calculateProps();
			mpc.fight();
//			mpc.saveFile();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
