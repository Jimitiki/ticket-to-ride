package deltamonstarz.tickettoride.views.gamePlay;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import deltamonstarz.tickettoride.R;

/**
 * Created by cwjohn42 on 3/25/17.
 */

public class NumberInputView extends View {
	int minValue;
	int maxValue;
	int value;

	Button incr;
	Button decr;
	EditText numInput;

	public NumberInputView(Context context) {
		super(context);
	}

	public NumberInputView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NumberInputView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	public View buildView() {
		View v = inflate(getContext(), R.layout.number_input_view, null);
		incr = (Button) v.findViewById(R.id.incr);
		decr = (Button) v.findViewById(R.id.decr);
		numInput = (EditText) v.findViewById(R.id.numInput);

		incr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (value < maxValue) {
					maxValue++;
				}
			}
		});

		decr.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (value > minValue) {
					minValue--;
				}
			}
		});

		numInput.setOnFocusChangeListener(new OnFocusChangeListener() {
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
				numInput.setText(Integer.toString(value));
			}
		});
		return v;
	}

	public int getValue() {
		return value;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public interface OnValueChangedListener {
		public void onValueChanged();
	}
}
