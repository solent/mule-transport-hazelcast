/**
 * Copyright (C) 2013 SOLENT SAS <oss@solent.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
