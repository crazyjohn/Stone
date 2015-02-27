package com.i4joy.util;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import akka.actor.ActorRef;
import akka.actor.WriteBuffer;
import akka.contrib.pattern.DistributedPubSubMediator.Publish;
import akka.pattern.Patterns;
import akka.util.Timeout;

import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_getAmuletId;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_getEquipmentId;
import com.i4joy.akka.kok.db.protobufs.KOKDBPacket.DB_getMercenaryId;
import com.i4joy.akka.kok.db.wqueue.WQ_ClientTableService;
import com.i4joy.akka.kok.protobufs.KOKPacket.ErrorReponse;
import com.i4joy.akka.kok.protobufs.KOKPacket.PacketInfo;

public class Tools {

	public static Tools intance;

	private static Tools getIntance() {
		if (intance == null) {
			intance = new Tools();
		}
		return intance;
	}

	protected final Log logger = LogFactory.getLog(getClass());

	public static String httpPost(String url, HashMap<String, String> hm) {

		HttpClient httpClient = null;
		HttpPost httppost = null;
		try {
			httpClient = new DefaultHttpClient();
			httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 40000);
			httpClient.getParams().setIntParameter(HttpConnectionParams.CONNECTION_TIMEOUT, 40000);
			httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
			List<NameValuePair> formparams = new ArrayList<NameValuePair>();
			String[] keys = new String[hm.size()];
			hm.keySet().toArray(keys);
			for (int i = 0; i < keys.length; i++) {
				formparams.add(new BasicNameValuePair(keys[i], hm.get(keys[i])));
			}
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
			httppost = new HttpPost(url);
			httppost.setHeader("Connection", "close");
			httppost.setEntity(entity);
			HttpResponse response = httpClient.execute(httppost);
			int iResponCode = response.getStatusLine().getStatusCode();
			if (iResponCode == 200) {
				String strResult = EntityUtils.toString(response.getEntity());
				System.out.println("返回值 " + strResult);
				return strResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Tools.printError(e, getIntance().logger, "");
		} finally {
			if (httppost != null) {
				// editby: crazyjohn 2015-2-27 
				//httppost.releaseConnection();
			}
			if (httpClient != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return null;
	}

	public static void printError(Exception e, Log logger, String message) {
		if (logger != null)
			logger.error(message, e);
		e.printStackTrace();
	}

	public static String getUUID() {
		return UUID.randomUUID().toString();
	}

	public static void printlnInfo(String msg, Log logger) {
		System.out.println(msg);
		if (logger != null&&logger.isInfoEnabled())
			logger.info(msg);
	}

	public static void printlnDebug(String msg, Log logger) {
		System.out.println(msg);
		if (logger != null&&logger.isDebugEnabled())
			logger.debug(msg);
	}
	
	public static void printlnWarn(String msg, Log logger) {
		System.out.println(msg);
		if (logger != null)
			logger.warn(msg);
	}

	public static Random s_random;
	public static boolean s_debug = false;

	public static int getRandomNum() {
		if (s_random == null) {
			s_random = new Random(System.currentTimeMillis());
		}
		return s_random.nextInt();
	}

	public static int getRandomNum(int min, int max) {
		try {
			if (max == min)
				return min;

			if (max < min) {
				int temp1 = min;
				min = max;
				max = temp1;
			}
			if (s_random == null) {
				s_random = new Random(System.currentTimeMillis());
			}
			Random _rand = new Random(s_random.nextLong());
			int temp = max - min;
			temp = Math.abs(_rand.nextInt() % temp);
			temp += min;
			return temp;
		} catch (Exception e) {
			e.printStackTrace();
			Tools.printError(e, getIntance().logger, "");
			return -1;
		}
	}

	/*
	 * 获取Request IP
	 */
	public static String getIpAddr(HttpServletRequest request, Log logger) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		if (logger != null) {
			// Tools.println("用户IP: " + ip, logger);
		}
		return ip;
	}

	// 将127.0.0.1形式的IP地址转换成十进制整数，这里没有进行任何错误处理
	public static long ipToLong(String strIp) {
		long[] ip = new long[4];
		// 先找到IP地址字符串中.的位置
		int position1 = strIp.indexOf(".");
		int position2 = strIp.indexOf(".", position1 + 1);
		int position3 = strIp.indexOf(".", position2 + 1);
		// 将每个.之间的字符串转换成整型
		ip[0] = Long.parseLong(strIp.substring(0, position1));
		ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
		ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
		ip[3] = Long.parseLong(strIp.substring(position3 + 1));
		return (ip[0] * 1000000000) + (ip[1] * 1000000) + (ip[2] * 1000) + ip[3];
	}

	// 将十进制整数形式转换成127.0.0.1形式的ip地址
	public static String longToIP(long longIp) {
		StringBuffer sb = new StringBuffer("");
		// 直接除 1000000000
		sb.append(String.valueOf((longIp / 1000000000)));
		sb.append(".");
		longIp -= longIp / 1000000000 * 1000000000;
		// 除 1000000
		sb.append(String.valueOf((longIp / 1000000)));
		sb.append(".");
		longIp -= longIp / 1000000 * 1000000;
		// 除 1000
		sb.append(String.valueOf((longIp / 1000)));
		sb.append(".");
		longIp -= longIp / 1000 * 1000;
		//
		sb.append(String.valueOf((longIp)));
		return sb.toString();
	}

	public static byte[] serialize(Object obj) {
		try {
			ByteArrayOutputStream buffer = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(buffer);
			oos.writeObject(obj);
			oos.close();
			return buffer.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			Tools.printError(e, getIntance().logger, "");
			throw new RuntimeException("error writing to byte-array!");
		}
	}

	public static Object deserialize(byte[] bytes) {
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(bytes);
			ObjectInputStream ois = new ObjectInputStream(input);
			return ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
			Tools.printError(e, getIntance().logger, "");
			throw new RuntimeException("error reading from byte-array!");
		}
	}

