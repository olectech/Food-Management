package pl.oltek.solek.foodmanagement;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class ProduktyLista extends ArrayAdapter<Produkt> {
    private Activity context;
    private List<Produkt> produktList;

    public ProduktyLista(Activity context, List<Produkt> produktList){
            super(context, R.layout.list_layout, produktList);
            this.context = context;
            this.produktList = produktList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View listViewItem = inflater.inflate(R.layout.list_layout, null, true);

        TextView textViewNazwa = (TextView)listViewItem.findViewById(R.id.nazwaProdukt);
        TextView textViewData = (TextView)listViewItem.findViewById(R.id.dataProdukt);

        Produkt produkt = produktList.get(position);

        textViewData.setText("Data ważności: "+produkt.produktData);
        textViewNazwa.setText(produkt.produktNazwa);

        return listViewItem;
    }
}
