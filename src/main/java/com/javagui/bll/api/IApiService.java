package com.javagui.bll.api;

import com.javagui.gui.model.MovieDTO;

public interface IApiService {
    MovieDTO getMovieByTitle(String title);
}
