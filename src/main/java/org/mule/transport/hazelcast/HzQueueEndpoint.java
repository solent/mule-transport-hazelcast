package org.mule.transport.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IQueue;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractTransportMessageHandler;
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
