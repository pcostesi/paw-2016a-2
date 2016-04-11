package ar.edu.itba;

public class UserServicesImpl implements UserServices{

	@Override
	public User create(String name, String password) {
		return new User(name, password);
	}

}
