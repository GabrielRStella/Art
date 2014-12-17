package com.ralitski.art.core;

import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * used to store classes to be used as artists.
 * 
 * @author ralitski
 */
public class ArtistBank {
	
	private static Map<String, ArtManager> art = new HashMap<>();
	
	public static void refresh() {
		java.util.List<Class<?>> classes = CodeBank.getClasses();
		//EXTREME DOUBLE SYNC
		synchronized(classes) {
			synchronized(art) {
				for(Class<?> c : classes) {
					try {
						ArtManager a = new ArtManager(c);
						if(a.getArtist() != null) {
							art.put(a.getName(), a);
						}
					} catch (Exception e) {
						//silence, you're in a library
					}
				}
			}
		}
	}
	
	public static void addArt(ArtManager a) {
		synchronized(art) {
			art.put(a.getName(), a);
		}
	}
	
	public static ArtManager getArt(String name) {
		synchronized(art) {
			return art.get(name);
		}
	}
	
	//should synchronize on this list when retrieved
	public static Map<String, ArtManager> getArtists() {
		return art;
	}
}
