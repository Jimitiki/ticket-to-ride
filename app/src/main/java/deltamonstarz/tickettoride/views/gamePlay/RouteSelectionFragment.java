package deltamonstarz.tickettoride.views.gamePlay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.City;
import delta.monstarz.shared.model.Route;
import deltamonstarz.tickettoride.R;
import deltamonstarz.tickettoride.presenters.RoutePresenter;

public class RouteSelectionFragment extends DialogFragment {
	private RoutePresenter presenter;
	private GameActivity activity;

	private Button cancel;
	private Button confirm;
	private RecyclerView sourceCities;
	private RecyclerView destinationCities;
	private RecyclerView cardColors;

	private City sourceCity;
	private Route route;
	private CardColor cardColor;
	private boolean sourceSelected;
	private boolean destinationSelected;
	private boolean colorSelected;
	View sourceListItem;
	View destinationListItem;
	View cardListItem;
	LinearLayoutManager sourceLayoutManager;
	LinearLayoutManager destinationLayoutManager;
	LinearLayoutManager cardLayoutManager;

	private List<Route> availableRoutes;
	private List<City> sources;

	private SourceCitiesAdapter sourceAdapter;
	private DestinationCitiesAdapter destinationAdapter;
	private CardColorAdapter cardColorAdapter;

	public RouteSelectionFragment() {}

