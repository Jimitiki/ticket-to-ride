package delta.monstarz.server;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

import delta.monstarz.exceptions.PreferenceFormatException;
import delta.monstarz.shared.Image;
import delta.monstarz.shared.Segment;

/**
 * This class stores game settings, including map data and card deck data.
 *
 * @author bradcarter
 */
public class Preferences {

	/*
	//Class Members-----------------------------------------------------------
	//Static Fields
	private static final String PREFERENCES_FILE_NAME = "/Users/bradcarter/Desktop/preferences.json";
	private static final String MAP_OBJECT = "map";
	private static final String MAP_WIDTH = "width";
	private static final String MAP_HEIGHT = "height";
	private static final String CITY_LIST = "CityList";
	private static final String CITY_NAME = "name";
	private static final String ROUTE_LIST = "name";
	private static final String ROUTE_SEGMENTS = "name";
	private static final String CARDS_OBJECT = "name";
	private static final String TRAIN_CARD_LIST = "name";
	private static final String DESTINATION_CARD_LIST = "name";
	private static final String FILE = "file";
	private static final String POSITION = "position";
	private static final String X = "x";
	private static final String Y = "y";
	private static final String ROTATION = "name";
	private static final String COLOR = "name";
	private static final String TRAIN_VALUE = "name";
	private static final String ENDPOINTS = "name";
	private static final String POINT_VALUE = "name";

	//Singleton instance and method
	private static Preferences singleton;

	public static Preferences instance()
	{
		if(singleton == null)
		{
			singleton = new Preferences();
		}
		return singleton;
	}

	//Instance Members-----------------------------------------------
	//Instance Variables
	private Image mMap;
	private List<City> mCities;
	private List<Route> mRoutes;
	private TrainDeck mTrainDeck;
	private DestinationDeck mDestinationDeck;

	//Constructors
	private Preferences()
	{
		mMap = null;
		mCities = new ArrayList<>();
		mRoutes = new ArrayList<>();
		mDestinationDeck = new DestinationDeck();
		mTrainDeck = new TrainDeck();
	}

	//Public Methods
	/**
	 * Loads preferences from assets/preferences.json.
	 *
	 *
	 * @throws IOException if preferences.json cannot be read.
	 *
	public void parsePreferenceFile() throws IOException
	{
		//Clear the current preferences
		mMap = null;
		mCities.clear();
		mRoutes.clear();
		mTrainDeck.clear();
		mDestinationDeck.clear();

		//Get the file contents
		String contents = new String(Files.readAllBytes(Paths.get(PREFERENCES_FILE_NAME)));//TODO Fix file access

		//Parse JSON
		JsonParser parser = new JsonParser();
		JsonObject preferenceObject = parser.parse(contents).getAsJsonObject();

		JsonObject mapObject = preferenceObject.getAsJsonObject(MAP_OBJECT);
		parseMap(mapObject);

		JsonArray cityList = preferenceObject.getAsJsonArray(CITY_LIST);
		parseCities(cityList);

		JsonArray routeList = preferenceObject.getAsJsonArray(ROUTE_LIST);
		parseRoutes(routeList);

		JsonArray trainCardList = preferenceObject.getAsJsonArray(TRAIN_CARD_LIST);
		parseTrainCards(trainCardList);

		JsonArray destinationCardList = preferenceObject.getAsJsonArray(DESTINATION_CARD_LIST);
		parseDestinationCards(destinationCardList);
	}

	//Internal Methods
	private void parseMap(JsonObject mapObject)
	{
		String fileName = mapObject.get(FILE).getAsString();
		int width = mapObject.get(MAP_WIDTH).getAsInt();
		int height = mapObject.get(MAP_HEIGHT).getAsInt();
		File file = new File(fileName);

		mMap = new Image(file, width, height);;
	}

	private void parseCities(JsonArray cityList)
	{
		for(int i = 0; i < cityList.size(); i++) {
			JsonObject cityObject = cityList.get(i).getAsJsonObject();
			String name = cityObject.get(CITY_NAME).getAsString();
			Point position = parsePoint(cityObject.get(POSITION).getAsJsonObject());
			mCities.add(new City(name, position));
		}
	}

	private void parseRoutes(JsonArray routeList) throws PreferenceFormatException
	{
		for(int i = 0; i < routeList.size(); i++) {
			JsonObject routeObject = routeList.get(i).getAsJsonObject();

			//Parse the Endpoints
			JsonArray endpointArray = routeObject.get(ENDPOINTS).getAsJsonArray();
			if(endpointArray.size() != 2) {
				throw new PreferenceFormatException("Invalid number of endpoints: " + String.valueOf(endpointArray.size()));
			}
			String endpoint1 = endpointArray.get(0).getAsString();
			String endpoint2 = endpointArray.get(1).getAsString();

			//Parse the Segments
			List<Segment> segments = parseSegments(routeObject.get(ROUTE_SEGMENTS).getAsJsonArray());

			//Parse color
			String color = routeObject.get(COLOR).getAsString();

//			"segmants":[{"x":20, "y":62, "rotation":0}, {"x":62, "y":35, "rotation":90}],

			//Route route = new Route()
		}
	}

	private List<Segment> parseSegments(JsonArray segmentArray)
	{

	}

	private void parseTrainCards(JsonArray trainCardList)
	{

	}

	private void parseDestinationCards(JsonArray destinationCardList)
	{

	}

	private Point parsePoint(JsonObject point)
	{

	}


	public static void main(String[] args)
	{
		Preferences pref = Preferences.instance();
		try {
			pref.parsePreferenceFile();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
