package hu.ait.karen.bkktix.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.TicketType;
import hu.ait.karen.bkktix.data.Ticket;

/**
 * source: http://www.androidhive.info/2013/07/android-expandable-list-view-tutorial/
 */

public class MyTixExpandableListAdapter extends BaseExpandableListAdapter {

    public enum HeaderType{
        VALIDATED_TICKETS, _20_MINUTE_TICKETS, _60_MINUTE_TICKETS, _120_MINUTE_TICKETS
    }

    private Context _context;
    private List<HeaderType> listDataHeaders;
    private HashMap<HeaderType, List<Ticket>> listDataChildren;


    public MyTixExpandableListAdapter(Context context) {
        this._context = context;
        listDataHeaders = new ArrayList<HeaderType>();
        for (HeaderType headerType: HeaderType.values()
             ) {
            listDataHeaders.add(headerType);
        }

        listDataChildren = new HashMap<HeaderType, List<Ticket>>();
        for (HeaderType header: listDataHeaders) {
            listDataChildren.put(header, new ArrayList<Ticket>());
        }

    }

    public void addChild(Ticket ticket){
        TicketType type = ticket.getTicketType();
        switch (type){
            case _20_MINUTES:
                listDataChildren.get(HeaderType._20_MINUTE_TICKETS).add(ticket);
                break;
            case _60_MINUTES:
                listDataChildren.get(HeaderType._60_MINUTE_TICKETS).add(ticket);
                break;
            case _120_MINUTES:
                listDataChildren.get(HeaderType._120_MINUTE_TICKETS).add(ticket);
                break;
        }
    }

    public void validateTicket(Ticket ticket){
        // TODO move to validated section
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChildren.get(this.listDataHeaders.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

//        //TODO !!!!
//        final String childText = getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_ticket_item, null);
        }

        TextView txtListChild = (TextView) convertView
                .findViewById(R.id.lblListItem);

        //TODO idontthinkthis'll work. get type, date purchased
        txtListChild.setText(getChild(groupPosition, childPosition).toString());
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChildren.get(this.listDataHeaders.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeaders.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeaders.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle;
        HeaderType header = (HeaderType) getGroup(groupPosition);
        switch (header){
            case VALIDATED_TICKETS:
                headerTitle = _context.getString(R.string.validated_tickets);
                break;
            case _20_MINUTE_TICKETS:
                headerTitle = _context.getString(R.string._20_min_tix);
                break;
            case _60_MINUTE_TICKETS:
                headerTitle = _context.getString(R.string._60_min_tix);
                break;
            case _120_MINUTE_TICKETS:
                headerTitle = _context.getString(R.string._120_min_tix);
                break;
            default:
                headerTitle = "idk";

                //TODO STRING^
        }

//        String headerTitle = getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_headers, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.tvListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        TextView tvNumberTickets = (TextView) convertView.findViewById(R.id.tvNumberTickets);
        tvNumberTickets.setTypeface(null, Typeface.BOLD);

        //TODO this needs to work someday
//        tvNumberTickets.setText(getChildrenCount(groupPosition));

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}