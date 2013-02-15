/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package org.mule.transport.hazelcast.config;

import org.mule.tck.FunctionalTestCase;
import org.mule.transport.hazelcast.HzConnector;

/**
 * TODO
 */
public class HzNamespaceHandlerTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        //TODO You'll need to edit this file to configure the properties specific to your transport
        return "hazelcast-namespace-config.xml";
    }

    public void testHazelcastConfig() throws Exception
    {
        HzConnector c = (HzConnector) muleContext.getRegistry().lookupConnector("hazelcastConnector");
        assertNotNull(c);
        assertTrue(c.isConnected());
        assertTrue(c.isStarted());

        //TODO Assert specific properties are configured correctly
    }
}
