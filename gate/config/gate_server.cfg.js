// ===== do js import begin =====//
var ServerType = com.stone.proto.Servers.ServerType;
//=====  do js import end   =====//
// serverInfo
config.getServerInfo().setName("Game").setType(ServerType.GATE);
// bind ip
config.bindIp = "0.0.0.0";
// bind port 
config.port = "8080";