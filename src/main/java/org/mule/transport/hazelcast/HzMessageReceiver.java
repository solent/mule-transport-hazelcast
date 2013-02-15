package org.mule.transport.hazelcast;

import org.mule.api.MuleMessage;
import org.mule.api.construct.FlowConstruct;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.api.transport.Connector;
import org.mule.transport.AbstractPollingMessageReceiver;
import org.mule.transport.AbstractTransportMessageHandler;
import org.mule.transport.ConnectException;
import org.mule.transport.hazelcast.HzCollectionEndpoint.MessageFactory;

public class HzMessageReceiver extends AbstractPollingMessageReceiver {

    private HzCollectionEndpoint delegate;

    private MessageFactory factory = new HzCollectionEndpointMessageFactoryAdapter( this );

    public HzMessageReceiver(Connector connector, FlowConstruct flowConstruct, InboundEndpoint endpoint) throws CreateException {
        super( connector, flowConstruct, endpoint );

        this.delegate = HzCollectionEndpointFactory.newEndpoint( this, endpoint );
    }

    @Override
    public void doConnect() throws ConnectException {
        /* IMPLEMENTATION NOTE: This method should make a connection to the underlying
           transport i.e. connect to a socket or register a soap service. When
           there is no connection to be made this method should be used to
           check that resources are available. For example the
           FileMessageReceiver checks that the directories it will be using
           are available and readable. The MessageReceiver should remain in a
           'stopped' state even after the doConnect() method is called. This
           means that a connection has been made but no events will be
           received until the start() method is called.

           Calling start() on the MessageReceiver will call doConnect() if the receiver
           hasn't connected already. */

        /* IMPLEMENTATION NOTE: If you need to spawn any threads such as
           worker threads for this receiver you can schedule a worker thread
           with the work manager i.e.

             getWorkManager().scheduleWork(worker, WorkManager.INDEFINITE, null, null);
           Where 'worker' implemments javax.resource.spi.work.Work */

        /* IMPLEMENTATION NOTE: When throwing an exception from this method
           you need to throw an ConnectException that accepts a Message, a
           cause exception and a reference to this MessageReceiver i.e.

             throw new ConnectException(new Message(Messages.FAILED_TO_SCHEDULE_WORK), e, this);
        */
    }

    @Override
    public void doDisconnect() throws ConnectException {
        /* IMPLEMENTATION NOTE: Disconnects and tidies up any rources allocted
           using the doConnect() method. This method should return the
           MessageReceiver into a disconnected state so that it can be
           connected again using the doConnect() method. */
    }

    public void poll() throws Exception {
        MuleMessage muleMessage = this.delegate.poll( this.factory );
        if ( muleMessage != null ) {
            this.routeMessage( muleMessage );
        }
    }
}
