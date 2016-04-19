package id.ac.itb.securesms;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Rakhmatullah Yoga S on 17/04/2016.
 */
public class SmsAdapter extends RecyclerView.Adapter<SmsAdapter.SmsViewHolder> {
    public boolean isOutbox = false;
    private List<Sms> smsList;

    @Override
    public SmsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sms_list_row, parent, false);

        return new SmsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SmsViewHolder holder, int position) {
        final Sms sms = smsList.get(position);
        holder.address.setText(sms.getAddress());
        holder.body.setText(sms.getBody());
        holder.datetime.setText(sms.getDatetime());
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, MessageDetailActivity.class);
                intent.putExtra(MessageDetailActivity.SENDER, sms.getAddress());
                intent.putExtra(MessageDetailActivity.BODY, sms.getBody());
                intent.putExtra(MessageDetailActivity.IS_OUTBOX, isOutbox);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return smsList.size();
    }

    public SmsAdapter(List<Sms> smsList) {
        this.smsList = smsList;
    }

    public class SmsViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public TextView address, datetime, body;

        public SmsViewHolder(View view) {
            super(view);
            mView = view;
            address = (TextView) view.findViewById(R.id.address);
            body = (TextView) view.findViewById(R.id.body);
            datetime = (TextView) view.findViewById(R.id.datetime);
        }
    }
}
