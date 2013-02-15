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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

abstract class HzAbstractCollectionEndpoint implements HzCollectionEndpoint {

    protected final Logger logger;

    protected HzAbstractCollectionEndpoint(String collectionName) {
        this.logger = LoggerFactory.getLogger( this.getClass().getName() + "/" + collectionName );
        this.logger.debug( "Endpoint created for collection '" + collectionName + "'" );
    }

}
