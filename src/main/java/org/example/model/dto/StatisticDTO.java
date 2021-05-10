package org.example.model.dto;

import lombok.Data;

@Data
public class StatisticDTO {
    private long postsCount;
    private long likesCount;
    private long dislikesCount;
    private long viewsCount;
    private long firstPublication;
}
