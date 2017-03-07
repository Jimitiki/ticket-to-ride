package deltamonstarz.tickettoride.views.gamePlay;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.io.IOException;
import java.io.InputStream;

import delta.monstarz.shared.model.PlayerColor;

public class MapView extends View {
	private Bitmap mapImage;
	private GameActivity activity;
	private Rect sourceRect;
	private RectF destRect;
	private float mapScaleX;
	private float mapScaleY;

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

	public void generateBitmap(String mapImagePath, int viewHeight, int viewWidth) {
		try {
			InputStream is = activity.getAssets().open(MAP_PATH_PREFIX + mapImagePath);
			mapImage = BitmapFactory.decodeStream(is);
			sourceRect = new Rect(0, 0, mapImage.getWidth(), mapImage.getHeight());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void redraw()
	{
		invalidate();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (destRect == null) {
			destRect = new RectF(0, 0, getWidth(), getHeight());
			mapScaleX = getWidth() / mapImage.getWidth();
			mapScaleY = getHeight() / mapImage.getHeight();
			//1442, 740
		}
		canvas.drawBitmap(mapImage, sourceRect, destRect, null);
		System.out.println(getHeight() + "x : y" + getWidth());
	}

	private void drawTrain(int x, int y, int theta, PlayerColor color)
	{
		if (trainImages[color.getValue()] == null) {

		}
	}
}
