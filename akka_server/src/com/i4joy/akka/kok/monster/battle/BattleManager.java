/**
 * 
 */
package com.i4joy.akka.kok.monster.battle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.i4joy.akka.kok.monster.amulet.AmuletTempletManager;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletBasePropertyTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletRefineTemplet;
import com.i4joy.akka.kok.monster.amulet.templet.AmuletUpGradeTemplet;
import com.i4joy.akka.kok.monster.equipment.EquipmentTempletManager;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentBasePropertyTemplet;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentRefineTemplete;
import com.i4joy.akka.kok.monster.equipment.templet.EquipmentUpGradeTemplet;
import com.i4joy.akka.kok.monster.mercenary.MercenaryProperty;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyPercent;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;
import com.i4joy.akka.kok.monster.mercenary.MercenaryTempletManager;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryFriendlyDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryLevelDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMeridiansTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryMinorPropertyTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryPredestineTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryQualityDataTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenarySkillTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTalentTemplet;
import com.i4joy.akka.kok.monster.mercenary.templet.MercenaryTemplet;
import com.i4joy.akka.kok.monster.player.PlayerEntity;
import com.i4joy.util.Tools;
import com.ump.model.Amulet;
import com.ump.model.Equipment;
import com.ump.model.Equipment_extra;
import com.ump.model.Mercenary;
import com.ump.model.Team;

/**
 * @author Administrator
 *
 */
public class BattleManager {
	
	static BattleManager self;
	
	Log logger = LogFactory.getLog(getClass());

	/**
	 * 
	 */
	public BattleManager() {
		
	}
	
	public static BattleManager getInstance(){
		return BattleManager.self;
	}
	
	public void init(){
		
		BattleManager.self=this;
	}
	
	/**
	 * 根据在阵容中的位置，换算成在战斗中的对应位置
	 * @param teamPos
	 * @param isAttacker
	 * @return
	 */
	public int getPosition(int teamPos,boolean isAttacker){
		if(isAttacker){
			if(teamPos<5){
				return teamPos+1; 
			}else{
				return teamPos+1+5;
			}
		}else{
			if(teamPos<5){
				return teamPos+1+5;
			}else{
				return teamPos+1+8;
			}
		}
	}
	
