/**
 * 
 */
package com.i4joy.akka.kok.calculator;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.language.RefinedSoundex;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.calculator.MercenaryDisignData.AmuletData;
import com.i4joy.akka.kok.calculator.MercenaryDisignData.EquipmentData;
import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletBasePropertyTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletRefineTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradeTemplet;
import com.i4joy.akka.kok.monster.battle.BattleManager;
import com.i4joy.akka.kok.monster.battle.CalculatorProps;
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
import com.i4joy.util.Tools;

/**
 * 侠客数据计算工具
 * @author Administrator
 *
 */
public class MercenaryPropsCalculator {
	
	private List<MercenaryDisignData> mdds=new ArrayList<MercenaryDisignData>();
	
	private List<CalculatorProps> cps=new ArrayList<CalculatorProps>();
	
	/**
	 * 装备洗炼百分比
	 */
	static final int EQUIPMENT_REFINE_PERCENT=5;
	
	private XSSFWorkbook workbook;

	/**
	 * 
	 */
	public MercenaryPropsCalculator() {
		
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
			mdd.setMercenaryId(Tools.getCellValue(row.getCell(0), Long.class));
			mdd.setLevel(Tools.getCellValue(row.getCell(1), Integer.class));
			mdd.setQuality(Tools.getCellValue(row.getCell(2), Integer.class));
			mdd.setFriendlyLevel(Tools.getCellValue(row.getCell(3), Integer.class));
			mdd.setMeridiansLevel(Tools.getCellValue(row.getCell(4), Integer.class));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(5), Long.class), Tools.getCellValue(row.getCell(6), Integer.class),0));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(7), Long.class), Tools.getCellValue(row.getCell(8), Integer.class),0));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(9), Long.class), Tools.getCellValue(row.getCell(10), Integer.class),0));
			mdd.addEquipment(new EquipmentData(Tools.getCellValue(row.getCell(11), Long.class), Tools.getCellValue(row.getCell(12), Integer.class),0));
			mdd.addAmulet(new AmuletData(Tools.getCellValue(row.getCell(13), Long.class), Tools.getCellValue(row.getCell(14), Integer.class), Tools.getCellValue(row.getCell(15), Integer.class),0));
			mdd.addAmulet(new AmuletData(Tools.getCellValue(row.getCell(16), Long.class), Tools.getCellValue(row.getCell(17), Integer.class), Tools.getCellValue(row.getCell(18), Integer.class),0));
			
			this.mdds.add(mdd);
			index++;
		}
	}
	
	public void calculateProps(){
		MercenaryTempletManager mtm=MercenaryTempletManager.getInstance();
		EquipmentTempletManager etm=EquipmentTempletManager.getInstance();
		AmuletTempletManager atm=AmuletTempletManager.getInstance();
		long[] mercenaryIds=new long[this.mdds.size()];
		for(int i=0;i<mercenaryIds.length;i++){
			mercenaryIds[i]=this.mdds.get(i).getMercenaryId();
		}
		
		for(MercenaryDisignData mdd:this.mdds){
			CalculatorProps cp=new CalculatorProps();
			cp.setMercenaryId(mdd.getMercenaryId());
			this.cps.add(cp);
			
			MercenaryTemplet mt=mtm.getMercenaryTemplet(mdd.getMercenaryId());
			
			//强化等级属性
			MercenaryLevelDataTemplet mldt=mtm.getLevelData(mdd.getMercenaryId(), mdd.getLevel());
			cp.addAllValue(mldt.getAllProps());
			
			//进化等级属性
			MercenaryQualityDataTemplet mqdt=mtm.getQualityData(mdd.getMercenaryId(), mdd.getQuality());
			cp.addAllValue(mqdt.getAllProps());
			
			//次要属性
			MercenaryMinorPropertyTemplet mmpt = mtm.getMercenaryMinorPropertyTemplet(mt.getMinorPropType());
			cp.addAllValue(mmpt.getAllProps());
			
			//情义等级属性
			MercenaryFriendlyDataTemplet mfdt=mtm.getFriendlyData(mt.getFriendshipId(), mdd.getFriendlyLevel());
			if(mfdt!=null){
				cp.addAllValue(mfdt.getAllProps());
			}
			
			//经脉等级属性
			MercenaryMeridiansTemplet mmt=mtm.getMeridiansData(mdd.getMeridiansLevel());
			if(mmt!=null){
				cp.addAllValue(mmt.getAllProps());
			}
			
			
			//天赋属性
			List<MercenaryTalentTemplet> mtts=mtm.getMercenaryTalentTemplets(mdd.getMercenaryId(), mdd.getQuality());
			for(MercenaryTalentTemplet mtt:mtts){
				MercenaryProperty mm=mtt.getMercenaryProperty();
				if(mm!=null){
					if(mm instanceof MercenaryPropertyValue){
						cp.addValue((MercenaryPropertyValue)mm);
					}else if(mm instanceof MercenaryPropertyPercent){
						cp.addPercent((MercenaryPropertyPercent)mm);
					}
				}
			}
			
			//装备属性
			for(EquipmentData ed:mdd.getEds()){
				EquipmentBasePropertyTemplet ebpt=etm.getEquipmentBasePropertyTemplet(ed.getEquipmentId());
				
				EquipmentUpGradeTemplet eut=etm.getLevelData(ed.getEquipmentId(), ed.getLevel());
				cp.addAllValue(eut.getAllProps());
				//装备洗炼属性
				EquipmentRefineTemplete ert=etm.getEquipmentRefineTemplete(ebpt.getXilian_id(), ed.getLevel());
				this.addEquipmentRefineProps(ert, cp);
			}
			
			//宝物属性
			for(AmuletData ad:mdd.getAds()){
				//强化等级数据
				AmuletUpGradeTemplet aut=atm.getAmuletUpGradeTemplet(ad.getAmuletId(), ad.getLevel());
				for(MercenaryProperty mp:aut.getMercenaryPropertys()){
					if(mp instanceof MercenaryPropertyValue){
						cp.addValue((MercenaryPropertyValue)mp);
					}else if(mp instanceof MercenaryPropertyPercent){
						cp.addPercent((MercenaryPropertyPercent)mp);
					}
				}
				
				//进化等级数据
				AmuletRefineTemplet art=atm.getAmuletRefineTemplet(ad.getAmuletId(), ad.getQuality());
				for(MercenaryProperty mp:art.getMercenaryPropertys()){
					if(mp instanceof MercenaryPropertyValue){
						cp.addValue((MercenaryPropertyValue)mp);
					}else if(mp instanceof MercenaryPropertyPercent){
						cp.addPercent((MercenaryPropertyPercent)mp);
					}
				}
			}
			
			//配缘属性
			List<MercenaryPredestineTemplet> mpts=mtm.getActiveMercenaryPredestineTemplet(mdd.getMercenaryId(), mdd.getEquipmentIds(), mdd.getAmuletIds(), mercenaryIds);
			for(MercenaryPredestineTemplet mpt:mpts){
				for(MercenaryProperty mp:mpt.getMercenaryPropertys()){
					if(mp instanceof MercenaryPropertyValue){
						cp.addValue((MercenaryPropertyValue)mp);
					}else if(mp instanceof MercenaryPropertyPercent){
						cp.addPercent((MercenaryPropertyPercent)mp);
					}
				}
			}
		}
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
		for(CalculatorProps cp:this.cps){
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
			MercenaryPropsCalculator mpc=new MercenaryPropsCalculator();
			mpc.initTempletManager();
			mpc.readMercenaryData(args[0]);
			mpc.calculateProps();
			mpc.saveFile();
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		

	}

}
