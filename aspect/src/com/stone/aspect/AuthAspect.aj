package com.imop.sgt.local.auth;
import com.imop.platform.local.callback.ICallback;
import com.imop.platform.local.response.IResponse;
import com.imop.platform.local.response.LoginResponse;
import com.imop.platform.local.type.LoginType;
import com.imop.sgt.common.ILoginCallback;
import com.imop.sgt.common.protocol.AuthMessages.LoginResult.ResultCode;
 
/**
 * 平台认证切面(包含登陆和登出两个切入点)
 * 
 * @author crazyjohn
 * 
 */ 
public aspect AuthAspect extends AbstractLocalAspect {
	
	/**
	 * 登陆认证切入点
	 * 
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 * @param ip
	 *            客户度IP地址
	 * @param callback
	 *            登陆认证回调接口
	 */
	public pointcut doAuth(String userName, String password, String ip,
			ILoginCallback callback):
				execution(void com.imop.sgt.worldserver.auth.handler.AuthMessageHandler.doAuth(..))
				&& args(userName, password, ip, callback);

	/**
	 * 登出切入点
	 *  
	 * @param accountGuid
	 *            账号guid
	 * @param clientIp
	 *            客户端IP地址
	 * @param callback
	 *            登出回调对象
	 */
	public pointcut doLogOut(long accountGuid, String clientIp):
		execution(void com.imop.sgt.worldserver.session.SessionHandler.removeOnlineAccount(..))
		&& args(accountGuid, clientIp);

	/**
	 * 对应于验证切入点的环绕通知方法
	 * 
	 * @param userName
	 * @param password
	 * @param ip
	 * @param loginType
	 */
	void around(final String userName, String password, String clientIp,
			final ILoginCallback loginCallback): doAuth(userName, password, clientIp, loginCallback) {
		if (config.isUseLocalAuthorize()) {
			localService.login(userName, password, clientIp,
					LoginType.UserPasswordLogin, new ICallback() {

						@Override
						public void onSuccess(IResponse response) {
							LoginResponse loginResponse = (LoginResponse) response;
							loginCallback.onLoginSucceeded(0, loginResponse.getUserId(), 
									loginResponse.isAntiIndulge());
						}

						@Override
						public void onFail(IResponse response) {
							// 进行错误处理, 先记录日志
							if (logger.isWarnEnabled()) {
								logger.warn("Login failed, worldPlayer name is: "
										+ userName
										+ ", errorCode is: "
										+ response.getErrorCode());
							}
							// 账号锁定
							if (response.getErrorCode() == AuthorizeError.ACCOUNT_LOCKED
									.getType()) {
								loginCallback.onReceiveResultCode(ResultCode.ACCOUNT_LOCKED);
								return;
							} else if (response.getErrorCode() == AuthorizeError.USERNAME_PASSWORD_WRONG
									.getType()) {
								loginCallback.onReceiveResultCode(ResultCode.USERNAME_PASSWORD_WRONG);
							} else if (response.getErrorCode() == AuthorizeError.ACCOUNT_INACTIVE
									.getType()) {
								loginCallback.onReceiveResultCode(ResultCode.ACCOUNT_INACTIVE);
							}
						}

					});
			return;
		}
		// 如果没有启用平台认证策略,则执行本地认证
		proceed(userName, password, clientIp, loginCallback);
	}

	before(final long accountGuid, String clientIp): 
		doLogOut(accountGuid, clientIp){
		// 如果启用平台认证, 则调用平台登出接口
		if (config.isUseLocalAuthorize()) {
			localService.logout(accountGuid, clientIp, new ICallback() {

				@Override
				public void onFail(IResponse response) {
					if (logger.isWarnEnabled()) {
						logger.warn("Local exit error, worldPlayer guid is: "
								+ accountGuid + ", errorCode is: "
								+ response.getErrorCode());
					}
				}

				@Override
				public void onSuccess(IResponse response) {
					if (logger.isInfoEnabled()) {
						logger.info("Local exit ok, worldPlayer guid is: "
								+ accountGuid);
					}
				}

			});
		}
	}
	
	
	
}
