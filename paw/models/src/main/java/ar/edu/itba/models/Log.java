package ar.edu.itba.models;

import java.util.Date;
import java.util.Optional;

import org.immutables.value.Value;

@Value.Immutable
public abstract class Log{

	public abstract int logId();
	public abstract Date date();
	public abstract String title();
	public abstract Optional<String> description();
	public abstract User postedBy();

}
