package com.stone.core.msg;

/**
 * 定义消息类型,规则如下:
 * 
 * <pre>
 * 1.所有消息类型均为short类型，消息类型保证惟一
 * 2.系统内部消息以SYS_开头
 * 3.客户端发往GameServer的以CG_开头 
 * 4.GameServer发往客户端的以GC_开头 
 * 5.保留消息类型0-100,给系统内部一些特殊消息使用
 * 6.每个子系统或模块的消息类型定义应放在一起
 * </pre>
 * <p>
 * <font color = 'red'> 大家注意给消息类型加注释, 方便以后阅读!</font>
 * 
 */
public abstract class MessageType {
	/** Flash socket 发送的policy request请求协议 "<policy" 中第3,4两个字节ol的16进制表示,28524 */
	public static final short FLASH_POLICY = 0x6f6c;
	public static final short MSG_UNKNOWN = 0;

	/* === 系统内部消息类型定义开始,范围0~100 === */
	public static final short SYS_SESSION_OPEN = 1;
	public static final short SYS_SESSION_CLOSE = 2;
	public static final short SYS_SCHEDULE = 3;
	public static final short SCHD_ASYNC_FINISH = 10;
	public static final short SCHD_PLAYER_ASYNC_FINISH = 11;
	public static final short SYS_TEST_MSG_LENGTH = 14;
	public static final short SYS_TEST_FLOOD_BYTE_ATTACK = 15;
	/* === 系统内部消息类型定义结束 === */

	// /////////////
	// 服务器内部状态
	// ////////////
	public static short STAUS_BEGIN = 400;

	private static short BASE_NUMBER = 2000;
	/** 每个大系统分配的消息个数 */
	public static final short NUMBER_PER_SYS = 350;
	/** 每个子系统分配的消息个数 */
	public static final short NUMBER_PER_SUB_SYS = 50;

	// /////////////
	// 各模块通用的消息
	// ////////////
	public static short COMMON_BEGIN = BASE_NUMBER;
	public static final short CG_ADMIN_COMMAND = ++COMMON_BEGIN;
	public static final short GC_SYSTEM_MESSAGE = ++COMMON_BEGIN;
	public static final short GC_WAITING_START = ++COMMON_BEGIN;
	public static final short GC_WAITING_OVER = ++COMMON_BEGIN;
	public static final short GC_COMMON_ROLE_EFFECT = ++COMMON_BEGIN;
	public static final short GC_COMMON_CHARGE = ++COMMON_BEGIN;
	public static final short GC_PING = ++COMMON_BEGIN;
	public static final short CG_PING = ++COMMON_BEGIN;
	public static final short GC_SYSTEM_NOTICE = ++COMMON_BEGIN;
	public static final short CG_HANDSHAKE = ++COMMON_BEGIN;
	public static final short GC_HANDSHAKE = ++COMMON_BEGIN;
	public static final short GC_SHOW_OPTION_DLG = ++COMMON_BEGIN;
	public static final short CG_SELECT_OPTION = ++COMMON_BEGIN;
	public static final short GC_COMMON_ASK_AND_ANSWER_URL = ++COMMON_BEGIN;

	// /////////////
	// 玩家登录退出模块
	// ////////////
	public static short PLAYER_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	/** 玩家请求登陆 */
	public static final short CG_PLAYER_LOGIN = ++PLAYER_BEGIN;
	/** 服务器返回玩家登陆结果 */
	public static final short GC_PLAYER_LOGIN_RESULT = ++PLAYER_BEGIN;
	/** 玩家请求角色列表 */
	public static final short CG_GET_CHAR_LIST = ++PLAYER_BEGIN;
	/** 服务器返回玩家角色列表 */
	public static final short GC_CHAR_LIST = ++PLAYER_BEGIN;
	/** 玩家选角色 */
	public static final short CG_SELECT_CHAR = ++PLAYER_BEGIN;
	/** 玩家创建角色 */
	public static final short CG_CREATE_CHAR = ++PLAYER_BEGIN;
	/** 玩家创建角色结果 */
	public static final short GC_CREATE_CHAR_RESULT = ++PLAYER_BEGIN;
	/** 通知客户端准备进入场景 */
	public static final short GC_ENTER_SCENE = ++PLAYER_BEGIN;
	/** 通知服务器已经准备好进入场景 */
	public static final short CG_ENTER_SCENE_READY = ++PLAYER_BEGIN;
	/** 服务器发给客户端的创建角色模版 */
	public static final short GC_CHARACTER_TEMPLATE = ++PLAYER_BEGIN;
	/** 自动生成角色名字请求 */
	public static final short CG_AUTO_NAME = ++PLAYER_BEGIN;
	/** 服务器返回自动生成的角色名字 */
	public static final short GC_AUTO_NAME = ++PLAYER_BEGIN;
	/** 购买体力 */
	public static final short CG_BUY_ENERGY = ++PLAYER_BEGIN;
	/** 请求恢复体力 */
	public static final short CG_RECOVER_ENERGY = ++PLAYER_BEGIN;
	/** 响应恢复体力 */
	public static final short GC_RECOVER_ENERGY = ++PLAYER_BEGIN;
	/** 开放功能 */
	public static final short GC_OPEN_GAME_FUNCS = ++PLAYER_BEGIN;
	/** 功能开放的信息 */
	public static final short GC_FUNCTION_INFO = ++PLAYER_BEGIN;
	/** 下发战斗泡泡内容 */
	public static final short GC_BATTLE_POP = ++PLAYER_BEGIN;
	/** 通知客户端直接进征战场景 */
	public static final short GC_DIRECT_TO_AREA_SCENE = ++PLAYER_BEGIN;
	/** 玩家使用cookie登录 */
	public static final short CG_PLAYER_COOLIE_LOGIN = ++PLAYER_BEGIN;
	/** 玩家心跳包，防止长时间不活动被TGW(server)踢掉 */
	public static final short CG_PLAYER_HEART_BEAT = ++PLAYER_BEGIN;
	/** 功能关闭的信息 */
	public static final short GC_CLOSE_GAME_FUNCS = ++PLAYER_BEGIN;
	/** 功能提示集合 */
	public static final short GC_FUNC_NOTIFIES = ++ PLAYER_BEGIN;
	/** 单个功能提示 */
	public static final short GC_FUNC_NOTIFY = ++ PLAYER_BEGIN;

	// /////////////
	// 玩家角色基本属性和操作模块
	// ////////////
	public static short HUMAN_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	/** 角色int型属性更新 */
	public static final short GC_PROPERTY_CHANGED_NUMBER = ++HUMAN_BEGIN;
	/** 角色对象型属性更新 */
	public static final short GC_PROPERTY_CHANGED_STRING = ++HUMAN_BEGIN;
	/** 角色基本信息 */
	public static final short GC_CHARACTER_INFO = ++HUMAN_BEGIN;
	/** 登录时候下发角色全部属性信息 */
	public static final short GC_CHARACTER_PROPERTIES = ++HUMAN_BEGIN;
	/** 属性点分配 */
	public static final short CG_DISTRIBUTE_PROPERTY_POINT = ++HUMAN_BEGIN;
	/** 角色升级事件 */
	public static final short GC_HUMAN_LEVEL_UP = ++HUMAN_BEGIN;
	/** 角色信息展示 */
	public static final short CG_CHARACTER_SHOW_INFO = ++HUMAN_BEGIN;
	/** 角色信息展示 */
	public static final short GC_CHARACTER_SHOW_INFO = ++HUMAN_BEGIN;
	/** 为了新手引导加的一个没用的消息 */
	public static final short CG_SHOW_CHARACTER_PANEL = ++HUMAN_BEGIN;
	/** 重置属性点 */
	public static final short CG_RESET_PROPERTY_POINT = ++HUMAN_BEGIN;
	/** 角色升级信息, 通知客户端展示 */
	public static final short GC_HUMAN_LEVEL_UP_INFO = ++HUMAN_BEGIN;

	// /////////////
	// 聊天模块
	// ////////////
	public static short CHAT_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 客户端端请求聊天 */
	public static final short CG_CHAT_MSG = ++CHAT_BEGIN;
	/** 服务器下发聊天信息 */
	public static final short GC_CHAT_MSG = ++CHAT_BEGIN;

	// /////////////
	// 建筑模块
	// ////////////
	public static short BUILDING_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 点击建筑 */
	public static final short CG_CLICK_BUILDING = ++BUILDING_BEGIN;
	/** 点击建筑子功能 */
	public static final short CG_CLICK_BUILDING_FUNC = ++BUILDING_BEGIN;
	/** 显示建筑功能列表 */
	public static final short GC_SHOW_BUILDING_FUNC = ++BUILDING_BEGIN;
	/** 返回所有建筑基础信息 */
	public static final short GC_BUILDING_LIST = ++BUILDING_BEGIN;
	/** 建筑升级 */
	public static final short CG_UPGRADE_BUILDING = ++BUILDING_BEGIN;
	/** 建筑升级面板 */
	public static final short GC_SHOW_BUILDING_UPGRADE_PANEL = ++BUILDING_BEGIN;

	// /////////////
	// 背包模块
	// ////////////
	public static short BAG_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 整理背包，更新背包所有物品 */
	public static final short CG_BAG_TIDY = ++BAG_BEGIN;
	/** 更新整个背包返回结果，在打开背包和整理背包后返回该结果 */
	public static final short GC_BAG_UPDATE = ++BAG_BEGIN;
	/** 移除物品 */
	public static final short CG_REMOVE_ITEM = ++BAG_BEGIN;
	/** 服务器返回移除物品处理结果 */
	public static final short GC_REMOVE_ITEM = ++BAG_BEGIN;
	/** 移动物品，包括物品换位置 */
	public static final short CG_MOVE_ITEM = ++BAG_BEGIN;
	/** 移动物品，包括物品换位置 */
	public static final short GC_MOVE_ITEM = ++BAG_BEGIN;
	/** 更新单个物品 */
	public static final short GC_ITEM_UPDATE = ++BAG_BEGIN;
	/** 客户端查询升级背包价格 */
	public static final short CG_QUERY_BAG_UPGRADE_PRICE = ++BAG_BEGIN;
	/** 服务器返回升级背包价格 */
	public static final short GC_BAG_UPGRADE_PRICE = ++BAG_BEGIN;
	/** 客户端发送背包升级请求 */
	public static final short CG_BAG_UPGRADE = ++BAG_BEGIN;
	/** 服务器返回背包升级结果 */
	public static final short GC_BAG_UPGRADE_RESULT = ++BAG_BEGIN;
	/** 使用物品 */
	public static final short CG_ITEM_USE = ++BAG_BEGIN;

	// /////////////
	// 商城模块
	// ////////////
	public static short MALL_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 购买商城物品 */
	public static final short CG_BUY_MALL_ITEM = ++MALL_BEGIN;
	/** 显示单个商品信息 */
	public static final short CG_SHOW_MALL_ITEM = ++MALL_BEGIN;
	/** 显示单个商品信息 */
	public static final short GC_SHOW_MALL_ITEM = ++MALL_BEGIN;
	/** 显示所有商品信息 */
	public static final short CG_SHOW_MALL_ITEM_LIST = ++MALL_BEGIN;
	/** 显示所有商品信息 */
	public static final short GC_SHOW_MALL_ITEM_LIST = ++MALL_BEGIN;
	/** 购买商品结果 */
	public static final short GC_BUY_MALL_ITEM = ++MALL_BEGIN;
	/** 查看商品价格 */
	public static final short CG_ASK_MALL_ITEM = ++MALL_BEGIN;
	/** 查看商品价格 */
	public static final short GC_ASK_MALL_ITEM = ++MALL_BEGIN;
	/** 返回购买商城物品的信息 */
	public static final short GC_ASK_MALL_ITEM_INFO = ++MALL_BEGIN;

	// ////////////
	// 公告模块
	// ////////////
	public static short BULLETIN_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 服务器发送的公告信息列表 */
	public static final short GC_BULLETIN_LIST = ++BULLETIN_BEGIN;
	/** 服务器通知停播某个公告 */
	public static final short GC_BULLETIN_STOP = ++BULLETIN_BEGIN;

	// ////////////
	// CD模块
	// ////////////
	public static short CD_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 所有cd信息 */
	public static final short GC_CD_QUEUE_INFOS = ++CD_BEGIN;
	/** 消除cd */
	public static final short CG_REMOVE_CD = ++CD_BEGIN;
	/** 单个cd信息 */
	public static final short GC_CD_QUEUE_INFO = ++CD_BEGIN;
	/** 加速cd */
	public static final short CG_CD_SPEED_UP = ++CD_BEGIN;
	/** 加速cd */
	public static final short GC_CD_SPEED_UP = ++CD_BEGIN;

	// ////////////
	// 任务模块
	// ////////////
	public static short QUEST_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 服务器返回任务列表 */
	public static final short GC_QUEST_LIST = ++QUEST_BEGIN;
	/** 客户端请求完成任务 */
	public static final short CG_FINISH_QUEST = ++QUEST_BEGIN;
	/** 服务端通知状态变更 */
	public static final short GC_QUEST_STATE = ++QUEST_BEGIN;
	/** 服务器返回日常任务列表 */
	public static final short GC_DAILY_QUEST_LIST = ++QUEST_BEGIN;
	/** 服务器返回的单个任务数据更新的消息 */
	public static final short GC_SINGLE_QUEST_UPDATE = ++QUEST_BEGIN;
	/** 请求日常任务列表 */
	public static final short CG_GET_DAILY_QUEST_LIST = ++QUEST_BEGIN;
	/** 服务端通知追加新的任务 */
	public static final short GC_APPEND_NEW_QUESTS = ++QUEST_BEGIN;
	/** 服务器通知客户端更新计数器 */
	public static final short GC_UPDATE_QUEST_COUNTER = ++QUEST_BEGIN;
	/** 服务器通知客户端端更新玩家日常任务积分 */
	public static final short GC_UPDATE_DAILY_QUEST_SCORE = ++QUEST_BEGIN;
	/** 客户端请求打开日常任务奖励箱子 */
	public static final short CG_OPEN_DAILY_QUEST_REWARD_BOX = ++QUEST_BEGIN;
	/** 服务器通知日常任务奖励箱子状态改变 */
	public static final short GC_DAILY_QUEST_REWARD_BOX_STATE_UPDATE = ++QUEST_BEGIN;
	/** 日常任务是有有可领取的奖励 */
	public static final short GC_DAILY_QUEST_STATE = ++QUEST_BEGIN;
	/** 接受日常任务 */
	public static final short CG_RECEIVE_DAILY_QUEST = ++QUEST_BEGIN;
	/** 请求自动完成日常任务 */
	public static final short CG_AUTO_COMPLETE_DAILY_QUEST = ++QUEST_BEGIN;
	/** 响应自动完成日常任务 */
	public static final short GC_AUTO_COMPLETE_DAILY_QUEST = ++QUEST_BEGIN;
	/** 取消日常任务 */
	public static final short CG_ABORT_DAILY_QUEST = ++QUEST_BEGIN;
	/** 申请加速完成日常任务 */
	public static final short CG_APPLY_QUICKLY_COMPLETE_DAILY_QUEST = ++QUEST_BEGIN;
	/** 响应申请加速完成日常任务 */
	public static final short GC_APPLY_QUICKLY_COMPLETE_DAILY_QUEST = ++QUEST_BEGIN;
	/** 请求加速完成日常任务 */
	public static final short CG_QUICKLY_COMPLETE_DAILY_QUEST = ++QUEST_BEGIN;
	/** 请求领取日常任务奖励 */
	public static final short CG_GET_DAILY_QUEST_REWARD = ++QUEST_BEGIN;
	/** 响应领取日常任务奖励 */
	public static final short GC_GET_DAILY_QUEST_REWARD = ++QUEST_BEGIN;
	/** 请求刷新日常任务列表 */
	public static final short CG_REFRESH_DAILY_QUEST_LIST = ++QUEST_BEGIN;
	/** 响应刷新日常任务列表 */
	public static final short GC_REFRESH_DAILY_QUEST_LIST = ++QUEST_BEGIN;
	
	// ////////////
	// 商店模块
	// ////////////
	public static short SHOP_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 购买商城物品 */
	public static final short CG_BUY_SHOP_ITEM = ++SHOP_BEGIN;
	/** 出售商城物品 */
	public static final short CG_SELL_SHOP_ITEM = ++SHOP_BEGIN;
	/** 打开商城 */
	public static final short CG_SHOW_SHOP_ITEM_LIST = ++SHOP_BEGIN;
	/** 返回打开商城的信息 */
	public static final short GC_SHOW_SHOP_ITEM_LIST = ++SHOP_BEGIN;
	/** 打开神秘商店 */
	public static final short CG_SHOW_SPECIAL_SHOP_PANEL = ++SHOP_BEGIN;
	/** 打开神秘商店 */
	public static final short GC_SHOW_SPECIAL_SHOP_PANEL = ++SHOP_BEGIN;
	/** 购买神秘商店物品 */
	public static final short CG_BUY_SPECIAL_SHOP_ITEM = ++SHOP_BEGIN;
	/** 购买神秘商店物品 */
	public static final short GC_BUY_SPECIAL_SHOP_ITEM = ++SHOP_BEGIN;
	/** 刷新神秘商店 */
	public static final short CG_REFRESH_SPECIAL_SHOP = ++SHOP_BEGIN;

