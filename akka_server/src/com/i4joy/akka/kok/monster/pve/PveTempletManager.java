/**
 * 
 */
package com.i4joy.akka.kok.monster.pve;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.i4joy.akka.kok.TextProperties;
import com.i4joy.akka.kok.monster.DataCheckListener;
import com.i4joy.akka.kok.monster.DataCheckService;
import com.i4joy.akka.kok.monster.mercenary.MercenaryConstants;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyEnum;
import com.i4joy.akka.kok.monster.mercenary.MercenaryPropertyValue;
import com.i4joy.akka.kok.monster.pve.templet.ChapterTemplet;
import com.i4joy.akka.kok.monster.pve.templet.DialogTemplet;
import com.i4joy.akka.kok.monster.pve.templet.EnemyLineupTemplet;
import com.i4joy.akka.kok.monster.pve.templet.EnemyTemplet;
import com.i4joy.akka.kok.monster.pve.templet.RareStageTemplet;
import com.i4joy.akka.kok.monster.pve.templet.RewardTemplet;
import com.i4joy.akka.kok.monster.pve.templet.StageGroup;
import com.i4joy.akka.kok.monster.pve.templet.StageTemplet;
import com.i4joy.util.Tools;

/**
 * PVE数据管理器
 * @author Administrator
 *
 */
public class PveTempletManager implements DataCheckListener{
	
	static PveTempletManager self;
	
	private String path;
	
	/**
	 * 敌人模板
	 */
	Map<Integer, EnemyTemplet> enemyTemplets=new HashMap<Integer, EnemyTemplet>();
	
	/**
	 * 敌人阵容模板
	 */
	Map<Integer, EnemyLineupTemplet> enemyLineupTemplets=new HashMap<Integer, EnemyLineupTemplet>();
	
	/**
	 * 章节模板
	 */
	Map<Integer, ChapterTemplet> chapterTemplets=new TreeMap<Integer, ChapterTemplet>();
	
	/**
	 * 剧情对话文字
	 */
	Map<Integer, String> dialogTextTemplets=new HashMap<Integer, String>();
	
	/**
	 * 剧情模板
	 */
	Map<Integer, DialogTemplet> dialogTemplets=new HashMap<Integer, DialogTemplet>();
	
	/**
	 * 关卡奖励模板 
	 */
	Map<Integer, RewardTemplet> rewardTemplets=new HashMap<Integer, RewardTemplet>();
	
	/**
	 * 关卡模板
	 * 第一层描述每个章节下的所有关卡，KEY为章节ID
	 * 第二层KEY为关卡ID
	 */
	Map<Integer, Map<Integer,StageGroup>> stageGroupTemplets=new HashMap<Integer, Map<Integer,StageGroup>>();
	
	/**
	 * 精英关卡模板
	 */
	Map<Integer, RareStageTemplet> rareStageTemplets=new HashMap<Integer, RareStageTemplet>();

	/**
	 * 
	 */
	public PveTempletManager() {
		
	}
	
	public void init() throws Exception{
		long t=System.currentTimeMillis();
		this.path=TextProperties.getText("DESIGN_FILE_PATH");
		
		this.readEnemyData();
		this.readLineupData();
		this.readChapterData();
		this.readDialogTextData();
		this.readDialogData();
		this.readRewardData();
		this.readStageData();
		this.readRareStageData();
		
		DataCheckService.addListener(this);
		PveTempletManager.self=this;
		System.out.println("[系统初始化] [完成] [耗时：+"+(System.currentTimeMillis()-t)+"ms] ["+this.getClass()+"]");
	}
	
	public static PveTempletManager getInstance(){
		return PveTempletManager.self;
	}
	
