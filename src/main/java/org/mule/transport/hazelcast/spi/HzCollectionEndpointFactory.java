/**
 * Copyright (C) 2013 SOLENT SAS <oss@solent.fr>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.mule.transport.hazelcast.spi;

import org.mule.api.endpoint.EndpointURI;
import org.mule.api.endpoint.ImmutableEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.transport.hazelcast.i18n.HzMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * Knows what concrete HzCollectionEndpoint to instantiate according to endpoint URI. <br/>
 * Expected endpoint URI format is : "hz://<collection type>/<collection name>". <br/>
 */
public class HzCollectionEndpointFactory {

    private static HzCollectionEndpointFactory INSTANCE = new HzCollectionEndpointFactory();

    /**
     * Creates an HzResourceEndpoint matching the given Mule endpoint URI. <br/>
     * Expected URI format is "hz://<collection type>/<collection name>"
     * @param component
     * @param endpoint
     * @return
     * @throws CreateException
     */
    public static HzCollectionEndpoint newEndpoint(Object component, ImmutableEndpoint endpoint) throws CreateException {
        return INSTANCE.createEndPoint( component, endpoint );
    }

    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    public static final String MAP_COLLECTION_TYPE = "map";

    public static final String QUEUE_COLLECTION_TYPE = "queue";

    private static interface NamedCollectionEndpointFactory {
        HzCollectionEndpoint createEndpoint(String collectionName);
    }

    private final Map<String, NamedCollectionEndpointFactory> factories = new TreeMap<String, NamedCollectionEndpointFactory>() {
        {
            this.put( MAP_COLLECTION_TYPE, new NamedCollectionEndpointFactory() {
                public HzCollectionEndpoint createEndpoint(String collectionName) {
                    return new HzMapEndpoint( collectionName );
                }
            } );

            this.put( QUEUE_COLLECTION_TYPE, new NamedCollectionEndpointFactory() {
                public HzCollectionEndpoint createEndpoint(String collectionName) {
                    return new HzQueueEndpoint( collectionName );
                }
            } );
        }

    };

    private final Pattern trailingSlash = Pattern.compile( "^/" );

    private HzCollectionEndpoint createEndPoint(Object component, ImmutableEndpoint endpoint) throws CreateException {

        HzCollectionEndpoint hzResourceEndpoint;

        EndpointURI uri = endpoint.getEndpointURI();

        // The "authority" of the URI determines the type of collection
        String resourceType = uri.getAuthority();

        // Retrieve factory for that type
        NamedCollectionEndpointFactory factory = this.factories.get( resourceType );

        // No factory => this is an unsupported collection type
        if ( factory == null ) {
            throw new CreateException( HzMessages.invalidAuthority( resourceType ), component );
        }

        // The "path" of the URI determines the type of the collection
        String path = uri.getPath();
        String resourceName = "";
        if ( path != null ) {
            // Strip trailing slash from path
            resourceName = trailingSlash.matcher( path ).replaceFirst( "" );
        }

        // A collection name must be provided
        if ( resourceName.isEmpty() ) {
            throw new CreateException( HzMessages.invalidResourcePath( resourceName ), component );
        }

        if ( logger.isDebugEnabled() ) {
            logger.debug( "Creating endpoint for type '{}' and name '{}'", resourceType, resourceName );
        }

        return factory.createEndpoint( resourceName );
    }
}
