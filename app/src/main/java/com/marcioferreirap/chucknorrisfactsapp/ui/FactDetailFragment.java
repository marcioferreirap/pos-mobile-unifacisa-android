package com.marcioferreirap.chucknorrisfactsapp.ui;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.marcioferreirap.chucknorrisfactsapp.R;
import com.marcioferreirap.chucknorrisfactsapp.dao.AppDatabase;
import com.marcioferreirap.chucknorrisfactsapp.dao.FactDao;
import com.marcioferreirap.chucknorrisfactsapp.databinding.FragmentFactDetailBinding;
import com.marcioferreirap.chucknorrisfactsapp.http.FactsParser;
import com.marcioferreirap.chucknorrisfactsapp.model.Fact;

import org.parceler.Parcels;

import java.io.IOException;

public class FactDetailFragment extends Fragment {

    public static final String EXTRA_FACT = "fact";
    public static final String DETAIL_FRAGMENT = "detailFragment";

    FragmentFactDetailBinding binding;
    FactDao dao;
    ShareActionProvider shareActionProvider;


    public FactDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        setHasOptionsMenu(true);
        dao = AppDatabase.getInMemoryDatabase(getContext()).factDao();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final Fact fact = Parcels.unwrap(getArguments().getParcelable(EXTRA_FACT));
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_fact_detail, container, false);
        binding.setFact(fact);
        binding.fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavorite();
            }
        });

        new FactTask().execute();
        return binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
/*        if (getResources().getBoolean(R.bool.smartphone)) {
            ((AppCompatActivity) getActivity()).setSupportActionBar(binding);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }*/
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_chuck_facts, menu);

        // Locate MenuItem with ShareActionProvider
        MenuItem item = menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);



    }


    public static FactDetailFragment newInstance(Fact f) {
        Bundle params = new Bundle();
        params.putParcelable(EXTRA_FACT, Parcels.wrap(f));

        FactDetailFragment fd = new FactDetailFragment();
        fd.setArguments(params);

        return fd;
    }

    public void toggleFavorite() {
        final Fact fact = binding.getFact();
        boolean isFavorite = dao.isFavorite(fact.id);
        if (isFavorite) {
            dao.deleteFact(fact);
            Toast.makeText(getActivity(), R.string.msg_favorite_removed, Toast.LENGTH_SHORT).show();
            updateFabIcon(dao.isFavorite(fact.id));
        } else {
            dao.insertFact(fact);
            Toast.makeText(getActivity(), R.string.msg_favorite_added, Toast.LENGTH_SHORT).show();
            updateFabIcon(dao.isFavorite(fact.id));
        }

/*        // Animate
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(
                binding.fab, View.SCALE_X, 0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(
                binding.fab, View.SCALE_Y, 0f);
        scaleX.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationRepeat(Animator animation) {
                super.onAnimationRepeat(animation);
                updateFabIcon(dao.isFavorite(movie.imdbId));
            }
        });
        scaleY.setRepeatMode(ValueAnimator.REVERSE);
        scaleX.setRepeatCount(1);
        scaleY.setRepeatCount(1);
        AnimatorSet set = new AnimatorSet();
        set.playTogether(scaleX, scaleY);
        set.start();*/
    }

    private void updateFabIcon(boolean isFavorite){
        binding.fabFav.setImageResource(isFavorite ? R.drawable.ic_fab_fav : R.drawable.ic_fab_no_fav);
    }

    private void setShareIntent(Fact f) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, f.value+" <- Essa lezera");
        sendIntent.setType("text/plain");
        shareActionProvider.setShareIntent(sendIntent);
    }

    class FactTask extends AsyncTask<String, Void, Fact> {

        @Override
        protected Fact doInBackground(String... params) {
            try {
                return FactsParser.getRamdom();

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Fact f) {
            super.onPostExecute(f);
            if (f != null) {
                binding.setFact(f);
                setShareIntent(f);


            }
        }
    }





}
