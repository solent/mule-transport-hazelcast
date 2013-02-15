package org.mule.transport.hazelcast;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.IMap;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractTransportMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class HzMapEndpoint implements HzCollectionEndpoint {

    private final IMap<Object, Object> map;

    private final Logger logger;

    public HzMapEndpoint(String mapName) {
        this.map = Hazelcast.getMap( mapName );
        this.logger = LoggerFactory.getLogger( this.getClass().getName() + "[" + mapName + "]" );
        this.logger.debug( "Created" );
    }

    public MuleMessage poll(MessageFactory factory) throws Exception {
        MuleMessage result = null;
        Iterator keys = this.map.keySet().iterator();
        if ( keys.hasNext() ) {
            // Remove the entry corresponding to the first key from the map
            final Object key = keys.next();
            final Object value = this.map.remove( key );

            if ( this.logger.isDebugEnabled() ) {
                this.logger.debug( String.format( "Removed [%s] (was '%s')", key, value ) );
            }

            if ( value != null ) {
                // Use the value as message payload, the key as message identifier property
                result = factory.createMuleMessage( value );
                result.setOutboundProperty( HzConstants.MESSAGE_IDENTIFIER_HEADER, key );
                if ( logger.isDebugEnabled() ) {
                    logger.debug( String.format( "Returning message w/ identifier '%s' and value '%s'", key, value ) );
                }
            }
            else {
                logger.warn( String.format( "Got <null> value for key '%s'", key ) );
            }
        }
        else {
            logger.debug( "Map is empty" );
        }

        return result;
    }

    public void dispatch(MuleMessage muleMessage) throws Exception {
        Object message = muleMessage.getPayload();
        Object messageId = muleMessage.getInboundProperty( HzConstants.MESSAGE_IDENTIFIER_HEADER );
        if ( messageId == null ) {
            messageId = muleMessage.getOutboundProperty( HzConstants.MESSAGE_IDENTIFIER_HEADER );
        }

        if ( messageId == null ) {
            throw new IllegalArgumentException( "Message identifier not found as '" + HzConstants.MESSAGE_IDENTIFIER_HEADER + "' inbound property on the given message." );
        }

        if ( logger.isDebugEnabled() ) {
            logger.debug( String.format( "Putting '%s'='%s'", messageId, message ) );
        }

        this.map.put( messageId, message );
    }
}
