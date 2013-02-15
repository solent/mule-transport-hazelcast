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

import org.mule.api.transaction.TransactionException;
import org.mule.api.transaction.Transaction;
import org.mule.api.transaction.TransactionFactory;
import org.mule.api.MuleContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <code>HazelcastTransactionFactory</code> Creates a
 * HazelcastTransaction
 *
 * @see HzTransaction
 */
public class HzTransactionFactory implements TransactionFactory
{

    private Logger logger = LoggerFactory.getLogger( this.getClass() );

    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    public HzTransactionFactory() {
        logger.debug( "Created" );
    }

    public Transaction beginTransaction(MuleContext muleContext) throws TransactionException {
        logger.debug( "beginTransaction" );
        HzTransaction tx = new HzTransaction( muleContext );
        tx.begin();
        return tx;
    }

    public boolean isTransacted() {
        return true;
    }
}