	// ////////////
	// 占星模块
	// ////////////
	public static short ASTROLOGICAL_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求占星 */
	public static final short CG_ASTROLOGICAL = ++ASTROLOGICAL_BEGIN;

	// /////////////
	// 主城模块
	// ////////////
	public static short LEVY_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 点击征收 */
	public static final short CG_LEVY = ++LEVY_BEGIN;
	/** 打开主城面板 */
	public static final short CG_OPEN_MAIN_CITY_PANEL = ++LEVY_BEGIN;
	/** 刷新收集信息 */
	public static final short GC_UPDATE_MAIN_CITY_PANEL = ++LEVY_BEGIN;
	/** 收集魔法石 */
	public static final short CG_COLLECT_MAGIC_STONE = ++LEVY_BEGIN;
	/** 收集魔法石 */
	public static final short GC_COLLECT_MAGIC_STONE = ++LEVY_BEGIN;
	/** 收集魔法石任务完成 */
	public static final short GC_COLLECT_MAGIC_STONE_COMPLETE = ++LEVY_BEGIN;
	/** 返回征收结果 */
	public static final short GC_LEVY = ++LEVY_BEGIN;
	/** 请求押注 */
	public static final short CG_LEVY_BET = ++LEVY_BEGIN;
	/** 响应押注 */
	public static final short GC_LEVY_BET = ++LEVY_BEGIN;
	/** 请求攻城怪物信息 */
	public static final short CG_GET_MAIN_CITY_MONSTER_INFO = ++LEVY_BEGIN;
	/** 发送攻城怪物信息 */
	public static final short GC_SEND_MAIN_CITY_MONSTER_INFO = ++LEVY_BEGIN;
	/** 攻打主城怪物 */
	public static final short CG_BATTLE_MAIN_CITY_MONSTER = ++LEVY_BEGIN;
	
	// /////////////
	// RPC
	// ////////////
	public static short RPC_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** RPC请求消息 */
	public static final short RPC_REQUEST = ++RPC_BEGIN;
	/** RPC响应 */
	public static final short RPC_RESPONSE = ++RPC_BEGIN;
	/** RPC错误 */
	public static final short RPC_ERROR = ++RPC_BEGIN;

	// /////////////
	// 收获模块
	// ////////////
	public static short HARVEST_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 显示收获矿石信息 */
	public static final short GC_SHOW_HARVEST_MINE_INFO = ++HARVEST_BEGIN;
	/** 显示收获木材信息 */
	public static final short GC_SHOW_HARVEST_WOOD_INFO = ++HARVEST_BEGIN;
	/** 收获材料（矿石/木材） */
	public static final short CG_HARVEST = ++HARVEST_BEGIN;
	/** 收获矿石 */
	public static final short GC_HARVEST_MINE = ++HARVEST_BEGIN;
	/** 收获木材 */
	public static final short GC_HARVEST_WOOD = ++HARVEST_BEGIN;
	/** 增加收获材料次数（矿石/木材） */
	public static final short CG_ADD_HARVEST_NUM = ++HARVEST_BEGIN;
	/** 增加收获材料次数后的更新消息 */
	public static final short GC_ADD_HARVEST_NUM = ++HARVEST_BEGIN;
	/** 更改增加收获次数消费魔晶是否提示的状态 */
	public static final short CG_CHANGE_HARVEST_COST_ALERT_STATE = ++HARVEST_BEGIN;
	/** 收获材料时发生奇遇 */
	public static final short GC_HARVEST_ENCOUNTER = ++HARVEST_BEGIN;
	/** 收获时发生奇遇后玩家的选择 */
	public static final short CG_HARVEST_ENCOUNTER_CHOICE = ++HARVEST_BEGIN;
	/** 收获矿石的奇遇结果 */
	public static final short GC_HARVEST_MINE_ENCOUNTER_RESULT = ++HARVEST_BEGIN;
	/** 收获木材的奇遇结果 */
	public static final short GC_HARVEST_WOOD_ENCOUNTER_RESULT = ++HARVEST_BEGIN;
	/** 收获所有矿坑 */
	public static final short CG_ALL_MINE_HARVEST = ++HARVEST_BEGIN;
	/** 请求清除所有矿坑cd需要的花费 */
	public static final short CG_REMOVE_ALL_MINE_CD = ++HARVEST_BEGIN;
	/** 请求清除所有矿坑cd需要的花费 */
	public static final short GC_REMOVE_ALL_MINE_CD = ++HARVEST_BEGIN;
	/** 清除所有矿坑cd */
	public static final short CG_REMOVE_ALL_MINE_CD_CONFIRM = ++HARVEST_BEGIN;

	// /////////////
	// 道具模块
	// ////////////
	public static short ITEM_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求镶嵌宝石 */
	public static final short CG_GEM_EMBED = ++ITEM_BEGIN;
	/** 请求镶嵌宝石 */
	public static final short GC_GEM_EMBED = ++ITEM_BEGIN;
	/** 请求卸下某个位置的宝石 */
	public static final short CG_GEM_EXTRACT = ++ITEM_BEGIN;
	/** 成功卸下宝石 */
	public static final short GC_GEM_EXTRACT = ++ITEM_BEGIN;
	/** 打开宝石镶嵌面板 */
	public static final short GC_SHOW_GEM_EMBED_PANEL = ++ITEM_BEGIN;
	/** 开孔 */
	public static final short CG_GEM_PUNCH = ++ITEM_BEGIN;
	/** 开孔成功 */
	public static final short GC_GEM_PUNCH = ++ITEM_BEGIN;
	/** 装备放到宝石镶嵌面板 */
	public static final short CG_PUT_EQUIP = ++ITEM_BEGIN;
	/** 返回装备上宝石信息 */
	public static final short GC_PUT_EQUIP = ++ITEM_BEGIN;
	/** 请求装备强化 */
	public static final short CG_EQUIP_UPGRADE = ++ITEM_BEGIN;
	/** 选中要强化的装备 */
	public static final short CG_SELECT_EQUIP = ++ITEM_BEGIN;
	/** 返回强化装备下一级装备信息 */
	public static final short GC_SELECT_EQUIP = ++ITEM_BEGIN;
	/** 打开装备强化面板 */
	public static final short GC_SHOW_EQUIP_UPGRADE_PANEL = ++ITEM_BEGIN;
	/** 发起装备打造请求 */
	public static final short CG_SELECT_EQUIP_PAPER_TO_MAKE = ++ITEM_BEGIN;
	/** 更新装备打造面板数据 */
	public static final short GC_UPDATE_EQUIP_MAKE_PANEL = ++ITEM_BEGIN;
	/** 发起装备打造请求 */
	public static final short CG_EQUIP_MAKE = ++ITEM_BEGIN;
	/** 返回装备打造结果 */
	public static final short GC_EQUIP_MAKE = ++ITEM_BEGIN;
	/** 装备强化结果 */
	public static final short GC_EQUIP_UPGRADE = ++ITEM_BEGIN;
	/** 请求展示物品或其他 */
	public static final short CG_SHOW_SOMETHING = ++ITEM_BEGIN;
	/** 返回展示的物品信息 */
	public static final short GC_SHOW_ITEM = ++ITEM_BEGIN;
	/** 装备打孔花费确认 */
	public static final short CG_GEM_PUNCH_CONFIRM = ++ITEM_BEGIN;
	/** 装备打孔花费确认 */
	public static final short GC_GEM_PUNCH_CONFIRM = ++ITEM_BEGIN;
	/** 下发快捷购买的物品信息 */
	public static final short GC_ITEM_QUICK_BUY_INFO = ++ITEM_BEGIN;
	/** 材料引导 */
	public static final short CG_MATERIAL_GUIDE = ++ITEM_BEGIN;
	/** 材料引导 */
	public static final short GC_MATERIAL_GUIDE = ++ITEM_BEGIN;
	/** 判断装备上否可以升级 */
	public static final short CG_CHECK_EQUIP_MAKE = ++ITEM_BEGIN;
	/** 判断装备上否可以升级 */
	public static final short GC_CHECK_EQUIP_MAKE = ++ITEM_BEGIN;
	/** 选中装备去装备制作 */
	public static final short CG_SELECT_EQUIP_TO_MAKE = ++ITEM_BEGIN;
	/** 选中装备去装备制作 */
	public static final short GC_SELECT_EQUIP_TO_MAKE = ++ITEM_BEGIN;
	/** 响应使用秘药 */
	public static final short GC_USE_NOSTRUM = ++ITEM_BEGIN;

	// /////////////
	// 管理服和游戏服的协议制定
	// ////////////
	public static short MG_BEGIN = STAUS_BEGIN;
	/** 管理服向游戏服请求服务器组状态 */
	public static final short MG_GET_REALM_INFO = ++MG_BEGIN;
	/** 游戏服返回服务器组信息 */
	public static final short GM_REALM_INFO = ++MG_BEGIN;
	/** 管理服请求关闭对外服务 */
	public static final short MG_STOP_EXTERNAL_SERVICE = ++MG_BEGIN;
	/** 管理服请求开启对外服务 */
	public static final short MG_START_EXTERNAL_SERVICE = ++MG_BEGIN;
	/** 管理服请求添加公告 */
	public static final short MG_BULLETIN_ADD = ++MG_BEGIN;
	/** 管理服请求更新公告 */
	public static final short MG_BULLETIN_UPDATE = ++MG_BEGIN;
	/** 管理服请求移除公告 */
	public static final short MG_BULLETIN_REMOVE = ++MG_BEGIN;
	/** 游戏服返回添加公告结果 */
	public static final short GM_BULLETIN_ADD = ++MG_BEGIN;
	/** 游戏服返回更新公告结果 */
	public static final short GM_BULLETIN_UPDATE = ++MG_BEGIN;
	/** 游戏服返回移除公告结果 */
	public static final short GM_BULLETIN_REMOVE = ++MG_BEGIN;
	/** 管理服请求发送邮件 */
	public static final short MG_SEND_MAIL = ++MG_BEGIN;
	/** 游戏服返回邮件发送结果 */
	public static final short GM_SEND_MAIL = ++MG_BEGIN;
	/** 管理服请求将某个角色踢下线 */
	public static final short MG_KICK_OFF_CHARACTER = ++MG_BEGIN;
	/** 游戏服返回将某个角色踢下线的结果 */
	public static final short GM_KICK_OFF_CHARACTER = ++MG_BEGIN;
	/** 请求查询在线人数 */
	public static final short MG_QUERY_ONLINE_NUMBER = ++MG_BEGIN;
	/** 游戏服返回在线人数 */
	public static final short GM_QUERY_ONLINE_NUMBER = ++MG_BEGIN;
	/** 请求查询玩家在线状态 */
	public static final short MG_CHECK_CHARACTER_ONLINE_STATE = ++MG_BEGIN;
	/** 游戏服返回玩家在线状态 */
	public static final short GM_CHECK_CHARACTER_ONLINE_STATE = ++MG_BEGIN;
	/** 请求查询玩家状态统计信息 */
	public static final short MG_QUERY_PLAYER_STATE_STATISTIC = ++MG_BEGIN;
	/** 游戏服返回玩家状态数据 */
	public static final short GM_QUERY_PLAYER_STATE_STATISTIC = ++MG_BEGIN;
	/** 管理服请求将所有角色踢下线 */
	public static final short MG_KICK_OFF_ALL_CHARACTER = ++MG_BEGIN;
	/** 游戏服返回将所有角色踢下线的结果 */
	public static final short GM_KICK_OFF_ALL_CHARACTER = ++MG_BEGIN;
	/** 更新运营系统的设置 */
	public static final short MG_UPDATE_MARKET_ACT_SETING = ++MG_BEGIN;
	/** 返回更新运营系统的设置的结果 */
	public static final short GM_UPDATE_MARKET_ACT_SETING = ++MG_BEGIN;
	/** 数据实体的数据库请求信息 */
	public static final short MG_DB_ENTITY_OPERATION_INFO = ++MG_BEGIN;
	/** 返回数据实体的数据库请求信息 */
	public static final short GM_DB_ENTITY_OPERATION_INFO = ++MG_BEGIN;
	/** 更新定时邮件请求 */
	public static final short MG_UPDATE_MAIL = ++MG_BEGIN;
	/** 返回更新定时邮件结果 */
	public static final short GM_UPDATE_MAIL = ++MG_BEGIN;
	/** 取消定时邮件发送请求 */
	public static final short MG_CANCEL_MAIL = ++MG_BEGIN;
	/** 返回取消定时邮件结果 */
	public static final short GM_CANCEL_MAIL = ++MG_BEGIN;
	/** 请求查询在线玩家 */
	public static final short MG_ONLINE_HUMAN_REQUEST = ++MG_BEGIN;
	/** 返回在线玩家信息 */
	public static final short GM_ONLINE_HUMAN = ++MG_BEGIN;
	/** 更新登陆墙状态 */
	public static final short MG_UPDATE_LOGIN_WALL_STATE = ++MG_BEGIN;
	/** 查询登陆墙状态 */
	public static final short MG_GET_LOGIN_WALL_STATE = ++MG_BEGIN;
	/** 返回登陆墙状态 */
	public static final short GM_GET_LOGIN_WALL_STATE = ++MG_BEGIN;
	/** 停服 */
	public static final short MG_STOP_SERVER = ++MG_BEGIN;
	/** 获取定时邮件请求 */
	public static final short MG_GET_TIMING_MAIL_LIST = ++MG_BEGIN;
	/** 获取定时邮件列表响应 */
	public static final short GM_GET_TIMING_MAIL_LIST = ++MG_BEGIN;
	/** 支付服务通知发货 */
	public static final short PG_SEND_ITEM = ++MG_BEGIN;

	// /////////////
	// 好友模块
	// ////////////
	public static short FRIEND_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 系统推荐好友 */
	public static final short CG_SHOW_LUCK_FRIENDS = ++FRIEND_BEGIN;
	/** 返回系统推荐好友 */
	public static final short GC_SHOW_LUCK_FRIENDS = ++FRIEND_BEGIN;
	/** 请求添加多个推荐好友 */
	public static final short CG_ADD_FRIENDS = ++FRIEND_BEGIN;
	/** 请求添加好友 */
	public static final short CG_ADD_FRIEND = ++FRIEND_BEGIN;
	/** 请求删除好友 */
	public static final short CG_REMOVE_FRIEND = ++FRIEND_BEGIN;
	/** 请求显示好友列表 */
	public static final short CG_SHOW_FRIENDS = ++FRIEND_BEGIN;
	/** 返回好友列表 */
	public static final short GC_SHOW_FRIENDS = ++FRIEND_BEGIN;
	/** 请求好友留言 */
	public static final short CG_FRIEND_CHAT = ++FRIEND_BEGIN;
	/** 接收好友留言 */
	public static final short GC_FRIEND_CHAT = ++FRIEND_BEGIN;
	/** 查看好友申请列表 */
	public static final short CG_SHOW_FRIEND_APPLY_LIST = ++FRIEND_BEGIN;
	/** 返回好友申请列表 */
	public static final short GC_SHOW_FRIEND_APPLY_LIST = ++FRIEND_BEGIN;
	/** 查看自己发送出的好友申请 */
	public static final short CG_SHOW_SELF_APPLY_LIST = ++FRIEND_BEGIN;
	/** 返回自己发送出的好友申请 */
	public static final short GC_SHOW_SELF_APPLY_LIST = ++FRIEND_BEGIN;
	/** 同意好友申请 */
	public static final short CG_ADD_FRIEND_AGREE = ++FRIEND_BEGIN;
	/** 拒绝好友申请 */
	public static final short CG_ADD_FRIEND_REFUSE = ++FRIEND_BEGIN;
	/** 赠送好友体力 */
	public static final short CG_GIVE_ENERGY = ++FRIEND_BEGIN;
	/** 领取好友赠送的体力 */
	public static final short CG_GET_ENERGY = ++FRIEND_BEGIN;
	/** A请求添加好友B,如果B在线将A的信息发给B */
	public static final short GC_FRIEND_APPLY = ++FRIEND_BEGIN;
	/** 取消自己发出的好友申请 */
	public static final short CG_CANCEL_APPLY_FRIEND = ++FRIEND_BEGIN;
	/** 好友赠送体力的通知 */
	public static final short GC_GIVE_ENERGY = ++FRIEND_BEGIN;
	/** 删除好友 */
	public static final short GC_REMOVE_FRIEND = ++FRIEND_BEGIN;
	/** 更新好友信息 */
	public static final short GC_UPDATE_FRIEND_INFO = ++FRIEND_BEGIN;
	/** 好友申请列表里面某个记录删除(同意或者取消时发) */
	public static final short GC_REMOVE_APPLY = ++FRIEND_BEGIN;
	/** 添加好友成功 */
	public static final short GC_ADD_FRIEND_INFO = ++FRIEND_BEGIN;
	/** 好友剩余体力领取次数 */
	public static final short GC_LEFT_FRIEND_REWARD_TIMES = ++FRIEND_BEGIN;
	/** 某玩家已不在线 */
	public static final short GC_FRIEND_OFFLINE = ++FRIEND_BEGIN;
	/** 最近联系人 */
	public static final short CG_SHOW_LATEST_FRIENDS = ++FRIEND_BEGIN;
	/** 最近联系人 */
	public static final short GC_SHOW_LATEST_FRIENDS = ++FRIEND_BEGIN;
	/** 好友切磋信息 */
	public static final short CG_SHOW_FRIEND_BATTLEINFOS = ++FRIEND_BEGIN;
	/** 好友切磋信息 */
	public static final short GC_SHOW_FRIEND_BATTLEINFOS = ++FRIEND_BEGIN;
	/** 新增好友切磋信息 */
	public static final short GC_ADD_FRIEND_BATTLEINFO = ++FRIEND_BEGIN;
	/** 推荐添加好友 */
	public static final short GC_RECOMMEND_FRIEND_ADD_INFO = ++FRIEND_BEGIN;
	/** 打开私聊面板 */
	public static final short CG_OPEN_PRIVATE_CHAT_PANEL = ++FRIEND_BEGIN;
	/** 打开私聊面板 */
	public static final short GC_OPEN_PRIVATE_CHAT_PANEL = ++FRIEND_BEGIN;
	/** 好友切磋结果 */
	public static final short GC_FRIEND_BATTLE_RESULT = ++FRIEND_BEGIN;

