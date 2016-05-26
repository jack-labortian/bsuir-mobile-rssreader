package com.bsuir.rssreader.datasource.specification;

/**
 * @author Eugene Novik
 */
public class FindAllNotesSpecification implements SqlSpecification{
    @Override
    public String toSqlClauses() {
        return "select * from items order by title";
    }
}
