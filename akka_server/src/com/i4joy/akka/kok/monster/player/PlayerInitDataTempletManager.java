/**
 * 
 */
package com.i4joy.akka.kok.monster.player;

import java.io.FileInputStream;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.player.templet.PlayerInitDataTemplet;
import com.i4joy.util.Tools;

/**
 * 玩家角色初始数据模板管理器
 * 
 * @author Administrator
 * 
 */
public class PlayerInitDataTempletManager {

	static PlayerInitDataTempletManager self;

	static final String FILE_NAME = "角色默认属性表.xlsx";

	PlayerInitDataTemplet initDataTemplet;

	/**
	 * 
	 */
	public PlayerInitDataTempletManager() {

	}

	public void init() throws Exception {
		long t = System.currentTimeMillis();
		this.readData();
		PlayerInitDataTempletManager.self = this;
		System.out.println("[系统初始化] [完成] [耗时：+" + (System.currentTimeMillis() - t) + "ms] [" + this.getClass() + "]");
	}

	public static PlayerInitDataTempletManager getInstance() {
		return PlayerInitDataTempletManager.self;
	}

	private void readData() throws Exception {
		FileInputStream fis = new FileInputStream(TextProperties.getText("DESIGN_FILE_PATH") + FILE_NAME);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		Row row = sheet.getRow(index);
		this.initDataTemplet = Tools.fillByExcelRow(row, PlayerInitDataTemplet.class);
		System.out.println(this.initDataTemplet);
	}

	public PlayerInitDataTemplet getPlayerInitDataTemplet() {
		return initDataTemplet;
	}

}
