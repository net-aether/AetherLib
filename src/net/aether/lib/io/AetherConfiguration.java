package net.aether.lib.io;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.xml.ws.spi.http.HttpExchange;
import javax.xml.ws.spi.http.HttpHandler;

import net.aether.lib.exceptions.InvalidTypeException;

//TODO
public class AetherConfiguration implements FileConfiguration {

	/**
	 * Convenience Constructor.<br>
	 * @see #AetherConfiguration(AetherConfigurationLoader, AetherConfigurationPair...)
	 */
	AetherConfiguration() {
		this(null, new AetherConfigurationPair[0]);
	}
	/**
	 * Convenience Constructor.<br>
	 * @see #AetherConfiguration(AetherConfigurationLoader, AetherConfigurationPair...)
	 */
	AetherConfiguration(AetherConfigurationLoader loader) {
		this(loader, new AetherConfigurationPair[0]);
	}
	/**
	 * Convenience Constructor.<br>
	 * @see #AetherConfiguration(AetherConfigurationLoader, AetherConfigurationPair...)
	 */
	AetherConfiguration(AetherConfigurationPair... pairs) {
		this(null, pairs);
	}
	/**
	 * Creates a Configuration from loader, filled by the supplied pairs
	 * @param loader
	 * @param pairs
	 */
	AetherConfiguration(AetherConfigurationLoader loader, AetherConfigurationPair... pairs) {
		this.loader = loader;
	}
	
	private final AetherConfigurationLoader loader;
	private HashMap<String, AetherConfigurationPair> valueMap = new HashMap<>();
	
	public void set(String path, Object value) {}
	public void setFallback(String path, String fallback) {
		// TODO Auto-generated method stub
		
	}
	public String getFallback(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setType(String path, Class<?> type) {
		// TODO Auto-generated method stub
		
	}
	public void setType(String path, String type) {
		// TODO Auto-generated method stub
		
	}
	public String getTypeName(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	public Class<?> getType(String path) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	public void trySet(String path, Object value) {
		// TODO Auto-generated method stub
		
	}
	public void forceSet(String path, Object value) {
		// TODO Auto-generated method stub
		
	}
	public String[] getComment(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	public void setComment(String path, String... comment) {
		// TODO Auto-generated method stub
		
	}
	public boolean isSet(String path) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean hasFallback(String path) {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean isList(String path) {
		// TODO Auto-generated method stub
		return false;
	}
	public Object get(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	public <T> T get(String path, Class<? extends T> parseToClass) throws InvalidTypeException {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean getBoolean(String path) throws InvalidTypeException {
		// TODO Auto-generated method stub
		return false;
	}
	public int getInt(String path) throws InvalidTypeException {
		// TODO Auto-generated method stub
		return 0;
	}
	public String getString(String path) {
		// TODO Auto-generated method stub
		return null;
	}
	public float getFloat(String path) throws InvalidTypeException {
		// TODO Auto-generated method stub
		return 0;
	}
	public double getDouble(String path) throws InvalidTypeException {
		// TODO Auto-generated method stub
		return 0;
	}
	public long getLong(String path) throws InvalidTypeException {
		// TODO Auto-generated method stub
		return 0;
	}
	
	static final class AetherConfigurationPair {
		
		private String[] comment;
		private String type;
		private Object value, fallback;
		
	}

	@Override
	public void load(File file) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void load(String string) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void save(File file, boolean prettyPrint) throws IOException {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String asString() {
		// TODO Auto-generated method stub
		return null;
	}
	
}