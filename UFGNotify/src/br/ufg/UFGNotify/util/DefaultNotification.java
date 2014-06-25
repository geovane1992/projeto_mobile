package br.ufg.UFGNotify.util;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import android.content.Context;
import br.ufg.UFGNotify.model.Notification;
import br.ufg.UFGNotify.model.Sender;

public class DefaultNotification {

	private static List<Notification> notifications;

	public static List<Notification> getList(Context context) {
		notifications = new ArrayList<Notification>();
		for (Sender sender : DefaultSender.getSenders(context)) {
			for (int i = 0; i < 10; i++) {
				Notification notification = new Notification(new DateTime(),
						"Esta é uma notificação de teste... ", "Esta é uma notificação de teste...",
						sender.getId(), true);
				notifications.add(notification);
			}
		}
		return notifications;
	}	
}