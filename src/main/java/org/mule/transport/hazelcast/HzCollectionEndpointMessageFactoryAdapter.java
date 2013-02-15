package org.mule.transport.hazelcast;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractTransportMessageHandler;
import org.mule.transport.hazelcast.HzCollectionEndpoint.MessageFactory;

import java.lang.Object;
import java.lang.Override;

public class HzCollectionEndpointMessageFactoryAdapter implements MessageFactory {

    private AbstractTransportMessageHandler adaptee;

    public HzCollectionEndpointMessageFactoryAdapter(AbstractTransportMessageHandler adaptee) {
        this.adaptee = adaptee;
    }

    @Override
    public MuleMessage createMuleMessage(Object payload) throws MuleException {
        return adaptee.createMuleMessage( payload );
    }
}
