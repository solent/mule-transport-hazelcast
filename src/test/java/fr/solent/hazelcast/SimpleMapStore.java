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
package fr.solent.hazelcast;

import com.hazelcast.core.MapStore;
import org.apache.log4j.Logger;

import java.util.*;

public class SimpleMapStore implements MapStore {

    private Logger logger = Logger.getLogger( this.toString() );

    private final Map<Object, Object> store = new HashMap<Object, Object>();

    public void store(Object key, Object value) {
        logger.debug( String.format( "Storing '%s'='%s'", key, value ) );
        this.store.put( key, value );
    }

    public void storeAll(Map map) {
        logger.debug( "storeAll" );
        this.store.putAll( map );
    }

    public void delete(Object key) {
        logger.debug( String.format( "Delete '%s'", key ) );
        this.store.remove( key );
    }

    public void deleteAll(Collection keys) {
        logger.debug( "deleteAll" );
        for ( Object key : keys ) {
            this.store.remove( key );
        }
    }

    public Object load(Object key) {
        Object result = this.store.get( key );
        logger.debug( String.format( "load('%s')=%s", key, result ) );
        return result;
    }

    public Map loadAll(Collection keys) {
        logger.debug( "loadAll" );
        Map<Object,Object> all = new HashMap<Object, Object>(  );
        for ( Object key : keys ) {
            Object value = this.store.get( key );
            if ( value != null ) {
                all.put( key, value );
            }
        }
        return all;
    }

    public Set loadAllKeys() {
        // Ensure nothing gets eagerly loaded
        //return null;
        return this.store.keySet();
    }
}
