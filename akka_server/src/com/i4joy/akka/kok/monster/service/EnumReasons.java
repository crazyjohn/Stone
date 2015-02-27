package com.i4joy.akka.kok.monster.service;

import com.i4joy.akka.kok.LanguageProperties;

public enum EnumReasons {

	/**
	 * GM 扣钱
	 */
	GM_COST_MONEY(1, LanguageProperties.getText("GMCOSTMONEY")),GM_ADD_EXP(2, LanguageProperties.getText("GMADDEXP"));

	final int index;

	final String name;

	private EnumReasons(int index, String name) {
		this.index = index;
		this.name = name;
	}

}
