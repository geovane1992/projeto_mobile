package br.ufg.UFGNotify.persistence.helper;

import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.Notification;
import br.ufg.UFGNotify.model.User;
import br.ufg.UFGNotify.persistence.dao.NotificationDAO;
import br.ufg.UFGNotify.persistence.dao.UserDAO;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

/**
 * Classe de responsável por gerenciar o esquema do banco de dados e conter os
 * objetos de acesso a dados de todas classes pojo.
 * 
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

	private final String LOG_TAG = getClass().getSimpleName();

	private UserDAO userDao = null;
	private NotificationDAO notificationDao = null;

	private static final AtomicInteger usageCounter = new AtomicInteger(0);
	private static DatabaseHelper helperInstance = null;

	private DatabaseHelper(final Context context) {
		super(context, context.getString(R.string.database_name), null, Integer
				.valueOf(context.getString(R.string.database_version)),
				R.raw.ormlite_config);
	}

	/**
	 * Retorna o helper ({@link #helperInstance}) que é uma instância do objeto
	 * que gerencia as conexões com o banco de dados e os objetos de acesso a
	 * dados (DAO). Será criada uma instância do helper e será cacheado para
	 * futuras chamadas a este método. <br>
	 * <b>A cada chamada a este método deverá haver 1 e somente uma chamada ao
	 * método {@link #close()}.</b>
	 * 
	 * @param context
	 *            contexto da aplicação.
	 * @return o helper.
	 */
	public static synchronized DatabaseHelper getHelperInstance(
			final Context context) {
		if (helperInstance == null) {
			helperInstance = new DatabaseHelper(context);
		}
		usageCounter.incrementAndGet();
		Log.d("DatabaseHelper", String.format(
				"Uso de conexões com banco de dados: %d", usageCounter.get()));
		return helperInstance;
	}

	/**
	 * Este método é chamado quando o banco de dados é criado pela primeira vez.
	 */
	@Override
	public void onCreate(final SQLiteDatabase database,
			final ConnectionSource connectionSource) {
		Log.d(LOG_TAG, "Chamada do método onCreate em execução...");
		try {
			int schemaCreatedCount = 0;
			schemaCreatedCount += TableUtils.createTable(connectionSource,
					User.class);
			schemaCreatedCount += TableUtils.createTable(connectionSource,
					Notification.class);
			// schemaCreatedCount += TableUtils.createTable(connectionSource,
			// Class.class);
			Log.d(LOG_TAG, String.format(
					"%d script(s) executado(s) com sucesso!",
					schemaCreatedCount));
			insertStaticDataIntoDatabase(database);
		} catch (final SQLException e) {
			Log.e(LOG_TAG, "Erro ao tentar criar esquema do banco de dados.", e);
			throw new RuntimeException(e);
		}
	}

	/**
	 * Este método é chamado quando a aplicação é atualizada e houver uma versão
	 * superior do banco de dados (Refere-se à:
	 * {@link br.ufg.UFGNotify.R.string#database_version}).<br>
	 * <br>
	 * Assim para que a aplicação se ajuste ás modificações feita no banco de
	 * dados, as tabelas são recriadas (
	 * {@link TableUtils#createTable(ConnectionSource, Class)}, depois
	 * {@link TableUtils#dropTable(ConnectionSource, Class, boolean)}).
	 */
	@Override
	public void onUpgrade(final SQLiteDatabase database,
			final ConnectionSource connectionSource, final int oldVersion,
			final int newVersion) {
		Log.d(LOG_TAG, "Chamada do método onUpgrade em execução...");
		try {
			int schemaDeletedCount = 0;
			schemaDeletedCount += TableUtils.dropTable(connectionSource,
					User.class, true);
			schemaDeletedCount += TableUtils.dropTable(connectionSource,
					Notification.class, true);
			Log.d(LOG_TAG, String.format(
					"%d script(s) executado(s) com sucesso!",
					schemaDeletedCount));
			super.onCreate(database);
		} catch (final SQLException e) {
			Log.e(LOG_TAG,
					String.format(
							"Erro ao tentar atualizar esquema do banco de dados da versão %d para a versão %d.",
							oldVersion, newVersion), e);
			throw new RuntimeException(e);
		}
	}

	private DatabaseHelper getHelper() {
		return helperInstance;
	}

	/**
	 * Retorna o objeto de acesso a dados para a classe {@link User}. Será
	 * criada uma instância do objeto e será cacheado para futuras chamadas a
	 * este método.
	 * 
	 * @return o DAO da classe {@link User}.
	 * @throws SQLException
	 */
	public UserDAO getUserDao() throws SQLException {
		if (userDao == null) {
			userDao = new UserDAO(getHelper().getConnectionSource());
		}
		return userDao;
	}

	/**
	 * Retorna o objeto de acesso a dados para a classe {@link Notification}.
	 * Será criada uma instância do objeto e será cacheado para futuras chamadas
	 * a este método.
	 * 
	 * @return o DAO da classe {@link Notification}.
	 * @throws SQLException
	 */
	public NotificationDAO getNotificationDao() throws SQLException {
		if (notificationDao == null) {
			notificationDao = new NotificationDAO(getHelper()
					.getConnectionSource());
		}
		return notificationDao;
	}

	/**
	 * Fecha a conexão com banco de dados e libera as instâncias alocadas para
	 * os objetos de acesso a dados (DAO). Para cada chamada ao método estático
	 * ({@link #getHelperInstance(Context)}, deverá haver 1 e somente 1 chamada
	 * para este método.<br>
	 * Por exemplo: Se houverem 3 chamadas ao método estático
	 * {@link #getHelperInstance(Context)} então somente na terceira chamada a
	 * este método, o helper ({@link #helperInstance}) e suas conexões serão
	 * liberadas e fechadas.
	 */
	@Override
	public void close() {
		Log.d(LOG_TAG, String.format("Uso de conexões com banco de dados: %d",
				usageCounter.get()));
		if (usageCounter.decrementAndGet() == 0) {
			Log.d("DatabaseHelper", "Fechando conexão com banco de dados.");
			super.close();
			userDao = null;
			notificationDao = null;
			helperInstance = null;
		}
	}

	private synchronized void insertStaticDataIntoDatabase(
			final SQLiteDatabase database) throws SQLException {
		if (database == null) {
			throw new IllegalArgumentException("");
		}

		if (database.isOpen()) {
			if (getUserDao().isTableExists()) {

			}
		}
	}

}