	private void readEnemyData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.ENEMY_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			EnemyTemplet et=new EnemyTemplet();
			et.setEnemyId(Tools.getCellValue(row.getCell(0), Integer.class));
			et.setMercenaryId(Tools.getCellValue(row.getCell(1), Long.class));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HP, Tools.getCellValue(row.getCell(2), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD, Tools.getCellValue(row.getCell(3), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AD_DEF, Tools.getCellValue(row.getCell(4), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.AP_DEF, Tools.getCellValue(row.getCell(5), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.CRITICAL, Tools.getCellValue(row.getCell(6), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.RESILIENCE, Tools.getCellValue(row.getCell(7), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.HIT_RATING, Tools.getCellValue(row.getCell(8), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.DODGE, Tools.getCellValue(row.getCell(9), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.BLOCK, Tools.getCellValue(row.getCell(10), Integer.class)));
			et.addProperty(new MercenaryPropertyValue(MercenaryPropertyEnum.PENETRATE, Tools.getCellValue(row.getCell(11), Integer.class)));
			
			this.enemyTemplets.put(et.getEnemyId(), et);
			
			index++;
		}
	}
	
	private void readLineupData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.ENEMY_LIENUP_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			EnemyLineupTemplet elt=new EnemyLineupTemplet();
			elt.setId(Tools.getCellValue(row.getCell(0), Integer.class));
			for(int i=1;i<=8;i++){
				int enemy=Tools.getCellValue(row.getCell(i), Integer.class);
				if(!this.enemyTemplets.containsKey(enemy)){
					throw new Exception("[阵容中的敌人ID不存在] [文件："+(path + PveConstants.ENEMY_LIENUP_FILE_PATH)+"] [SHEET："+sheet.getSheetName()+"] [行："+index+"] [敌人ID：]"+enemy);
				}
				elt.addEnemy(enemy);
			}
			
			this.enemyLineupTemplets.put(elt.getId(), elt);
			index++;
		}
	}
	
	private void readChapterData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.CHAPTER_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			ChapterTemplet ct=new ChapterTemplet();
			ct.setId(Tools.getCellValue(row.getCell(0), Integer.class));
			ct.setTitle(Tools.getCellValue(row.getCell(1), String.class));
			ct.setDesc(Tools.getCellValue(row.getCell(2), String.class));
			ct.addReward(Tools.getCellValue(row.getCell(3), Integer.class), Tools.getCellValue(row.getCell(4), Integer.class));
			ct.addReward(Tools.getCellValue(row.getCell(5), Integer.class), Tools.getCellValue(row.getCell(6), Integer.class));
			ct.addReward(Tools.getCellValue(row.getCell(7), Integer.class), Tools.getCellValue(row.getCell(8), Integer.class));
			
			this.chapterTemplets.put(ct.getId(), ct);
			index++;
		}
	}
	
