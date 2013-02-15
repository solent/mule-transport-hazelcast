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

import org.mule.api.MuleMessage;
import org.mule.api.endpoint.InboundEndpoint;
import org.mule.api.lifecycle.CreateException;
import org.mule.transport.AbstractMessageRequester;
import org.mule.transport.hazelcast.spi.HzCollectionEndpoint;
import org.mule.transport.hazelcast.spi.HzCollectionEndpoint.MessageFactory;
import org.mule.transport.hazelcast.spi.HzCollectionEndpointFactory;

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

