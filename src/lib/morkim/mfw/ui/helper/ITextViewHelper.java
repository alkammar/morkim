package lib.morkim.mfw.ui.helper;

import android.widget.TextView;

public interface ITextViewHelper {
	
	static final String FONT_NORMAL = "fonts/FuturaStd-Book.otf";
	static final String FONT_BOLD = "fonts/FuturaStd-Bold.otf";
	
	public TextView loadTextView(int id);
	public TextView loadTextView(int id, int textId);
	public TextView loadTextView(int id, CharSequence text);
	public TextView loadTextViewBold(int id);
	public TextView loadTextViewBold(int id, int textId);
	public TextView loadTextViewBold(int id, CharSequence text);
}
