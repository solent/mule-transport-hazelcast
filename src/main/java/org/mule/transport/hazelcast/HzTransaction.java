/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
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
