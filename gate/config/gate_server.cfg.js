// ===== do js import begin =====//
var ServerType = com.stone.proto.Servers.ServerType;
//=====  do js import end   =====//
config.name = "Gate";
// serverInfo
config.getServerInfo().setName(config.name).setType(ServerType.GATE);
// bind ip
config.bindIp = "0.0.0.0";
// bind port 
config.port = "8080";
// external port for other server node to connect
config.addMasterPort("gateExternal", 8421);