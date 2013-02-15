package org.mule.transport.hazelcast;

import org.mule.transport.AbstractMessageDispatcherFactory;
import org.mule.api.MuleException;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.transport.MessageDispatcher;

public class HzMessageDispatcherFactory extends AbstractMessageDispatcherFactory
{
    @Override
    public MessageDispatcher create(OutboundEndpoint endpoint) throws MuleException
    {
        return new HzMessageDispatcher(endpoint);
    }
}
