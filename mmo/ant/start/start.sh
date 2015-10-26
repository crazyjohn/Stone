>./console.log
nohup java -cp ".:lib/*" -Xms4096m -Xmx4096m -Xmn1024M -Xss2M -XX:MaxTenuringThreshold=20 -XX:+HeapDumpOnOutOfMemoryError com/stone/game/GameServer >>./console.log &
tail -f console.log