package com.i4joy.akka.kok.monster.service;

import com.i4joy.akka.kok.LanguageProperties;

public enum EnumFails {
	UNCATCH(0, "没有捕获"), 
	UNFAIL(1, "没失败"), 
	EQUIPMENT_FULL(2, LanguageProperties.getText("GMCOSTMONEY")), 
	MERCENARY_FULL(3, LanguageProperties.getText("GMADDEXP")),
	MERCENARYTEMPLATE(4, "没有找到侠客模板"), 
	PLAYERMERCENARY(5, "获得主角侠客失败"),
	MERCENARYFULL(7, "侠客背包已经满了"),
	EQUIPMENTFULL(8, "装备背包已经满了"),
	AMULETFULL(9, "法宝背包已经满了"),
	EQUIPMENTPIRECE(10, "装备碎片背包已经满了"),
	MERCENARYGHOSTE(11, "没有找到侠客碎片"),
	PLAYERMERCENARYGHOSTE(12, "玩家侠客碎片背包没有找到侠客碎片"),
	MERCENARYDEBIRSNUM(13, "侠客碎片不够"),
	EQUIPMENTDEBIRSTEMPLET(14, "装备碎片模板没有"),
	PLAYEREQUIPMENTDEBIRSTEMPLET(15, "玩家装备碎片背包里没有装备碎片"),
	EQUIPMENTTEMPLET(16, "装备模板没有"),
	EQUIPMENTYDEBIRSNUM(17, "侠客碎片不够"),
	AMULETTEMPLET(18, "法宝模板没有"),
	AMULETDEBIRSTEMPLET(19, "法宝碎片模板没有"),
	PLAYERAMULETDEBIRSTEMPLET(20, "玩家装备碎片背包里没有装备碎片"),
	AMULETDEBIRTEMPLET(21, "法宝模板没有"),
	AMULETYDEBIRSNUM(22, "侠客碎片不够"),
	MERCENARYTEMPLATEISA(23, "侠客不能炼化"), 
	MERCENARY(23, "玩家身上没有这个侠客"), 
	MERCENARYQUALITYLEVEL(24, "侠客进阶大于1不能炼化"), 
	PLAYEREQUIPMENT(25, "玩家没有装备"), 
	PLAYERGOLD(26, "玩家元宝不够"), 
	MERCENARYQUALITY(27, "没有下一级侠客进阶信息"),
	TEAMTEMPLATE(28, "没有阵营模板"),
	TEAMTEMPLATENUM(29, "超过阵营数量"),
	GETTEAM(30, "获得阵营失败"),
	UPGRADEOVERLEVEL(31, "超过可升级最大等级"),
	PLAYERMONEY(32, "玩家钱不够"), 
	EQUIPMENTUPGRADEPAY(33, "装备升级消耗没有找到"), 
	UPGRADENOCARDS(34, "没有强化对卡片"), 
	PLAYERITEMS(35, "道具不够"), 
	AMULETREFINETEMPLET(36,"法宝进阶模板没有找到"),
	EQUIPMENTUNXILiAN(37,"装备不能洗炼"),
	EQUIPMENTREFINE(38, "没有找到装备洗练数据信息"), 
	NOHADXILIANSTONE(39, "玩家没有洗练石"), 
	UNXILIAN(40, "洗练条件不满足"), 
	MERCENARYUPGRADE(6, "没有下一级侠客升级信息");

	final int index;

	final String name;

	private EnumFails(int index, String name) {
		this.index = index;
		this.name = name;
	}
}
