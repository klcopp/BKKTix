package hu.ait.karen.bkktix.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import hu.ait.karen.bkktix.MainActivity;
import hu.ait.karen.bkktix.R;
import hu.ait.karen.bkktix.data.Ticket;


public class HistoryRecyclerAdapter
            extends RecyclerView.Adapter<HistoryRecyclerAdapter.ViewHolder>

    {

        private List<Ticket> ticketHistoryList;
        private Context context;

//        private Realm realmTodo;


        public HistoryRecyclerAdapter(Context context /*, Realm realmTodo*/) {
        this.context = context;
//            this.realmTodo = realmTodo;

//        realmTodo = Realm.getDefaultInstance();

//            RealmResults<Todo> todoResult =
//                    realmTodo.where(Todo.class).findAll();

        ticketHistoryList = new ArrayList<Ticket>();

//            for (int i = 0; i < todoResult.size(); i++) {
//                todoList.add(todoResult.get(i));
//            }
    }


        @Override
        public ViewHolder onCreateViewHolder (ViewGroup parent,int viewType){
        View rowView = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.list_history_ticket, parent, false);

        return new ViewHolder(rowView);
    }

        @Override
        public void onBindViewHolder ( final ViewHolder holder, int position){
        Ticket ticket = ticketHistoryList.get(position);
        holder.tvDateUsed.setText(ticket.getDateValidated().toString());
        holder.tvDatePurchased.setText(ticket.getDatePurchased().toString());
    }

        @Override
        public int getItemCount () {
        return ticketHistoryList.size();
    }

//        @Override
//        public void onItemDismiss(int position) {
//            EstimatedPriceTotal.getInstance().subtractFromEstimatedTotalPrice(Double.parseDouble(todoList.get(position).getEstimatedPriceString()));
//
////            realmTodo.beginTransaction();
////            ticketHistoryList.get(position).deleteFromRealm();
////            realmTodo.commitTransaction();
//
//
//            ticketHistoryList.remove(position);
//
////             refreshes the whole list
////            notifyDataSetChanged();
////             refreshes just the relevant part that has been deleted
//
//            notifyItemRemoved(position);
//        }

//        @Override
//        public void onItemMove(int fromPosition, int toPosition) {
//        /*todoList.add(toPosition, todoList.get(fromPosition));
//        todoList.remove(fromPosition);*/
//
//            if (fromPosition < toPosition) {
//                for (int i = fromPosition; i < toPosition; i++) {
//                    Collections.swap(todoList, i, i + 1);
//                }
//            } else {
//                for (int i = fromPosition; i > toPosition; i--) {
//                    Collections.swap(todoList, i, i - 1);
//                }
//            }
//
//
//            notifyItemMoved(fromPosition, toPosition);
//        }

    public void addHistoricalTicket(Ticket ticket) {
//            realmTodo.beginTransaction();
//            _Todo newTodo = realmTodo.createObject(_Todo.class, UUID.randomUUID().toString());
//            newTodo.setTodoText(todoTitle);
//            //set other things here

//            realmTodo.commitTransaction();

        ticketHistoryList.add(0, ticket);
        notifyItemInserted(0);
    }


    public void deleteAll() {
//            realmTodo.beginTransaction();
//            realmTodo.deleteAll();
//            realmTodo.commitTransaction();

        ticketHistoryList.clear();
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {


        private TextView tvDateUsed;
        private TextView tvDatePurchased;


        public ViewHolder(View itemView) {
            super(itemView);

            tvDateUsed = (TextView) itemView.findViewById(R.id.tvDateUsed);
            tvDatePurchased = (TextView) itemView.findViewById(R.id.tvDatePurchased);

        }
    }

//
//        public void closeRealm() {
//            realmTodo.close();
//        }
}

