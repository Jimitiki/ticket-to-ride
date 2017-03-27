package deltamonstarz.tickettoride.views.gamePlay;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	Map<CardColor, Integer> routeColorToColorID;

	private City sourceCity;
	private Route selectedRoute;
	private CardColor selectedColor;
	int selectedCardCount;
	int goldCardCount;
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
	private Map<CardColor, Integer> cardCounts;

	private SourceCitiesAdapter sourceAdapter;
	private DestinationCitiesAdapter destinationAdapter;
	private CardColorAdapter cardColorAdapter;

	public RouteSelectionFragment() {
		routeColorToColorID = new HashMap<>();
		routeColorToColorID.put(CardColor.BLACK, R.color.route_black);
		routeColorToColorID.put(CardColor.BLUE, R.color.route_blue);
		routeColorToColorID.put(CardColor.GOLD, R.color.route_gray);
		routeColorToColorID.put(CardColor.GREEN, R.color.route_green);
		routeColorToColorID.put(CardColor.ORANGE, R.color.route_orange);
		routeColorToColorID.put(CardColor.PINK, R.color.route_pink);
		routeColorToColorID.put(CardColor.RED, R.color.route_red);
		routeColorToColorID.put(CardColor.YELLOW, R.color.route_yellow);
		routeColorToColorID.put(CardColor.WHITE, R.color.route_white);
	}

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
		if (selectedRoute != null) {
			selectedRoute = null;
		}
		confirm.setEnabled(false);
		if (cardColorAdapter != null) {
			cardColorAdapter.clear();
		}
	}

	private void onToggleDestinationCity(Route route, View view) {
		if (route.equals(selectedRoute)) {
			destinationSelected = false;
			selectedRoute = null;
			view.setBackgroundColor(getResources().getColor(R.color.grayButton));
			cardColorAdapter.clear();
		} else {
			if (destinationSelected) {
				destinationListItem.setBackgroundColor(getResources().getColor(R.color.grayButton));
			} else {
				destinationSelected = true;
			}
			selectedRoute = route;
			getCardColors();
			destinationListItem = view;
			view.setBackgroundColor(getResources().getColor(R.color.greenButton));
		}
		confirm.setEnabled(false);
	}

	private void updateCardCount(CardColor color, int cardCount) {
		int cardCountTotal;
		if (color == CardColor.GOLD) {
			goldCardCount = cardCount;
			cardCountTotal = cardCount;
		} else {
			selectedCardCount = cardCount;
			cardCountTotal = cardCount + goldCardCount;
		}
		if (selectedColor == null && color != CardColor.GOLD) {
			selectedColor = color;

		} else if (cardCount == 0 && color != CardColor.GOLD) {
			selectedColor = null;
		}
		CardColor colorUsed = selectedColor == null ? CardColor.GOLD : selectedColor;
		if (selectedRoute.verifyCardColorByCount(colorUsed, cardCountTotal)
				&& cardCountTotal == selectedRoute.getLength()) {
			confirm.setEnabled(true);
		} else {
			confirm.setEnabled(false);
		}
		cardColorAdapter.updateEnableHolder();
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
		destinationAdapter.setDestinationCities(presenter.getAvailableDestinations(sourceCity));
	}

	private void getCardColors() {
		cardCounts = presenter.getUsableCards(selectedRoute.getID());

		if (cardColorAdapter == null) {
			cardColorAdapter = new CardColorAdapter();
			cardColors.setAdapter(cardColorAdapter);
		}
		cardColorAdapter.setAvailableCards();
	}

	private void onConfirmSelection() {
		presenter.claimRoute(selectedRoute.getID(), selectedColor, goldCardCount);
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
				City city = cities.get(position);
				
				holder.city = city;
				if (city.equals(sourceCity)) {
					holder.cityName.setBackgroundColor(getResources().getColor(R.color.greenButton));
				} else {
					holder.cityName.setBackgroundColor(getResources().getColor(R.color.grayButton));
				}
				
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
			cityName.setGravity(Gravity.CENTER_VERTICAL);
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
			holder.cityName.setText("(" + Integer.toString(route.getLength())
					+ ")  " + route.getOtherCity(sourceCity).getName());
			holder.route = route;
			holder.colorIndicator.setBackgroundColor(getResources().getColor(routeColorToColorID.get(route.getColor())));
			if (route.equals(selectedRoute)) {
				holder.cityName.setBackgroundColor(getResources().getColor(R.color.greenButton));
			} else {
				holder.cityName.setBackgroundColor(getResources().getColor(R.color.grayButton));
			}
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
			cityName.setGravity(Gravity.CENTER_VERTICAL);

			itemView.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onToggleDestinationCity(route, cityName);
				}
			});
		}
	}

	private class CardColorAdapter extends RecyclerView.Adapter<CardColorHolder> {
		private List<CardColor> availableCards;
		private List<CardColorHolder> visibleHolders = new ArrayList<>();

		@Override
		public CardColorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			View view = LayoutInflater
					.from(parent.getContext())
					.inflate(R.layout.card_color_item, parent, false);
			return new CardColorHolder(view);
		}

		@Override
		public void onBindViewHolder(CardColorHolder holder, int position) {
			try {
				CardColor cardColor = availableCards.get(position);
				holder.color = cardColor;
				holder.maxValue = selectedRoute.getLength() < cardCounts.get(cardColor) ?
						selectedRoute.getLength() : cardCounts.get(cardColor);
				holder.value = 0;
				holder.numInput.setText("0");
				String colorName = cardColor.toString();
				colorName = colorName.substring(0, 1).toUpperCase() + colorName.substring(1);
				holder.colorText.setText(colorName + " - (" + cardCounts.get(cardColor) + ")");

				if (position == visibleHolders.size()) {
					visibleHolders.add(holder);
				} else {
					visibleHolders.set(position, holder);
				}
				handleDisableHolder(holder);
			} catch (NullPointerException e) {
				e.printStackTrace();
			}
		}

		@Override
		public int getItemCount() {
			return availableCards.size();
		}

		private void clear() {
			availableCards = new ArrayList<>();
			notifyDataSetChanged();
		}

		private void setAvailableCards() {
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

		private void updateEnableHolder() {
			for (CardColorHolder holder : visibleHolders) {
				handleDisableHolder(holder);
			}
		}

		private void handleDisableHolder(CardColorHolder holder) {
			CardColor cardColor = holder.color;
			if (selectedColor != null && cardColor != selectedColor && cardColor != CardColor.GOLD) {
				holder.disable();
			} else {
				holder.enable();
			}
		}
	}

	private class CardColorHolder extends RecyclerView.ViewHolder {
		CardColor color;

		int value;
		int maxValue;
		int minValue;

		TextView colorText;
		Button incr;
		Button decr;
		EditText numInput;

		private CardColorHolder(final View itemView) {
			super(itemView);

			colorText = (TextView) itemView.findViewById(R.id.CardColorText);
			colorText.setGravity(Gravity.CENTER_VERTICAL);

			incr = (Button) itemView.findViewById(R.id.incr);
			decr = (Button) itemView.findViewById(R.id.decr);
			numInput = (EditText) itemView.findViewById(R.id.numInput);
			numInput.setGravity(Gravity.CENTER_HORIZONTAL);

			incr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (value < maxValue) {
						value++;
						updateValue();
					}
				}
			});

			decr.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (value > minValue) {
						value--;
						updateValue();
					}
				}
			});

			numInput.setOnFocusChangeListener(new View.OnFocusChangeListener() {
				@Override
				public void onFocusChange(View v, boolean hasFocus) {
					int inputValue = Integer.parseInt(numInput.getText().toString());
					if (inputValue > maxValue) {
						inputValue = maxValue;
					}
					if (inputValue < maxValue) {
						inputValue = minValue;
					}
					value = inputValue;
					updateValue();
				}
			});
		}

		private void setCardCount(int max) {
			value = 0;
			maxValue = max;
			updateValue();
		}

		private void updateValue() {
			numInput.setText(Integer.toString(value));
			updateCardCount(color, value);
		}

		private void disable() {
			incr.setEnabled(false);
			decr.setEnabled(false);
			numInput.setEnabled(false);
		}

		private void enable() {
			incr.setEnabled(true);
			decr.setEnabled(true);
			numInput.setEnabled(true);
		}
	}
}
