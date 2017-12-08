package com.marcioferreirap.chucknorrisfactsapp.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.marcioferreirap.chucknorrisfactsapp.R;
import com.marcioferreirap.chucknorrisfactsapp.databinding.ActivityFactDetailBinding;
import com.marcioferreirap.chucknorrisfactsapp.model.Fact;

import org.parceler.Parcels;

public class FactDetailActivity extends AppCompatActivity {

    public static final String EXTRA_FACT = "fact";
    public static final String DETAIL_FRAGMENT = "detailFragment";

    ActivityFactDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(
                this, R.layout.activity_fact_detail);

        Fact fact = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_FACT));

        if (savedInstanceState == null) {
            FactDetailFragment mdf = FactDetailFragment.newInstance(fact);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_detail, mdf, DETAIL_FRAGMENT)
                    .commit();
        }
    }

}
