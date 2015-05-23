package com.ralitski.art.core.event;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.ralitski.art.api.Generator;
import com.ralitski.art.api.NonNullMap;
import com.ralitski.art.api.Pair;

public class EventSystem {

    private Map<Class<?>, List<OMPair>> handlers;
    
    public EventSystem() {
    	Map<Class<?>, List<OMPair>> map = new NonNullMap<Class<?>, List<OMPair>>(new ListGenerator<OMPair>());
    	handlers = Collections.synchronizedMap(map);
    }
    
    public void clearHandlers() {
    	handlers.clear();
    }

    /**
     * @param o object to be added to the handler list
     */
    public void addHandler(Listener l) {
        for (Method m : l.getClass().getDeclaredMethods()) {
        	EventHandler handler = m.getAnnotation(EventHandler.class);
            if(handler != null) {
            	List<OMPair> list = handlers.get(handler.value());
            	list.add(new OMPair(l, m));
                m.setAccessible(true);
            }
        }
    }
    
    public void callEvent(Object event) {
        for (OMPair pair : handlers.get(event.getClass())) {
            callEvent(pair, event);
        }
    }

    private void callEvent(OMPair pair, Object event) {
    	Listener o = pair.getKey();
    	if(o.isActive()) {
            Method m = pair.getValue();
            try {
                m.invoke(o, event);
            } catch (Exception ex) {
            	try {
            		m.invoke(o);
            	} catch(Exception ex2) {
                    Logger.getLogger(EventSystem.class.getName()).log(Level.SEVERE,
                            "Could not pass event \"" + event + "\" to listener \"" + o + "\"",
                            ex2);
            	}
            }
    	}
    }

    //so I dont have to deal with nested generics
    private class OMPair extends Pair<Listener, Method> {

        public OMPair(Listener o, Method m) {
            super(o, m);
        }
    }
    
    private class ListGenerator<T> implements Generator<List<T>> {

		@Override
		public List<T> next() {
			//love me some linked lists
			return Collections.synchronizedList(new LinkedList<T>());
		}
    	
    }
}
