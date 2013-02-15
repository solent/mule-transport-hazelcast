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
package org.mule.transport.hazelcast.config;

import org.mule.config.spring.handlers.AbstractMuleNamespaceHandler;
import org.mule.transport.hazelcast.HzConnector;
import org.mule.endpoint.URIBuilder;
import org.mule.transport.hazelcast.HzConstants;

/**
 * Registers a Bean Definition Parser for handling <code><hazelcast:connector></code> elements
 * and supporting endpoint elements.
 */
public class HzNamespaceHandler extends AbstractMuleNamespaceHandler
{
    public void init()
    {
        /* This creates handlers for 'endpoint', 'outbound-endpoint' and 'inbound-endpoint' elements.
           The defaults are sufficient unless you have endpoint styles different from the Mule standard ones
           The URIBuilder as constants for common required attributes, but you can also pass in a user-defined String[].
         */
        registerStandardTransportEndpoints( HzConstants.PROTOCOL_IDENTIFIER, URIBuilder.PATH_ATTRIBUTES);

        /* This will create the handler for your custom 'connector' element.  You will need to add handlers for any other
           xml elements you define.  For more information see:
           http://www.mulesoft.org/documentation/display/MULE3USER/Creating+a+Custom+XML+Namespace
        */
        registerConnectorDefinitionParser(HzConnector.class);
    }
}
