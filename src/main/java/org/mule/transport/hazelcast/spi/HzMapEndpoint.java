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
import com.hazelcast.core.IMap;
import org.mule.api.MuleMessage;
import org.mule.transport.hazelcast.HzConstants;
import org.mule.transport.hazelcast.spi.HzAbstractCollectionEndpoint;

import java.util.Iterator;

/**
 * HzCollectionEndpoint wrapping an Hazelcast Map. <br/>
 */
public class HzMapEndpoint extends HzAbstractCollectionEndpoint {

    public static final String MESSAGE_IDENTIFIER_HEADER = "com.hazelcast.map.key";

    private final IMap<Object, Object> map;

    /**
     * Creates an endpoint to the Hazelcast map with the given name. <br/>
     * @param mapName
     */
    public HzMapEndpoint(String mapName) {
        super( mapName );
        this.map = Hazelcast.getMap( mapName );
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
                result.setOutboundProperty( MESSAGE_IDENTIFIER_HEADER, key );
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
        Object messageId = muleMessage.getInboundProperty( MESSAGE_IDENTIFIER_HEADER );
        if ( messageId == null ) {
            messageId = muleMessage.getOutboundProperty( MESSAGE_IDENTIFIER_HEADER );
        }

        if ( messageId == null ) {
            throw new IllegalArgumentException( "Message identifier not found as '" + MESSAGE_IDENTIFIER_HEADER + "' inbound or outbound property on the given message." );
        }

        if ( logger.isDebugEnabled() ) {
            logger.debug( String.format( "Putting '%s'='%s'", messageId, message ) );
        }

        this.map.put( messageId, message );
    }
}
