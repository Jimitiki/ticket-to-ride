package deltamonstarz.tickettoride.views.gamePlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import delta.monstarz.shared.model.CardColor;
import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.Segment;

public class MapView extends View {
	private static Bitmap mapImage;
	private GameActivity activity;
	private GameFragment fragment;
	private static Rect sourceRect;
	private static RectF destRect;
	private static float mapScaleX;
	private static float mapScaleY;
	private static List<Route> claimedRoutes = new ArrayList<>();

	private final float TRAIN_SCALE = (float) 0.22;

	private final static String[] TRAIN_IMAGES = {
			"Blue.png",
			"Green.png",
			"Red.png",
			"Yellow.png",
			"Black.png"
	};
	private static Bitmap[] trainImages = new Bitmap[5];
	private final static String MAP_PATH_PREFIX = "maps/";
	private final static String TRAIN_PATH_PREFIX = "train_icons/";

	public MapView(Context context) {
		super(context);
	}

	public MapView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MapView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public void setActivity(GameActivity activity) {
		this.activity = activity;
	}

	public void setClaimedRoutes(List<Route> routes) {
		claimedRoutes = routes;
	}

	public void generateBitmap(String mapImagePath, int viewHeight, int viewWidth) {
		try {
			if (mapImage == null) {
				InputStream is = activity.getAssets().open(MAP_PATH_PREFIX + mapImagePath);
				mapImage = BitmapFactory.decodeStream(is);
				sourceRect = new Rect(0, 0, mapImage.getWidth(), mapImage.getHeight());
//				ArrayList<Segment> segments = new ArrayList<>();
//				segments.add(new Segment(1432, 760, 48));
//				segments.add(new Segment(1475, 812, 48));
//				segments.add(new Segment(1517, 865, 48));
//				segments.add(new Segment(1560, 920, 48));
//				segments.add(new Segment(1600, 970, 48));
//				Route route = new Route(1, "Atlanta", "Miami", 5, CardColor.BLUE, segments);
//				route.setOwner("a");
//				route.setTrainColor(PlayerColor.GREEN);
//				claimedRoutes.add(route);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redraw() {
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (destRect == null) {
			destRect = new RectF(0, 0, getWidth(), getHeight());
			System.out.println("width: " + getWidth() + "   height: " + getHeight());
			mapScaleX = getWidth() / (float) mapImage.getWidth();
			mapScaleY = getHeight() / (float) mapImage.getHeight();
		}
		canvas.drawBitmap(mapImage, sourceRect, destRect, null);
		drawRoutes(canvas);
	}

	private void drawRoutes(Canvas canvas) {
		for (Route route : claimedRoutes) {
			List<Segment> routeSegments = route.getSegments();
			for (Segment segment : routeSegments) {
				drawTrain(segment, route.getTrainColor(), canvas);
			}
		}
//		drawTrain(new Segment(1442, 740, 0), PlayerColor.RED, canvas);
	}

	private void drawTrain(Segment segment, PlayerColor color, Canvas canvas)
	{

		try {
			if (trainImages[color.getValue()] == null) {
					InputStream is = activity.getAssets().open(TRAIN_PATH_PREFIX + TRAIN_IMAGES[color.getValue()]);
					trainImages[color.getValue()] = BitmapFactory.decodeStream(is);
			}
			Bitmap trainImage = trainImages[color.getValue()];
			canvas.save();
			Matrix transform = new Matrix();
			transform.postRotate(segment.getRotation());
			transform.postTranslate(segment.getX(), segment.getY());
			transform.postScale(mapScaleX, mapScaleY);
			canvas.setMatrix(transform);
			RectF trainLocation = new RectF(0, 0, trainImage.getWidth() * TRAIN_SCALE,
					trainImage.getHeight() * TRAIN_SCALE);
			canvas.drawBitmap(trainImage, new Rect(0, 0, trainImage.getWidth(), trainImage.getHeight()),
					trainLocation, null);
			canvas.restore();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