	// /////////////
	// 排行榜
	// ////////////
	public static short RANK_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开排行榜 */
	public static final short CG_SHOW_RANK = ++RANK_BEGIN;
	/** 显示等级排行榜数据 */
	public static final short GC_SHOW_LEVEL_RANK = ++RANK_BEGIN;
	/** 显示军衔排行榜数据 */
	public static final short GC_SHOW_TITLE_RANK = ++RANK_BEGIN;
	/** 显示荣誉排行榜数据 */
	public static final short GC_SHOW_HONOR_RANK = ++RANK_BEGIN;
	/** 显示VIP排行榜数据 */
	public static final short GC_SHOW_VIP_RANK = ++RANK_BEGIN;
	/** 显示军团等级排行榜数据 */
	public static final short GC_SHOW_LEGION_LEVEL_RANK = ++RANK_BEGIN;
	/** 显示军团成员排行榜数据 */
	public static final short GC_SHOW_LEGION_MEMBER_RANK = ++RANK_BEGIN;
	/** 获取排行奖励 */
	public static final short CG_GET_RANK_REWARD = ++RANK_BEGIN;
	/** 获取等级排行奖励 */
	public static final short GC_GET_LEVEL_RANK_REWARD = ++RANK_BEGIN;

	// /////////////
	// 邮件
	// ////////////
	public static short MAIL_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 客户端请求打开邮件列表 */
	public static final short CG_SHOW_MAILLIST = ++MAIL_BEGIN;
	/** 服务器返回邮件列表给客户端 */
	public static final short GC_SHOW_MAILLIST = ++MAIL_BEGIN;
	/** 请求查看邮件 */
	public static final short CG_SHOW_MAIL = ++MAIL_BEGIN;
	/** 发送邮件信息到客户端 */
	public static final short GC_SHOW_MAIL = ++MAIL_BEGIN;
	/** 请求拾取附件中物品 */
	public static final short CG_PICKUP_ATTACHMENT = ++MAIL_BEGIN;
	/** 服务器端拾取附件中物品后返回 */
	public static final short GC_PICKUP_ATTACHMENT = ++MAIL_BEGIN;
	/** 请求删除邮件 */
	public static final short CG_DELETE_MAIL = ++MAIL_BEGIN;
	/** 删除邮件 */
	public static final short GC_DELETE_MAIL = ++MAIL_BEGIN;
	/** 请求发送邮件 */
	public static final short CG_SEND_MAIL = ++MAIL_BEGIN;
	/** 收到邮件 */
	public static final short GC_RECEIVE_MAIL = ++MAIL_BEGIN;
	/** 服务器返回发送邮件的结果 */
	public static final short GC_SEND_MAIL = ++MAIL_BEGIN;

	// /////////////
	// 宝石相关
	// ////////////
	public static short GEM_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 查看收获的宝石信息 */
	public static final short CG_VIEW_HARVEST_GEM = ++GEM_BEGIN;
	/** 服务器返回收获宝石信息 */
	public static final short GC_VIEW_HARVEST_GEM = ++GEM_BEGIN;
	/** 客户端收获宝石的请求消息 */
	public static final short CG_HARVEST_GEM = ++GEM_BEGIN;
	/** 服务器端返回收获宝石的结果 */
	public static final short GC_HARVEST_GEM = ++GEM_BEGIN;
	/** 服务器端更新收获宝石信息 */
	public static final short GC_UPDATE_HARVEST_GEM = ++GEM_BEGIN;
	/** 服务器端更新收获宝石信息 */
	public static final short CG_SELECT_GEM_TO_SYNTHESIS = ++GEM_BEGIN;
	/** 服务器端更新收获宝石信息 */
	public static final short GC_UPDATE_GEM_SYNTHESIS_PANEL = ++GEM_BEGIN;
	/** 客户端合成宝石请求消息 */
	public static final short CG_GEM_SYNTHESIS = ++GEM_BEGIN;
	/** 服务器端合成宝石处理结果 */
	public static final short GC_GEM_SYNTHESIS = ++GEM_BEGIN;

	// /////////////
	// 星运
	// ////////////
	public static short HOROSCOPE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开星运面板 */
	public static final short CG_SHOW_HOROSCOPE_PANEL = ++HOROSCOPE_BEGIN;
	/** 打开星运面板 */
	public static final short GC_SHOW_HOROSCOPE_PANEL = ++HOROSCOPE_BEGIN;
	/** 占星 */
	public static final short CG_HOROSCOPE_GAMBLE = ++HOROSCOPE_BEGIN;
	/** 占星 */
	public static final short GC_HOROSCOPE_GAMBLE = ++HOROSCOPE_BEGIN;
	/** 一键占星 */
	public static final short CG_HOROSCOPE_GAMBLE_AUTO = ++HOROSCOPE_BEGIN;
	/** 一键占星 */
	public static final short GC_HOROSCOPE_GAMBLE_AUTO = ++HOROSCOPE_BEGIN;
	/** 合成星运确认消息 */
	public static final short CG_HOROSCOPE_COMPOUND_CONFIRM = ++HOROSCOPE_BEGIN;
	/** 合成 */
	public static final short GC_HOROSCOPE_COMPOUND = ++HOROSCOPE_BEGIN;
	/** 一键合成 */
	public static final short CG_HOROSCOPE_COMPOUND_AUTO = ++HOROSCOPE_BEGIN;
	/** 一键合成 */
	public static final short GC_HOROSCOPE_COMPOUND_AUTO = ++HOROSCOPE_BEGIN;
	/** 装备星运 */
	public static final short CG_HOROSCOPE_ON = ++HOROSCOPE_BEGIN;
	/** 卸下星运 */
	public static final short CG_HOROSCOPE_OFF = ++HOROSCOPE_BEGIN;
	/** 移动星运 */
	public static final short CG_HOROSCOPE_SWAP = ++HOROSCOPE_BEGIN;
	/** 移除星运 */
	public static final short GC_HOROSCOPE_REMOVE = ++HOROSCOPE_BEGIN;
	/** 更新星运 */
	public static final short GC_HOROSCOPE_UPDATE = ++HOROSCOPE_BEGIN;
	/** 更新占星师状态 */
	public static final short GC_UPDATE_STARGAZERS = ++HOROSCOPE_BEGIN;
	/** 星运描述信息 */
	public static final short CG_HOROSCOPE_INTRODUCE = ++HOROSCOPE_BEGIN;
	/** 星运描述信息 */
	public static final short GC_HOROSCOPE_INTRODUCE = ++HOROSCOPE_BEGIN;
	/** 星运仓库开格子价格询问 */
	public static final short CG_HOROSCOPE_STORAGE_UPGRADE_PRICE = ++HOROSCOPE_BEGIN;
	/** 星运仓库开格子价格询问 */
	public static final short GC_HOROSCOPE_STORAGE_UPGRADE_PRICE = ++HOROSCOPE_BEGIN;
	/** 星运仓库开格子 */
	public static final short CG_HOROSCOPE_STORAGE_UPGRADE = ++HOROSCOPE_BEGIN;
	/** 星运仓库开格子 */
	public static final short GC_HOROSCOPE_STORAGE_UPGRADE = ++HOROSCOPE_BEGIN;
	/** 更新某位置同时删除某星运 */
	public static final short GC_HOROSCOPE_UPDATE_AND_REMOVE = ++HOROSCOPE_BEGIN;
	/** 已装备星运的信息 */
	public static final short GC_EQUIP_HOROSCOPE_INFOS = ++HOROSCOPE_BEGIN;
	/** 拾取星运 */
	public static final short CG_HOROSCOPE_PICK_UP = ++HOROSCOPE_BEGIN;

	// /////////////
	// 科技
	// ////////////
	public static short TECHNOLOGY_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 查看科技具体信息 */
	public static final short CG_SHOW_TECHNOLOGY_INFO = ++TECHNOLOGY_BEGIN;
	/** 科技升级 */
	public static final short CG_UPGRADE_TECHNOLOGY = ++TECHNOLOGY_BEGIN;
	/** 打开科技面板 */
	public static final short GC_SHOW_TECHNOLOGY_PANEL = ++TECHNOLOGY_BEGIN;
	/** 显示科技具体信息 */
	public static final short GC_SHOW_TECHNOLOGY_INFO = ++TECHNOLOGY_BEGIN;
	/** 科技升级结果信息 */
	public static final short GC_UPGRADE_TECHNOLOGY = ++TECHNOLOGY_BEGIN;
	/** 打开科技面板 */
	public static final short CG_SHOW_TECHNOLOGY_PANEL = ++TECHNOLOGY_BEGIN;

	// /////////////
	// 战斗
	// ////////////
	public static short BATTLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 服务器下发开始战斗的信息 */
	public static final short GC_START_BATTLE_INFO = ++BATTLE_BEGIN;
	/** 客户端请求和地图怪开始战斗 */
	public static final short CG_START_BATTLE_WITH_MONSTER = ++BATTLE_BEGIN;
	/** 客户端请求普通攻击(交换宝石) */
	public static final short CG_NORMAL_ATTACK = ++BATTLE_BEGIN;
	/** 服务器通知玩家可以开始行动了 */
	public static final short GC_START_BATTLE_ACTION = ++BATTLE_BEGIN;
	/** 服务器通知客户端普通攻击 */
	public static final short GC_NORMAL_ATTACK = ++BATTLE_BEGIN;
	/** 服务器通知客户端更新棋盘 */
	public static final short GC_UPDATE_CHESS_BOARD = ++BATTLE_BEGIN;
	/** 服务器通知客户端开始行动计时 */
	public static final short GC_START_ACTION_TIMER = ++BATTLE_BEGIN;
	/** 服务器通知客户端行动超时 */
	public static final short GC_CHOOSE_ACTION_TIMEOUT = ++BATTLE_BEGIN;
	/** 客户端通知服务器动画播放完了 */
	public static final short CG_ANIMATION_OVER = ++BATTLE_BEGIN;
	/** 客户端通知服务端战斗资源加载完毕 */
	public static final short CG_BATTLE_RESOURCE_LOADED = ++BATTLE_BEGIN;
	/** 服务端通知客户端转换宝石颜色 */
	public static final short GC_CHANGE_GEMS_COLOR = ++BATTLE_BEGIN;
	/** 服务端通知客户端战斗结束 */
	public static final short GC_BATTLE_FINISHED = ++BATTLE_BEGIN;
	/** 服务端通知退出战斗 */
	public static final short GC_EXIT_BATTLE = ++BATTLE_BEGIN;
	/** 服务端通知角色死亡 */
	public static final short GC_BATTLE_UNIT_DEAD = ++BATTLE_BEGIN;
	/** 服务端通知新的战斗回合 */
	public static final short GC_BATTLE_NEW_ROUND = ++BATTLE_BEGIN;
	/** 客户端请求和指定好友角色开始战斗 */
	public static final short CG_START_BATTLE_WITH_FRIEND = ++BATTLE_BEGIN;
	/** 服务器通知有战斗邀请 */
	public static final short GC_JOIN_BATTLE_REQUEST = ++BATTLE_BEGIN;
	/** 客户端响应战斗邀请 */
	public static final short CG_JOIN_BATTLE_RESPONSE = ++BATTLE_BEGIN;
	/** 通知客户端额外回合 */
	public static final short GC_ADD_BATTLE_ACTION_TIME = ++BATTLE_BEGIN;
	/** 服务器通知客户端棋盘信息 */
	public static final short GC_CHESS_BOARD_INFO = ++BATTLE_BEGIN;
	/** 客户端通知服务器动重置棋盘画播放完了 */
	public static final short CG_RESET_CHESSBOARD_ANIMATION_OVER = ++BATTLE_BEGIN;
	/** 服务器通知调试战斗棋盘不同步 */
	public static final short GC_DEBUG_CHESSBOARD_SYNC_ERROR = ++BATTLE_BEGIN;
	/** 服务器通知更新黑宝石攻击加成 */
	public static final short GC_UPDATE_BLACK_GEM_ATTACK_ADD_RATE = ++BATTLE_BEGIN;
	/** 服务器通知等待对方准备好 */
	public static final short GC_BATTLE_WAITING_OTHER_UNIT_READY = ++BATTLE_BEGIN;
	/** 服务器通知网络环境太差, 请重试 */
	public static final short GC_BAD_NET_ENVIRONMENT = ++BATTLE_BEGIN;
	/** 请求战斗聊天信息 */
	public static final short CG_BATTLE_CHAT = ++BATTLE_BEGIN;
	/** 服务器广播战斗聊天信息 */
	public static final short GC_BATTLE_CHAT = ++BATTLE_BEGIN;
	/** 客户端请求托管战斗 */
	public static final short CG_REQUEST_HOSTING_BATTLE = ++BATTLE_BEGIN;
	/** 服务器通知托管战斗开始 */
	public static final short GC_START_HOSTING_BATTLE = ++BATTLE_BEGIN;
	/** 客户端请求投降战斗 */
	public static final short CG_REQUEST_BATTLE_GIVE_UP = ++BATTLE_BEGIN;
	/** 服务器通知战斗投降 */
	public static final short GC_BATTLE_GIVE_UP = ++BATTLE_BEGIN;
	/** 客户端已经离开了战斗场景通知 */
	public static final short CG_ALREADY_LEAVE_BATTLE_SCENE = ++BATTLE_BEGIN;
	/** 客户端请求取消托管 */
	public static final short CG_REQUEST_CANCEL_HOSTING_BATTLE = ++BATTLE_BEGIN;
	/** 服务器通知取消托管成功 */
	public static final short GC_CANCEL_HOSTING_BATTLE_SUCCEED = ++BATTLE_BEGIN;
	/** 通知等待对方接受战斗邀请 */
	public static final short GC_WAITTING_BATTLE_RESPONSE = ++BATTLE_BEGIN;

