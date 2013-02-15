package org.mule.transport.hazelcast;

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.transport.AbstractMessageDispatcher;

public class HzMessageDispatcher extends AbstractMessageDispatcher {

    private HzCollectionEndpoint hzCollectionEndpoint;

    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    public HzMessageDispatcher(OutboundEndpoint endpoint) throws CreateException {
        super( endpoint );
        this.hzCollectionEndpoint = HzCollectionEndpointFactory.newEndpoint( this, endpoint );
    }

    @Override
    public void doDispatch(MuleEvent event) throws Exception {
        this.hzCollectionEndpoint.dispatch( event.getMessage() );
    }

    @Override
    public MuleMessage doSend(MuleEvent event) throws Exception {
        this.doDispatch( event );
        // Don't expect return values from Hazelcast endpoints
        return createNullMuleMessage();
    }
}

