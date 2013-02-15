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

import org.mule.api.transport.Connector;
import org.mule.transport.AbstractConnectorTestCase;

public class HzConnectorTestCase extends AbstractConnectorTestCase
{
    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    @Override
    public Connector createConnector() throws Exception
    {
        /* IMPLEMENTATION NOTE: Create and initialise an instance of your
           connector here. Do not actually call the connect method. */

        HzConnector connector = new HzConnector(muleContext);
        connector.setName("Test");
        // TODO Set any additional properties on the connector here
        return connector;
    }

    @Override
    public String getTestEndpointURI()
    {
        return "hz://map/test";
    }

    @Override
    public Object getValidMessage() throws Exception
    {
        return "this is a test message";
    }


    public void testProperties() throws Exception
    {
        // TODO test setting and retrieving any custom properties on the
        // Connector as necessary
    }
}
