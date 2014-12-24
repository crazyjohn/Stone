package com.stone.core.constants;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 游戏中的日志类会被多线程并发去写;<br>
 * log4j对并发的处理是加锁, 在线人数多, 活动频繁的时候, 会造成很大的压力, 我们的解决办法是使用线程本地变量<br>
 * FIXME: SERVER 以后的优化方向是<br>
 * 1. 战斗无关的业务可以直接使用Log4j的factory.getLogger(); <br>
 * 2. 自己封装的工厂中会有一次hash的代价, 如果确定无并发风险的, 可以进行本地缓存;<br>
 * 3. 有并发风险的一定要使用包装的工厂;<br>
 * 
 * @author crazyjohn
 * 
 */
public class Loggers {
	/** 实体相关的日志 */
	public static final Logger ENTITY_LOGGER = LoggerFactory
			.getLogger("hifun.soul.entity");

	/** 收获相关的日志 */
	public static final Logger HARVEST_LOGGER = LoggerFactory
			.getLogger("hifun.soul.harvest");

	/** 背包相关的日志 */
	public static final Logger BAG_LOGGER = LoggerFactory
			.getLogger("hifun.soul.bag");

	/** 建筑相关的日志 */
	public static final Logger BUILDING_LOGGER = LoggerFactory
			.getLogger("hifun.soul.building");

	/** 好友相关的日志 */
	public static final Logger FRIEND_LOGGER = LoggerFactory
			.getLogger("hifun.soul.friend");

	/** 排行榜相关的日志 */
	public static final Logger RANK_LOGGER = LoggerFactory
			.getLogger("hifun.soul.rank");

	/** 邮件相关的日志 */
	public static final Logger MAIL_LOGGER = LoggerFactory
			.getLogger("hifun.soul.mail");

	/** 物品相关的日志 */
	public static final Logger ITEM_LOGGER = LoggerFactory
			.getLogger("hifun.soul.item");

	/** 星运相关的日志 */
	public static final Logger HOROSCOPE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.horoscope");

	/** 科技日志 */
	public static final Logger TECHNOLOGY_LOGGER = LoggerFactory
			.getLogger("hifun.soul.technology");

	/** GameServer相关日志 */
	public static final Logger GAME_LOGGER = LoggerFactory
			.getLogger("hifun.soul.gameserver");

	/** Scene相关日志 */
	public static final Logger SCENE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.scene");

	/** 消息相关日志 */
	public static final Logger MSG_LOGGER = LoggerFactory
			.getLogger("hifun.soul.msg");

	/** 最早的占星相关日志 */
	public static final Logger ASTROLOGICAL_LOGGER = LoggerFactory
			.getLogger("hifun.soul.astrological");

	/** 魔晶兑换日志 */
	public static final Logger CRYSTALEXCHANGE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.crystalexchange");

	/** 连续登陆奖励日志 */
	public static final Logger LOGINREWARD_LOGGER = LoggerFactory
			.getLogger("hifun.soul.loginreward");

	/** 在线奖励日志 */
	public static final Logger ONLINEREWARD_LOGGER = LoggerFactory
			.getLogger("hifun.soul.onlinereward");

	/** 大转盘抽奖日志 */
	public static final Logger TURNTABLE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.turntable");

	/** 关卡日志 */
	public static final Logger STAGE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.stage");

	/** 公告日志 */
	public static final Logger BULLETIN_LOGGER = LoggerFactory
			.getLogger("hifun.soul.bulletin");

	/** 充值日志 */
	public static final Logger RECHARGE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.recharge");
	
	/** 充值活动日志 */
	public static final Logger RECHARGE_ACTIVITY_LOGGER = LoggerFactory
			.getLogger("hifun.soul.rechargeactivity");

	/** 活动日志 */
	public static final Logger ACTIVITY_LOGGER = LoggerFactory
			.getLogger("hifun.soul.activity");

	/** 训练日志 */
	public static final Logger TRAINING_LOGGER = LoggerFactory
			.getLogger("hifun.soul.training");

	/** CD日志 */
	public static final Logger CD_LOGGER = LoggerFactory
			.getLogger("hifun.soul.cd");

	/** 聊天日志 */
	public static final Logger CHAT_LOGGER = LoggerFactory
			.getLogger("hifun.soul.chat");

	/** 竞技场日志 */
	public static final Logger ARENA_LOGGER = LoggerFactory
			.getLogger("hifun.soul.arena");

	/** boss战日志 */
	public static final Logger BOSS_LOGGER = LoggerFactory
			.getLogger("hifun.soul.boss");

	/** gm问答日志 */
	public static final Logger GM_QUESTION_LOGGER = LoggerFactory
			.getLogger("hifun.soul.gmquestion");

	/** 矿场日志 */
	public static final Logger MINE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.mine");

	/** 匹配战日志 */
	public static final Logger MATCH_BATTLE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.matchbattle");

	// ----------------------------------------

	/** Server相关的日志 */
	/** 调度日志 */
	public static final Logger TIMMER_LOGGER = LoggerFactory
			.getLogger("hifun.soul.timer");

	/** 模版日志 */
	public static final Logger TEMPLATE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.template");

	public static final Logger SERVER_STATUS_LOGGER = LoggerFactory
			.getLogger("hifun.soul.serverStatus");

	public static final Logger SERVER_LOGGER = LoggerFactory
			.getLogger("hifun.soul.server");

	public static final Logger CLIENT_LOGGER = LoggerFactory
			.getLogger("hifun.soul.client");

	/** 缓存日志 */
	public static final Logger CACHE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.cache");

	/** 玩家日志 */
	public static final Logger playerLogger = LoggerFactory
			.getLogger("hifun.soul.player");

	/** GM日志 */
	public static final Logger GM_LOGGER = LoggerFactory
			.getLogger("hifun.soul.gm");

	/** 模版脚本日志 */
	public static final Logger SCRIPT_LOGGER = LoggerFactory
			.getLogger("hifun.soul.script");

	/** 断言日志 */
	public static final Logger ASSERT_LOGGER = LoggerFactory
			.getLogger("hifun.soul.assert");

	/** 平台日志 */
	public static final Logger LOCAL_LOGGER = LoggerFactory
			.getLogger("hifun.soul.local");

	/** 战斗日志 */
	public static Logger Battle_Logger = LoggerFactory
			.getLogger("hifun.soul.battle");

	/** 试炼日志 */
	public static Logger REFINE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.refine");

	/** 军团boss战日志 */
	public static final Logger LEGION_BOSS_LOGGER = LoggerFactory
			.getLogger("hifun.soul.legionboss");
	
	/** 战俘营日志 */
	public static final Logger PRISON_LOGGER = LoggerFactory
			.getLogger("hifun.soul.prison");
	
	/** 军团矿战日志 */
	public static final Logger LEGION_MINE_LOGGER = LoggerFactory
			.getLogger("hifun.soul.legionmine");
}
