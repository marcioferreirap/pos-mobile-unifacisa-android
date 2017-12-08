package com.marcioferreirap.chucknorrisfactsapp.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.marcioferreirap.chucknorrisfactsapp.model.Fact;

import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface FactDao {
    @Insert(onConflict = IGNORE)
    void insertFact(Fact fact);

    @Query("SELECT * FROM Fact ORDER BY value")
    LiveData<List<Fact>> listAllFavorites();

    @Query("SELECT COUNT(*) FROM Fact WHERE id = :id")
    boolean isFavorite(String id);

    @Delete
    void deleteFact(Fact... facts);
}
