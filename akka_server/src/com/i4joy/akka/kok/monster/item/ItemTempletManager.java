/**
 * 
 */
package com.i4joy.akka.kok.monster.item;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.DataCheckListener;
import com.i4joy.akka.kok.monster.DataCheckService;
import com.i4joy.akka.kok.monster.ProtoBuffFileMakeService;
import com.i4joy.akka.kok.monster.ProtoBuffFileMaker;
import com.i4joy.akka.kok.monster.item.templet.ItemRewardTemplet;
import com.i4joy.akka.kok.monster.item.templet.ItemTemplet;
import com.i4joy.akka.kok.protobufs.res.KOKRes.BaseClientInfo;
import com.i4joy.akka.kok.protobufs.res.KOKRes.ItemClientInfo;
import com.i4joy.util.Tools;

/**
 * @author Administrator
 * 
 */
public class ItemTempletManager implements ProtoBuffFileMaker, DataCheckListener {

	String path;

	static ItemTempletManager self;

	Map<Long, ItemTemplet> itemTemplets = new HashMap<Long, ItemTemplet>();
	Map<Long, ItemRewardTemplet> itemRewardTemplets = new HashMap<Long, ItemRewardTemplet>();

	/**
	 * 
	 */
	public ItemTempletManager() {

	}

	public void init() throws Exception {
		long t = System.currentTimeMillis();
		this.path = TextProperties.getText("DESIGN_FILE_PATH");
		this.readItemData();
		this.readItemRewardData();
		DataCheckService.addListener(this);
		ProtoBuffFileMakeService.addMaker(this);
		ItemTempletManager.self = this;
		System.out.println("[系统初始化] [完成] [耗时：" + (System.currentTimeMillis() - t) + "ms] [" + this.getClass() + "]");
	}

	public static ItemTempletManager getInstance() {
		return ItemTempletManager.self;
	}

	private void readItemRewardData() throws Exception {
		File dir = new File(this.path + ItemConstants.ITEMREWARD);
		File[] fils = dir.listFiles();
		for (File file : fils) {
			if (!file.getName().contains("_")) {
				continue;
			}
			String info[] = file.getName().split("_");
			long equipmentId = Long.parseLong(info[0]);
			ItemRewardTemplet mls = new ItemRewardTemplet();
			this.itemRewardTemplets.put(equipmentId, mls);

			FileInputStream fis = new FileInputStream(file);
			XSSFWorkbook workbook = new XSSFWorkbook(fis);
			Sheet sheetLevel = workbook.getSheetAt(0);
			int index = 1;
			while (true) {
				Row row = sheetLevel.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				mls.addReword((long) row.getCell(0).getNumericCellValue(), (int) row.getCell(1).getNumericCellValue(), (int) row.getCell(2).getNumericCellValue(), (int) row.getCell(3).getNumericCellValue());
				index++;
			}
			index = 1;
			sheetLevel = workbook.getSheetAt(1);
			while (true) {
				Row row = sheetLevel.getRow(index);
				if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
					break;
				}
				mls.addProbability((int) row.getCell(0).getNumericCellValue(), (int) (row.getCell(1).getNumericCellValue()*10000f));
				index++;
			}
		}
	}

	private void readItemData() throws Exception {
		FileInputStream fis = new FileInputStream(path + ItemConstants.ITEM_FILE_PAAH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}

			ItemTemplet it = Tools.fillByExcelRow(row, ItemTemplet.class);
			this.itemTemplets.put(it.getId(), it);

			index++;
		}
	}

	@Override
	public void dataCheck() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void makeFile() throws Exception {
		List<BaseClientInfo> bcis = new ArrayList<BaseClientInfo>();
		Set<Entry<Long, ItemTemplet>> set = this.itemTemplets.entrySet();
		for (Entry<Long, ItemTemplet> en : set) {
			ItemTemplet it = en.getValue();

			ItemClientInfo.Builder itemBuilder = ItemClientInfo.newBuilder();
			itemBuilder.setPrice(it.getPrice());
			itemBuilder.setUiId("" + it.getUiId());
			if (it.getType() == ItemTypeEnum.CHEST.getType() || it.getType() == ItemTypeEnum.CHEST_KEY.getType()) {
				itemBuilder.setItemNeed(it.getProps());
			}

			BaseClientInfo.Builder bcib = BaseClientInfo.newBuilder();
			bcib.setId((int) it.getId());
			bcib.setName(it.getName());
			bcib.setDesc(it.getDesc());
			bcib.setIci(itemBuilder);

			bcis.add(bcib.build());
		}

		byte[][] datas = new byte[bcis.size()][];
		for (int i = 0; i < datas.length; i++) {
			datas[i] = bcis.get(i).toByteArray();
		}
		Tools.makeFile(this.getFilePath() + "item-bag.db", datas);

	}

	@Override
	public String getFilePath() {
		return "D:\\Projects\\client\\Dev\\GamePrj_KL\\Assets\\StreamingAssets\\db\\";
	}

	public ItemTemplet getItemTemplet(long baseId) {
		return itemTemplets.get(baseId);
	}
	
	public ItemRewardTemplet getItemRewardTemplet(long baseId)
	{
		return itemRewardTemplets.get(baseId);
	}
}
