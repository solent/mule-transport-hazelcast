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

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.Transaction;
import org.mule.api.MuleContext;
import org.mule.api.transaction.TransactionException;
import org.mule.transaction.AbstractSingleResourceTransaction;

/**
 * <code>HazelcastTransaction</code> is a wrapper for a
 * Hazelcast local transaction. This object holds the tx resource and
 * controls the when the transaction committed or rolled back.
 *
 */
public class HzTransaction extends AbstractSingleResourceTransaction
{
    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    public HzTransaction(MuleContext muleContext)
    {
        super(muleContext);
    }

    @Override
    public void bindResource(Object key, Object resource) throws TransactionException
    {
        logger.info( String.format( "bindResource( '%s', '%s')", key, resource ) );
        super.bindResource( key, resource );
    }

    @Override
    protected void doBegin() throws TransactionException
    {
        logger.info( "doBegin" );
        Transaction transaction = Hazelcast.getTransaction();
        transaction.begin();
    }

    @Override
    protected void doCommit() throws TransactionException
    {
        logger.info( "doCommit" );
        Transaction transaction = Hazelcast.getTransaction();
        transaction.commit();
    }

    @Override
    protected void doRollback() throws TransactionException
    {
        logger.info( "doRollback" );
        Transaction transaction = Hazelcast.getTransaction();
        transaction.rollback();
    }
}
