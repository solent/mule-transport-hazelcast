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
