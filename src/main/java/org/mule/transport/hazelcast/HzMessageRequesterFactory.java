package org.mule.transport.hazelcast;

import org.mule.api.MuleException;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.transport.MessageRequester;
import org.mule.transport.AbstractMessageRequesterFactory;

public class HzMessageRequesterFactory extends AbstractMessageRequesterFactory {

    public MessageRequester create(InboundEndpoint endpoint) throws MuleException {
        return new HzMessageRequester( endpoint );
    }
}
