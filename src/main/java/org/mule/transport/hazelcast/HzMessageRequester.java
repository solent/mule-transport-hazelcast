package org.mule.transport.hazelcast;

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.transport.AbstractMessageRequester;
import org.mule.transport.hazelcast.HzCollectionEndpoint.MessageFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class HzMessageRequester extends AbstractMessageRequester
{

    private HzCollectionEndpoint hzCollectionEndpoint;

    private MessageFactory factory = new HzCollectionEndpointMessageFactoryAdapter( this );

    public HzMessageRequester(InboundEndpoint endpoint) throws CreateException {
        super( endpoint );
        this.hzCollectionEndpoint = HzCollectionEndpointFactory.newEndpoint( this, endpoint );
    }

    private MuleMessage doBlockingPoll() throws Exception {
        MuleMessage result = null;
        hzCollectionEndpoint.poll( factory );
        while ( result == null ) {
            Thread.yield();
        }

        return result;
    }

    protected MuleMessage doRequest(long timeout) throws Exception {
        FutureTask<MuleMessage> task = new FutureTask<MuleMessage>( new Callable<MuleMessage>() {
            public MuleMessage call() throws Exception {
                return doBlockingPoll();
            }
        } );
        task.run();

        try {
            return task.get( timeout, TimeUnit.MILLISECONDS );
        }
        catch (TimeoutException e ) {
                return null;
        }
    }
}

