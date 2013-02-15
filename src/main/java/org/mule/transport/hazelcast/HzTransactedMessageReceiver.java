package org.mule.transport.hazelcast;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.service.Service;
import org.mule.api.transport.Connector;
import org.mule.transport.ConnectException;
import org.mule.transport.TransactedPollingMessageReceiver;
import org.mule.transport.hazelcast.HzCollectionEndpoint.MessageFactory;

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
