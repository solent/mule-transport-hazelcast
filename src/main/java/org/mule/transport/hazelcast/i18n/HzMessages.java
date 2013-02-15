/*
 * $Id$
 * --------------------------------------------------------------------------------------
 * Copyright (c) MuleSoft, Inc.  All rights reserved.  http://www.mulesoft.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */

package org.mule.transport.hazelcast.i18n;

import org.mule.config.i18n.Message;
import org.mule.config.i18n.MessageFactory;

public class HzMessages extends MessageFactory
{

    private static final String BUNDLE_PATH = getBundlePath( "hazelcast" );

    private static HzMessages INSTANCE = new HzMessages();

    public static Message invalidAuthority(String authority) {
        return INSTANCE.createMessage( BUNDLE_PATH, 1, authority );
    }

    public static Message invalidResourcePath(String path) {
        return INSTANCE.createMessage( BUNDLE_PATH, 2, path );
    }
}