	// /////////////
	// 技能
	// ////////////
	public static short SKILL_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 客户端请求释放技能 */
	public static final short CG_USE_SKILL = ++SKILL_BEGIN;
	/** 服务端通知客户端使用技能 */
	public static final short GC_USE_SKILL = ++SKILL_BEGIN;
	/** 服务器通知技能效果直接作用 */
	public static final short GC_EFFECT_DIRECT = ++SKILL_BEGIN;
	/** 服务器通知客户端技能未命中 */
	public static final short GC_EFFECT_MISS = ++SKILL_BEGIN;
	/** 服务器通知buff效果开始 */
	public static final short GC_START_BUFF_EFFECT = ++SKILL_BEGIN;
	/** 服务器通知buff效果结束 */
	public static final short GC_STOP_BUFF_EFFECT = ++SKILL_BEGIN;
	/** 服务器通知更新buff状态 */
	public static final short GC_UPDATE_BUFF_STATE = ++SKILL_BEGIN;
	/** 服务器通知替换旧的buff效果 */
	public static final short GC_REPLACE_OLD_BUFF = ++SKILL_BEGIN;
	/** 服务器通清除buff效果 */
	public static final short GC_CLEAR_BUFF = ++SKILL_BEGIN;
	/** 服务器通知更新技能冷却状态 */
	public static final short GC_UPDATE_SKILL_STATE = ++SKILL_BEGIN;
	/** 服务器发送所有玩家的技能 */
	public static final short GC_ALL_SKILLS = ++SKILL_BEGIN;
	/** 服务器通知客户端战斗携带的技能 */
	public static final short GC_BATTLE_CARRIED_SKILLS = ++SKILL_BEGIN;
	/** 客户端请求装备技能 */
	public static final short CG_EQUIP_SKILL = ++SKILL_BEGIN;
	/** 客户端请求卸下技能 */
	public static final short CG_UNEQUIP_SKILL = ++SKILL_BEGIN;
	/** 服务端通知更新技能的装备状态 */
	public static final short GC_UPDATE_SKILL_EQUIP_STATE = ++SKILL_BEGIN;
	/** 服务器通知血量变化 */
	public static final short GC_BATTLE_HP_CHANGED = ++SKILL_BEGIN;
	/** 服务器通知魔变化 */
	public static final short GC_BATTLE_MAGIC_CHANGED = ++SKILL_BEGIN;
	/** 请求技能栏的状态 */
	public static final short CG_GET_SKILL_SLOTS_INFO = ++SKILL_BEGIN;
	/** 服务器返回技能栏的状态 */
	public static final short GC_SKILL_SLOTS_INFO = ++SKILL_BEGIN;
	/** 客户端获取技能栏tips */
	public static final short CG_GET_SKILL_SLOT_TIP = ++SKILL_BEGIN;
	/** 服务器返回技能栏tips */
	public static final short GC_SKILL_SLOT_INFO = ++SKILL_BEGIN;
	/** 客户端请求购买技能栏 */
	public static final short CG_OPEN_SKILL_SLOT = ++SKILL_BEGIN;
	/** 客户端请求直接购买技能栏 */
	public static final short CG_DIRECT_OPEN_SKILL_SLOT = ++SKILL_BEGIN;
	/** 服务器返回购买技能栏结果 */
	public static final short GC_OPEN_SKILL_SLOT_RESULT = ++SKILL_BEGIN;
	/** 学习技能 */
	public static final short CG_STUDY_SKILL = ++SKILL_BEGIN;
	/** 重置技能 */
	public static final short CG_RESET_SKILLS = ++SKILL_BEGIN;
	/** 打开技能面板 */
	public static final short CG_SHOW_SKILL_PANEL = ++SKILL_BEGIN;

	// /////////////
	// 训练场
	// ////////////
	public static short TRAINING_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开训练场面板 */
	public static final short CG_OPEN_TRAINING_PANEL = ++TRAINING_BEGIN;
	/** 更新训练场面板信息 */
	public static final short GC_UPDATE_TRAINING_PANEL = ++TRAINING_BEGIN;
	/** 开始训练 */
	public static final short CG_TRAINING_BEGIN = ++TRAINING_BEGIN;
	/** 开始训练 */
	public static final short GC_TRAINING_BEGIN_UPDATE = ++TRAINING_BEGIN;
	/** 收获训练所得经验 */
	public static final short CG_COLLECT_TRAINING_EXP = ++TRAINING_BEGIN;
	/** 返回收获经验结果 */
	public static final short GC_COLLECT_TRAINING_EXP = ++TRAINING_BEGIN;
	/** 更新训练场状态 */
	public static final short GC_UPDATE_TRAINING_STATE = ++TRAINING_BEGIN;

	// /////////////
	// 魔晶兑换
	// ////////////
	public static short CRYSTAL_EXCHANGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 魔晶兑换 */
	public static final short CG_CRYSTAL_EXCHANGE = ++CRYSTAL_EXCHANGE_BEGIN;
	/** 请求打开魔晶对面面板 */
	public static final short CG_SHOW_CRYSTAL_EXCHANGE_PANEL = ++CRYSTAL_EXCHANGE_BEGIN;
	/** 打开魔晶兑换面板 */
	public static final short GC_SHOW_CRYSTAL_EXCHANGE_PANEL = ++CRYSTAL_EXCHANGE_BEGIN;
	/** 登录时候发送今天剩余的兑换次数 */
	public static final short GC_CRYSTAL_EXCHANGE_TIMES = ++CRYSTAL_EXCHANGE_BEGIN;

	// /////////////
	// 连续登陆奖励
	// ////////////
	public static short LOGIN_REWARD_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 领取奖励 */
	public static final short CG_GET_REWARD = ++LOGIN_REWARD_BEGIN;
	/** 领取奖励 */
	public static final short GC_GET_REWARD = ++LOGIN_REWARD_BEGIN;
	/** 领取特殊奖励 */
	public static final short CG_GET_SPECIAL_REWARD = ++LOGIN_REWARD_BEGIN;
	/** 领取特殊奖励 */
	public static final short GC_GET_SPECIAL_REWARD = ++LOGIN_REWARD_BEGIN;
	/** 请求打开连续登陆奖励面板 */
	public static final short CG_SHOW_LOGIN_REWARD_PANEL = ++LOGIN_REWARD_BEGIN;
	/** 显示连续登陆奖励面板 */
	public static final short GC_SHOW_LOGIN_REWARD_PANEL = ++LOGIN_REWARD_BEGIN;
	/** 连续登陆是否有可领取奖励 */
	public static final short GC_HAS_LOGIN_REWARD = ++LOGIN_REWARD_BEGIN;

	// /////////////
	// 在线奖励
	// ////////////
	public static short ONLINE_REWARD_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 领取在线奖励 */
	public static final short CG_GET_ONLINE_REWARD = ++ONLINE_REWARD_BEGIN;
	/** 显示在线奖励cd */
	public static final short GC_SHOW_ONLINE_REWARD = ++ONLINE_REWARD_BEGIN;
	/** 在线奖励领取完 */
	public static final short GC_ONLINE_REWARD_END = ++ONLINE_REWARD_BEGIN;

	// /////////////
	// 大转盘抽奖系统
	// ////////////
	public static short TURNTABLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 大转盘抽奖 */
	public static final short CG_LOTTERY = ++TURNTABLE_BEGIN;
	/** 打开大转盘面板 */
	public static final short CG_SHOW_TURNTABLE_PANEL = ++TURNTABLE_BEGIN;
	/** 大转盘抽奖 */
	public static final short GC_LOTTERY = ++TURNTABLE_BEGIN;
	/** 打开大转盘面板 */
	public static final short GC_SHOW_TURNTABLE_PANEL = ++TURNTABLE_BEGIN;

	// /////////////
	// 新手引导系统
	// ////////////
	public static short GUIDE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 新手引导信息 */
	public static final short GC_GUIDE_INFO = ++GUIDE_BEGIN;

	// /////////////
	// 关卡系统
	// ////////////
	public static short STAGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 进入征战场景 */
	public static final short CG_ENTER_BATTLE_SCENE = ++STAGE_BEGIN;
	/** 返回征战场景信息 */
	public static final short GC_ENTER_BATTLE_SCENE = ++STAGE_BEGIN;
	/** 攻打关卡 */
	public static final short CG_ATTACK_STAGE = ++STAGE_BEGIN;
	/** 攻打关卡结果信息 */
	public static final short GC_ATTACK_STAGE = ++STAGE_BEGIN;
	/** 过关奖励信息 */
	public static final short GC_PASS_STAGE_REWARD = ++STAGE_BEGIN;
	/** 领取过关奖励 */
	public static final short CG_GET_PASS_STAGE_REWARD = ++STAGE_BEGIN;
	/** 领取过关奖励成功 */
	public static final short GC_GET_PASS_STAGE_REWARD = ++STAGE_BEGIN;
	/** 查看关卡信息 */
	public static final short CG_STAGE_INFO = ++STAGE_BEGIN;
	/** 返回关卡信息 */
	public static final short GC_STAGE_INFO = ++STAGE_BEGIN;
	/** 自动攻击关卡 */
	public static final short CG_AUTO_ATTACK_STAGE = ++STAGE_BEGIN;
	/** 返回关卡剧情信息 */
	public static final short GC_STAGE_DRAMA_INFO = ++STAGE_BEGIN;
	/** 点击关卡 */
	public static final short CG_CLICK_STAGE = ++STAGE_BEGIN;
	/** 关卡选择 */
	public static final short CG_SHOW_STAGE_MAPS = ++STAGE_BEGIN;
	/** 关卡选择 */
	public static final short GC_SHOW_STAGE_MAPS = ++STAGE_BEGIN;
	/** 获取所有关卡战斗奖励 */
	public static final short CG_GET_ALL_BATTLE_REWARD = ++STAGE_BEGIN;
	/** 领取关卡评星奖励 */
	public static final short CG_GET_STAGE_STAR_REWARD = ++STAGE_BEGIN;
	/** 领取关卡评星奖励 */
	public static final short GC_GET_STAGE_STAR_REWARD = ++STAGE_BEGIN;
	/** 关卡星星总数变化时同步星星总数量和奖励可领取状态 */
	public static final short GC_STAR_AND_REWARD_STATE = ++STAGE_BEGIN;
	/** 打开地图完美通关奖励的板子 */
	public static final short CG_SHOW_MAP_PERFECT_REWARD_PANEL = ++STAGE_BEGIN;
	/** 打开地图完美通关奖励的板子 */
	public static final short GC_SHOW_MAP_PERFECT_REWARD_PANEL = ++STAGE_BEGIN;
	/** 领取地图完美通关奖励 */
	public static final short CG_GET_MAP_PERFECT_REWARD = ++STAGE_BEGIN;
	/** 领取地图完美通关奖励 */
	public static final short GC_GET_MAP_PERFECT_REWARD = ++STAGE_BEGIN;
	/** 更新完美通关奖励的领取状态 */
	public static final short GC_UPDATE_PERFECT_REWARD_STATE = ++STAGE_BEGIN;
	/** 攻打关卡返回关卡有可能出的物品 */
	public static final short GC_CLICK_STAGE = ++STAGE_BEGIN;
	/** 请求进入指定的地图据点 */
	public static final short CG_ENTER_MAP_STRONGHOLD = ++STAGE_BEGIN;
	/** 服务器返回据点的信息 */
	public static final short GC_ENTER_MAP_STRONGHOLD = ++STAGE_BEGIN;
	/** 请求点击不完美关卡 */
	public static final short CG_CLICK_UNPERFECT_STAGE = ++STAGE_BEGIN;
	/** 响应点击不完美关卡 */
	public static final short GC_CLICK_UNPERFECT_STAGE = ++STAGE_BEGIN;

	// /////////////
	// VIP
	// ////////////
	public static short VIP_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 显示VIP面板信息 */
	public static final short CG_VIP_SHOW_PANNEL = ++VIP_BEGIN;
	/** 显示VIP面板信息 */
	public static final short GC_VIP_SHOW_PANNEL = ++VIP_BEGIN;
	/** 请求显示VIP详情 */
	public static final short CG_SHOW_VIP_INFO = ++VIP_BEGIN;
	/** 服务器返回VIP详情 */
	public static final short GC_SHOW_VIP_INFO = ++VIP_BEGIN;
	/** 下发VIP特权数据 */
	public static final short GC_DISPATCH_VIP_INFO = ++VIP_BEGIN;
	/** 显示充值活动信息 */
	public static final short CG_SHOW_CHARGE_ACTIVITY = ++VIP_BEGIN;
	/** 显示充值活动信息 */
	public static final short GC_SHOW_CHARGE_ACTIVITY = ++VIP_BEGIN;

	// /////////////
	// 防沉迷
	// ////////////
	public static short ANTI_INDULGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求更新收益倍率 */
	public static final short GC_UPDATE_REVENUE_RATE_REQUEST = ++ANTI_INDULGE_BEGIN;
	/** 客户端返回平台接口所提供的收益倍率 */
	public static final short CG_UPDATE_REVENUE_RATE_RESPONSE = ++ANTI_INDULGE_BEGIN;

	// /////////////
	// 问答活动
	// ////////////
	public static short QUESTION_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 客户端请求打开问答面板 */
	public static final short CG_OPEN_QUESTION_PANNEL = ++QUESTION_BEGIN;
	/** 服务器更新问答面板信息 */
	public static final short GC_UPDATE_QUESTION_PANNEL = ++QUESTION_BEGIN;
	/** 提交问题答案 */
	public static final short CG_ANSWER_SUBMIT = ++QUESTION_BEGIN;
	/** 发放问答奖励 */
	public static final short GC_ANSWER_UPDATE = ++QUESTION_BEGIN;
	/** 兑换积分请求 */
	public static final short CG_EXCHANGE_SCORE = ++QUESTION_BEGIN;
	/** 兑换积分后的更新消息 */
	public static final short GC_EXCHANGE_SCORE = ++QUESTION_BEGIN;
	/** 返回购买祈福花费 */
	public static final short GC_BUY_BLESS_COST = ++QUESTION_BEGIN;
	/** 更新积分兑换面板信息 */
	public static final short GC_UPDATE_SCORE_EXCHANGE_PANEL = ++QUESTION_BEGIN;
	/** 一键答题 */
	public static final short CG_ONEKEY_ANSWER_QUESTION = ++QUEST_BEGIN;

	// /////////////
	// 消费提醒
	// ////////////
	public static short COSTNOTIFY_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 设置消费提醒 */
	public static final short CG_UPDATE_COST_NOTIFY = ++COSTNOTIFY_BEGIN;
	/** 设置消费提醒 */
	public static final short GC_UPDATE_COST_NOTIFY = ++COSTNOTIFY_BEGIN;

	// /////////////
	// 竞技场
	// ////////////
	public static short ARENA_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开竞技场面板 */
	public static final short CG_OPEN_ARENA_PANEL = ++ARENA_BEGIN;
	/** 打开竞技场面板 */
	public static final short GC_OPEN_ARENA_PANEL = ++ARENA_BEGIN;
	/** 查看竞技场排名奖励信息 */
	public static final short CG_VIEW_ARENA_RANK_REWARD_INFO = ++ARENA_BEGIN;
	/** 查看竞技场排名奖励信息 */
	public static final short GC_VIEW_ARENA_RANK_REWARD_INFO = ++ARENA_BEGIN;
	/** 领取竞技场排名奖励 */
	public static final short CG_GET_ARENA_RANK_REWARD = ++ARENA_BEGIN;
	/** 领取竞技场排名奖励 */
	public static final short GC_GET_ARENA_RANK_REWARD = ++ARENA_BEGIN;
	/** 添加竞技场战斗次数 */
	public static final short CG_ADD_ARENA_BATTLE_TIME = ++ARENA_BEGIN;
	/** 添加竞技场战斗次数 */
	public static final short GC_ADD_ARENA_BATTLE_TIME = ++ARENA_BEGIN;
	/** 显示竞技场排名列表 */
	public static final short CG_SHOW_ARENA_RANK_LIST = ++ARENA_BEGIN;
	/** 显示竞技场排名列表 */
	public static final short GC_SHOW_ARENA_RANK_LIST = ++ARENA_BEGIN;
	/** 攻击竞技场成员 */
	public static final short CG_ATTACK_ARENA_MEMBER = ++ARENA_BEGIN;
	/** 攻击竞技场成员 */
	public static final short GC_ATTACK_ARENA_MEMBER = ++ARENA_BEGIN;

	// /////////////
	// 快捷购买
	// ////////////
	public static short EXPEDITE_BUY_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 快捷购买 */
	public static final short CG_EXPEDITE_BUY = ++EXPEDITE_BUY_BEGIN;
	/** 快捷购买 */
	public static final short GC_EXPEDITE_BUY = ++EXPEDITE_BUY_BEGIN;

