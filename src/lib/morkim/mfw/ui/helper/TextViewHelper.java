package lib.morkim.mfw.ui.helper;

import lib.morkim.mfw.ui.Screen;
import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

public class TextViewHelper implements ITextViewHelper {

	private Screen screen;
	private Context context;

	public TextViewHelper(Context context, Screen view) {

		this.context = context;
		this.screen = view;
	}

	@Override
	public TextView loadTextView(int id) {

		TextView tv = (TextView) screen.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));

		return tv;
	}

	@Override
	public TextView loadTextView(int id, int textId) {

		TextView tv = (TextView) screen.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));
		tv.setText(textId);

		return tv;
	}

	@Override
	public TextView loadTextView(int id, CharSequence text) {

		TextView tv = (TextView) screen.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(),
				FONT_NORMAL));
		tv.setText(text);

		return tv;
	}

	public TextView loadTextViewBold(int id) {

		TextView tv = (TextView) screen.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_BOLD));

		return tv;
	}

	public TextView loadTextViewBold(int id, int textId) {

		TextView tv = (TextView) screen.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_BOLD));
		tv.setText(textId);

		return tv;
	}

	public TextView loadTextViewBold(int id, CharSequence text) {

		TextView tv = (TextView) screen.findViewById(id);
		tv.setTypeface(Typeface.createFromAsset(context.getAssets(), FONT_BOLD));
		tv.setText(text);

		return tv;
	}

	public void setText(TextView tv, String text) {

		tv.setText(text);
	}

	public void setText(int id, String text) {

		((TextView) screen.findViewById(id)).setText(text);
	}
}
