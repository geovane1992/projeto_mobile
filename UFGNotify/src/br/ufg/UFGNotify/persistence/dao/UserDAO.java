package br.ufg.UFGNotify.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import br.ufg.UFGNotify.model.User;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;


public class UserDAO extends BaseDaoImpl<User, Integer> {

	public UserDAO(ConnectionSource connectionSource) throws SQLException {
		super(User.class);
		// Necessário setar o connectionSource no Dao
		setConnectionSource(connectionSource);
		// É necessário dar um initialize para iniciar o Dao
		initialize();
	}

	public User findFromName(String name) {
		try {
			List<User> users = this.queryForEq(User.COLUMN_NAME_USER, name);
			if (!users.isEmpty()) {
				return users.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public User findFomEmail(String email) {
		try {
			List<User> users = this.queryForEq(User.COLUMN_NAME_EMAIL, email);
			if (!users.isEmpty()) {
				return users.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Verifica se o usuario ja esta registrado
	 * 
	 * @param name
	 *            nome de usuario
	 * @return true, se usuario estiver registado, caso contrario false
	 */
	public boolean isNameRegistred(String name) {
		// Nome de usuario ja registrado
		User user = findFromName(name);
		if (user != null) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * @param email
	 * @return
	 */
	public boolean isEmailRegistred(String email) {
		// Email ja registrado
		User user = findFomEmail(email);
		if (user != null) {
			return true;
		}

		return false;
	}

	public List<User> listAll() {
		try {
			return this.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
