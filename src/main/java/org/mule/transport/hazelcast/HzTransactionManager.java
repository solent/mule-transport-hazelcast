package org.mule.transport.hazelcast;

import javax.transaction.*;

public class HzTransactionManager implements TransactionManager {

    public void begin() throws NotSupportedException, SystemException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void commit() throws HeuristicMixedException, HeuristicRollbackException, IllegalStateException, RollbackException, SecurityException, SystemException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public int getStatus() throws SystemException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Transaction getTransaction() throws SystemException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void resume(Transaction tobj) throws IllegalStateException, InvalidTransactionException, SystemException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void rollback() throws IllegalStateException, SecurityException, SystemException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setRollbackOnly() throws IllegalStateException, SystemException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setTransactionTimeout(int seconds) throws SystemException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Transaction suspend() throws SystemException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
