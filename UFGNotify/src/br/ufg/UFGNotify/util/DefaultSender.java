package br.ufg.UFGNotify.util;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.Sender;

public class DefaultSender {
	
	public static List<Sender> getSenders(final Context context) {
		List<Sender> senders = new ArrayList<Sender>();
		senders.add(create(Sender.LIBRARY, context));
		senders.add(create(Sender.COURSE_COORDINATOR, context));
		senders.add(create(Sender.BOARD_UNITY, context));
		senders.add(create(Sender.DOCENTE_DISCIPLINA, context));
		senders.add(create(Sender.PRO_RECTORY, context));
		senders.add(create(Sender.RECTORY, context));
		senders.add(create(Sender.GENERAL, context));
		return senders;
	}

	public static Sender create(final int id, final Context context) {
		Sender sender = null;

		switch (id) {
		case Sender.COURSE_COORDINATOR:
			sender = new Sender(id,
					context.getString(R.string.course_coordinator));
			break;

		case Sender.BOARD_UNITY:
			sender = new Sender(id, context.getString(R.string.board_of_unity));
			break;

		case Sender.LIBRARY:
			sender = new Sender(id, context.getString(R.string.library));
			break;

		case Sender.PRO_RECTORY:
			sender = new Sender(id, context.getString(R.string.pro_rectory));
			break;

		case Sender.RECTORY:
			sender = new Sender(id, context.getString(R.string.rectory));
			break;

		case Sender.DOCENTE_DISCIPLINA:
			sender = new Sender(id, context.getString(R.string.disciplines));
			break;

		case Sender.GENERAL:
			sender = new Sender(id, context.getString(R.string.general));
			break;

		default:
			break;
		}
		return sender;
	}
}
