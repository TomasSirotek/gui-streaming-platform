package com.javagui.bll.api.contract;

import com.javagui.gui.model.MovieDTO;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface IMovie {
    @Headers({"Content-Type: application/json", "apikey: {apikey}"})
    @RequestLine("GET /?t={title}")
    MovieDTO movieByName(@Param("title") String title);
}
