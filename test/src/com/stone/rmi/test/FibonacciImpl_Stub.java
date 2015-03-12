package com.stone.rmi.test;

import java.lang.reflect.Method;
import java.math.BigInteger;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.UnexpectedException;
import java.rmi.server.RemoteRef;
import java.rmi.server.RemoteStub;

import server.IFibonacci;

public final class FibonacciImpl_Stub
  extends RemoteStub
  implements IFibonacci, Remote
{
  private static final long serialVersionUID = 2L;
  private static Method $method_getFibonacci_0;
  private static Method $method_getFibonacci_1;
  
  static
  {
    try
    {
      $method_getFibonacci_0 = IFibonacci.class.getMethod("getFibonacci", new Class[] { Integer.TYPE });
      $method_getFibonacci_1 = IFibonacci.class.getMethod("getFibonacci", new Class[] { BigInteger.class });
    }
    catch (NoSuchMethodException localNoSuchMethodException)
    {
      throw new NoSuchMethodError("stub class initialization failed");
    }
  }
  
  public FibonacciImpl_Stub(RemoteRef paramRemoteRef)
  {
    super(paramRemoteRef);
  }
  
  public BigInteger getFibonacci(int paramInt)
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_getFibonacci_0, new Object[] { new Integer(paramInt) }, 8557226178185172057L);
      return (BigInteger)localObject;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
  
  public BigInteger getFibonacci(BigInteger paramBigInteger)
    throws RemoteException
  {
    try
    {
      Object localObject = this.ref.invoke(this, $method_getFibonacci_1, new Object[] { paramBigInteger }, -1488178825651406284L);
      return (BigInteger)localObject;
    }
    catch (RuntimeException localRuntimeException)
    {
      throw localRuntimeException;
    }
    catch (RemoteException localRemoteException)
    {
      throw localRemoteException;
    }
    catch (Exception localException)
    {
      throw new UnexpectedException("undeclared checked exception", localException);
    }
  }
}


/* Location:           C:\Program Files\Java\jdk1.7.0_60\bin\
 * Qualified Name:     server.FibonacciImpl_Stub
 * JD-Core Version:    0.7.0.1
 */