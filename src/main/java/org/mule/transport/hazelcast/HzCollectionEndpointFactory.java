package org.mule.transport.hazelcast;

import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.transport.hazelcast.i18n.HzMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Knows what concrete HzCollectionEndpoint to instantiate according to endpoint URI. <br/>
 * Expected endpoint URI format is : "hz://<collection type>/<collection name>". <br/>
 */
public class HzCollectionEndpointFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger( HzCollectionEndpointFactory.class );

    public static final String MAP_COLLECTION_TYPE = "map";

    public static final String QUEUE_COLLECTION_TYPE = "queue";

    /**
     * Creates an HzResourceEndpoint matching the given Mule endpoint URI. <br/>
     * @param component
     * @param endpoint
     * @return
     * @throws CreateException
     */
    public static HzCollectionEndpoint newEndpoint(Object component, ImmutableEndpoint endpoint) throws CreateException {

        HzCollectionEndpoint hzResourceEndpoint;

        EndpointURI uri = endpoint.getEndpointURI();

        String resourceType = uri.getAuthority();
        String resourceName = uri.getPath().replaceFirst( "^/", "" );
        if ( resourceName.isEmpty() ) {
            throw new CreateException( HzMessages.invalidResourcePath( resourceName ), component );
        }

        if ( LOGGER.isDebugEnabled() ) {
            LOGGER.debug( "Creating Hazelcast collection endpoint for type '{}' and name '{}'", resourceType, resourceName );
        }

        if ( MAP_COLLECTION_TYPE.equals( resourceType ) ) {
            hzResourceEndpoint = new HzMapEndpoint( resourceName );
        }
        else if ( QUEUE_COLLECTION_TYPE.equals( resourceType ) ) {
            hzResourceEndpoint = new HzQueueEndpoint( resourceName );
        }
        else {
            throw new CreateException( HzMessages.invalidAuthority( resourceType ), component );
        }

        return hzResourceEndpoint;
    }
}
