package daniels.reactive.blog.time;

public interface TimeSource {

	/**
	 * Returns the current time in milliseconds from epoch.
	 */
	long millis();
}
