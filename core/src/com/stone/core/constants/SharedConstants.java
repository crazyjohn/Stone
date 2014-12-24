package com.stone.core.constants;

/**
 * 全局共享的常量
 * 
 * 
 * @author crazyjohn;
 */
public interface SharedConstants {

	/** 系统默认的编码,UTF-8 {@index} */
	public static final String DEFAULT_CHARSET = "UTF-8";
	public static final int TYPE_NULL = 0;

	/** 所有Excel中用于记录配置信息的id值 */
	public static final int CONFIG_TEMPLATE_DEFAULT_ID = 1;

	/** 模版上配置小数时默认的基数 */
	public static final float DEFAULT_FLOAT_BASE = 10000.0f;

	// GameServer状态相关定义
	/** GameServer状态：拥挤 */
	public static final int GS_STATUS_FULL = 0;
	/** GameServer状态：正常，人比较少 */
	public static final int GS_STATUS_NORMAL = 1;
	/** GameServer状态：推荐 */
	public static final int GS_STATUS_RECOMMEND = 2;
	/** GameServer状态：维护或者下线 */
	public static final int GS_STATUS_MAINTAIN = 3;
	/** GameServer状态的阈值 : 超过 1000 人就算拥挤 */
	public static final int GS_STATUS_FULL_LIMIT = 1000;
	/** GameServer向WorldServer的汇报间隔 秒,Game Server配置的汇报时间间隔不能低于该值 */
	public static final int MAX_GAMESERVER_REPORT_PERIOR = 1800;
	/** GameServer的心跳间隔,单位为毫秒 */
	public static final int GS_HEART_BEAT_INTERVAL = 500;
	/** 场景每次tick处理的消息条数 */
	public static final int SCENE_MSG_PROCESS_COUNT_PER_TICK = 32;
	/** 场景每帧处理的玩家消息数量 */
	public static final int PLAYER_MSG_PROCESS_COUNT_PER_TICK = 16;

	/* 聊天范围 */
	/** 私聊，一对一 */
	public static final int CHAT_SCOPE_PRIVATE = 0x00000001;
	/** 附近，同区域内的12个玩家 */
	public static final int CHAT_SCOPE_NEAR = 0x00000002;
	/** 地区，同一城市下的玩家 */
	public static final int CHAT_SCOPE_REGION = 0x00000004;
	/** 帮派，同一军团下的玩家 */
	public static final int CHAT_SCOPE_GUILD = 0x00000008;
	/** 世界 */
	public static final int CHAT_SCOPE_WORLD = 0x00000010;
	/** 国家，相同国家内的玩家 */
	public static final int CHAT_SCOPE_ALLIANCE = 0x00000020;
	/** 喇叭 */
	public static final int CHAT_SCOPE_TRUMPET = 0x00000040;
	/** 系统消息 */
	public static final int CHAT_SCOPE_SYSTEM = 0x00000080;
	/** 默认接收所有频道 */
	public static final int CHAT_SCOPE_DEFAULT = 0x000000FF;

	/* 玩家常量 */
	/** 有公会 */
	public static final int PLAYER_PARTY_HAVE = 1;
	/** 无公会 */
	public static final int PLAYER_PARTY_NONE = 2;
	/** 玩家角色名的最大长度 */
	public static final int PLAYER_ROLE_MAX_LEN = 16;
	/** 每个玩家最多可创建的角色数 */
	public static final int MAX_ROLE_PER_PLAYER = 1;

	/** 角色未进入游戏时默认的角色ID */
	public static final int DEFAULT_CHAR_ID_BEFORE_ENTER_GAME = -1;

	/* 权限相关 */
	/** 玩家 ： 默认权限 */
	public static final int ACCOUNT_ROLE_USER = 0;
	/** GM ：管理员权限 */
	public static final int ACCOUNT_ROLE_GM = 1;
	/** DEBUG : DEBUG权限 */
	public static final int ACCOUNT_ROLE_DEBUG = 2;

	/* 登录认证的方式 */
	/** 认证方式：数据库认证，测试用 */
	public static final int AUTH_TYPE_DB = 0;
	/** 认证方式：MOP,校内接口认证，正式运营用 */
	public static final int AUTH_TYPE_INTERFACE = 1;

	/* 角色相关 */

	/** 角色姓名最小允许中文字符数 */
	public static final int MIN_NAME_LENGTH_ZHCN = 2;
	/** 角色姓名最大允许中文字符数 */
	public static final int MAX_NAME_LENGTH_ZHCN = 7;
	/** 角色姓名最小允许英文字符数 */
	public static final int MIN_NAME_LENGTH_ENG = 4;
	/** 角色姓名最大允许英文字符数 */
	public static final int MAX_NAME_LENGTH_ENG = 14;

