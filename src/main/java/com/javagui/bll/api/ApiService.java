package com.javagui.bll.api;

import com.javagui.bll.api.contract.IMovie;
import com.javagui.gui.model.MovieDTO;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;

public class ApiService implements IApiService {
    private static final String API_URL = "http://www.omdbapi.com/";

    /**
     * method to create new instance and configure on the way
     *
     * @return IMovie interface that calls the api
     */
    private IMovie privateBuiltDto() {
        return Feign.builder() // creates new instance to config client
                .requestInterceptor(new ApiKeyRequestInterceptor())
                .decoder(new GsonDecoder()) // parse Json data from API - library added for this
                .logLevel(Logger.Level.FULL)
                .target(IMovie.class, API_URL); // spec target for the client
    }

    @Override
    public MovieDTO getMovieByTitle(String title) {
        IMovie movieCall = privateBuiltDto();
        return movieCall.movieByName(title);
    }
}
