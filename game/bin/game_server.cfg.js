// ===== do js import begin =====//
var ServerType = com.stone.proto.Servers.ServerType;
//=====  do js import end   =====//
// serverInfo
config.getServerInfo().setName("Game").setType(ServerType.GAME);
// bind ip
config.bindIp = "0.0.0.0";
// bind port 
config.port = "8081";
// allMasterServers
config.addMasterAddress("Gate", "0.0.0.0", 8080, ServerType.GATE);
// db
config.dbServiceType = "hibernate";
config.dbConfigName="game_server_hibernate.cfg.xml"
config.getDataServiceProperties().setProperty("hibernate.connection.url", "jdbc:mysql://localhost:3306/stone?useUnicode=true&characterEncoding=utf-8&useServerPrepStmts=true");
config.getDataServiceProperties().setProperty("hibernate.connection.username", "root");
config.getDataServiceProperties().setProperty("hibernate.connection.password", "");