	public static final int MIN_FLAG_WORD_LENGTH_ENG = 2;
	public static final int MAX_FLAG_WORD_LENGTH_ENG = 2;

	public static final int MIN_LEAVE_WORD_LENGTH_ENG = 4;
	public static final int MAX_LEAVE_WORD_LENGTH_ENG = 20;

	public static final int MIN_GUILD_NAME_LENGTH_ENG = 2;
	public static final int MAX_GUILD_NAME_LENGTH_ENG = 10;

	public static final int MIN_GUILD_MESSAGE_LENGTH_ENG = 0;
	public static final int MAX_GUILD_MESSAGE_LENGTH_ENG = 60;

	public static final int MIN_GUILD_SYMBOL_NAME_LENGTH_ENG = 4;
	public static final int MAX_GUILD_SYMBOL_NAME_LENGTH_ENG = 16;

	public static final int MIN_MAIL_TITLE_LENGTH_ENG = 2;
	public static final int MAX_MAIL_TITLE_LENGTH_ENG = 30;

	public static final int MIN_MAIL_CONTENT_LENGTH_ENG = 4;
	public static final int MAX_MAIL_CONTENT_LENGTH_ENG = 360;

	public static final int MAX_DIAMOND_CARRY_AMOUNT = 100000000;

	public static final int CHARGE_MM_2_GOLD_RATE = 10;

	/* 充值相关 */
	/** 允许玩家一次性兑换MM的最大数量 */
	public static final int MAX_EXCHANGE_MM = MAX_DIAMOND_CARRY_AMOUNT
			/ CHARGE_MM_2_GOLD_RATE;

	/** 允许直充的最大数量 */
	public static final int MAX_CHARGE_AMOUNT = 10000;

	/** 直充对入的美元换算成为钻石的比例 */
	public static final int DIRECT_CHARGE_MM_2_GOLD_RATE = 10;

	/* 对外Http接口相关 */

	/** 访问local平台所需的MD5 KEY 值 */
	public static final String LOCAL_MD5_KEY = "c762000b3eb6955de0862f435b28a8eb";

	/** 进行直充,请求的md5所需要的 KEY值 */
	public static final String HITHERE_MD5_KEY = "7545647f8bf84fb9be9a93209c5d0d91";

	/* 模版相关 */
	/** 取模版中的第一个元素（针对模版中只有一行的情况） */
	public static final int FIRST_ID = 1;

	/** 所有不存在的名称 */
	public static final String NOT_EXIST_NAME = "null";

	public static final String OPERATION_COM_RENREN = "renren";

	public static final String OPERATION_COM_HITHERE = "hithere";

	/** 角色可同时接取的最大任务数 */
	public static final int CHARCTER_MAX_ACCEPTING_COUNT = 10;
	/** 最大的缓存数量 */
	public static final int MAX_CACHE_SIZE = 10000;
	/** 管理场景的ID */
	public static final int SCENE_FOR_MANAGE_ID = 0;

	/** 角色初始化金币数目 */
	public static final int INIT_HUMAN_COINS = 100;
	/** 每个玩家最多的角色数 */
	public static final int PLAYER_MAX_CHAR_SIZE = 3;
	/** 玩家初始登记 */
	public static final int INIT_HUMAN_LEVEL = 1;
	/** 玩家建筑初始等级 */
	public static final int INIT_HUMAN_BUILDING_LEVEL = 1;

	/* 背包相关 */
	/** 玩家初始背包大小 */
	public static final int INIT_HUMAN_BAG_SIZE = 25;
	/** 主背包升一级所开启的容量 */
	public static final int MAIN_BAG_UPDATE_LEVEL_SIZE = 1;
	/** 主背包页大小 */
	public static final int MAIN_BAG_PAGE_SIZE = 30;
	/** 主背包最大容量 */
	public static final int MAIN_BAG_MAX_SIZE = 90;

	// 战斗相关
	/** 当一次消除达到此数目时候,当前玩家多一回合 */
	public static final int ADDED_ROUND_COUNT = 3;
	/** 模拟怪物战斗时思考的时间; 单位为毫秒 */
	public static final long BATTLE_MONSTER_THINK_TIMES = 1000;
	/** 等待客户端播放动画超时时间 */
	public static final long BATTLE_WAITING_FOR_ANIMATION_TIMEOUT = 20 * 1000;
	/** 等待客户端播放重置棋盘动画超时时间 */
	public static final long BATTLE_WAITING_FOR_RESET_CHESS_BOARD_ANIMATION_TIMEOUT = 5 * 1000;
	/** 战斗行动超时时间 */
	public static final int BATTLE_ACTION_TIMEOUT = 20 * 1000;
	/** 战斗行动超时扣除血量 */
	public static final int BATTLE_ACTION_TIMEOUT_DEDUCT_HP = 10;
	/** 玩家在战斗中最多携带的技能 */
	public static final int BATTLE_MAX_CARRIED_SKILLS = 5;
	// 棋盘大小
	public static final int GEM_MAX_ROW = 6;
	public static final int GEM_MAX_COL = 6;
	/** 玩家的最大技能栏位数 */
	public static final int MAX_SKILL_SLOT_COUNT = 5;
	/** 非法的技能栏索引 */
	public static final int INVALID_SKILL_SLOT_INDEX = -1;
	/** 技能发展类型初始值 */
	public static final int SKILL_DEVELOP_INIT = -1;

