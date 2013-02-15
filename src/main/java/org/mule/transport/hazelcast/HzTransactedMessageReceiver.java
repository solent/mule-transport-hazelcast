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

import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.TransactedPollingMessageReceiver;
import org.mule.transport.hazelcast.spi.HzCollectionEndpoint;
import org.mule.transport.hazelcast.spi.HzCollectionEndpoint.MessageFactory;
import org.mule.transport.hazelcast.spi.HzCollectionEndpointFactory;

import java.util.Collections;
import java.util.List;

/**
 * Transacted version of the HazelcastTransactedMessageReceiver. <br/>
 * As a matter of fact, the transactional aspect is managed by parent class. <br/>
 */
public class HzTransactedMessageReceiver extends TransactedPollingMessageReceiver {

    private HzCollectionEndpoint hzCollectionEndpoint;

    private MessageFactory factory = new HzCollectionEndpointMessageFactoryAdapter( this );

    public HzTransactedMessageReceiver(Connector connector, FlowConstruct flowConstruct, InboundEndpoint endpoint) throws CreateException {
        super( connector, flowConstruct, endpoint );

        this.hzCollectionEndpoint = HzCollectionEndpointFactory.newEndpoint( this, endpoint );
    }

    @Override
    protected List<MuleMessage> getMessages() throws Exception {
        MuleMessage message = this.hzCollectionEndpoint.poll( this.factory );
        if ( message != null ) {
            logger.debug( "Got one message" );
            return Collections.singletonList( message );
        }
        else {
            logger.debug( "No message" );
            return Collections.EMPTY_LIST;
        }
    }

    @Override
    protected void processMessage(Object message) throws Exception {
        logger.debug( "Processing message" );
        MuleMessage muleMessage = createMuleMessage( message );
        routeMessage( muleMessage );
    }
}
