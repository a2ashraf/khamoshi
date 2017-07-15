package ahsan.io.khamoshi.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ahsan.io.khamoshi.R;


public class MatrixTableAdapter<T> extends BaseTableAdapter
{

	private final static int WIDTH_DIP = 110;
	private final static int HEIGHT_DIP = 32;

	private final Context context;
	private final String headers[] = { "Date", "FAJR", "SUNRISE", "ZUHR","ASR", "SUNSET", "ISHA", };

	private T[][] table;

	private final int width;
	private final int height;
	
	public MatrixTableAdapter(Context context)
	{
		this(context, null);
	}

	public MatrixTableAdapter(Context context, T[][] table)
	{
		
		this.context = context;
		Resources r = context.getResources();

		width = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, WIDTH_DIP, r.getDisplayMetrics()));
		height = Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT_DIP, r.getDisplayMetrics()));

		setInformation(table);
	}

	public void setInformation(T[][] table)
	{
		this.table = table;
	}

	@Override
	public int getRowCount()
	{
		return table.length - 1;
	}

	@Override
	public int getColumnCount()
	{
		if(table[0]!=null)
			return table[0].length - 1;
		else return 0;
	}

	@Override
	public View getView(int row, int column, View convertView, ViewGroup parent)
	{
		
		final View view;
		switch (getItemViewType(row, column)) {
			case 0:
				view = getFirstHeader(row, column, convertView, parent);
				break;
			case 1:
				view = getHeader(row, column, convertView, parent);
				break;
			case 2:
				view = getFirstBody(row, column, convertView, parent);
				break;
			case 3:
				view = getBody(row, column, convertView, parent);
				break;
			case 4:
				view = getFamilyView(row, column, convertView, parent);
				break;
			default:
				throw new RuntimeException("wtf?");
		}
		return view;
		
		
//		if (convertView == null) {
//			convertView = new TextView(context);
//			((TextView) convertView).setGravity(Gravity.CENTER_VERTICAL);
//		}
//		((TextView) convertView).setText(table[row + 1][column + 1].toString());
//
//
		
//		return convertView;
	}
	
	
	private View getFirstHeader(int row, int column, View convertView,
								ViewGroup parent)
	{
		if (convertView == null) {
			convertView =  LayoutInflater.from(context).inflate(
					R.layout.item_table_header_first, parent, false);
		}
		((TextView) convertView.findViewById(R.id.table_item_text))
				.setText(headers[0].toString());
		return convertView;
	}
	
	private View getHeader(int row, int column, View convertView,
						   ViewGroup parent)
	{
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_table_header, parent, false);
		}
		((TextView) convertView.findViewById(R.id.table_item_text))
				.setText(headers[column + 1]);
		return convertView;
	}
	
	private View getFirstBody(int row, int column, View convertView,
							  ViewGroup parent)
	{
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_table_first, parent, false);
		}
		convertView
				.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1
						: R.drawable.bg_table_color2);
		((TextView) convertView.findViewById(R.id.table_item_text))
		
		.setText(table[row + 1][column + 1].toString());
		
		return convertView;
	}
	
	private View getBody(int row, int column, View convertView,
						 ViewGroup parent)
	{
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_table,
					parent, false);
		}
		convertView
				.setBackgroundResource(row % 2 == 0 ? R.drawable.bg_table_color1
						: R.drawable.bg_table_color2);
		((TextView) convertView.findViewById(R.id.table_item_text))
				.setText(table[row + 1][column + 1].toString());		return convertView;
	}
	
	private View getFamilyView(int row, int column, View convertView,
							   ViewGroup parent)
	{
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.item_table_family, parent, false);
		}
		final String string;
		if (column == -1) {
			string ="4444"; //getFamily(row).name;
		} else {
			string = "";
		}
		((TextView) convertView.findViewById(R.id.table_item_text))
				.setText(string);
		return convertView;
	}

	@Override
	public int getHeight(int row)
	{
		return height;
	}

	@Override
	public int getWidth(int column)
	{
		return width;
	}

	@Override
	public int getItemViewType(int row, int column)
	{
		return 0;
	}

	@Override
	public int getViewTypeCount()
	{
		return 1;
	}

	@Override
	public int getBackgroundResId(int row, int column)
	{
		return 0;
	}

	@Override
	public int getBackgroundHighlightResId(int row, int column) {
		return R.drawable.item_highlight_rect;
	}

	@Override
	public boolean isRowSelectable(int row) {
		return true;
	}

	@Override
	public long getItemId(int row, int column) {
		return 0;
	}

	@Override
	public Object getItem(int row, int column) {
		if (row <= getRowCount() && column <= getColumnCount()) {
			return this.table[row][column];
		} else {
			return null;
		}
	}
}
