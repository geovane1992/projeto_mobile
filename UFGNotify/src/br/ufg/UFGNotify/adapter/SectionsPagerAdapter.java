package br.ufg.UFGNotify.adapter;

import java.util.List;

import br.ufg.UFGNotify.activity.fragment.NotificationFragment;
import br.ufg.UFGNotify.model.Sender;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class SectionsPagerAdapter extends FragmentPagerAdapter {
	List<Sender> senders;

	public SectionsPagerAdapter(FragmentManager fm, List<Sender> senders) {
		super(fm);
		this.senders = senders;
	}

	@Override
	public Fragment getItem(int position) {
		return new NotificationFragment(senders.get(position).getId());
	}

	@Override
	public int getCount() {
		return senders.size();
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return senders.get(position).getName();
	}

}
