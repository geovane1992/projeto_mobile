package br.ufg.UFGNotify.model;

import java.io.Serializable;

import org.joda.time.DateTime;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "notification")
public class Notification implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String COLUMN_ID = "notification_id";
	public final static String COLUMN_TITLE = "title";
	public final static String COLUMN_CONTENT = "content";
	public final static String COLUMN_SENDER = "sender";
	public final static String COLUMN_READ = "read";
	public final static String COLUMN_DATE = "date";

	@DatabaseField(columnName = COLUMN_ID, generatedId = true)
	Long id;

	@DatabaseField(columnName = COLUMN_TITLE)
	String title;

	@DatabaseField(columnName = COLUMN_CONTENT)
	String content;

	@DatabaseField(columnName = COLUMN_DATE, dataType = DataType.DATE_TIME)
	DateTime date;

	@DatabaseField(columnName = COLUMN_SENDER, dataType = DataType.INTEGER_OBJ)
	Integer sender;

	@DatabaseField(columnName = COLUMN_READ, dataType = DataType.BOOLEAN_OBJ)
	Boolean read;

	Notification() {
	}

	public Notification(Long id, DateTime date, String title, String content,
			Integer sender, Boolean read) {
		super();
		this.id = id;
		this.date = date;
		this.title = title;
		this.content = content;
		this.sender = sender;
		this.read = read;
	}

	public Notification(DateTime date, String title, String content,
			Integer sender, Boolean read) {
		super();
		this.date = date;
		this.title = title;
		this.content = content;
		this.sender = sender;
		this.read = read;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public DateTime getDate() {
		return date;
	}

	public void setDate(DateTime date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getSender() {
		return sender;
	}

	public void setSender(Integer sender) {
		this.sender = sender;
	}

	public Boolean isRead() {
		return read;
	}

	public void setRead(Boolean read) {
		this.read = read;
	}

}
