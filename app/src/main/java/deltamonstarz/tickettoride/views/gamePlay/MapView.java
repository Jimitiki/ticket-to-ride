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

import delta.monstarz.shared.model.PlayerColor;
import delta.monstarz.shared.model.Route;
import delta.monstarz.shared.model.Segment;

public class MapView extends View {
	private Bitmap mapImage;
	private GameActivity activity;
	private static Rect sourceRect;
	private static RectF destRect;
	private static float mapScaleX;
	private static float mapScaleY;
	private List<Route> claimedRoutes = new ArrayList<>();

	private final float TRAIN_SCALE = (float) 0.18;
	private final int TRAIN_OFFSET_X = -2;
	private final int TRAIN_OFFSET_Y = -3;

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
	}

	private void drawTrain(Segment segment, PlayerColor color, Canvas canvas)
	{

		try {
			if (trainImages[color.getValue()] == null) {
					InputStream is = activity.getAssets().open(TRAIN_PATH_PREFIX + TRAIN_IMAGES[color.getValue()]);
					trainImages[color.getValue()] = BitmapFactory.decodeStream(is);
			}

			Bitmap trainImage = trainImages[color.getValue()];

			Matrix matrix = new Matrix();
			matrix.postRotate(segment.getRotation());
			matrix.postTranslate(segment.getX() + TRAIN_OFFSET_X, segment.getY() + TRAIN_OFFSET_Y);
			matrix.postScale(mapScaleX, mapScaleY);

			canvas.save();
			canvas.setMatrix(matrix);
			float trainWidth = TRAIN_SCALE * trainImage.getWidth();
			float trainHeight = TRAIN_SCALE * trainImage.getHeight();
			canvas.drawBitmap(trainImage, new Rect(0, 0, trainImage.getWidth(), trainImage.getHeight()),
					new RectF(0, 0, trainWidth, trainHeight), null);
			canvas.restore();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
