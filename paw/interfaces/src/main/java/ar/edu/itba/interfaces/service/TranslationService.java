package ar.edu.itba.interfaces.service;

@FunctionalInterface
public interface TranslationService {
	
	/**
	 * Get translation by its translation id.
	 * @param id Id to search translation for.
	 * @param objects List of parameters of the id.
	 * @return The translation
	 */
	public String getMessage(String id, Object... objects);
}
