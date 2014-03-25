package app.philm.in.state.mappers;

import com.uwetrottmann.tmdb.entities.Credits;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import app.philm.in.model.Person;
import app.philm.in.model.PhilmMovieCredit;
import app.philm.in.state.MoviesState;

public class TmdbCrewEntityMapper extends BaseEntityMapper<Credits.CrewMember, Person> {

    @Inject
    public TmdbCrewEntityMapper(MoviesState state) {
        super(state);
    }

    @Override
    public Person map(Credits.CrewMember entity) {
        Person item = getEntity(String.valueOf(entity.id));

        if (item == null) {
            // No item, so create one
            item = new Person();
        }

        // We already have a movie, so just update it wrapped value
        item.setFromTmdb(entity);
        putEntity(item);

        return item;
    }

    public List<PhilmMovieCredit> mapCredits(List<Credits.CrewMember> entities) {
        final ArrayList<PhilmMovieCredit> credits = new ArrayList<>(entities.size());
        for (Credits.CrewMember entity : entities) {
            credits.add(new PhilmMovieCredit(map(entity), entity.job, entity.department));
        }
        Collections.sort(credits);
        return credits;
    }

    @Override
    Person getEntity(String id) {
        return mMoviesState.getPeople().get(id);
    }

    @Override
    void putEntity(Person entity) {
        mMoviesState.getPeople().put(String.valueOf(entity.getTmdbId()), entity);
    }
}