	// /////////////
	// 冥想系统
	// ////////////
	public static short MEDITATION_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 更新冥想面板消息 */
	public static final short CG_UPDATE_MEDITATION_PANNEL = ++MEDITATION_BEGIN;
	/** 更新冥想面板消息 */
	public static final short GC_UPDATE_MEDITATION_PANNEL = ++MEDITATION_BEGIN;
	/** 更新好友协助位消息 */
	public static final short GC_UPDATE_MEDITATION_ASSIST_POSITION = ++MEDITATION_BEGIN;
	/** 申请好友协助 */
	public static final short CG_ASSIST_MEDITATION_APPLY = ++MEDITATION_BEGIN;
	/** 收到好友协助申请消息 */
	public static final short GC_ASSIST_MEDITATION_APPLY = ++MEDITATION_BEGIN;
	/** 处理好友协助申请消息 */
	public static final short CG_DEAL_ASSIST_MEDITATION_APPLY = ++MEDITATION_BEGIN;
	/** 开启冥想协助位 */
	public static final short CG_QUERY_OPEN_ASSIST_POSITION_PRICE = ++MEDITATION_BEGIN;
	/** 开启冥想协助位 */
	public static final short GC_OPEN_ASSIST_POSITION_PRICE = ++MEDITATION_BEGIN;
	/** 开启冥想协助位 */
	public static final short CG_OPEN_ASSIST_POSITION = ++MEDITATION_BEGIN;
	/** 开启冥想协助位 */
	public static final short GC_OPEN_ASSIST_POSITION = ++MEDITATION_BEGIN;
	/** 开始冥想 */
	public static final short CG_START_MEDITATION = ++MEDITATION_BEGIN;
	/** 中止冥想 */
	public static final short CG_ABORT_MEDITATION = ++MEDITATION_BEGIN;
	/** 冥想完成 */
	public static final short GC_MEDITATION_COMPLETE = ++MEDITATION_BEGIN;
	/** 可协助的好友不足 */
	public static final short GC_FRIEND_FOR_ASSIST_NOT_ENOUGH = ++MEDITATION_BEGIN;
	/** 查询结束当前冥想可获得多少科技点 */
	public static final short CG_QUERY_MEDITATION_POINT = ++MEDITATION_BEGIN;
	/** 查询结束当前冥想可获得多少科技点 */
	public static final short GC_QUERY_MEDITATION_POINT = ++MEDITATION_BEGIN;
	/** 开始冥想 */
	public static final short GC_START_MEDITATION = ++MEDITATION_BEGIN;
	/** 更新冥想状态 */
	public static final short GC_UPDATE_MEDITATION_STATE = ++MEDITATION_BEGIN;
	/** 查看当前的冥想总收益 */
	public static final short CG_TOTAL_MEDITATION_POINT = ++MEDITATION_BEGIN;

	// /////////////
	// boss战系统
	// ////////////
	public static short BOSS_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 参与BOSS战 */
	public static final short GC_JOIN_BOSS_WAR = ++BOSS_BEGIN;
	/** 金币鼓舞 */
	public static final short CG_ENCOURAGE_BOSS_WAR = ++BOSS_BEGIN;
	/** 金币鼓舞 */
	public static final short GC_ENCOURAGE_BOSS_WAR = ++BOSS_BEGIN;
	/** 充能 */
	public static final short CG_CHARGED_BOSS_WAR = ++BOSS_BEGIN;
	/** 充能 */
	public static final short GC_CHARGED_BOSS_WAR = ++BOSS_BEGIN;
	/** 用充能打击 */
	public static final short CG_CHARGED_BOSS = ++BOSS_BEGIN;
	/** 攻击boss */
	public static final short CG_ATTACK_BOSS = ++BOSS_BEGIN;
	/** 获取boss奖励 */
	public static final short CG_GET_BOSS_REWARD = ++BOSS_BEGIN;
	/** 获取boss奖励 */
	public static final short GC_GET_BOSS_REWARD = ++BOSS_BEGIN;
	/** 通知有boss战奖励 */
	public static final short GC_HAS_BOSS_REWARD = ++BOSS_BEGIN;
	/** 请求进入boss战 */
	public static final short CG_ENTER_BOSS_WAR = ++BOSS_BEGIN;
	/** 用充能攻击 */
	public static final short GC_CHARGED_BOSS = ++BOSS_BEGIN;

	// /////////////
	// 精英副本系统
	// ////////////
	public static short ELITE_STAGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开精英副本 */
	public static final short CG_ENTER_ELITE_STAGE = ++ELITE_STAGE_BEGIN;
	/** 进入精英副本 */
	public static final short GC_ENTER_ELITE_STAGE = ++ELITE_STAGE_BEGIN;
	/** 精英副本类型列表 */
	public static final short GC_ELITE_STAGE_TYPE_LIST = ++ELITE_STAGE_BEGIN;
	/** 精英副本进入战斗 */
	public static final short CG_ATTACK_ELITE_STAGE_BATTLE = ++ELITE_STAGE_BEGIN;
	/** 进入精英副本战斗前的警告 */
	public static final short GC_ATTACK_ELITE_STAGE_WARNING = ++ELITE_STAGE_BEGIN;
	/** 精英副本战斗结果 */
	public static final short GC_ELITE_STAGE_BATTLE_RESULT = ++ELITE_STAGE_BEGIN;
	/** 精英副本类型列表 */
	public static final short GC_NEW_ELITE_STAGE_TYPE_OPEN = ++ELITE_STAGE_BEGIN;
	/** 精英副本进入战斗 */
	public static final short GC_UPDATE_ELITE_STAGE_CHALLENGE_COUNTER = ++ELITE_STAGE_BEGIN;
	/** 进入精英副本战斗前的警告 */
	public static final short CG_REFRESH_ELITE_STAGE_CHALLENGE_STATE = ++ELITE_STAGE_BEGIN;
	/** 精英副本战斗结果 */
	public static final short GC_UPDATE_ELITE_STAGE_CHALLENGE_STATE = ++ELITE_STAGE_BEGIN;
	/** 打开精英副本面板 */
	public static final short CG_OPEN_ELITE_STAGE_PANEL = ++ELITE_STAGE_BEGIN;
	/** 打开精英副本面板 */
	public static final short GC_OPEN_ELITE_STAGE_PANEL = ++ELITE_STAGE_BEGIN;

	// /////////////
	// 活动管理相关消息
	// ////////////
	public static short ACTIVITY_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求获取活动列表 */
	public static final short CG_QUERY_ACTIVITY_LIST = ++ACTIVITY_BEGIN;
	/** 更新活动列表 */
	public static final short GC_ACTIVITY_LIST = ++ACTIVITY_BEGIN;
	/** 更新独立活动列表 */
	public static final short GC_ALONE_ACTIVITY_LIST = ++ACTIVITY_BEGIN;
	/** 参加活动 */
	public static final short CG_ENTER_ACTIVITY = ++ACTIVITY_BEGIN;
	/** 更新活动状态 */
	public static final short GC_UPDATE_ACTIVITY_STATE = ++ACTIVITY_BEGIN;

	// /////////////
	// gm问答消息
	// ////////////
	public static short GMQUESTION_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 玩家提交问题 */
	public static final short CG_SUBMIT_QUESTION = ++GMQUESTION_BEGIN;
	/** 查看问答列表 */
	public static final short CG_SHOW_QUESTIONS = ++GMQUESTION_BEGIN;
	/** 返回问答列表 */
	public static final short GC_SHOW_QUESTIONS = ++GMQUESTION_BEGIN;

	// /////////////
	// 奖励消息
	// ////////////
	public static short REWARD_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 获取奖励 */
	public static final short CG_GET_COMMON_REWARD = ++REWARD_BEGIN;
	/** 移除奖励 */
	public static final short GC_REMOVE_COMMON_REWARD = ++REWARD_BEGIN;
	/** 增加奖励 */
	public static final short GC_ADD_COMMON_REWARD = ++REWARD_BEGIN;

	// /////////////
	// 矿场消息
	// ////////////
	public static short MINE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 更新采矿面板消息 */
	public static final short CG_UPDATE_MINE_PANNEL = ++MINE_BEGIN;
	/** 更新采矿面板消息 */
	public static final short GC_UPDATE_MINE_PANNEL = ++MINE_BEGIN;
	/** 开启矿坑 */
	public static final short CG_OPEN_MINE_FIELD = ++MINE_BEGIN;
	/** 开启矿坑结果 */
	public static final short GC_OPEN_MINE_FIELD = ++MINE_BEGIN;
	/** 开启矿坑战斗请求 */
	public static final short GC_OPEN_MINE_FIELD_BATTLE_REQUEST = ++MINE_BEGIN;
	/** 开启矿坑战斗客户端回复 */
	public static final short CG_OPEN_MINE_FIELD_BATTLE_RESPONSE = ++MINE_BEGIN;
	/** 矿坑收获请求 */
	public static final short CG_MINE_HARVEST = ++MINE_BEGIN;
	/** 矿坑收获结果 */
	public static final short GC_MINE_HARVEST = ++MINE_BEGIN;
	/** 购买矿坑开采权次数 */
	public static final short CG_BUY_MINE_TIMES = ++MINE_BEGIN;
	/** 购买矿坑开采权返回结果 */
	public static final short GC_BUY_MINE_TIMES = ++MINE_BEGIN;
	/** 重置矿坑请求 */
	public static final short CG_RESET_MINE_FIELD = ++MINE_BEGIN;
	/** 重置矿坑结果 */
	public static final short GC_RESET_MINE_FIELD = ++MINE_BEGIN;
	/** 矿场可以收获的通知消息 */
	public static final short GC_MINE_FIELD_LIST = ++MINE_BEGIN;

	// /////////////
	// 矿场消息
	// ////////////
	public static short HELPER_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求小助手信息 */
	public static final short CG_SHOW_HELP_INFOS = ++HELPER_BEGIN;
	/** 返回小助手信息 */
	public static final short GC_SHOW_HELP_INFOS = ++HELPER_BEGIN;

	// /////////////
	// 扫荡消息
	// ////////////
	public static short AUTO_BATTLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开精英副本扫荡面板 */
	public static final short CG_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL = ++AUTO_BATTLE_BEGIN;
	/** 打开精英副本扫荡面板 */
	public static final short GC_SHOW_ELITE_STAGE_AUTO_BATTLE_PANEL = ++AUTO_BATTLE_BEGIN;
	/** 精英副本开始扫荡 */
	public static final short CG_START_ELITE_AUTO_BATTLE = ++AUTO_BATTLE_BEGIN;
	/** 精英副本取消扫荡 */
	public static final short CG_CANCEL_ELITE_AUTO_BATTLE = ++AUTO_BATTLE_BEGIN;
	/** 精英副本扫荡状态 */
	public static final short GC_ELITE_AUTO_BATTLE_STATE = ++AUTO_BATTLE_BEGIN;
	/** 精英副本扫荡结果 */
	public static final short GC_ELITE_AUTO_BATTLE_RESULT = ++AUTO_BATTLE_BEGIN;
	/** 打开关卡扫荡面板 */
	public static final short CG_SHOW_STAGE_AUTO_BATTLE_PANEL = ++AUTO_BATTLE_BEGIN;
	/** 返回关卡单轮扫荡需要的时间和体力 */
	public static final short GC_SHOW_STAGE_AUTO_BATTLE_PANEL = ++AUTO_BATTLE_BEGIN;
	/** 关卡开始扫荡 */
	public static final short CG_START_STAGE_AUTO_BATTLE = ++AUTO_BATTLE_BEGIN;
	/** 关卡取消扫荡 */
	public static final short CG_CANCEL_STAGE_AUTO_BATTLE = ++AUTO_BATTLE_BEGIN;
	/** 关卡扫荡状态 */
	public static final short GC_STAGE_AUTO_BATTLE_STATE = ++AUTO_BATTLE_BEGIN;
	/** 关卡扫荡结果 */
	public static final short GC_STAGE_AUTO_BATTLE_RESULT = ++AUTO_BATTLE_BEGIN;

	// /////////////
	// 荣誉商店
	// ////////////
	public static short HONOUR_SHOP_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 显示荣誉商店道具列表 */
	public static final short CG_SHOW_HONOUR_SHOP_ITEM_LIST = ++HONOUR_SHOP_BEGIN;
	/** 显示荣誉商店道具列表 */
	public static final short GC_SHOW_HONOUR_SHOP_ITEM_LIST = ++HONOUR_SHOP_BEGIN;
	/** 购买选中道具 */
	public static final short CG_BUY_HONOUR_SHOP_ITEM = ++HONOUR_SHOP_BEGIN;
	/** 购买选中道具结果 */
	public static final short GC_BUY_HONOUR_SHOP_ITEM = ++HONOUR_SHOP_BEGIN;

	// /////////////
	// 天赋系统
	// ////////////
	public static short GIFT_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求显示天赋面板 */
	public static final short CG_SHOW_GIFT_PANEL = ++GIFT_BEGIN;
	/** 更新整个天赋面板数据 */
	public static final short GC_SHOW_GIFT_PANEL = ++GIFT_BEGIN;
	/** 请求激活天赋 */
	public static final short CG_ACTIVE_GIFT = ++GIFT_BEGIN;
	/** 更新天赋状态 */
	public static final short GC_UPDATE_GIFT_STATE = ++GIFT_BEGIN;
	/** 购买选中道具结果 */
	public static final short CG_RESET_GIFT = ++GIFT_BEGIN;
	/** 更新天赋激活提醒状态 */
	public static final short GC_UPDATE_GIFT_ACTIVE_NOTICE_STATE = ++GIFT_BEGIN;
	/** 请求升级天赋 */
	public static final short CG_UPGRADE_GIFT = ++GIFT_BEGIN;

	// /////////////
	// 培养系统
	// ////////////
	public static short FOSTER_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求显示培养面板 */
	public static final short CG_SHOW_FOSTER_PANEL = ++FOSTER_BEGIN;
	/** 返回培养面板数据 */
	public static final short GC_SHOW_FOSTER_PANEL = ++FOSTER_BEGIN;
	/** 培养请求 */
	public static final short CG_FOSTER = ++FOSTER_BEGIN;
	/** 返回培养获得的属性值 */
	public static final short GC_FOSTER = ++FOSTER_BEGIN;
	/** 保存该次培养获得的属性值 */
	public static final short CG_SAVE_FOSTER = ++FOSTER_BEGIN;
	/** 返回保存后的培养属性信息 */
	public static final short GC_SAVE_FOSTER = ++FOSTER_BEGIN;

	// /////////////
	// 洗炼系统
	// ////////////
	public static short FORGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 选中装备去洗炼 */
	public static final short CG_SELECT_EQUIP_TO_FORGE = ++FORGE_BEGIN;
	/** 选中装备去洗炼 */
	public static final short GC_SELECT_EQUIP_TO_FORGE = ++FORGE_BEGIN;
	/** 装备洗炼锁定 */
	public static final short CG_EQUIP_FORGE_TO_LOCK = ++FORGE_BEGIN;
	/** 装备洗炼锁定 */
	public static final short GC_EQUIP_FORGE_TO_LOCK = ++FORGE_BEGIN;
	/** 装备洗炼 */
	public static final short CG_EQUIP_FORGE = ++FORGE_BEGIN;
	/** 更新洗炼面板 */
	public static final short GC_UPDATE_EQUIP_FORGE_PANEL = ++FORGE_BEGIN;
	/** 替换洗练属性 */
	public static final short CG_EQUIP_FORGE_REPLACE = ++FORGE_BEGIN;
	/** 替换洗练属性 */
	public static final short GC_EQUIP_FORGE_REPLACE = ++FORGE_BEGIN;
	/** 取消洗练属性 */
	public static final short CG_EQUIP_FORGE_CANCEL = ++FORGE_BEGIN;
	/** 取消洗练属性 */
	public static final short GC_EQUIP_FORGE_CANCEL = ++FORGE_BEGIN;

	// /////////////
	// 试炼系统
	// ////////////
	public static short REFINE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开某个试炼副本 */
	public static final short CG_OPEN_REFINE_PANEL = ++REFINE_BEGIN;
	/** 打开某个试炼副本 */
	public static final short GC_OPEN_REFINE_PANEL = ++REFINE_BEGIN;
	/** 攻打试炼关卡 */
	public static final short CG_ATTACK_REFINE_STAGE = ++REFINE_BEGIN;
	/** 自动攻打试炼关卡 */
	public static final short CG_AUTO_ATTACK_REFINE_STAGE = ++REFINE_BEGIN;
	/** 一键扫荡试炼关卡 */
	public static final short CG_ATTACK_ALL_REFINE_STAGE = ++REFINE_BEGIN;
	/** 刷新试炼副本 */
	public static final short CG_REFRESH_REFINE_MAP = ++REFINE_BEGIN;
	/** 攻打关卡奖励信息 */
	public static final short GC_ATTACK_REFINE_STAGE = ++REFINE_BEGIN;
	/** 首次攻打关卡奖励信息 */
	public static final short GC_ATTACK_REFINE_STAGE_FIRST = ++REFINE_BEGIN;
	/** 背包满的提示 */
	public static final short GC_REFINE_BAG_FULL = ++REFINE_BEGIN;
	/** 更新面板 */
	public static final short GC_UPDATE_REFINE_PANEL = ++REFINE_BEGIN;

