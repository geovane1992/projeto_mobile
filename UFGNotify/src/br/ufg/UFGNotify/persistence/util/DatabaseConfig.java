package br.ufg.UFGNotify.persistence.util;

import java.io.IOException;
import java.sql.SQLException;

import br.ufg.UFGNotify.model.Notification;
import br.ufg.UFGNotify.model.User;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Classe responsável por criar e definir o arquivo de mapeamento de
 * persistência objeto-relacional das classes pojo ({@link User} e
 * {@link Notification}) que estão anotadas para configuração e uso do framework
 * de persistência.
 */
public final class DatabaseConfig extends OrmLiteConfigUtil {

	private static final Class<?>[] CLASSES = new Class[] { User.class,
			Notification.class };

	public static void main(final String[] args) {
		try {
			writeConfigFile("ormlite_config.txt", CLASSES);
		} catch (final SQLException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}
}
