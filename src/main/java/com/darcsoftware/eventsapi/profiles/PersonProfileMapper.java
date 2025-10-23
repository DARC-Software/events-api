package com.darcsoftware.eventsapi.profiles;

import org.apache.ibatis.annotations.*;
import java.util.List;

@Mapper
public interface PersonProfileMapper {

    @SelectProvider(type = PersonProfileSql.class, method = "listPeopleSql")
    @ConstructorArgs({
            @Arg(column = "party_id",           javaType = long.class),
            @Arg(column = "party_display_name", javaType = String.class),
            @Arg(column = "party_slug",         javaType = String.class),
            @Arg(column = "party_type",         javaType = String.class),
            @Arg(column = "first_name",         javaType = String.class),
            @Arg(column = "last_name",          javaType = String.class),
            @Arg(column = "stage_name",         javaType = String.class),
            @Arg(column = "bio",                javaType = String.class),
            @Arg(column = "avatar_url",         javaType = String.class),
            @Arg(column = "instagram",          javaType = String.class),
            @Arg(column = "tiktok",             javaType = String.class),
            @Arg(column = "facebook",           javaType = String.class)
    })
    List<PersonProfileJoinRow> listPeople(
            @Param("q") String q,
            @Param("limit") int limit,
            @Param("offset") int offset
    );

    @SelectProvider(type = PersonProfileSql.class, method = "countPeopleSql")
    long countPeople(@Param("q") String q);
}