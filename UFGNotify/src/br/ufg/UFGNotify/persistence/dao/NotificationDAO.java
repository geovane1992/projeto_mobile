package br.ufg.UFGNotify.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import br.ufg.UFGNotify.model.Notification;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;


public class NotificationDAO extends BaseDaoImpl<Notification, Long> {

	public NotificationDAO(ConnectionSource connectionSource)
			throws SQLException {
		super(Notification.class);
		// Necessário setar o connectionSource no Dao
		setConnectionSource(connectionSource);
		// É necessário dar um initialize para iniciar o Dao
		initialize();
	}

	public List<Notification> listFomSender(Long id) {
		try {
			List<Notification> notifications = this.queryForEq(
					Notification.COLUMN_SENDER, id);
			if (!notifications.isEmpty()) {
				return notifications;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public List<Notification> listAll() {
		try {
			return this.queryForAll();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
