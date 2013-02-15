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
