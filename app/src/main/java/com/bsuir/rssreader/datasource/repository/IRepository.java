package com.bsuir.rssreader.datasource.repository;

import com.bsuir.rssreader.datasource.model.RSSModel;
import com.bsuir.rssreader.datasource.specification.SqlSpecification;


import java.util.List;

/**
 * @author Eugene Novik
 */
public interface IRepository {

    void add(RSSModel note);

    void remove(RSSModel note);

    void update(RSSModel note); // Think it as replace for set

    List<RSSModel> query(SqlSpecification specification);
}