	public static String getNano(int len) {
		long time = System.nanoTime();
		String name = "" + time;
		return name.substring(name.length() - len, name.length());
	}

	// public static byte[] packageBytes(byte[] data) throws IOException {
	// ByteArrayOutputStream baos = new ByteArrayOutputStream();
	// DataOutputStream dos = new DataOutputStream(baos);
	// dos.writeInt(data.length);
	// dos.write(data);
	// baos.flush();
	// dos.flush();
	// data = baos.toByteArray();
	// baos.close();
	// dos.close();
	// return data;
	// }

	public static void main(String[] args) {
		// HashMap<String, String> hm = new HashMap<String, String>();
		// hm.put("aaa", "bbb");
		// System.out.println(Tools.httpPost("http://localhost:8877/example",
		// hm));
		System.out.println(ipToLong("112.125.94.155") + " " + longToIP(ipToLong("112.125.94.155")));
	}

	public static void changeFileName() {
		File path = new File("D:\\Projects\\design\\Design\\数值表\\宝物系统\\宝物升级数值表");
		File[] fs = path.listFiles();
		for (File f : fs) {
			try {
				if (f.isFile() && f.getName().contains("_")) {
					String name = f.getName();
					String[] ss = name.split("_");
					long id = Long.parseLong(ss[0]);
					id += 5000000;
					f.renameTo(new File(f.getParent() + File.separator + id + "_" + ss[1]));
					System.out.println(f);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @param c
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getCellValue(Cell c, Class<T> clazz) {
		if (clazz == Integer.class) {
			if (c == null) {
				return (T) (new Integer(0));
			}
			return (T) (new Integer((int) c.getNumericCellValue()));
		} else if (clazz == Long.class) {
			if (c == null) {
				return (T) (new Long(0));
			}
			return (T) new Long((long) c.getNumericCellValue());
		} else if (clazz == Short.class) {
			if (c == null) {
				return (T) (new Short((short) 0));
			}
			return (T) new Short((short) c.getNumericCellValue());
		} else if (clazz == Float.class) {
			if (c == null) {
				return (T) (new Float(0f));
			}
			return (T) new Float((float) c.getNumericCellValue());
		} else if (clazz == Byte.class) {
			if (c == null) {
				return (T) (new Byte((byte) 0));
			}
			return (T) new Byte((byte) c.getNumericCellValue());
		} else if (clazz == Double.class) {
			if (c == null) {
				return (T) (new Double(0));
			}
			return (T) new Double(c.getNumericCellValue());
		} else if (clazz == Boolean.class) {
			if (c == null) {
				return (T) (new Boolean(false));
			}
			if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return (T) new Boolean(c.getBooleanCellValue());
			} else {
				if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					int n = (int) c.getNumericCellValue();
					boolean b = false;
					if (n > 0) {
						b = true;
					}
					return (T) new Boolean(b);
				} else {
					return (T) new Boolean(false);
				}
			}

		} else if (clazz == String.class) {
			if (c == null) {
				return (T) "";
			}
			if (c.getCellType() == Cell.CELL_TYPE_STRING) {
				return (T) c.getStringCellValue();
			} else {
				return (T) ("" + (int) c.getNumericCellValue());
			}
		} else if (clazz == Long[][].class) {
			if (c == null) {
				return (T) new Long[0][0];
			}
			String str = c.getStringCellValue();
			String[] strs = str.split("&");
			Long[][] temp = new Long[strs.length][];
			for (int i = 0; i < strs.length; i++) {
				String[] tempStr = strs[i].split("_");
				temp[i] = new Long[tempStr.length];
				for (int j = 0; j < temp[i].length; j++) {
					temp[i][j] = Long.parseLong(tempStr[j]);
				}
			}
			return (T) temp;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getCellValueByBaseType(Cell c, Class<T> clazz) {
		if (clazz == Integer.TYPE) {
			if (c == null) {
				return (T) (new Integer(0));
			}
			return (T) (new Integer((int) c.getNumericCellValue()));
		} else if (clazz == Long.TYPE) {
			if (c == null) {
				return (T) (new Long(0));
			}
			return (T) new Long((long) c.getNumericCellValue());
		} else if (clazz == Short.TYPE) {
			if (c == null) {
				return (T) (new Short((short) 0));
			}
			return (T) new Short((short) c.getNumericCellValue());
		} else if (clazz == Float.TYPE) {
			if (c == null) {
				return (T) (new Float(0f));
			}
			return (T) new Float((float) c.getNumericCellValue());
		} else if (clazz == Double.TYPE) {
			if (c == null) {
				return (T) (new Double(0));
			}
			return (T) new Double(c.getNumericCellValue());
		} else if (clazz == Boolean.TYPE) {
			if (c == null) {
				return (T) (new Boolean(false));
			}
			if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
				return (T) new Boolean(c.getBooleanCellValue());
			} else {
				if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					int n = (int) c.getNumericCellValue();
					boolean b = false;
					if (n > 0) {
						b = true;
					}
					return (T) new Boolean(b);
				} else {
					return (T) new Boolean(false);
				}
			}

		} else if (clazz == Byte.TYPE) {
			if (c == null) {
				return (T) (new Byte((byte) 0));
			}
			return (T) new Byte((byte) c.getNumericCellValue());
		} else if (clazz == String.class) {
			if (c == null) {
				return (T) "";
			}
			if (c.getCellType() == Cell.CELL_TYPE_STRING || c.getCellType() == Cell.CELL_TYPE_FORMULA) {
				return (T) c.getStringCellValue();
			} else {
				return (T) ("" + (int) c.getNumericCellValue());
			}
		} else if (clazz == long[][].class) {
			if (c == null) {
				return (T) new long[0][0];
			}
			String str = "";
			if (c.getCellType() == Cell.CELL_TYPE_STRING || c.getCellType() == Cell.CELL_TYPE_FORMULA) {
				try{
					str=c.getStringCellValue();
				}catch (Exception e) {
					str=""+(int)c.getNumericCellValue();
				}
				
			}else{
				str=""+(int)c.getNumericCellValue();
			}
			
			String[] strs = str.split("&");
			long[][] temp = new long[strs.length][];
			for (int i = 0; i < strs.length; i++) {
				String[] tempStr = strs[i].split("_");
				temp[i] = new long[tempStr.length];
				for (int j = 0; j < temp[i].length; j++) {
					temp[i][j] = Long.parseLong(tempStr[j]);
				}
			}
			return (T) temp;
		} else if (clazz == float[][].class) {
			if (c == null) {
				return (T) new float[0][0];
			}
			String str = "";
			if (c.getCellType() == Cell.CELL_TYPE_STRING || c.getCellType() == Cell.CELL_TYPE_FORMULA) {
				try{
					str=c.getStringCellValue();
				}catch (Exception e) {
					str=""+(int)c.getNumericCellValue();
				}
				
			}else{
				str=""+(int)c.getNumericCellValue();
			}
			
			String[] strs = str.split("&");
			float[][] temp = new float[strs.length][];
			for (int i = 0; i < strs.length; i++) {
				String[] tempStr = strs[i].split("_");
				temp[i] = new float[tempStr.length];
				for (int j = 0; j < temp[i].length; j++) {
					temp[i][j] = Float.parseFloat(tempStr[j]);
				}
			}
			return (T) temp;
		}
		return null;
	}

	/**
	 * 从数据库中获取数据实体
	 * 
	 * @param player
	 * @param mediator
	 * @param DBName
	 * @param tableName
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T doDbQuery(Object msg, ActorRef mediator, String DBName, String tableName, Class<T> clazz) {

		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish(DBName + tableName, msg), timeout);
		try {
			Object o = Await.result(future, timeout.duration());
			return (T) o;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void doDbQuery(Object msg, ActorRef mediator, String DBName, String tableName) {
		mediator.tell(new Publish(DBName + tableName, msg), ActorRef.noSender());
	}

	public static int getMercenaryId(ActorRef mediator) {
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish("DBMaster", DB_getMercenaryId.newBuilder().build()), timeout);
		try {
			DB_getMercenaryId mercenaryId = (DB_getMercenaryId) Await.result(future, timeout.duration());
			return (int) mercenaryId.getMercenaryId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getEquipmentId(ActorRef mediator) {
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish("DBMaster", DB_getEquipmentId.newBuilder().build()), timeout);
		try {
			DB_getEquipmentId mercenaryId = (DB_getEquipmentId) Await.result(future, timeout.duration());
			return (int) mercenaryId.getEquipmentId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getAmuletId(ActorRef mediator) {
		Timeout timeout = new Timeout(Duration.create(5, "seconds"));
		Future<Object> future = Patterns.ask(mediator, new Publish("DBMaster", DB_getAmuletId.newBuilder().build()), timeout);
		try {
			DB_getAmuletId mercenaryId = (DB_getAmuletId) Await.result(future, timeout.duration());
			return (int) mercenaryId.getAmuletId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 给客户端发协议
	 * 
	 * @param builder
	 * @param sender
	 * @param self
	 * @param pid
	 */
	public static void sendPacket(byte[] data, ActorRef sender, ActorRef self, short pid) {
		PacketInfo.Builder packetBuilder = PacketInfo.newBuilder();
		packetBuilder.setId(pid);
		packetBuilder.setData(com.google.protobuf.ByteString.copyFrom(data));
		sender.tell(packetBuilder.build(), self);
	}

	public static byte[] getErrorResponse(int errorCode) {
		ErrorReponse.Builder builder = ErrorReponse.newBuilder();
		builder.setTime(12);
		return builder.build().toByteArray();
	}

	/**
	 * 自动将excel中一行数据赋值给给定的类，返回实体 要求变量定义顺序必须和excel中列的顺序一致，而且必须有set方法
	 * 
	 * @param row
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T fillByExcelRow(Row row, Class<T> clazz) throws Exception {
		T t = clazz.newInstance();
		Field[] fs = clazz.getDeclaredFields();
		Method[] ms = clazz.getDeclaredMethods();
		int index = 0;
		for (Field f : fs) {
			String name = f.getName();
			String methodName = "set" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
			for (Method m : ms) {
				if (m.getName().equals(methodName)) {
					m.invoke(t, getCellValueByBaseType(row.getCell(index), f.getType()));
					index++;
					break;
				}
			}
		}
		return t;
	}

	public static byte[] getShortToBytes(short value) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(2);
		byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
		byteBuffer.putShort(value);
		return byteBuffer.array();
	}

	public static void makeFile(String filePath, byte[][] datas) throws Exception {
		DataOutputStream dos = null;
		try {
			dos = new DataOutputStream(new FileOutputStream(new File(filePath)));
			byte[] num = Tools.getShortToBytes((short) datas.length);
			dos.write(num);
			for (byte[] data : datas) {
				byte[] len = Tools.getShortToBytes((short) data.length);
				dos.write(len);
				dos.write(data);
			}

			dos.flush();
			System.out.println("[创建文件] [文件：" + filePath + "]");
		} finally {
			if (dos != null) {
				dos.close();
			}
		}
	}

	public static void makeFile(String filePath, byte[] data) throws Exception {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(filePath));
			fos.write(data);
			fos.flush();
			System.out.println("[创建文件] [文件：" + filePath + "]");
		} finally {
			if (fos != null) {
				fos.close();
			}
		}
	}

}