	// /////////////
	// 匹配战系统
	// ////////////
	public static short MATCH_BATTLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 进入匹配战场景 */
	public static final short GC_JOIN_MATCH_BATTLE_SCENE = ++MATCH_BATTLE_BEGIN;
	/** 鼓舞 */
	public static final short CG_ENCOURAGE_MATCH_BATTLE = ++MATCH_BATTLE_BEGIN;
	/** 鼓舞 */
	public static final short GC_ENCOURAGE_MATCH_BATTLE = ++MATCH_BATTLE_BEGIN;
	/** 发送战报 */
	public static final short GC_BATTLE_REPORT = ++MATCH_BATTLE_BEGIN;
	/** 更新排行 */
	public static final short GC_UPDATE_MATCH_BATTLE_RANK = ++MATCH_BATTLE_BEGIN;
	/** 参战 */
	public static final short CG_READY_FOR_MATCH_BATTLE = ++MATCH_BATTLE_BEGIN;
	/** 更新自动参战设置 */
	public static final short CG_UPDATE_AUTO_MATCH_BATTLE_SETTING = ++MATCH_BATTLE_BEGIN;
	/** 更新参战人员信息 */
	public static final short GC_UPDATE_MATCH_BATTLE_ROLE = ++MATCH_BATTLE_BEGIN;
	/** 离开匹配战场景 */
	public static final short CG_LEAVE_MATCH_BATTLE_SCENE = ++MATCH_BATTLE_BEGIN;
	/** 取消参战准备 */
	public static final short CG_CANCEL_READY_FOR_MATCH_BATTLE = ++MATCH_BATTLE_BEGIN;
	/** 新进匹配战场景的角色 */
	public static final short GC_ADD_MATCH_BATTLE_ROLE = ++MATCH_BATTLE_BEGIN;
	/** 离开匹配战场景的角色id */
	public static final short GC_REMOVE_MATCH_BATTLE_ROLE = ++MATCH_BATTLE_BEGIN;
	/** 战斗完或轮空进行更新个人的消息 */
	public static final short GC_UPDATE_MATCH_BATTLE_RESULT = ++MATCH_BATTLE_BEGIN;
	/** 服务器更新自动参战设置 */
	public static final short GC_UPDATE_AUTO_MATCH_BATTLE_SETTING = ++MATCH_BATTLE_BEGIN;
	/** 请求进入匹配战场景 */
	public static final short CG_JOIN_MATCH_BATTLE_SCENE = ++MATCH_BATTLE_BEGIN;

	// /////////////
	// 功能助手
	// ////////////
	public static short FUNC_HELPER_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开功能助手面板 */
	public static final short CG_OPEN_FUNC_HELP_PANEL = ++FUNC_HELPER_BEGIN;
	/** 更新功能助手面板信息 */
	public static final short GC_OPEN_FUNC_HELP_PANEL = ++FUNC_HELPER_BEGIN;
	/** 请求显示某个分类下的功能列表 */
	public static final short CG_SHOW_FUNC_HELP_INFOS = ++FUNC_HELPER_BEGIN;
	/** 显示某个分类下的功能列表 */
	public static final short GC_SHOW_FUNC_HELP_INFOS = ++FUNC_HELPER_BEGIN;

	// /////////////
	// 勇者之路
	// ////////////
	public static short WARRIOR_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开勇士之路面板请求 */
	public static final short CG_OPEN_WARRIOR_WAY_PANEL = ++WARRIOR_BEGIN;
	/** 更新勇士之路面板信息 */
	public static final short GC_OPEN_WARRIOR_WAY_PANEL = ++WARRIOR_BEGIN;
	/** 请求刷新对手 */
	public static final short CG_REFRESH_OPPONENT = ++WARRIOR_BEGIN;
	/** 更新对手信息 */
	public static final short GC_UPDATE_OPPONENT = ++WARRIOR_BEGIN;
	/** 客户端发送挑战请求 */
	public static final short CG_START_WARRIOR_BATTLE_REQUEST = ++WARRIOR_BEGIN;
	/** 更新任务 */
	public static final short GC_UPDATE_WARRIOR_QUEST = ++WARRIOR_BEGIN;
	/** 领取奖励 */
	public static final short CG_GET_WARRIOR_QUEST_REWARD = ++WARRIOR_BEGIN;
	/** 更新所有奖励 */
	public static final short GC_GET_WARRIOR_QUEST_REWARD = ++WARRIOR_BEGIN;
	/** 更新当前奖励领取状态 */
	public static final short GC_UPDATE_WARRIOR_QUEST_REWARD_STATE = ++WARRIOR_BEGIN;
	/** 勇者之路战斗邀请 */
	public static final short GC_JOIN_WARRIOR_BATTLE_REQUEST = ++WARRIOR_BEGIN;
	/** 勇者之路是否已经完成 */
	public static final short GC_WARRIOR_STATE = ++WARRIOR_BEGIN;

	// /////////////
	// 战斗引导
	// ////////////
	public static short BATTLE_GUIDE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 战斗棋盘引导 */
	public static final short GC_BATTLE_GUIDE_CHESSBOARD = ++BATTLE_GUIDE_BEGIN;
	/** 战斗四连消引导 */
	public static final short GC_BATTLE_GUIDE_FOUR_BOMBS = ++BATTLE_GUIDE_BEGIN;
	/** 战斗三连消引导 */
	public static final short GC_BATTLE_GUIDE_THREE_BOMBS = ++BATTLE_GUIDE_BEGIN;
	/** 战斗使用技能引导 */
	public static final short GC_BATTLE_GUIDE_USE_SKILL = ++BATTLE_GUIDE_BEGIN;
	/** 战斗技能说明引导 */
	public static final short GC_BATTLE_GUIDE_SKILL_INFO = ++BATTLE_GUIDE_BEGIN;

	// /////////////
	// 黄钻
	// ////////////
	public static short YELLOW_VIP_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 更新黄钻新手礼包领取状态 */
	public static final short CG_OPEN_YELLOW_VIP_REWARD_PANEL = ++YELLOW_VIP_BEGIN;
	/** 更新黄钻新手礼包领取状态 */
	public static final short GC_OPEN_YELLOW_VIP_REWARD_PANEL = ++YELLOW_VIP_BEGIN;
	/** 领取黄钻礼包请求 */
	public static final short CG_RECEIVE_YELLOW_VIP_REWARD = ++YELLOW_VIP_BEGIN;
	/** 更新黄钻礼包领取状态 */
	public static final short GC_UPDATE_YELLOW_VIP_REWARD_STATE = ++YELLOW_VIP_BEGIN;
	/** 领取黄钻升级礼包请求 */
	public static final short CG_RECEIVE_YELLOW_VIP_LEVEL_UP_REWARD = ++YELLOW_VIP_BEGIN;

	// /////////////
	// 腾讯充值
	// ////////////
	public static short RECHARGE_TX_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 打开充值面板 */
	public static final short CG_SHOW_RECHARGE_PANEL = ++RECHARGE_TX_BEGIN;
	/** 打开充值面板 */
	public static final short GC_SHOW_RECHARGE_PANEL = ++RECHARGE_TX_BEGIN;
	/** 申请充值 */
	public static final short CG_RECHARGE = ++RECHARGE_TX_BEGIN;
	/** 申请充值 */
	public static final short GC_RECHARGE = ++RECHARGE_TX_BEGIN;
	/** 充值是否已经结束 */
	public static final short GC_RECHARGE_FINISHED = ++RECHARGE_TX_BEGIN;

	// /////////////
	// 军衔
	// ////////////
	public static short TITLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开军衔面板 */
	public static final short CG_OPEN_TITLE_PANEL = ++TITLE_BEGIN;
	/** 响应打开军衔面板 */
	public static final short GC_OPEN_TITLE_PANEL = ++TITLE_BEGIN;
	/** 请求升级军衔 */
	public static final short CG_TITLE_LEVEL_UP = ++TITLE_BEGIN;
	/** 响应升级军衔 */
	public static final short GC_TITLE_LEVEL_UP = ++TITLE_BEGIN;
	/** 请求领取当日军衔俸禄 */
	public static final short CG_GET_TITLE_SALARY = ++TITLE_BEGIN;
	/** 响应领取当日军衔俸禄 */
	public static final short GC_GET_TITLE_SALARY = ++TITLE_BEGIN;

	// /////////////
	// 军团
	// ////////////
	public static short LEGION_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开军团面板 */
	public static final short CG_OPEN_LEGION_PANEL = ++LEGION_BEGIN;
	/** 响应打开军团面板 */
	public static final short GC_OPEN_LEGION_PANEL = ++LEGION_BEGIN;
	/** 请求展示军团列表标签页 */
	public static final short CG_SHOW_LEGION_LIST_TAB = ++LEGION_BEGIN;
	/** 响应展示军团列表标签页 */
	public static final short GC_SHOW_LEGION_LIST_TAB = ++LEGION_BEGIN;
	/** 请求展示所在军团信息标签页 */
	public static final short CG_SHOW_LEGION_INFO_TAB = ++LEGION_BEGIN;
	/** 响应展示所在军团信息标签页 */
	public static final short GC_SHOW_LEGION_INFO_TAB = ++LEGION_BEGIN;
	/** 请求展示军团成员标签页 */
	public static final short CG_SHOW_LEGION_MEMBER_TAB = ++LEGION_BEGIN;
	/** 响应展示军团成员标签页 */
	public static final short GC_SHOW_LEGION_MEMBER_TAB = ++LEGION_BEGIN;
	/** 请求展示军团日志标签页 */
	public static final short CG_SHOW_LEGION_LOG_TAB = ++LEGION_BEGIN;
	/** 响应展示军团日志标签页 */
	public static final short GC_SHOW_LEGION_LOG_TAB = ++LEGION_BEGIN;
	/** 请求展示军团申请标签页 */
	public static final short CG_SHOW_LEGION_APPLY_TAB = ++LEGION_BEGIN;
	/** 响应展示军团申请标签页 */
	public static final short GC_SHOW_LEGION_APPLY_TAB = ++LEGION_BEGIN;
	/** 请求创建军团 */
	public static final short CG_CREATE_LEGION = ++LEGION_BEGIN;
	/** 响应创建军团 */
	public static final short GC_CREATE_LEGION = ++LEGION_BEGIN;
	/** 请求申请加入军团 */
	public static final short CG_APPLY_JOIN_LEGION = ++LEGION_BEGIN;
	/** 响应申请加入军团 */
	public static final short GC_APPLY_JOIN_LEGION = ++LEGION_BEGIN;
	/** 同意加入军团 */
	public static final short CG_AGREE_JOIN_LEGION = ++LEGION_BEGIN;
	/** 响应同意加入军团 */
	public static final short GC_AGREE_JOIN_LEGION = ++LEGION_BEGIN;
	/** 拒绝加入军团 */
	public static final short CG_REJECT_JOIN_LEGION = ++LEGION_BEGIN;
	/** 响应拒绝加入军团 */
	public static final short GC_REJECT_JOIN_LEGION = ++LEGION_BEGIN;
	/** 请求退出军团 */
	public static final short CG_QUIT_LEGION = ++LEGION_BEGIN;
	/** 响应退出军团 */
	public static final short GC_QUIT_LEGION = ++LEGION_BEGIN;
	/** 请求解散军团 */
	public static final short CG_DISSOLVE_LEGION = ++LEGION_BEGIN;
	/** 响应解散军团 */
	public static final short GC_DISSOLVE_LEGION = ++LEGION_BEGIN;
	/** 请求删除军团成员 */
	public static final short CG_REMOVE_LEGION_MEMBER = ++LEGION_BEGIN;
	/** 响应删除军团成员 */
	public static final short GC_REMOVE_LEGION_MEMBER = ++LEGION_BEGIN;
	/** 请求晋升团长 */
	public static final short CG_APPLY_LEGION_COMMANDER = ++LEGION_BEGIN;
	/** 响应晋升团长 */
	public static final short GC_APPLY_LEGION_COMMANDER = ++LEGION_BEGIN;
	/** 请求转让军团 */
	public static final short CG_TRANSFER_LEGION = ++LEGION_BEGIN;
	/** 响应转让军团 */
	public static final short GC_TRANSFER_LEGION = ++LEGION_BEGIN;
	/** 请求搜索军团 */
	public static final short CG_SEARCH_LEGION = ++LEGION_BEGIN;
	/** 响应搜索军团 */
	public static final short GC_SEARCH_LEGION = ++LEGION_BEGIN;
	/** 请求添加公告 */
	public static final short CG_ADD_LEGION_NOTICE = ++LEGION_BEGIN;
	/** 响应添加公告 */
	public static final short GC_ADD_LEGION_NOTICE = ++LEGION_BEGIN;
	/** 更新角色所在军团信息 */
	public static final short GC_UPDATE_HUMAN_LEGION_INFO =++LEGION_BEGIN;
	// 军团新功能
	/** 请求升级军团建筑 */
	public static final short CG_UPGRADE_LEGION_BUILDING = ++LEGION_BEGIN;
	/** 响应升级军团建筑 */
	public static final short GC_UPGRADE_LEGION_BUILDING = ++LEGION_BEGIN;
	/** 请求展示冥想殿标签 */
	public static final short CG_SHOW_LEGION_MEDITATION_TAB = ++LEGION_BEGIN;
	/** 响应展示冥想殿标签 */
	public static final short GC_SHOW_LEGION_MEDITATION_TAB = ++LEGION_BEGIN;
	/** 请求军团冥想 */
	public static final short CG_LEGION_MEDITATION = ++LEGION_BEGIN;
	/** 响应军团冥想 */
	public static final short GC_LEGION_MEDITATION = ++LEGION_BEGIN;
	/** 请求展示军团商店标签 */
	public static final short CG_SHOW_LEGION_SHOP_TAB = ++LEGION_BEGIN;
	/** 响应展示军团商店标签 */
	public static final short GC_SHOW_LEGION_SHOP_TAB = ++LEGION_BEGIN;
	/** 请求展示军团某一类型商品 */
	public static final short CG_SHOW_LEGION_SHOP_ITEMS = ++LEGION_BEGIN;
	/** 响应展示军团某一类型商品 */
	public static final short GC_SHOW_LEGION_SHOP_ITEMS = ++LEGION_BEGIN;
	/** 请求购买军团商品 */
	public static final short CG_BUY_LEGION_SHOP_ITEM = ++LEGION_BEGIN;
	/** 响应购买军团商品 */
	public static final short GC_BUY_LEGION_SHOP_ITEM = ++LEGION_BEGIN;
	/** 请求展示军团荣誉堂标签 */
	public static final short CG_SHOW_LEGION_HONOR_TAB = ++LEGION_BEGIN;
	/** 响应展示军团荣誉堂标签 */
	public static final short GC_SHOW_LEGION_HONOR_TAB = ++LEGION_BEGIN;
	/** 请求兑换军团头衔 */
	public static final short CG_EXCHANGE_LEGION_TITLE = ++LEGION_BEGIN;
	/** 响应兑换军团头衔 */
	public static final short GC_EXCHANGE_LEGION_TITLE = ++LEGION_BEGIN;
	/** 请求展示军团科技厅标签 */
	public static final short CG_SHOW_LEGION_TECHNOLOGY_TAB = ++LEGION_BEGIN;
	/** 响应展示军团科技厅标签 */
	public static final short GC_SHOW_LEGION_TECHNOLOGY_TAB = ++LEGION_BEGIN;
	/** 请求捐献军团科技 */
	public static final short CG_CONTRIBUTE_LEGION_TECHNOLOGY = ++LEGION_BEGIN;
	/** 响应捐献军团科技 */
	public static final short GC_CONTRIBUTE_LEGION_TECHNOLOGY = ++LEGION_BEGIN;
	/** 请求展示军团悬赏令标签 */
	public static final short CG_SHOW_LEGION_TASK_TAB = ++LEGION_BEGIN;
	/** 响应展示军团悬赏令标签 */
	public static final short GC_SHOW_LEGION_TASK_TAB = ++LEGION_BEGIN;
	/** 请求刷新军团任务列表 */
	public static final short CG_REFRESH_LEGION_TASK_LIST = ++LEGION_BEGIN;
	/** 响应刷新军团任务列表 */
	public static final short GC_REFRESH_LEGION_TASK_LIST = ++LEGION_BEGIN;
	/** 请求接受军团任务 */
	public static final short CG_START_LEGION_TASK = ++LEGION_BEGIN;
	/** 响应接受军团任务 */
	public static final short GC_START_LEGION_TASK = ++LEGION_BEGIN;
	/** 请求取消军团任务 */
	public static final short CG_ABORT_LEGION_TASK = ++LEGION_BEGIN;
	/** 响应取消军团任务 */
	public static final short GC_ABORT_LEGION_TASK = ++LEGION_BEGIN;
	/** 请求立即完成军团任务 */
	public static final short CG_COMPLETE_LEGION_TASK = ++LEGION_BEGIN;
	/** 响应立即完成军团任务 */
	public static final short GC_COMPLETE_LEGION_TASK = ++LEGION_BEGIN;
	/** 申请立即完成军团任务 */
	public static final short CG_APPLY_COMPLETE_LEGION_TASK = ++LEGION_BEGIN;
	/** 响应申请立即完成军团任务 */
	public static final short GC_APPLY_COMPLETE_LEGION_TASK = ++LEGION_BEGIN;
	/** 请求获得军团任务奖励 */
	public static final short CG_GET_LEGION_TASK_REWARD = ++LEGION_BEGIN;
	/** 响应获得军团任务奖励 */
	public static final short GC_GET_LEGION_TASK_REWARD = ++LEGION_BEGIN;
	/** 请求展示军团任务排行*/
	public static final short CG_SHOW_LEGION_TASK_RANK = ++LEGION_BEGIN;
	/** 响应展示军团任务排行 */
	public static final short GC_SHOW_LEGION_TASK_RANK = ++LEGION_BEGIN;
	/** 请求获得军团任务排行奖励 */
	public static final short CG_GET_LEGION_TASK_RANK_REWARD = ++LEGION_BEGIN;
	/** 响应获得军团任务排行奖励 */
	public static final short GC_GET_LEGION_TASK_RANK_REWARD = ++LEGION_BEGIN;
	/** 下发角色军团头衔信息 */
	public static final short GC_SEND_HUMAN_LEGION_TITLE_INFO = ++LEGION_BEGIN;
	/** 请求展示军团矿战标签 */
	public static final short CG_SHOW_LEGION_MINE_TAB = ++LEGION_BEGIN;
	/** 响应展示军团矿战标签 */
	public static final short GC_SHOW_LEGION_MINE_TAB = ++LEGION_BEGIN;
	/** 请求捐献军团魔晶 */
	public static final short CG_DONATE_LEGION_CRYSTAL = ++LEGION_BEGIN;
	/** 响应捐献军团魔晶 */
	public static final short GC_DONATE_LEGION_CRYSTAL = ++LEGION_BEGIN;
	