	@Override
	@NonNull
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.routeSelectionTitle);

		builder.setView(buildView());

		return builder.create();
	}

	public void setPresenter(RoutePresenter presenter) {
		this.presenter = presenter;
	}

	private View buildView(){
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.fragment_route_selection, null);

		confirm = (Button) view.findViewById(R.id.confirm_selection);
		cancel = (Button) view.findViewById(R.id.close_dialog);

		sourceCities = (RecyclerView) view.findViewById(R.id.sourceCities);
		destinationCities = (RecyclerView) view.findViewById(R.id.destinationCities);
		cardColors = (RecyclerView) view.findViewById(R.id.cardColorList);

		sourceCities.setHasFixedSize(true);
		destinationCities.setHasFixedSize(true);
		cardColors.setHasFixedSize(true);

		sourceLayoutManager = new LinearLayoutManager(getContext());
		destinationLayoutManager = new LinearLayoutManager(getContext());
		cardLayoutManager = new LinearLayoutManager(getContext());
		sourceCities.setLayoutManager(sourceLayoutManager);
		destinationCities.setLayoutManager(destinationLayoutManager);
		cardColors.setLayoutManager(cardLayoutManager);

		confirm.setEnabled(false);
		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onConfirmSelection();
			}
		});

		cancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});

		confirm.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				onConfirmSelection();
			}
		});
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getSourceCities();
	}

	private void onToggleSourceCity(City city, View view) {
		if (city.equals(sourceCity)) {
			sourceSelected = false;
			sourceCity = null;
			sourceListItem = null;
			view.setBackgroundColor(getResources().getColor(R.color.grayButton));
			destinationAdapter.clear();
		} else {
			if (sourceSelected) {
				sourceListItem.setBackgroundColor(getResources().getColor(R.color.grayButton));
			} else {
				sourceSelected = true;
			}
			sourceCity = city;
			getDestinationCities();
			sourceListItem = view;
			view.setBackgroundColor(getResources().getColor(R.color.greenButton));
		}
		confirm.setEnabled(false);
		if (cardColorAdapter != null) {
			cardColorAdapter.clear();
		}
	}

	private void onToggleDestinationCity(Route route, View view) {
		if (route.equals(this.route)) {
			destinationSelected = false;
			this.route = null;
			view.setBackgroundColor(getResources().getColor(R.color.grayButton));
			cardColorAdapter.clear();
		} else {
			if (destinationSelected) {
				destinationListItem.setBackgroundColor(getResources().getColor(R.color.grayButton));
			} else {
				destinationSelected = true;
			}
			this.route = route;
			getCardColors();
			destinationListItem = view;
			view.setBackgroundColor(getResources().getColor(R.color.greenButton));
		}
		confirm.setEnabled(false);
	}

	private void onToggleCardColor(CardColor cardColor, View view) {
		if (this.cardColor == cardColor) {
			colorSelected = false;
			this.cardColor = null;
			view.setBackgroundColor(getResources().getColor(R.color.grayButton));
			confirm.setEnabled(false);
		} else {
			if (colorSelected) {
				cardListItem.setBackgroundColor(getResources().getColor(R.color.grayButton));
			} else {
				colorSelected = true;
			}
			this.cardColor = cardColor;
			view.setBackgroundColor(getResources().getColor(R.color.greenButton));
			confirm.setEnabled(true);
		}
	}

	private void getSourceCities() {
		availableRoutes = presenter.getAvailableRoutes();
		sources = new ArrayList<>();
		for (Route route : availableRoutes) {
			if (!sources.contains(route.getCity1())) {
				sources.add(route.getCity1());
			}
			if (!sources.contains(route.getCity2())) {
				sources.add(route.getCity2());
			}
		}
		if (sourceAdapter == null) {
			sourceAdapter = new SourceCitiesAdapter();
			sourceCities.setAdapter(sourceAdapter);
		}
		Collections.sort(sources, new Comparator<City>() {
			@Override
			public int compare(City city1, City city2) {
				return city1.getName().compareTo(city2.getName());
			}
		});
		sourceAdapter.setSourceCities(sources);
	}

	private void getDestinationCities() {
		if (destinationAdapter == null) {
			destinationAdapter = new DestinationCitiesAdapter();
			destinationCities.setAdapter(destinationAdapter);
		}
		destinationAdapter.setDestinationCities(presenter.getConnectedRoutes(sourceCity));
	}

	private void getCardColors() {
		Map<CardColor, Integer> cardCounts = presenter.getUsableCards(route.getID());

		if (cardColorAdapter == null) {
			cardColorAdapter = new CardColorAdapter();
			cardColors.setAdapter(cardColorAdapter);
		}
		cardColorAdapter.setAvailableCards(cardCounts);
	}

	private void onConfirmSelection() {
		presenter.claimRoute(route.getID(), cardColor);
		dismiss();
	}

	private class SourceCitiesAdapter extends RecyclerView.Adapter<SourceCityHolder>{
		List<City> cities;

		@Override
		public SourceCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater
					.from(parent.getContext())
					.inflate(R.layout.route_source_item, parent, false);
			return new SourceCityHolder(view);
		}

		@Override
		public void onBindViewHolder(SourceCityHolder holder, int position) {
			try {
				holder.city = cities.get(position);
				holder.cityName.setText(holder.city.getName());
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getItemCount() {
			return cities.size();
		}

		public void setSourceCities(List<City> sourceCities) {
			cities = sourceCities;
			notifyDataSetChanged();
		}
	}

	private class SourceCityHolder extends RecyclerView.ViewHolder {
		private TextView cityName;
		private City city;

		SourceCityHolder(View itemView) {
			super(itemView);
			cityName = (TextView) itemView.findViewById(R.id.sourceCityName);
			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onToggleSourceCity(city, view);
				}
			});
		}
	}

	private class DestinationCitiesAdapter extends RecyclerView.Adapter<DestinationCityHolder> {
		List<Route> sourceRoutes;

		@Override
		public DestinationCityHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater
					.from(parent.getContext())
					.inflate(R.layout.route_destination_item, parent, false);
			return new DestinationCityHolder(view);
		}

		@Override
		public void onBindViewHolder(DestinationCityHolder holder, int position) {
			Route route = sourceRoutes.get(position);
			holder.cityName.setText(route.getOtherCity(sourceCity).getName());
			holder.route = route;
			holder.colorIndicator.setBackgroundColor(getResources().getColor(R.color.player_green));
		}

		@Override
		public int getItemCount() {
			return sourceRoutes.size();
		}

		void clear() {
			sourceRoutes = new ArrayList<>();
			notifyDataSetChanged();
		}

		void setDestinationCities(List<Route> destinationCities) {
			sourceRoutes = destinationCities;
			notifyDataSetChanged();
		}
	}

	private class DestinationCityHolder extends RecyclerView.ViewHolder {
		private View colorIndicator;
		private TextView cityName;

		private Route route;

		DestinationCityHolder(View itemView) {
			super(itemView);

			colorIndicator = itemView.findViewById(R.id.routeColor);
			cityName = (TextView) itemView.findViewById(R.id.destinationCityName);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onToggleDestinationCity(route, view);
				}
			});
		}
	}

	private class CardColorAdapter extends RecyclerView.Adapter<CardColorHolder> {
		private List<CardColor> availableCards;
		private Map<CardColor, Integer> cardCounts;

		@Override
		public CardColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater
					.from(parent.getContext())
					.inflate(R.layout.card_color_item, parent, false);
			return new CardColorHolder(view);
		}

		@Override
		public void onBindViewHolder(CardColorHolder holder, int position) {
			CardColor cardColor = availableCards.get(position);
			holder.color = cardColor;
			holder.colorText.setText(cardColor.toString().toUpperCase() +
					" - (" + cardCounts.get(cardColor) + ")");
		}

		@Override
		public int getItemCount() {
			return availableCards.size();
		}

		public void clear() {
			availableCards = new ArrayList<>();
			notifyDataSetChanged();
		}

		public void setAvailableCards(Map<CardColor, Integer> cardCounts) {
			availableCards = new ArrayList<>();
			for (Map.Entry<CardColor, Integer> cardEntry: cardCounts.entrySet()) {
				availableCards.add(cardEntry.getKey());
			}

			Collections.sort(availableCards, new Comparator<CardColor>() {
				@Override
				public int compare(CardColor color1, CardColor color2) {
					return color1.compareTo(color2);
				}
			});
			notifyDataSetChanged();
		}
	}

	private class CardColorHolder extends RecyclerView.ViewHolder {
		CardColor color;
		TextView colorText;
		public CardColorHolder(final View itemView) {
			super(itemView);

			colorText = (TextView) itemView.findViewById(R.id.CardColorText);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onToggleCardColor(color, itemView);
				}
			});
		}
	}
}
