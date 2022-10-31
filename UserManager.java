package db.interfaces;
import java.util.List;
import pojos.user.Role;
import pojos.user.User;

public interface UserManager {
	public void connect();
	public void disconnect();
	public void createUser(User user);
	public void createRole(Role role);
	public Role getRole(int id);
	public User getUser(String username);
	public List<Role> getRoles();
	public User checkPassword(String username, String password);
	public void setUsername(User user, String newUsername);
	public void deleteUser(User user);
}