	// /////////////
	// 神魄
	// ////////////
	public static short GODSOUL_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	/** 请求打开神魄面板 */
	public static final short CG_OPEN_GODSOUL_PANEL = ++GODSOUL_BEGIN;
	/** 响应打开神魄面板 */
	public static final short GC_OPEN_GODSOUL_PANEL = ++GODSOUL_BEGIN;
	/** 请求升级装备位 */
	public static final short CG_UPGRADE_EQUIP_BIT = ++GODSOUL_BEGIN;
	/** 响应升级装备位 */
	public static final short GC_UPGRADE_EQUIP_BIT = ++GODSOUL_BEGIN;
	/** 请求展示灵图标签页 */
	public static final short CG_SHOW_MAGIC_PAPER_TAB = ++GODSOUL_BEGIN;
	/** 响应展示灵图标签页 */
	public static final short GC_SHOW_MAGIC_PAPER_TAB = ++GODSOUL_BEGIN;
	/** 请求升级灵图 */
	public static final short CG_UPGRADE_MAGIC_PAPER = ++GODSOUL_BEGIN;
	/** 响应升级灵图 */
	public static final short GC_UPGRADE_MAGIC_PAPER = ++GODSOUL_BEGIN;

	// /////////////
	// 战俘营
	// ////////////
	public static short PRISON_BEGIN = (BASE_NUMBER += NUMBER_PER_SYS);
	/** 请求打开战俘营面板 */
	public static final short CG_OPEN_PRISON_PANEL = ++PRISON_BEGIN;
	/** 响应打开战俘营面板 */
	public static final short GC_OPEN_PRISON_PANEL = ++PRISON_BEGIN;
	/** 请求展示战俘营日志标签页 */
	public static final short CG_SHOW_PRISON_LOG_TAB = ++PRISON_BEGIN;
	/** 响应展示战俘营日志标签页 */
	public static final short GC_SHOW_PRISON_LOG_TAB = ++PRISON_BEGIN;
	// Start-------与主人类似部分--------------Start
	/** 展示抓捕页面 */
	public static final short CG_SHOW_ARREST_TAB = ++PRISON_BEGIN;
	/** 响应展示抓捕页面 */
	public static final short GC_SHOW_ARREST_TAB = ++PRISON_BEGIN;
	/** 展示手下败将页面 */
	public static final short CG_SHOW_LOOSER_TAB = ++PRISON_BEGIN;
	/** 响应展示手下败将页面 */
	public static final short GC_SHOW_LOOSER_TAB = ++PRISON_BEGIN;
	/** 展示夺俘之敌页面 */
	public static final short CG_SHOW_ENEMY_TAB = ++PRISON_BEGIN;
	/** 响应展示夺俘之敌页面 */
	public static final short GC_SHOW_ENEMY_TAB = ++PRISON_BEGIN;
	/** 申请抓捕 */
	public static final short CG_APPLY_ARREST = ++PRISON_BEGIN;
	/** 响应申请抓捕 */
	public static final short GC_APPLY_ARREST = ++PRISON_BEGIN;
	/** 购买抓捕次数 */
	public static final short CG_BUY_ARREST_NUM = ++PRISON_BEGIN;
	/** 响应购买抓捕次数 */
	public static final short GC_BUY_ARREST_NUM = ++PRISON_BEGIN;
	/** 展示解救标签页 */
	public static final short CG_SHOW_RESCUE_TAB = ++PRISON_BEGIN;
	/** 响应展示解救标签页 */
	public static final short GC_SHOW_RESCUE_TAB = ++PRISON_BEGIN;
	/** 申请解救 */
	public static final short CG_APPLY_RESCUE = ++PRISON_BEGIN;
	/** 响应申请解救 */
	public static final short GC_APPLY_RESCUE = ++PRISON_BEGIN;
	// End-------与主人类似部分--------------End
	/** 奴隶展示主人标签页 */
	public static final short CG_SLAVE_SHOW_MASTER_TAB = ++PRISON_BEGIN;
	/** 响应奴隶展示主人标签页 */
	public static final short GC_SLAVE_SHOW_MASTER_TAB = ++PRISON_BEGIN;
	/** 奴隶与主人互动 */
	public static final short CG_SLAVE_INTERACT = ++PRISON_BEGIN;
	/** 响应奴隶与主人互动 */
	public static final short GC_SLAVE_INTERACT = ++PRISON_BEGIN;
	/** 奴隶反抗主人 */
	public static final short CG_SLAVE_REVOLT = ++PRISON_BEGIN;
	/** 响应奴隶反抗主人 */
	public static final short GC_SLAVE_REVOLT = ++PRISON_BEGIN;
	/** 奴隶展示求救标签页 */
	public static final short CG_SLAVE_SHOW_SOS_TAB = ++PRISON_BEGIN;
	/** 响应奴隶展示求救标签页 */
	public static final short GC_SLAVE_SHOW_SOS_TAB = ++PRISON_BEGIN;
	/** 奴隶求救 */
	public static final short CG_SLAVE_FOR_HELP = ++PRISON_BEGIN;
	/** 响应奴隶求救 */
	public static final short GC_SLAVE_FOR_HELP = ++PRISON_BEGIN;
	/** 给被求救的角色发求救消息 */
	public static final short GC_SLAVE_FOR_HELPED = ++PRISON_BEGIN;
	/** 主人获取奴隶列表 */
	public static final short CG_MASTER_GET_SLAVE_LIST = ++PRISON_BEGIN;
	/** 响应主人获取奴隶列表 */
	public static final short GC_MASTER_GET_SLAVE_LIST = ++PRISON_BEGIN;
	/** 主人展示压榨奴隶页面 */
	public static final short CG_SHOW_PRESS_TAB = ++PRISON_BEGIN;
	/** 响应主人展示压榨奴隶页面 */
	public static final short GC_SHOW_PRESS_TAB = ++PRISON_BEGIN;
	/** 主人申请提取奴隶当前经验 */
	public static final short CG_MASTER_APPLY_EXTRACT_EXPERIENCE = ++PRISON_BEGIN;
	/** 响应主人申请提取奴隶当前经验 */
	public static final short GC_MASTER_APPLY_EXTRACT_EXPERIENCE = ++PRISON_BEGIN;
	/** 主人提取奴隶当前经验 */
	public static final short CG_MASTER_EXTRACT_EXPERIENCE = ++PRISON_BEGIN;
	/** 响应主人提取奴隶当前经验 */
	public static final short GC_MASTER_EXTRACT_EXPERIENCE = ++PRISON_BEGIN;
	/** 主人释放奴隶 */
	public static final short CG_MASTER_RELEASE = ++PRISON_BEGIN;
	/** 响应主人释放奴隶 */
	public static final short GC_MASTER_RELEASE = ++PRISON_BEGIN;
	/** 主人与奴隶互动 */
	public static final short CG_MASTER_INTERACT = ++PRISON_BEGIN;
	/** 响应主人与奴隶互动 */
	public static final short GC_MASTER_INTERACT = ++PRISON_BEGIN;
	/** 身份变更通知客户端更新面板 */
	public static final short GC_PRISON_IDENTITY_CHANGED = ++PRISON_BEGIN;

	// /////////////
	// 角斗场
	// ////////////
	public static short ABATTOIR_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开角斗场面板 */
	public static final short CG_OPEN_ABATTOIR_PANEL = ++PRISON_BEGIN;
	/** 响应打开角斗场面板 */
	public static final short GC_OPEN_ABATTOIR_PANEL = ++PRISON_BEGIN;
	/** 请求抢夺房间 */
	public static final short CG_LOOT_ABATTOIR_ROOM = ++PRISON_BEGIN;
	/** 请求退出房间 */
	public static final short CG_QUIT_ABATTOIR_ROOM = ++PRISON_BEGIN;
	/** 响应退出房间 */
	public static final short GC_QUIT_ABATTOIR_ROOM = ++PRISON_BEGIN;
	/** 请求购买角斗次数 */
	public static final short CG_BUY_ABATTOIR_WRESTLE_NUM = ++PRISON_BEGIN;
	/** 响应购买角斗次数 */
	public static final short GC_BUY_ABATTOIR_WRESTLE_NUM = ++PRISON_BEGIN;
	/** 请求提取威望 */
	public static final short CG_EXTRACT_ABATTOIR_HONOR = ++PRISON_BEGIN;
	/** 响应提取威望 */
	public static final short GC_EXTRACT_ABATTOIR_PRESTIGE = ++PRISON_BEGIN;
	/** 请求展示仇人列表 */
	public static final short CG_SHOW_ABATTOIR_ENEMY = ++PRISON_BEGIN;
	/** 响应展示仇人列表 */
	public static final short GC_SHOW_ABATTOIR_ENEMY = ++PRISON_BEGIN;

	// /////////////
	// 嗜血神殿
	// ////////////
	public static short BLOOD_TEMPLE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开嗜血神殿面板 */
	public static final short CG_OPEN_BLOOD_TEMPLE_PANEL = ++BLOOD_TEMPLE_BEGIN;
	/** 响应打开嗜血神殿面板 */
	public static final short GC_OPEN_BLOOD_TEMPLE_PANEL = ++BLOOD_TEMPLE_BEGIN;
	/** 请求展示嗜血神殿房间 */
	public static final short CG_SHOW_BLOOD_TEMPLE_ROOM = ++BLOOD_TEMPLE_BEGIN;
	/** 响应展示嗜血神殿房间 */
	public static final short GC_SHOW_BLOOD_TEMPLE_ROOM = ++BLOOD_TEMPLE_BEGIN;
	/** 请求抢夺房间 */
	public static final short CG_LOOT_BLOOD_TEMPLE_ROOM = ++BLOOD_TEMPLE_BEGIN;
	/** 请求退出房间 */
	public static final short CG_QUIT_BLOOD_TEMPLE_ROOM = ++BLOOD_TEMPLE_BEGIN;
	/** 响应退出房间 */
	public static final short GC_QUIT_BLOOD_TEMPLE_ROOM = ++BLOOD_TEMPLE_BEGIN;
	/** 请求购买角斗次数 */
	public static final short CG_BUY_BLOOD_TEMPLE_WRESTLE_NUM = ++BLOOD_TEMPLE_BEGIN;
	/** 响应购买角斗次数 */
	public static final short GC_BUY_BLOOD_TEMPLE_WRESTLE_NUM = ++BLOOD_TEMPLE_BEGIN;
	/** 请求提取威望 */
	public static final short CG_EXTRACT_BLOOD_TEMPLE_HONOR = ++BLOOD_TEMPLE_BEGIN;
	/** 响应提取威望 */
	public static final short GC_EXTRACT_BLOOD_TEMPLE_PRESTIGE = ++BLOOD_TEMPLE_BEGIN;
	/** 请求展示仇人列表 */
	public static final short CG_SHOW_BLOOD_TEMPLE_ENEMY = ++BLOOD_TEMPLE_BEGIN;
	/** 响应展示仇人列表 */
	public static final short GC_SHOW_BLOOD_TEMPLE_ENEMY = ++BLOOD_TEMPLE_BEGIN;

	// /////////////
	// 精灵
	// ////////////
	// 精灵酒馆
	public static short SPRITE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开精灵酒馆面板 */
	public static final short CG_OPEN_PUB_PANEL = ++SPRITE_BEGIN;
	/** 打开精灵酒馆面板响应 */
	public static final short GC_OPEN_PUB_PANEL = ++SPRITE_BEGIN;
	/** 请求开始对酒 */
	public static final short CG_FINGER_GUESSING = ++SPRITE_BEGIN;
	/** 开始对酒响应 */
	public static final short GC_FINGER_GUESSING = ++SPRITE_BEGIN;
	/** 请求出拳 */
	public static final short CG_GIVE_FINGER = ++SPRITE_BEGIN;
	/** 出拳响应 */
	public static final short GC_GIVE_FINGER = ++SPRITE_BEGIN;
	/** 服务器下发的页签信息列表 */
	public static final short GC_PUB_PAGE_INFO = ++SPRITE_BEGIN;
	/** 请求必胜出拳 */
	public static final short CG_SUCCEED_FINGER = ++SPRITE_BEGIN;
	/** 请求打开招募面板 */
	public static final short CG_OPEN_RECRUIT_PANEL = ++SPRITE_BEGIN;
	/** 打开招募面板响应 */
	public static final short GC_OPEN_RECRUIT_PANEL = ++SPRITE_BEGIN;
	/** 请求招募精灵 */
	public static final short CG_RECRUIT_SPRITE = ++SPRITE_BEGIN;
	/** 招募精灵响应 */
	public static final short GC_RECRUIT_SPRITE = ++SPRITE_BEGIN;
	/** 招募精灵配置 */
	public static final short GC_RECRUIT_CONFIG = ++SPRITE_BEGIN;
	// 精灵出战
	/** 请求打开精灵面板 */
	public static final short CG_OPEN_SPRITE_PANEL = ++SPRITE_BEGIN;
	/** 打开精灵面板响应 */
	public static final short GC_OPEN_SPRITE_PANEL = ++SPRITE_BEGIN;
	/** 请求购买精灵格子 */
	public static final short CG_BUY_SPRITE_CELL = ++SPRITE_BEGIN;
	/** 购买精灵格子响应 */
	public static final short GC_BUY_SPRITE_CELL = ++SPRITE_BEGIN;
	/** 请求装备精灵 */
	public static final short CG_EQUIP_SPRITE = ++SPRITE_BEGIN;
	/** 请求卸下精灵 */
	public static final short CG_UNEQUIP_SPRITE = ++SPRITE_BEGIN;
	/** 精灵装备状态 */
	public static final short GC_SPRITE_EQUIP_STATE = ++SPRITE_BEGIN;
	/** 请求精灵升级 */
	public static final short CG_SPRITE_LEVELUP = ++SPRITE_BEGIN;
	/** 精灵升级后信息更新 */
	public static final short GC_UPDATE_SPRITE = ++SPRITE_BEGIN;
	/** 请求丢弃精灵 */
	public static final short CG_DROP_SPRITE = ++SPRITE_BEGIN;
	/** 丢弃精灵响应 */
	public static final short GC_DROP_SPRITE = ++SPRITE_BEGIN;
	/** 激活套装对应的buff */
	public static final short GC_ACTIVATE_BUFF = ++SPRITE_BEGIN;
	// /////////////
	// 星图
	// ////////////
	// 精灵酒馆
	public static short STAR_MAP_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开星图面板 */
	public static final short CG_OPEN_STAR_MAP_PANEL = ++SPRITE_BEGIN;
	/** 响应打开星图面板 */
	public static final short GC_OPEN_STAR_MAP_PANEL = ++SPRITE_BEGIN;
	/** 请求星座列表 */
	public static final short CG_GET_SIGN_LIST = ++SPRITE_BEGIN;
	/** 返回星座列表 */
	public static final short GC_GET_SIGN_LIST = ++SPRITE_BEGIN;
	/** 激活指定星座 */
	public static final short CG_ACTIVATE_SIGN = ++SPRITE_BEGIN;
	/** 响应激活星座 */
	public static final short GC_ACTIVATE_SIGN = ++SPRITE_BEGIN;
	/** 服务器通知下一张星图激活 */
	public static final short GC_ACTIVATE_NEW_STAR_MAP = ++SPRITE_BEGIN;