	// 属性相关系数的除数
	public static final float PROPERTY_DIVISOR = 10000.0f;
	public static final float PROPERTY_PERCENT_DIVISOR = 100.0f;
	// 最大角色等级的经验值;
	public static final int MAX_HUMAN_LEVEL_EXP = 0;
	// 角色最大的经验值; 目前钱和经验都是整形;
	public static final int MAX_HUMAN_EXP = Integer.MAX_VALUE;
	public static final int MAX_HUMAN_MONEY = Integer.MAX_VALUE;
	/** 角色的最大等级 */
	public static final int MAX_HUMAN_LEVEL = 100;

	// 任务
	/** 非法的前置任务ID,代表某个任务没有前置任务 */
	public static final int QUEST_INVALID_PRE_ID = -1;

	// 建筑相关
	/** 建筑升级模版配置的cd时间的上限 */
	public static final long BUILDING_MAX_CD = 10000000000L;

	// 星运相关
	/** 星运格子大小 */
	public static final int HOROSCOPE_MAINBAG_SIZE = 18;
	/** 星运的仓库大小 */
	public static final int HOROSCOPE_STORAGEBAG_SIZE = 25;
	/** 星运仓库初始开启格子数量 */
	public static final int HOROSCOPE_STORAGE_INIT_SIZE = 5;
	/** 每日免费占星次数 */
	public static final int HOROSCOPE_GAMBLE_TIME = 1;

	// 连续登陆奖励
	/** 暂时不提供给策划配置 */
	public static final int LOGIN_REWARD_MAX_DAYS = 7;
	/** 连续登陆天数大于15天之后抽奖次数按15天计算 */
	public static final int LOGIN_MAX_DAYS = 15;
	/** 暂时不提供给策划配置 */
	public static final int LOGIN_REWARD_REWARD_SIZE = 9;

	// 商城
	/** 商城每页显示商品数量 */
	public static final int MALL_PAGE_SIZE = 9;

	// 商店
	/** 商店每页显示商品数量 */
	public static final int SHOP_PAGE_SIZE = 8;

	// 荣誉商店每页显示的数量
	public static final int HONOUR_PAGE_SIZE = 9;

	// 关卡
	/** 奖品数量 */
	public static final int STAGE_REWARD_NUM = 5;
	/** 默认关卡id */
	public static final int STAGE_DEFAULT_STAGEID = 1;
	/** 战斗消耗精力值 */
	public static final int STAGE_ENERGY_NUM = 2;

	// 精英副本
	/** 战斗消耗体力值 */
	public static final int ELITE_ENERGY_NUM = 5;
	/** 1星星兑换天赋点数 */
	public static final int STAR_TO_GIFT_POINT = 5;

	// 排行榜
	/** 角色等级排行榜每页显示的记录数 */
	public static final int HUMAN_LEVEL_RANK_PAGE_SIZE = 10;
	/** 角色军衔排行榜每页显示的记录数 */
	public static final int HUMAN_TITLE_RANK_PAGE_SIZE = 10;
	/** 角色荣誉排行榜每页显示的记录数 */
	public static final int HUMAN_HONOR_RANK_PAGE_SIZE = 10;
	/** 角色荣誉排行榜每页显示的记录数 */
	public static final int HUMAN_VIP_RANK_PAGE_SIZE = 10;
	/** 军团等级排行榜每页显示的记录数 */
	public static final int LEGION_LEVEL_RANK_PAGE_SIZE = 10;
	/** 军团人数排行榜每页显示的记录数 */
	public static final int LEGION_MEMBER_RANK_PAGE_SIZE = 10;
	/** 技能面板的最大技能数 */
	public static final int SKILL_PANEL_MAX_SKILLS = 16;

	// 科技
	/** 科技每页显示数量 */
	public static final int TECHNOLOGY_PAGE_SIZE = 12;
	/** 最大的经验值 */
	public static final int MAX_EXP = Integer.MAX_VALUE;
	/** 等待接受战斗邀请的时长 */
	public static final long WAITING_BATTLE_REQUEST_TIMES = 10 * 1000;

