package lib.morkim.mfw.ui.helper;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class ViewHelper implements ITextViewHelper {

	private Context context;
	private View view;

	public ViewHelper(Context context) {

		this.context = context;
	}

	public ViewHelper(Context context, View view) {

		this.context = context;
		this.view = view;
	}

	public void setView(View view) {

		this.view = view;
	}

	@Override
	public TextView loadTextView(int id) {

		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));

		return tv;
	}

	@Override
	public TextView loadTextView(int id, int textId) {

		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));
		tv.setText(textId);

		return tv;
	}

	@Override
	public TextView loadTextView(int id, CharSequence text) {

		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));
		tv.setText(text);

		return tv;
	}

	public TextView loadTextView(TextView view) {

		view.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));

		return view;
	}

	@Override
	public TextView loadTextViewBold(int id) {

		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_BOLD));

		return tv;
	}

	@Override
	public TextView loadTextViewBold(int id, int textId) {

		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_BOLD));
		tv.setText(textId);

		return tv;
	}

	@Override
	public TextView loadTextViewBold(int id, CharSequence text) {

		TextView tv = (TextView) view.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_BOLD));
		tv.setText(text);

		return tv;
	}

	public TextView loadTextViewBold(TextView view) {

		view.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_BOLD));

		return view;
	}
	
	public Button loadRadioButton(int buttonId) {

		RadioButton button = (RadioButton) view.findViewById(buttonId);
		button.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));

		return button;
	}

}