	// /////////////
	// 首充
	// ////////////
	public static short FIRST_RECHARGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开首充面板 */
	public static final short CG_OPEN_FIRST_RECHARGE_PANEL = ++FIRST_RECHARGE_BEGIN;
	/** 响应打开首充面板 */
	public static final short GC_OPEN_FIRST_RECHARGE_PANEL = ++FIRST_RECHARGE_BEGIN;
	/** 请求领取首充奖励 */
	public static final short CG_GET_FIRST_RECHARGE_REWARD = ++FIRST_RECHARGE_BEGIN;
	/** 响应领取首充奖励 */
	public static final short GC_GET_FIRST_RECHARGE_REWARD = ++FIRST_RECHARGE_BEGIN;

	/** 请求打开充值活动面板 */
	public static final short CG_OPEN_RECHARGE_ACTIVITY_PANEL = ++FIRST_RECHARGE_BEGIN;
	/** 响应打开充值活动面板 */
	public static final short GC_OPEN_RECHARGE_ACTIVITY_PANEL = ++FIRST_RECHARGE_BEGIN;

	// /////////////
	// 单笔充值
	// ////////////
	public static short SINGLE_RECHARGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开单笔充值标签 */
	public static final short CG_SHOW_SINGLE_RECHARGE_TAB = ++SINGLE_RECHARGE_BEGIN;
	/** 响应打开单笔充值标签 */
	public static final short GC_SHOW_SINGLE_RECHARGE_TAB = ++SINGLE_RECHARGE_BEGIN;
	/** 请求领取单笔充值奖励 */
	public static final short CG_GET_SINGLE_RECHARGE_REWARD = ++SINGLE_RECHARGE_BEGIN;
	/** 响应领取单笔充值奖励 */
	public static final short GC_GET_SINGLE_RECHARGE_REWARD = ++SINGLE_RECHARGE_BEGIN;

	// /////////////
	// 累计充值
	// ////////////
	public static short TOTAL_RECHARGE_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开累计充值标签 */
	public static final short CG_SHOW_TOTAL_RECHARGE_TAB = ++TOTAL_RECHARGE_BEGIN;
	/** 响应打开累计充值标签 */
	public static final short GC_SHOW_TOTAL_RECHARGE_TAB = ++TOTAL_RECHARGE_BEGIN;
	/** 请求领取累计充值奖励 */
	public static final short CG_GET_TOTAL_RECHARGE_REWARD = ++TOTAL_RECHARGE_BEGIN;
	/** 响应领取累计充值奖励 */
	public static final short GC_GET_TOTAL_RECHARGE_REWARD = ++TOTAL_RECHARGE_BEGIN;

	// /////////////
	// 附魔
	// ////////////
	public static short ATTACH_MAGIC_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开附魔面板 */
	public static final short CG_OPEN_ATTACH_MAGIC_PANEL = ++ATTACH_MAGIC_BEGIN;
	/** 响应打开附魔面板 */
	public static final short GC_OPEN_ATTACH_MAGIC_PANEL = ++ATTACH_MAGIC_BEGIN;
	/** 请求获取魔法信息 */
	public static final short CG_GET_MAGIC_INFO = ++ATTACH_MAGIC_BEGIN;
	/** 返回取魔法信息 */
	public static final short GC_GET_MAGIC_INFO = ++ATTACH_MAGIC_BEGIN;
	/** 请求转系 */
	public static final short CG_CHANGE_SKILL_DEVELOP_TYPE = ++ATTACH_MAGIC_BEGIN;
	/** 响应转系 */
	public static final short GC_CHANGE_SKILL_DEVELOP_TYPE = ++ATTACH_MAGIC_BEGIN;

	// /////////////
	// 阵营
	// ////////////
	public static short FACTION_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开阵营面板 */
	public static final short CG_OPEN_FACTION_PANEL = ++ATTACH_MAGIC_BEGIN;
	/** 响应打开阵营面板 */
	public static final short GC_OPEN_FACTION_PANEL = ++ATTACH_MAGIC_BEGIN;
	/** 响应打开选择阵营面板 */
	public static final short GC_OPEN_SELECT_FACTION_PANEL = ++ATTACH_MAGIC_BEGIN;
	/** 请求选择阵营 */
	public static final short CG_SELECT_FACTION = ++ATTACH_MAGIC_BEGIN;
	/** 响应选择阵营结果 */
	public static final short GC_SELECT_FACTION = ++ATTACH_MAGIC_BEGIN;
	/** 请求随机加入阵营 */
	public static final short CG_RANDOM_SELECT_FACTION = ++ATTACH_MAGIC_BEGIN;

	// /////////////
	// 战神之巅
	// ////////////
	public static short MARS_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开战神之巅面板 */
	public static final short CG_OPEN_MARS_PANEL = ++MARS_BEGIN;
	/** 响应打开战神之巅面板 */
	public static final short GC_OPEN_MARS_PANEL = ++MARS_BEGIN;
	/** 请求刷新战神之巅房间 */
	public static final short CG_RFRESH_MARS_ROOM = ++MARS_BEGIN;
	/** 响应刷新战神之巅房间 */
	public static final short GC_RFRESH_MARS_ROOM = ++MARS_BEGIN;
	/** 请求击杀战神之巅玩家 */
	public static final short CG_KILL_MARS_PLAYER = ++MARS_BEGIN;
	/** 响应击杀战神之巅玩家 */
	public static final short GC_KILL_MARS_PLAYER = ++MARS_BEGIN;
	/** 请求解锁战神之巅房间 */
	public static final short CG_UNLOCK_MARS_ROOM = ++MARS_BEGIN;
	/** 响应解锁战神之巅房间 */
	public static final short GC_UNLOCK_MARS_ROOM = ++MARS_BEGIN;
	/** 请求购买加倍次数 */
	public static final short CG_BUY_MARS_MULTIPLE_NUM = ++MARS_BEGIN;
	/** 响应购买加倍次数 */
	public static final short GC_BUY_MARS_MULTIPLE_NUM = ++MARS_BEGIN;
	/** 请求打开战神之巅排行榜面板 */
	public static final short CG_OPEN_MARS_RANK_PANEL = ++MARS_BEGIN;
	/** 响应打开战神之巅排行榜面板 */
	public static final short GC_OPEN_MARS_RANK_PANEL = ++MARS_BEGIN;
	/** 领取战神之巅击杀奖励 */
	public static final short CG_GET_MARS_KILL_REWARD = ++ARENA_BEGIN;
	/** 响应领取战神之巅击杀奖励 */
	public static final short GC_GET_MARS_KILL_REWARD = ++ARENA_BEGIN;

	// /////////////
	// 战神之巅
	// ////////////
	public static short TARGET_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开个人目标面板 */
	public static final short CG_OPEN_TARGET_PANEL = ++TARGET_BEGIN;
	/** 响应打开个人目标面板 */
	public static final short GC_OPEN_TARGET_PANEL = ++TARGET_BEGIN;
	/** 请求领取个人目标奖励 */
	public static final short CG_GET_TARGET_REWARD = ++TARGET_BEGIN;
	/** 响应领取个人目标奖励 */
	public static final short GC_GET_TARGET_REWARD = ++TARGET_BEGIN;
	/** 更新个人目标面板 */
	public static final short GC_UPDATE_TARGET_PANEL = ++TARGET_BEGIN;

	// /////////////
	// 军团boss战系统
	// ////////////
	public static short LEGION_BOSS_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 参与军团boss战 */
	public static final short GC_JOIN_LEGION_BOSS_WAR = ++LEGION_BOSS_BEGIN;
	/** 金币鼓舞 */
	public static final short CG_ENCOURAGE_LEGION_BOSS_WAR = ++LEGION_BOSS_BEGIN;
	/** 金币鼓舞 */
	public static final short GC_ENCOURAGE_LEGION_BOSS_WAR = ++LEGION_BOSS_BEGIN;
	/** 充能 */
	public static final short CG_CHARGED_LEGION_BOSS_WAR = ++LEGION_BOSS_BEGIN;
	/** 充能 */
	public static final short GC_CHARGED_LEGION_BOSS_WAR = ++LEGION_BOSS_BEGIN;
	/** 用充能打击 */
	public static final short CG_CHARGED_LEGION_BOSS = ++LEGION_BOSS_BEGIN;
	/** 攻击军团boss */
	public static final short CG_ATTACK_LEGION_BOSS = ++LEGION_BOSS_BEGIN;
	/** 通知有军团boss战奖励 */
	public static final short GC_HAS_LEGION_BOSS_REWARD = ++LEGION_BOSS_BEGIN;
	/** 请求进入军团boss战 */
	public static final short CG_ENTER_LEGION_BOSS_WAR = ++LEGION_BOSS_BEGIN;
	/** 用充能攻击 */
	public static final short GC_CHARGED_LEGION_BOSS = ++LEGION_BOSS_BEGIN;

	// /////////////
	// 军团矿战系统
	// ////////////
	public static short LEGION_MINE_WAR_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开军团矿战面板 */
	public static final short CG_OPEN_LEGION_MINE_WAR_PANEL = ++LEGION_MINE_WAR_BEGIN;
	/** 响应打开军团矿战面板 */
	public static final short GC_OPEN_LEGION_MINE_WAR_PANEL = ++LEGION_MINE_WAR_BEGIN;
	/** 请求移动 */
	public static final short CG_LEGION_MINE_WAR_MOVE = ++LEGION_MINE_WAR_BEGIN;
	/** 请求侦查 */
	public static final short CG_LEGION_MINE_WAR_WATCH = ++LEGION_MINE_WAR_BEGIN;
	/** 通知客户端更新矿堆列表 */
	public static final short GC_UPDATE_LEGION_MINE_LIST = ++LEGION_MINE_WAR_BEGIN;
	/** 通知客户端更新军团buf列表 */
	public static final short GC_UPDATE_LEGION_BUF_LIST = ++LEGION_MINE_WAR_BEGIN;
	/** 请求使用个人buf */
	public static final short CG_USE_SELF_BUF = ++LEGION_MINE_WAR_BEGIN;
	/** 响应使用个人buf */
	public static final short GC_USE_SELF_BUF = ++LEGION_MINE_WAR_BEGIN;
	/** 请求取消个人buf */
	public static final short CG_ABORT_SELF_BUF = ++LEGION_MINE_WAR_BEGIN;
	/** 响应取消个人buf */
	public static final short GC_ABORT_SELF_BUF = ++LEGION_MINE_WAR_BEGIN;
	/** 请求收获 */
	public static final short CG_LEGION_MINE_WAR_HARVEST = ++LEGION_MINE_WAR_BEGIN;
	/** 响应收获 */
	public static final short GC_LEGION_MINE_WAR_HARVEST = ++LEGION_MINE_WAR_BEGIN;
	/** 请求战斗 */
	public static final short CG_LEGION_MINE_WAR_BATTLE = ++LEGION_MINE_WAR_BEGIN;
	/** 请求鼓舞 */
	public static final short CG_LEGION_MINE_WAR_ENCOURAGE = ++LEGION_MINE_WAR_BEGIN;
	/** 响应鼓舞 */
	public static final short GC_LEGION_MINE_WAR_ENCOURAGE = ++LEGION_MINE_WAR_BEGIN;
	/** 请求退出 */
	public static final short CG_LEGION_MINE_WAR_QUIT = ++LEGION_MINE_WAR_BEGIN;
	/** 活动结束通知客户端 */
	public static final short GC_LEGION_MINE_WAR_END = ++LEGION_MINE_WAR_BEGIN;
	/** 更新矿战信息 */
	public static final short GC_UPDATE_MINE_WAR_INFO = ++LEGION_MINE_WAR_BEGIN;

	// /////////////
	// 秘药系统
	// ////////////
	public static short NOSTRUM_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开秘药面板 */
	public static final short CG_OPEN_NOSTRUM_PANEL = ++NOSTRUM_BEGIN;
	/** 响应打开秘药面板 */
	public static final short GC_OPEN_NOSTRUM_PANEL = ++NOSTRUM_BEGIN;

	// /////////////
	// 押运系统
	// ////////////
	public static short ESCORT_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开押运面板 */
	public static final short CG_OPEN_ESCORT_PANEL = ++ESCORT_BEGIN;
	/** 请求关闭押运面板 */
	public static final short CG_CLOSE_ESCORT_PANEL = ++ESCORT_BEGIN;
	/** 响应更新押运面板 */
	public static final short GC_UPDATE_ESCORT_PANEL = ++ESCORT_BEGIN;
	/** 请求展示怪物标签页 */
	public static final short CG_SHOW_MONSTER_TAB = ++ESCORT_BEGIN;
	/** 响应展示怪物标签页 */
	public static final short GC_SHOW_MONSTER_TAB = ++ESCORT_BEGIN;
	/** 请求展示押运标签页 */
	public static final short CG_SHOW_ESCORT_TAB = ++ESCORT_BEGIN;
	/** 响应展示押运标签页 */
	public static final short GC_SHOW_ESCORT_TAB = ++ESCORT_BEGIN;
	/** 拦截押运 */
	public static final short CG_ROB_ESCROT = ++ESCORT_BEGIN;
	/** 请求刷新押运怪物 */
	public static final short CG_ESCORT_REFRESH_MONSTER = ++ESCORT_BEGIN;
	/** 响应刷新押运怪物 */
	public static final short GC_ESCORT_REFRESH_MONSTER = ++ESCORT_BEGIN;
	/** 请求召唤最高品质怪物 */
	public static final short CG_ESCORT_CALL_MONSTER = ++ESCORT_BEGIN;
	/** 响应召唤最高品质怪物 */
	public static final short GC_ESCORT_CALL_MONSTER = ++ESCORT_BEGIN;
	/** 请求邀请护送好友 */
	public static final short CG_ESCORT_INVITE_FRIEND = ++ESCORT_BEGIN;
	/** 响应邀请护送好友 */
	public static final short GC_ESCORT_INVITE_FRIEND = ++ESCORT_BEGIN;
	/** 向好友发送邀请申请 */
	public static final short GC_ESCORT_INVITE_FRIEND_APPLY = ++ESCORT_BEGIN;
	/** 请求撤销邀请 */
	public static final short CG_ESCORT_ABORT_INVITE = ++ESCORT_BEGIN;
	/** 响应撤销邀请 */
	public static final short GC_ESCORT_ABORT_INVITE = ++ESCORT_BEGIN;
	/** 请求同意邀请 */
	public static final short CG_ESCORT_AGREE_INVITE = ++ESCORT_BEGIN;
	/** 响应同意邀请 */
	public static final short GC_ESCORT_AGREE_INVITE = ++ESCORT_BEGIN;
	/** 请求拒绝邀请 */
	public static final short CG_ESCORT_REJECT_INVITE = ++ESCORT_BEGIN;
	/** 响应拒绝邀请 */
	public static final short GC_ESCORT_REJECT_INVITE = ++ESCORT_BEGIN;
	/** 请求开始押运 */
	public static final short CG_START_ESCORT = ++ESCORT_BEGIN;
	/** 响应开始押运 */
	public static final short GC_START_ESCORT = ++ESCORT_BEGIN;
	/** 请求军团祈福 */
	public static final short CG_ESCORT_LEGION_PRAY = ++ESCORT_BEGIN;
	/** 响应军团祈福 */
	public static final short GC_ESCORT_LEGION_PRAY = ++ESCORT_BEGIN;
	/** 请求展示拦截榜 */
	public static final short CG_SHOW_ROB_RANK_TAB = ++ESCORT_BEGIN;
	/** 响应展示拦截榜 */
	public static final short GC_SHOW_ROB_RANK_TAB = ++ESCORT_BEGIN;
	/** 请求领取奖励 */
	public static final short CG_ESCORT_GET_REWARD = ++ESCORT_BEGIN;
	/** 响应领取奖励 */
	public static final short GC_ESCORT_GET_REWARD = ++ESCORT_BEGIN;
	/** 请求购买拦截次数 */
	public static final short CG_ESCORT_BUY_ROB_NUM = ++ESCORT_BEGIN;
	/** 响应购买拦截次数 */
	public static final short GC_ESCORT_BUY_ROB_NUM = ++ESCORT_BEGIN;
	
	// /////////////
	// 预见系统
	// ////////////
	public static short PREDICT_BEGIN = (BASE_NUMBER += NUMBER_PER_SUB_SYS);
	/** 请求打开预见面板 */
	public static final short CG_OPEN_PREDICT_PANEL = ++PREDICT_BEGIN;
	/** 响应打开预见面板 */
	public static final short GC_OPEN_PREDICT_PANEL = ++PREDICT_BEGIN;
	/** 请求展示预见列表 */
	public static final short CG_SHOW_PREDICT_LIST = ++PREDICT_BEGIN;
	/** 响应展示预见列表 */
	public static final short GC_SHOW_PREDICT_LIST = ++PREDICT_BEGIN;
	/** 请求激活预见 */
	public static final short CG_ACTIVATE_PREDICT = ++PREDICT_BEGIN;
	/** 响应激活预见 */
	public static final short GC_ACTIVATE_PREDICT = ++PREDICT_BEGIN;
}
