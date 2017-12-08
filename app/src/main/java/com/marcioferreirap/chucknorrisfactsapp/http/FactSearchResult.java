package com.marcioferreirap.chucknorrisfactsapp.http;

import com.google.gson.annotations.SerializedName;
import com.marcioferreirap.chucknorrisfactsapp.model.Fact;

import java.util.List;


class FactSearchResult {
    @SerializedName("Search")
    List<Fact> facts;
}
