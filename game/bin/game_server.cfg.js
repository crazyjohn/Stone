// 绑定ip
config.bindIp = "0.0.0.0";
// 绑定的端口
config.port = "8081";
// 游戏业务处理器个数
config.gameProcessorCount = 4;
// 游戏db处理器的个数
config.dbProcessorCount = 4;
// db
config.dbServiceType = "hibernate";
config.dbConfigName="game_server_hibernate.cfg.xml"
config.getDataServiceProperties().setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/stone?useUnicode=true&characterEncoding=utf-8&useServerPrepStmts=true");
config.getDataServiceProperties().setProperty("hibernate.connection.username", "root");
config.getDataServiceProperties().setProperty("hibernate.connection.password", "");