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
package org.mule.transport.hazelcast;

import org.mule.api.endpoint.InboundEndpoint;
import org.mule.tck.AbstractMuleTestCase;

public class HzConnectorFactoryTestCase extends AbstractMuleTestCase {
    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    public void testCreateFromFactory() throws Exception {
        InboundEndpoint endpoint = muleContext.getEndpointFactory().getInboundEndpoint( getEndpointURI() );
        assertNotNull( endpoint );
        assertNotNull( endpoint.getConnector() );
        assertTrue( endpoint.getConnector() instanceof HzConnector );
        assertEquals( getEndpointURI(), endpoint.getEndpointURI().getUri().toString() );
    }

    public String getEndpointURI() {
        return "hz://queue/this-is-the-queue-name";
    }
}
