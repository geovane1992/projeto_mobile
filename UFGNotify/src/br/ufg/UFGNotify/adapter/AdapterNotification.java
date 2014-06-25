package br.ufg.UFGNotify.adapter;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.ufg.UFGNotify.R;
import br.ufg.UFGNotify.model.Notification;
import br.ufg.UFGNotify.model.Sender;
import br.ufg.UFGNotify.util.DefaultSender;

public class AdapterNotification extends BaseAdapter {
	private LayoutInflater layoutInflater;
	private List<Notification> notifications;

	public AdapterNotification(Context context, List<Notification> notifications) {
		this.layoutInflater = LayoutInflater.from(context);
		this.notifications = notifications;
	}

	@Override
	public int getCount() {
		return this.notifications.size();
	}

	@Override
	public Object getItem(int position) {
		return this.notifications.get(position);
	}

	@Override
	public long getItemId(int position) {
		return this.notifications.get(position).getId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater
					.inflate(R.layout.list_row_layout, null);
			holder = new ViewHolder();
			holder.titleView = (TextView) convertView
					.findViewById(R.id.titleView);
			holder.senderView = (TextView) convertView
					.findViewById(R.id.sender);
			holder.dateView = (TextView) convertView
					.findViewById(R.id.dateView);
			holder.readView = (TextView) convertView
					.findViewById(R.id.readView);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		int id = notifications.get(position).getSender().intValue();
		Sender sender = DefaultSender.create(id, convertView.getContext());
		holder.senderView.setText(sender.getName());
		holder.titleView.setText(notifications.get(position).getTitle());
		holder.dateView.setText(getDateFormatted(notifications.get(position)
				.getDate()));
		holder.readView
				.setText(notifications.get(position).isRead().toString());

		return convertView;
	}

	class ViewHolder {
		TextView titleView;
		TextView dateView;
		TextView senderView;
		TextView readView;
	}

	public String getDateFormatted(DateTime date) {
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
		return dtfOut.print(date);
	}
}