	private void readDialogTextData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.DIALOG_TEXT_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			this.dialogTextTemplets.put(Tools.getCellValue(row.getCell(0), Integer.class), Tools.getCellValue(row.getCell(1), String.class));
			index++;
		}
	}
	
	private void readDialogData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.DIALOG_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			DialogTemplet dt=new DialogTemplet();
			dt.setStageId(Tools.getCellValue(row.getCell(0), Integer.class));
			String[] ss=Tools.getCellValue(row.getCell(1), String.class).split("&");
			for(String s:ss){
				String[] sss=s.split("_");
				long mercenaryId=Long.parseLong(sss[0]);
				int dialog=Integer.parseInt(sss[1]);
				if(!this.dialogTextTemplets.containsKey(dialog)){
					throw new Exception("[对话内容不存在] [文件："+(path + PveConstants.DIALOG_FILE_PATH)+"] [SHEET："+sheet.getSheetName()+"] [行："+index+"] [对话ID：]"+dialog);
				}
				dt.addDialogBeforeEnter(mercenaryId, dialog);
			}
			
			ss=Tools.getCellValue(row.getCell(2), String.class).split("&");
			for(String s:ss){
				String[] sss=s.split("_");
				long mercenaryId=Long.parseLong(sss[0]);
				int dialog=Integer.parseInt(sss[1]);
				if(!this.dialogTextTemplets.containsKey(dialog)){
					throw new Exception("[对话内容不存在] [文件："+(path + PveConstants.DIALOG_FILE_PATH)+"] [SHEET："+sheet.getSheetName()+"] [行："+index+"] [对话ID：]"+dialog);
				}
				dt.addDialogBeforeFight(mercenaryId, dialog);
			}
			
			ss=Tools.getCellValue(row.getCell(3), String.class).split("&");
			for(String s:ss){
				String[] sss=s.split("_");
				long mercenaryId=Long.parseLong(sss[0]);
				int dialog=Integer.parseInt(sss[1]);
				if(!this.dialogTextTemplets.containsKey(dialog)){
					throw new Exception("[对话内容不存在] [文件："+(path + PveConstants.DIALOG_FILE_PATH)+"] [SHEET："+sheet.getSheetName()+"] [行："+index+"] [对话ID：]"+dialog);
				}
				dt.addDialogAfterFight(mercenaryId, dialog);
			}
			
			this.dialogTemplets.put(dt.getStageId(), dt);
			
			index++;
		}
	}
	
	private void readRewardData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.REWARD_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			RewardTemplet rt=new RewardTemplet();
			rt.setId(Tools.getCellValue(row.getCell(0), Integer.class));
			Long[][] lss=Tools.getCellValue(row.getCell(1), Long[][].class);
			for(Long[] ls:lss){
				rt.addReward(ls[0], (int)ls[1].longValue(), (int)ls[2].longValue());
			}
			
			this.rewardTemplets.put(rt.getId(), rt);
			index++;
		}
	}
	
	private void readStageData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.STAGE_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			StageTemplet st=Tools.fillByExcelRow(row, StageTemplet.class);
			if(!this.enemyLineupTemplets.containsKey(st.getLineupId())||!this.enemyLineupTemplets.containsKey(st.getStoryLineupId())){
				throw new Exception("[关卡数据错误] [阵型ID不存在] [关卡ID："+st.getStageId()+"] [阵型ID："+st.getLineupId()+"] [剧情阵型ID："+st.getStoryLineupId()+"]");
			}
			if(!this.rewardTemplets.containsKey(st.getRewardsId())){
				throw new Exception("[关卡数据错误] [奖励ID不存在] [关卡ID："+st.getStageId()+"] [奖励ID："+st.getRewardsId()+"]");
			}
			if(st.getChapterId()>0&&!this.chapterTemplets.containsKey(st.getChapterId())){
				throw new Exception("[关卡数据错误] [章节ID不存在] [关卡ID："+st.getStageId()+"] [奖励ID："+st.getRewardsId()+"]");
			}
			
			Map<Integer, StageGroup> sgs=this.stageGroupTemplets.get(st.getChapterId());
			if(sgs==null){
				sgs=new TreeMap<Integer, StageGroup>();
				this.stageGroupTemplets.put(st.getChapterId(), sgs);
			}
			StageGroup sg=sgs.get(st.getStageId());
			if(sg==null){
				sg=new StageGroup();
				sg.setStageId(st.getStageId());
				sgs.put(sg.getStageId(), sg);
			}
			sg.addStage(st);
			
			index++;
		}
	}
	
	private void readRareStageData() throws Exception{
		FileInputStream fis = new FileInputStream(path + PveConstants.RARE_STAGE_FILE_PATH);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		Sheet sheet = workbook.getSheetAt(0);
		int index = 1;
		while (true) {
			Row row = sheet.getRow(index);
			if (row == null || row.getCell(0) == null || row.getCell(0).toString().length() == 0) {
				break;
			}
			
			RareStageTemplet rst=new RareStageTemplet();
			rst.setStageId(Tools.getCellValue(row.getCell(0), Integer.class));
			rst.setHeadStageId(Tools.getCellValue(row.getCell(1), Integer.class));
			rst.setHeadRareStageId(Tools.getCellValue(row.getCell(2), Integer.class));
			StageTemplet st=this.getStageTemplet(PveConstants.RARE_CHAPTER, rst.getStageId(), PveConstants.DIFFICULTY_RARE);
			if(st==null){
				throw new Exception("[精英关卡数据错误] [关卡数据不存在] [关卡ID："+rst.getStageId()+"]");
			}
			rst.setSt(st);
			
			this.rareStageTemplets.put(rst.getStageId(), rst);
			index++;
		}
	}
	
	public StageTemplet getStageTemplet(int chapterId,int stageId,int difficulty){
		Map<Integer, StageGroup> sgs=this.stageGroupTemplets.get(chapterId);
		if(sgs!=null){
			StageGroup sg=sgs.get(stageId);
			if(sg!=null){
				return sg.getStageTemplet(difficulty);
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		
	}

	@Override
	public void dataCheck() throws Exception {
		//TODO 验证阵容中的侠客ID是否存在
		//TODO 验证对话中的侠客ID是否存在
		
	}

}
