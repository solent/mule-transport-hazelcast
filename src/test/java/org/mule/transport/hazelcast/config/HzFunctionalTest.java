package org.mule.transport.hazelcast.config;

import com.hazelcast.core.*;
import org.mule.tck.FunctionalTestCase;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

@SuppressWarnings( "unchecked" )
public class HzFunctionalTest extends FunctionalTestCase {

    static {
        System.setProperty( "hazelcast.logging.type", "log4j" );
    }

    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected String getConfigResources() {
        return "test-config.xml";
    }

    private Object waitForMessage(String mapName, final Object key) throws InterruptedException, ExecutionException {
        final IMap map = Hazelcast.getMap( mapName );
        Future<Object> future = executorService.submit( new Callable<Object>() {
            public Object call() throws Exception {
                Object result = null;
                while ( result == null ) {
                    Thread.yield();
                    result = map.get( key );
                }

                return result;
            }
        } );

        try {
            return future.get( 2, TimeUnit.SECONDS );
        }
        catch ( TimeoutException e ) {
            String message = "Timedout while waiting for message on map" + mapName;
            logger.error( message, e );
            fail( message );
            return null;
        }
    }

    public void testSimple() throws Exception {
        Map in = Hazelcast.getMap( "map-in" );
        final String key = UUID.randomUUID().toString();
        final String inputMessage = "this is a test message";
        in.put( key, inputMessage );
        logger.info( "Input message written" );

        Object receivedMessage = waitForMessage( "map-out", key );
        assertNotNull( receivedMessage );
        assertEquals( inputMessage, receivedMessage );
        assertTrue( in.isEmpty() ); // Ensure that polling removes message from map

        muleContext.stop();
    }

    public void testChoice() throws Exception {
        final IMap failedRequests = Hazelcast.getMap( "failed-requests" );
        final IMap processedRequests = Hazelcast.getMap( "processed-requests" );

        final Map pendingRequests = Hazelcast.getMap( "pending-requests" );
        String validMessage = "This is a test message";
        String validMessageKey = UUID.randomUUID().toString();
        pendingRequests.put( validMessageKey, validMessage );

        Object failedMessage = Integer.MAX_VALUE; // Non string routes to 'failed-requests'
        String failedMessageKey = UUID.randomUUID().toString();
        pendingRequests.put( failedMessageKey, failedMessage );
        logger.info( "Failed message written to 'pending-requests'" );

        executorService.submit( new Runnable() {
            public void run() {
                // Waiting for messages to be moved from the input map ("pending-requests") to the output ones ("failed" and "processed")
                while ( ( ! pendingRequests.isEmpty() ) || failedRequests.isEmpty() || processedRequests.isEmpty() ) {
                    Thread.yield();
                }
            }
        } ).get( 5, TimeUnit.SECONDS );

        // And check our messages
        Object receivedFailedMessage = failedRequests.get( failedMessageKey );
        assertNotNull( receivedFailedMessage );
        assertEquals( failedMessage, receivedFailedMessage );

        Object receivedProcessedMessage = processedRequests.get( validMessageKey );
        assertNotNull( receivedProcessedMessage );
        assertEquals( validMessage, receivedProcessedMessage );

        assertTrue( pendingRequests.isEmpty() ); // Ensure that polling removes message from map
    }

    public void testTransaction() throws Exception {
        IQueue inputQueue = Hazelcast.getQueue( "pending-transactions" );
        IQueue outputQueue = Hazelcast.getQueue( "processed-transactions" );
        final String input = "This is a test";
        Hazelcast.getTransaction().begin();
        inputQueue.put( input );
        Hazelcast.getTransaction().commit();

        while ( ! inputQueue.isEmpty() ) {
            logger.info( "Waiting for input queue to be emptied" );
            Thread.sleep( 1000 );
        }

        while ( outputQueue.isEmpty() ) {
            logger.info( "Waiting for message to end up in output queue" );
            Thread.sleep( 1000 );
        }

        Object output = outputQueue.poll();
        assertNotNull( output );
        assertEquals( input, output );

        inputQueue.put( Integer.MAX_VALUE );

        Thread.sleep( 5000 );

        assertTrue( ! inputQueue.isEmpty() );
    }
}