	public Hashtable<String,RoleElementData> convertRoleElementData(PlayerEntity pe, List<Team> ts,long[] attendants,boolean isAttacker){
		try{
			MercenaryTempletManager mtm=MercenaryTempletManager.getInstance();
			long[] mercenaryTempletIds=new long[ts.size()+attendants.length];
			
			for(int i=0;i<mercenaryTempletIds.length;i++){
				if(i<ts.size()){
					Mercenary m=pe.getMercenary(ts.get(i).getMenceray_id());
					MercenaryTemplet mt=mtm.getMercenaryTemplet(m.getBase_id());
					mercenaryTempletIds[i]=mt.getId();
				}else{
					mercenaryTempletIds[i]=attendants[i-ts.size()];
				}
			}
			
			Hashtable<String,RoleElementData> reds=new Hashtable<String, RoleElementData>();
			for(Team t:ts){
				Mercenary m=pe.getMercenary(t.getMenceray_id());
				MercenaryTemplet mt=mtm.getMercenaryTemplet(m.getBase_id());
				
				RoleElementData red=new RoleElementData();
				red.posID=this.getPosition(t.getPostion(), isAttacker);
				reds.put(""+red.posID, red);
				CalculatorProps cp=this.getMercenaryProp(pe, t, red, mercenaryTempletIds);
				red.maxHp=cp.getProps(MercenaryPropertyEnum.HP);
				red.hp=red.maxHp;
				red.sex=(byte)mt.getSex();
				red.faction=(byte)mt.getCamp();
				red.attack=cp.getProps(MercenaryPropertyEnum.AD);
				red.attackInit=red.attack;
				red.fightDefense=cp.getProps(MercenaryPropertyEnum.AD_DEF);
				red.fightDefenseInit=red.fightDefense;
				red.magicDefense=cp.getProps(MercenaryPropertyEnum.AP_DEF);
				red.magicDefenseInit=red.magicDefense;
				red.critValue=(short)cp.getProps(MercenaryPropertyEnum.CRITICAL);
				red.toughnessValue=(short)cp.getProps(MercenaryPropertyEnum.RESILIENCE);
				red.hitValue=(short)cp.getProps(MercenaryPropertyEnum.HIT_RATING);
				red.missesRate=(short)cp.getProps(MercenaryPropertyEnum.DODGE);
				red.parryValue=(short)cp.getProps(MercenaryPropertyEnum.BLOCK);
				red.piercingValue=(short)cp.getProps(MercenaryPropertyEnum.PENETRATE);
			}
			
			return reds;
		}catch (Exception e) {
			Tools.printError(e, logger, "[计算战斗属性] [异常："+e+"] [角色ID："+pe.getPlayerId()+"]");
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取单个阵容的所有属性 
	 * @param pe
	 * @param t
	 * @param red 如果此方法用于获取属性面板数值时，此参数传NULL
	 * @return
	 */
	public CalculatorProps getMercenaryProp(PlayerEntity pe,Team t,RoleElementData red,long[] mercenaryTempletIds){
		CalculatorProps cp=new CalculatorProps();
		MercenaryTempletManager mtm=MercenaryTempletManager.getInstance();
		EquipmentTempletManager etm=EquipmentTempletManager.getInstance();
		AmuletTempletManager atm=AmuletTempletManager.getInstance();
		List<Mercenary> ms=pe.getMercenaryList();
		List<Equipment> es=pe.getEquipmentList();
		List<Amulet> as=pe.getAmuletList();
		
		Mercenary m=this.getMercenary(t.getMenceray_id(), ms);
		if(m==null){
			Tools.printlnWarn("[计算战斗属性] [侠客不存在] [角色ID："+pe.getPlayerId()+"] [侠客ID："+t.getMenceray_id()+"]", logger);
			return null;
		}
		MercenaryTemplet mt=mtm.getMercenaryTemplet(m.getBase_id());
		if(mt==null){
			Tools.printlnWarn("[计算战斗属性] [侠客模板不存在] [角色ID："+pe.getPlayerId()+"] [侠客ID："+t.getMenceray_id()+"] [模板ID："+m.getBase_id()+"]", logger);
			return null;
		}

		//主动技能
		if(red!=null){
			MercenarySkillTemplet mst=mtm.getMercenarySkillTemplet(mt.getNormalAbilityId());
			if(mst==null){
				Tools.printlnWarn("[计算战斗属性] [普通技能不存在] [角色ID："+pe.getPlayerId()+"] [侠客ID："+t.getMenceray_id()+"] [模板ID："+m.getBase_id()+"] [普通技能："+mt.getNormalAbilityId()+"]", logger);
				return null;
			}
			red.normalSkill=mst.getSkillElementData();
			mst=mtm.getMercenarySkillTemplet(mt.getRageAbilityId());
			if(mst!=null){
				red.rageSkill=mst.getSkillElementData();
			}
		}
		
		
		//强化等级属性
		MercenaryLevelDataTemplet mldt=mtm.getLevelData(m.getBase_id(), m.getLevel());
		cp.addAllValue(mldt.getAllProps());
		
		//进化等级属性
		MercenaryQualityDataTemplet mqdt=mtm.getQualityData(m.getBase_id(), m.getQuality());
		cp.addAllValue(mqdt.getAllProps());
		
		//次要属性
		MercenaryMinorPropertyTemplet mmpt = mtm.getMercenaryMinorPropertyTemplet(mt.getMinorPropType());
		cp.addAllValue(mmpt.getAllProps());
		
		//情义等级属性
		MercenaryFriendlyDataTemplet mfdt=mtm.getFriendlyData(mt.getFriendshipId(), m.getFriendly_level());
		if(mfdt!=null){
			cp.addAllValue(mfdt.getAllProps());
		}
		
		//经脉等级属性
		MercenaryMeridiansTemplet mmt=mtm.getMeridiansData(m.getMeridians_level());
		if(mmt!=null){
			cp.addAllValue(mmt.getAllProps());
		}
		
		//天赋属性
		List<MercenaryTalentTemplet> mtts=mtm.getMercenaryTalentTemplets(m.getBase_id(), m.getQuality());
		for(MercenaryTalentTemplet mtt:mtts){
			MercenaryProperty mp=mtt.getMercenaryProperty();
			this.addProp(cp, mp);
			
			if(red!=null){
				MercenarySkillTemplet mst=mtm.getMercenarySkillTemplet(mtt.getSkillId());
				if(mst!=null){
					red.passiveSkill.add(mst.getSkillElementData());
				}
			}
		}
		
		//装备属性
		this.addEquipmentProps(t, es, cp, etm);
		
		//宝物属性
		if(t.getBook_id()>0){
			Amulet book=this.getAmulet(t.getBook_id(), as);
			if(book==null){
				Tools.printlnWarn("[计算战斗属性] [宝物不存在] [角色ID："+pe.getPlayerId()+"] [宝物ID："+t.getBook_id()+"]", logger);
				return null;
			}
			book.calculatorProps(cp, red, atm, mtm);
		}
		if(t.getHorse_id()>0){
			Amulet horse=this.getAmulet(t.getHorse_id(), as);
			if(horse==null){
				Tools.printlnWarn("[计算战斗属性] [宝物不存在] [角色ID："+pe.getPlayerId()+"] [宝物ID："+t.getHorse_id()+"]", logger);
				return null;
			}
			horse.calculatorProps(cp, red, atm, mtm);
		}
		
		//配缘
		List<MercenaryPredestineTemplet> mpts=mtm.getActiveMercenaryPredestineTemplet(m.getBase_id(), t.getAllEquipments(pe), t.getAllAmulets(pe), mercenaryTempletIds);
		for(MercenaryPredestineTemplet mpt:mpts){
			List<MercenaryProperty> mps=mpt.getMercenaryPropertys();
			for(MercenaryProperty mp:mps){
				this.addProp(cp, mp);
			}
		}
		
		return cp;
	}
	
	private void addProp(CalculatorProps cp,MercenaryProperty mp){
		if(mp!=null){
			if(mp instanceof MercenaryPropertyValue){
				cp.addValue((MercenaryPropertyValue)mp);
			}else if(mp instanceof MercenaryPropertyPercent){
				cp.addPercent((MercenaryPropertyPercent)mp);
			}
		}
	}
	
	private void addEquipmentProps(Team t,List<Equipment> es,CalculatorProps cp,EquipmentTempletManager etm){
		for(int i=0;i<4;i++){
			Equipment e=null;
			switch(i){
			case 0:
				e=this.getEquipment(t.getBody_id(), es);
				break;
				
			case 1:
				e=this.getEquipment(t.getHat_id(), es);
				break;
				
			case 2:
				e=this.getEquipment(t.getNecklace_id(), es);
				break;

			case 3:
				e=this.getEquipment(t.getWeapon_id(), es);
				break;
			}
			if(e==null){
				continue;
			}
			EquipmentUpGradeTemplet eut=etm.getLevelData(e.getBase_id(), e.getLevel());
			cp.addAllValue(eut.getAllProps());
			
			//装备洗炼属性
			cp.addAllValue(e.getAllExtraProps());
		}
	}
	
	private Mercenary getMercenary(long id,List<Mercenary> ms){
		for(Mercenary m:ms){
			if(m.getMercenary_id()==id){
				return m;
			}
		}
		return null;
	}
	
	private Equipment getEquipment(long id,List<Equipment> es){
		for(Equipment e:es){
			if(e.getEquipment_id()==id){
				return e;
			}
		}
		
		return null;
	}
	
	private Amulet getAmulet(long id,List<Amulet> as){
		for(Amulet a:as){
			if(a.getAmulet_id()==id){
				return a;
			}
		}
		return null;
	}
	

}
