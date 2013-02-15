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

import org.mule.api.MuleEvent;
import org.mule.api.MuleMessage;
import org.mule.api.endpoint.OutboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.transport.AbstractMessageDispatcher;
import org.mule.transport.hazelcast.spi.HzCollectionEndpoint;
import org.mule.transport.hazelcast.spi.HzCollectionEndpointFactory;

public class HzMessageDispatcher extends AbstractMessageDispatcher {

    private HzCollectionEndpoint hzCollectionEndpoint;

    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    public HzMessageDispatcher(OutboundEndpoint endpoint) throws CreateException {
        super( endpoint );
        this.hzCollectionEndpoint = HzCollectionEndpointFactory.newEndpoint( this, endpoint );
    }

    @Override
    public void doDispatch(MuleEvent event) throws Exception {
        this.hzCollectionEndpoint.dispatch( event.getMessage() );
    }

    @Override
    public MuleMessage doSend(MuleEvent event) throws Exception {
        this.doDispatch( event );
        // Don't expect return values from Hazelcast endpoints
        return createNullMuleMessage();
    }
}

