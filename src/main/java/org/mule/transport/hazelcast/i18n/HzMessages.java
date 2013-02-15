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
