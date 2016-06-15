package ar.edu.itba.interfaces.service;

@FunctionalInterface
public interface TranslationService {
	
	public String getMessage(String id, Object... objects);
}
