/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.hazelcast;

import com.mockobjects.dynamic.Mock;
import org.mule.api.MuleContext;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.service.Service;
import org.mule.api.transport.MessageReceiver;
import org.mule.transport.AbstractMessageReceiverTestCase;

public class HzMessageReceiverTestCase extends AbstractMessageReceiverTestCase
{
    /* For general guidelines on writing transports see
       http://www.mulesoft.org/documentation/display/MULE3USER/Creating+Transports */

    @Override
    public MessageReceiver getMessageReceiver() throws Exception
    {
        Mock mockService = new Mock(Service.class);
        mockService.expectAndReturn("getResponseTransformer", null);
        return new HzMessageReceiver(endpoint.getConnector(), (Service) mockService.proxy(), endpoint);
    }

    @Override
    public InboundEndpoint getEndpoint() throws Exception
    {
        MuleContext context = createMuleContext();
        return context.getEndpointFactory().getInboundEndpoint( "hz://map/test-map" );
    }
}
