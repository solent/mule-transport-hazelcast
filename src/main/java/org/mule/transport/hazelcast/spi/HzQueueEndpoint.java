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
package org.mule.transport.hazelcast.spi;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IQueue;
import org.mule.api.MuleMessage;
import org.mule.transport.hazelcast.spi.HzCollectionEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Object;

public class HzQueueEndpoint implements HzCollectionEndpoint {

    private final Logger logger;

    private final IQueue queue;

    public HzQueueEndpoint(String queueName) {
        this.queue = Hazelcast.getQueue( queueName );
        this.logger = LoggerFactory.getLogger( this.getClass().getName() + "[" + queueName + "]" );
        this.logger.debug( "Created endpoint for Hazelcast queue '{}'", queueName );
    }

    public MuleMessage poll(MessageFactory factory) throws Exception {
        logger.debug( "Polling" );
        MuleMessage result = null;
        Object entry = this.queue.poll();
        if ( entry != null ) {
            logger.debug( "Entry found" );
            result = factory.createMuleMessage( entry );
        }
        else {
            logger.debug( "No entry found" );
        }

        return result;
    }

    public void dispatch(MuleMessage message) throws Exception {
        logger.debug( "Dispatching" );
        Object entry = message.getPayload();
        this.queue.put( entry );
    }
}
