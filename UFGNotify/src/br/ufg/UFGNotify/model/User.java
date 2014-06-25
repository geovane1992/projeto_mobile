package br.ufg.UFGNotify.model;

import java.io.Serializable;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "users")
public class User implements Serializable {
	public static final int STUDENT = 1;
	public static final int FUNCTIONARY = 2;

	private static final long serialVersionUID = 6883600272473593328L;

	public final static String COLUMN_NAME_ID = "user_id";
	public final static String COLUMN_NAME_FULL_NAME = "full_name";
	public final static String COLUMN_NAME_EMAIL = "email";
	public final static String COLUMN_NAME_USER = "name";
	public final static String COLUMN_NAME_PASSWORD = "password";
	public final static String COLUMN_NAME_TYPE = "type";

	@DatabaseField(columnName = COLUMN_NAME_ID, generatedId = true)
	private Long id;

	@DatabaseField(columnName = COLUMN_NAME_FULL_NAME)
	private String fullName;

	@DatabaseField(columnName = COLUMN_NAME_EMAIL, unique = true)
	private String email;

	@DatabaseField(columnName = COLUMN_NAME_USER, unique = true)
	private String name;

	@DatabaseField(columnName = COLUMN_NAME_PASSWORD)
	private String password;

	@DatabaseField(columnName = COLUMN_NAME_TYPE, dataType = DataType.INTEGER_OBJ)
	private Integer type;

	/**
	 * Construtor provido para o framework ORM
	 */
	User() {
		super();
	}

	/**
	 * Construtor com inicialização de todos atributos da classe.
	 * 
	 * @param id
	 * @param fullName
	 * @param email
	 * @param name
	 * @param password
	 * @param type
	 */
	public User(Long id, String fullName, String email, String name,
			String password, Integer type) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.email = email;
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public User(String fullName, String email, String name, String password,
			Integer type) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.name = name;
		this.password = password;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}


	@Override
	public boolean equals(final Object obj) {
		if (obj != null && obj instanceof User) {
			final User otherUSer = (User) obj;

			if (this.id == null || otherUSer.id == null) {
				return false;
			}

			if (this.name == null || otherUSer.name == null) {
				return false;
			}

			if (this.password == null || otherUSer.password == null) {
				return false;
			}

			if (this.fullName == null || otherUSer.fullName == null) {
				return false;
			}

			if (this.type == null || otherUSer.type == null) {
				return false;
			}

			return this.id.equals(otherUSer.id)
					&& this.name.equals(otherUSer.name)
					&& this.password.equals(otherUSer.password)
					&& this.fullName.equals(otherUSer.fullName)
					&& this.type.equals(otherUSer.type);
		}

		return false;
	}
}
