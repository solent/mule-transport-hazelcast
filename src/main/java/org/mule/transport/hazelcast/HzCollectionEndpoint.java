package org.mule.transport.hazelcast;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.transport.AbstractTransportMessageHandler;

public interface HzCollectionEndpoint {

    public interface MessageFactory {
        MuleMessage createMuleMessage(Object payload) throws MuleException;
    }

    MuleMessage poll(MessageFactory messageFactory) throws Exception;

    void dispatch(MuleMessage message) throws Exception;
}
