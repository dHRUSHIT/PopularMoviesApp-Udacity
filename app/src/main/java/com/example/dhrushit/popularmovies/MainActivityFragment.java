package com.example.dhrushit.popularmovies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    Movie movies[] = new Movie[100];
    ImageListAdapter movieAdapter;
    String[] posterList;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] initStrings=null;// = new String[100];
        movieAdapter = new ImageListAdapter(getActivity(),initStrings);
        View rootView = inflater.inflate(R.layout.fragment_main,container,false);
        GridView gridView = (GridView) rootView.findViewById(R.id.imageGridView);
        gridView.setAdapter(movieAdapter);
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    private void updateMovies() {
        FetchMoviesTask moviesTask = new FetchMoviesTask();
        moviesTask.execute();
    }

    public class ImageListAdapter extends BaseAdapter{
        private final String TAG = this.getClass().getSimpleName();
        private Context context;
        private LayoutInflater inflater;
        ImageView imageView;
        private String[] imageUrls;
        private int mResource;

        public ImageListAdapter(Context context,String[] imageUrls){


            this.context = context;
            this.imageUrls = imageUrls;

            inflater = LayoutInflater.from(context);

        }

        @Override
        public int getCount() {
            if(imageUrls != null)
                return imageUrls.length;
            else
                return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position,View convertView,ViewGroup parent){
            Log.v(TAG,"get view method");
            if(null == convertView){
                imageView = new ImageView(context);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);


            }else{
                imageView = (ImageView) convertView;
            }

            Picasso.with(context)
                    .load(movies[position].getPoster_path())
                    .error(R.drawable.loading)
                    .into(imageView);

            return imageView;
        }
    }

    public class FetchMoviesTask extends AsyncTask<Void, Void, String[]> {

        private final String TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String[] strings) {
            if(strings != null){

                movieAdapter.imageUrls = strings;
                movieAdapter.notifyDataSetChanged();
            }
        }

        @Override
        protected String[] doInBackground(Void... params) {

            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            String replyString = null;
            String key = getResources().getString(R.string.api_key);
            try{
                final String BASE_URI = "https://api.themoviedb.org/3/movie/";

                final String SORT_TYPE = "popular";
                String page_no = "1";

                Uri builtUri = Uri.parse(BASE_URI).buildUpon()
                        .appendPath(SORT_TYPE)
                        .appendQueryParameter("api_key",key)
                        .appendQueryParameter("language", "en-US")
                        .appendQueryParameter("page",page_no)
                        .build();
                URL url = new URL(builtUri.toString());

                Log.v(TAG,"Built Url : " + builtUri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if(inputStream == null)
                    replyString = null;
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while((line=reader.readLine()) != null){
                    buffer.append(line+"\n");
                }

                replyString = buffer.toString();
                Log.v(TAG,"replyString : "+buffer.toString());

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if(urlConnection != null){
                    urlConnection.disconnect();
                }
                if(reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG,"Error closing reader : " + e);
                    }
                }
            }

            try{
                return getMovieDataFromJson(replyString);
            }catch (JSONException e){
                Log.e(TAG,e.getMessage(),e);
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            return null;
        }

        private String[] getMovieDataFromJson(String replyString) throws JSONException, MalformedURLException {
            Log.v(TAG,"json string : "+replyString);

            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String ORIGINAL_TITLE = "original_title";
            final String ORIGINAL_LANGUAGE = "original_language";
            final String TITLE = "title";
            final String BACKDROP_PATH = "backdrop_path";
            final String OVERVIEW = "overview";
            final String RELEASEDATE = "release_date";
            final String BASE_IMAGE_URI = "http://image.tmdb.org/t/p/";
            final String IMAGE_SIZE = "w342";
            final String ISADULT = "adult";
            final String CONTAINS_VIDEO = "video";
            final String GENRES = "genre_ids";
            final String ID = "id";
            final String VOTE_COUNT = "vote_count";
            final String POPULARITY = "popularity";
            final String AVG_VOTE = "vote_average";


            JSONObject object = new JSONObject(replyString);
            JSONArray movieArray = object.getJSONArray(RESULTS);
            int size = movieArray.length();

            HttpURLConnection urlImageConnection = null;
            String[] resultStrs = new String[movieArray.length()];
            for(int i=0;i<size;i++){
                Movie temp_movie = new Movie();
                JSONObject jsonMovieObject = movieArray.getJSONObject(i);

                String poster_path = jsonMovieObject.getString(POSTER_PATH);
                String poster_link = BASE_IMAGE_URI+IMAGE_SIZE+poster_path;
                temp_movie.setPoster_path(poster_link);

                temp_movie.setAdult(jsonMovieObject.getBoolean(ISADULT));
                temp_movie.setBackdrop_path(jsonMovieObject.getString(BACKDROP_PATH));
                temp_movie.setId(jsonMovieObject.getInt(ID));
                temp_movie.setOriginal_language(jsonMovieObject.getString(ORIGINAL_LANGUAGE));
                temp_movie.setOriginal_title(jsonMovieObject.getString(ORIGINAL_TITLE));
                temp_movie.setOverview(jsonMovieObject.getString(OVERVIEW));
                temp_movie.setReleaseDate(jsonMovieObject.getString(RELEASEDATE));
                temp_movie.setVote_avg(jsonMovieObject.getDouble(AVG_VOTE));




                Log.v(TAG,"Built Uri : " + poster_link);
                movies[i] = temp_movie;
            }


            return resultStrs;
        }
    }
}