	// 竞技场
	/** 竞技场当前玩家可战斗玩家列表系数 */
	public static final int ARENA_CAN_BATTLE_RATIO = 10;
	/** 竞技场可见玩家列表系数 */
	public static final float ARENA_VISIBLE_RATIO = 100.0f;
	/** 竞技场可见玩家数量 */
	public static final int ARENA_VISIBLE_NUM = 5;
	/** 竞技场的排行榜显示玩家数量 */
	public static final int ARENA_RANK_NUM = 100;
	/** 竞技场显示提示数量 */
	public static final int ARENA_NOTICE_NUM = 5;
	/** 竞技场排行榜每页显示数量 */
	public static final int ARENA_RANK_PAGE_NUM = 10;

	// BOSS战
	public static final int BOSS_RANKING_SIZE = 10;
	// 角色名字最大长度
	public static final int MAX_CHARACTER_NAME_LENGTH = 5;

	// 神秘商店
	/** 神秘商店提示显示数量 */
	public static final int SPECIAL_SHOP_NOTIFY_NUM = 10;
	/** 神秘商品数量 */
	public static final int SPECIAL_SHOP_ITEM_NUM = 6;

	// 引导开关
	public static final boolean GUIDE_OPEN = false;

	// 勇者之路对手匹配随机池大小
	public static final int WARRIOR_OPPONENT_POOL_SIZE = 15;

	// qq平台相关
	/** 黄钻等级 */
	public static final String YELLOW_VIP_LEVEL = "yellow_vip_level";
	/** 是否为年费黄钻用户 */
	public static final String IS_YELLOW_YEAR_VIP = "is_yellow_year_vip";
	/** 是否为豪华版黄钻用户 */
	public static final String IS_YELLOW_HIGH_VIP = "is_yellow_high_vip";
	/** 腾讯开放平台用户id */
	public static final String OPEN_ID = "openid";

	// 军团相关
	/** 军团日志显示数量 */
	public static final int LEGION_LOG_NUM = 20;
	/** 军团最大等级 */
	public static final int LEGION_MAX_LEVEL = 10;
	/** 军团冥想日志显示数量 */
	public static final int LEGION_MEDITATION_LOG_NUM = 3;
	/** 军团任务数 */
	public static final int LEGION_TASK_NUM = 4;
	/** 军团任务积分榜数量 */
	public static final int LEGION_TASK_RANK_NUM = 10;

	// 角斗场相关
	/** 角斗场房间等级段编号间隔 */
	public static final int ABATTOIR_ROOM_ID_INTERVAL = 10;
	/** 角斗场免费角斗次数 */
	public static final int ABATTOIR_FREE_BUY_NUM = 5;

	// 嗜血神殿相关
	/** 嗜血神殿默认显示房间页码 */
	public static final int BLOOD_TEMPLE_DEFAULT_PAGE = 1;
	/** 嗜血神殿房间总页数 */
	public static final int BLOOD_TEMPLE_PAGE_COUNT = 5;
	/** 嗜血神殿免费角斗次数 */
	public static final int BLOOD_TEMPLE_FREE_BUY_NUM = 5;
	/** 嗜血神殿仇人列表显示个数 */
	public static final int BLOOD_TEMPLE_ENEMY_NUM = 5;

	/** 初始精灵格子大小 */
	public static final int INIT_HUMAN_SPRITE_BAG_SIZE = 5;
	/** 精灵默认等级 */
	public static final int SPRITE_DEFAULT_LEVEL = 1;

	// 首充相关
	/** 首充价值 */
	public static final int FIRST_RECHARGE_PRICE = 1999;

	/** 战神之巅手下败将显示个数 */
	public static final int MARS_LOSER_SHOW_NUM = 4;
	/** 战神之巅阵营排行榜显示个数 */
	public static final int MARS_FACTION_RANK_SHOW_NUM = 4;
	/** 战神之巅击杀奖励物品ID */
	public static final int MARS_KILL_REWARD_ID = 327002;
	// 军团BOSS战
	/** 排行榜长度 */
	public static final int LEGION_BOSS_RANKING_SIZE = 10;

	/** 税收押注显示骰子数 */
	public static final int LEVY_BET_DICE_NUM = 3;
	/** 税收押注默认显示骰子点数 */
	public static final int LEVY_BET_DEFAULT_POINT = 6;
	/** 押注掷出大的最小点数 */
	public static final int LEVY_BET_BIG_POINT = 11;

	/** 押运拦截日志显示个数 */
	public static final int ESCORT_ROB_LOG_NUM = 3;

	/** 日常任务显示个数 */
	public static final int DAILY_QUEST_NUM = 3;

	/** 一键答题收益倍数 */
	public static final int ONEKEY_ANSWER_MULTIPLE = 2;

	// 军团矿战相关
	/** 总矿位数量 */
	public static final int TOTAL_LEGION_MINE_NUM = 35;
}
